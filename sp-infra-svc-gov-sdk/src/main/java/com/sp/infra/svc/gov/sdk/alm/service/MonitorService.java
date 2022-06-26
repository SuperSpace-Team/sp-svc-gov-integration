package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.*;
import com.sp.infra.svc.gov.sdk.alm.context.InvokeContext;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.util.ExpressionHelper;
import com.sp.infra.svc.gov.sdk.command.cfg.Node;
import com.sp.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.util.CollectionUtils;
import com.sp.infra.svc.gov.sdk.util.StringUtils;
import com.sp.infra.svc.gov.sdk.util.TraceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author luchao
 * @date 2021/4/25 8:31 下午
 */
public class MonitorService {
	private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

	private MonitorGlobalContext context;
	private FusingProxyService fusingProxyService;

	public MonitorService(MonitorGlobalContext ctx, FusingProxyService fps) {
		context = ctx;
		fusingProxyService = fps;
	}

	
	private boolean shouldFetchData(InvokeContext ic) {
		// 这个地方不会有-100， 因为这个方法必须是拦截后才能进到这。
		// 而拦截意味着有配置信息， 有配置信息就不可能是-100.
		if (context.getCurrentVersion() == SdkCommonConstant.PG_VERSION_EMPTY_VERSION){
			logger.error("no configuration found. {}", ic.getClzEntry());
			return false;
		}
		
		if (context.ifMonitorStoped()) {
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("stop monitor data.");
			return false;
		}
		return true;
	}
	
	/**
	 * 尝试找到是否是要监控的方法。
	 * 此处不能使用ic.getClzEntry(). 因为clzentry是调用的写法。
	 * 比如parent类有service方法， child类没有，ic中的数据如下：
	 * ClzEntry: child.service
	 * DefClzEntry: parent.service 
	 * 
	 * 但是其他类，可以使用 child.service进行调用。
	 * 
	 * 如果用child.service配置，然后此处用clzentry进行匹配，问题是：
	 * 1）。 如果child.service 和 parent.service 都做了配置， 没法识别出来他们实际是同一个调用。 会造成1个方法调用， 处理了2遍。
	 * 2). 假如child.service方法存在，即，对parent.service做了重置，内部调用了super.service().  那么， 当执行到 child.service的入口时， 
	 *     会被拦截处理。然后，当执行到 parent.service时，也会被拦截一次。
	 * 
	 * 
	 * 
	 * 所以方法配置的规则是，必须根据方法定义的类进行配置。
	 * 此处只根据方法定义的类进行匹配。
	 * 
	 * @param ic
	 * @return
	 */
	private Map<Integer, BizEntryRule> getRules(InvokeContext ic) {
		return context.getRules(ic.getDefClzEntry());
	}
	
	/**
	 * 在方法执行前 抓取 in 的参数日志。 返回值为是否需要抓取。
	 * 
	 * @param ic
	 * @return
	 */
	public boolean preFetchData(InvokeContext ic) {
		logger.info("enter preFetchData. {}, {}", ic.getClzEntry(), ic.getDefClzEntry());

		if (! shouldFetchData(ic))
			return false;
		
		long start = System.currentTimeMillis(); // 记录执行开始时间。
		
		Object[] args = ic.getArgs();


		// 读取所有参数，构建供处理的data map，并保存。
		EvaluationContext dataCtx = new StandardEvaluationContext();
		dataCtx.setVariable(SdkCommonConstant.PG_LOG_RULE_CLASS, ic.getTargetClass().getName());
		for (int i = 0; i < args.length; i++) {
			dataCtx.setVariable(SdkCommonConstant.PARA_PREFIX + (i + 1), args[i]);
			ic.getInputData().put(SdkCommonConstant.PARA_PREFIX + (i + 1), args[i]);
		}

		// 根据 监控入口的 key，找到其所有配置信息。找到哪些业务维度，包含这个方法的 node。
		Map<Integer, BizEntryRule> rulesMap = getRules(ic);
		if (rulesMap != null) {
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("entry found. {}", ic.getDefClzEntry());
			// 根据类， 找符合条件的 biz 的node/tnode。并生成日志。
			findMatchNodeAndGenLog(ic, rulesMap, start, ic.getDefClzEntry(), dataCtx);
		} else {
			// 进行了拦截，却没有找到node。 这个不合理。需要实际输出 信息， 而不是仅debug。
			outputDebugIfNotFound(ic.getDefClzEntry());
		}

		if (ic.getBizLogCmdList().size() == 0) {
			logger.info("exit preFetchData. no node found for entry {}", ic.getDefClzEntry());
		}
		else {
			logger.info("exit preFetchData. class {}, found biz log size {}", ic.getDefClzEntry(), ic.getBizLogCmdList().size());
		}
		
		// 转换 节点 在业务执行后处理。
		return ic.getBizLogCmdList().size() > 0;
	}

	private void outputDebugIfNotFound(String clzMethod) {
		logger.info("no entry found for {}", clzMethod);
		Map<String, Map<Integer, BizEntryRule>> entryMap = context.getRulesData();
        for (Entry<String, Map<Integer, BizEntryRule>> entry : entryMap.entrySet()) {
        	Map<Integer, BizEntryRule> emap = entry.getValue();
        	logger.info("entry '{}' contains biz-factor: {}, values: {}", entry.getKey(), emap.keySet().toString(), entry.getValue());
		}
	}
	
	/**
	 * 生成方法调用前的inlog
	 *  生成的inlog可能是个list，会根据 关键字的数目， 一一匹配。
	 * 
	 * @param ic
	 * @param node
	 * @param expCmd
	 * @param dataCtx
	 * @param entry
	 */
	private void generatePreMonitorLog(InvokeContext ic, Node node, NodeExpressionCommand expCmd, EvaluationContext dataCtx, String entry) {
		Integer bizCode = node.getBizCode();
		if (logger.isDebugEnabled()) //NOSONAR
			logger.debug("node {} is match for method {} ", node.getCode(), entry);
		// 精简模式下， 不读取 入参日志。
		if (!context.ifSimpleMode(bizCode)) {
			String exp = node.getInLogExp();
			if (! StringUtils.isEmpty(exp)) {
				Expression expression = expCmd.getInExp();
				if (expression == null) {
					// 表达式不为空， 但是  expression却是null。 
					// 应该不可能出现， 因为 configservice 中，如果  对表达式 的编译 出错，不会更新版本。
					logger.error("inLog expression is null. {} , {}", node.getCode(), exp);
				} else { 
					List<String> loglist = ExpressionHelper.fetchData(expression, dataCtx);
					if (logger.isDebugEnabled()) { //NOSONAR
						if (loglist == null)
							loglist = Collections.EMPTY_LIST;
						StringBuilder sb = new StringBuilder();
						sb.append("in log list. size: ").append(loglist.size()).append("  keys:");
						for (String k : loglist) {
							sb.append(k).append(" , ");
						}
						logger.debug(sb.toString());
					}
					if (! CollectionUtils.isEmpty(loglist))
						ic.getNodeInLogMap().put(node.getCode(), loglist);
				}
			}
		}
		ic.getBizNodeMap().put(bizCode, node);
	}
	
	/**
	 * 查看一个 entry是否匹配。如果匹配，会生成inlog<br/>
	 * 
	 * 
	 * @param ic
	 * @param rulesMap
	 * @param start
	 * @param entry
	 * @param dataCtx
	 * @return
	 */
	private void findMatchNodeAndGenLog(InvokeContext ic, Map<Integer, BizEntryRule> rulesMap, long start, String entry,
			EvaluationContext dataCtx) {

		// 逐个处理所有业务维度的监控 节点。
		for (Entry<Integer, BizEntryRule> ruleEntry : rulesMap.entrySet()) {
			Integer bizCode = ruleEntry.getKey();
			boolean bizMatch = false;
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("check with biz: {}", bizCode);
			
			// 每个BizEntryRule是一条业务维度链路。
			BizEntryRule rule = ruleEntry.getValue();

			// 无论 普通节点还是转换节点， 每个factor只能有一个。所以，如果之前在这个factor上匹配到了。 就不再匹配
			// 换句话说， 必须仔细配置match的表达式，保证此处只能有一个node匹配成功 。
			for (Node node : rule.getNodeList()) {
				NodeExpressionCommand expCmd = context.getExpression(SdkCommonConstant.PG_NODE_TYPE_MONITOR,
						node.getCode());
				// 只看第一个匹配的。 多个匹配的情况， 由用户自行 保证 不能存在。
				if (match(expCmd, dataCtx)) {
					// 如果找到匹配的node，直接生成 inlog
					generatePreMonitorLog(ic, node, expCmd, dataCtx, entry);
					bizMatch = true;
					break;
				}
			}

			// 对转换日志，记录其是否匹配。
			for (TransformNode node : rule.getTransformNodeList()) {
				NodeExpressionCommand expCmd = context.getExpression(SdkCommonConstant.PG_NODE_TYPE_TRANSFORM,
						node.getCode());
				if (match(expCmd, dataCtx)) {
					if (logger.isDebugEnabled()) //NOSONAR
						logger.debug("transform node {} matches for method {} ", node, entry);
					// 只对第一个匹配到的转换节点记录。其他的不管。用户需要确保不会发生多重 命中的情况
					ic.getBizTransMap().put(bizCode, node);
					bizMatch = true;
					break;
				}
			}
			
			// 如果本 bizfactor找到了匹配的node/tnode，则生成一个 日志vo
			if (bizMatch) {
				if (logger.isDebugEnabled()) //NOSONAR
					logger.debug("biz {} match this call {}", bizCode, entry);
				
				MonitorLogMessage lmc = new MonitorLogMessage(SdkCommonConstant.LOG_TYPE_MONITOR);
				lmc.setAppId(context.getAppId());
				lmc.setHostName(context.getPgConfig().getHostName());
				lmc.setIp(context.getPgConfig().getIp());
				lmc.setThreadName(Thread.currentThread().getName());
				lmc.setCfgVersion(context.getCurrentVersion());
				lmc.setSdkVersion(context.getSdkVersion());
				lmc.setTimeStamp(start);
				lmc.setBizCode(bizCode);
				ic.getBizLogCmdList().add(lmc);
				
			} else {
				if (logger.isDebugEnabled())//NOSONAR
					logger.debug("biz {} does not match this call {}", bizCode, entry);
			}
		}
	}

	/**
	 * 判断是否可以匹配  某个node/tnode。
	 * 
	 * @param expCmd
	 * @param dataCtx
	 * @return
	 */
	private boolean match(NodeExpressionCommand expCmd, EvaluationContext dataCtx) {
		Expression expression = null;

		// 判断是否属于本业务维度。match expression
		expression = expCmd.getMatchExp();
		try {
			// 执行match expression。判断是否匹配本业务维度
			if (expression != null) {
				Object retObj = expression.getValue(dataCtx);
				if (!(retObj instanceof Boolean)) {
					// 返回值类型不是 boolean， 出错了。不做监控了。
					logger.error("match expression {}  is invalid, not a Boolean.", expression.getExpressionString());
					return false;
				}
				if (!(Boolean) retObj)
					return false;
			}

			// 执行seq match expression，判断是否属于本业务维度 的本 seq 节点。
			expression = expCmd.getSeqMatchExp();
			if (expression == null) {
				return true;
			}
			Object retObj = expression.getValue(dataCtx);
			if (retObj instanceof Boolean)
				return (Boolean) retObj;
			// 返回值类型不是 boolean， 出错了。不做监控了。
			logger.error("seq match expression {}  is invalid, not a Boolean.", expression.getExpressionString());
		} catch (EvaluationException ee) {
			if (expression == null)
				logger.error("expression is null {}", expCmd.getNodeCode());
			else
				logger.error("expression syntax error {}", expression.getExpressionString());
		} catch (Exception e) {
			logger.error("system error. ", e);
		}
		return false;
	}

	/**
	 * 生成一个node的 日志vo
	 * 
	 * @param ic
	 * @param lmc
	 * @param node
	 * @param dataCtx
	 * @param e
	 * @return
	 */
	private boolean generateNodeLog(InvokeContext ic, MonitorLogMessage lmc, Node node, EvaluationContext dataCtx, Throwable e) {
		if (logger.isDebugEnabled()) //NOSONAR
			logger.debug("generate log for biz: {} with node {}", lmc.getBizCode(), node.getCode());

		// 处理监控 节点。.
		List<String> inlist = ic.getNodeInLogMap().get(node.getCode());
		Map<String, List<String>> outMap = generatePostMonitorLog(lmc.getBizCode(), node, dataCtx, e != null);
		// 返回null表示 发生错误。那这个 lmc不再继续处理了。 废弃
		if (outMap == null)
			return false;

		// in 和 out的数据比对。并构建 日志 MonitorLogCommand。
		List<MonitorLogUnitCommand> monitorLogList = mergeMonitorLog(node, inlist, outMap, e != null);
		lmc.setMonitorLogList(monitorLogList);
		return true;
	}
	
	
	/**
	 * 方法执行后被调用。 用于生成全部的日志。
	 * 
	 * 
	 * @param ic
	 */
	public void postHandle(InvokeContext ic) {
		if (logger.isDebugEnabled())
			logger.debug("enter the postHandle. {}, {}", ic.getClzEntry(), ic.getDefClzEntry());

		long end = System.currentTimeMillis(); // 记录执行结束时间。
		
		Object retobj = ic.getReturned();
		Throwable e = ic.getThrowable();

		// 读取所有参数，构建供处理的data map。
		EvaluationContext dataCtx = new StandardEvaluationContext();
		dataCtx.setVariable(SdkCommonConstant.PG_LOG_RULE_CLASS, ic.getTargetClass().getName());
		dataCtx.setVariable(SdkCommonConstant.PG_LOG_RULE_IN, ic.getInputData());
		dataCtx.setVariable(SdkCommonConstant.PG_LOG_RULE_OUT, retobj);
		dataCtx.setVariable(SdkCommonConstant.PG_LOG_RULE_EXCEPTION, e);

		// 对每一个业务维度进行处理。
		// 每一次调用，在逻辑上，只可能属于一条业务链路中的一个node/Tnode。
		// 所以直接找到一个匹配的node 和tnode处理即可。
		for (MonitorLogMessage lmc : ic.getBizLogCmdList()) {
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("prepare to generate a message for biz: {}" , lmc.getBizCode());

			boolean found = true;
			
			// 检查是否有适用的 node。
			Node node = ic.getBizNodeMap().get(lmc.getBizCode());
			if (node != null) {
				if (! generateNodeLog(ic, lmc, node, dataCtx, e))
					continue;
			} else {
				// 有可能找不到。 因为getBizLogCmdList  中的日志， 有可能仅仅是由 TransformNode 引入的。
				found = false;
			}

			TransformNode tnode = ic.getBizTransMap().get(lmc.getBizCode());
			if (tnode != null) {
				if (logger.isDebugEnabled()) //NOSONAR
					logger.debug("generate log for biz: {} with t node {}", lmc.getBizCode(), tnode.getCode());
				// 处理转换节点
				NodeExpressionCommand expCmd = context.getExpression(SdkCommonConstant.PG_NODE_TYPE_TRANSFORM, tnode.getCode());
				List<TransformLogUnitCommand> retlist = ExpressionHelper.generateTransformLog(expCmd, tnode.getCode(), dataCtx);
				if (! CollectionUtils.isEmpty(retlist))
					lmc.setTransformLogList(retlist);
			} else {
				// node 和tnode都找不到，这不可能出现。 一定是bug了。
				if (! found) {
					logger.error("cannot find node for biz: {}" , lmc.getBizCode());
					continue;
				}
			}

			// 计算耗费时间。
			end -= lmc.getTimeStamp();
			lmc.setElaspedTime(end);

			// 加入发送队列
			if (CollectionUtils.isEmpty(lmc.getMonitorLogList())
					&& CollectionUtils.isEmpty(lmc.getTransformLogList())) {
				logger.error("no message generated for biz {}" , lmc.getBizCode());
			} else {
				logger.info("generate a monitor/transform message for biz: {}, size:{}", lmc.getBizCode(), lmc.getMonitorLogList().size());
				// 确定要发送日志了，再填写Skywalking的trace信息
				// 不在pre那地方填：1）有可能不发送。 2）有可能sw数据还没生成。
				TraceUtil.fillSkyWalkingInfo(lmc);
				fusingProxyService.addLog(lmc);
			}
		}
	}

	/**
	 * 方法执行后的 监控日志字符串的生成。  指返回值日志或者异常日志。
	 * 返回null表示错误。不再处理。 
	 * 
	 * 
	 * @param bizCode
	 * @param node
	 * @param dataCtx
	 * @param exception
	 * @return
	 */
	private Map<String, List<String>> generatePostMonitorLog(Integer bizCode, Node node, EvaluationContext dataCtx,
			boolean exception) {
		Map<String, List<String>> ret = new HashMap<>();
		NodeExpressionCommand expCmd = context.getExpression(SdkCommonConstant.PG_NODE_TYPE_MONITOR, node.getCode());
		if (expCmd == null) {
			logger.error("no expression cache found . {}" , node.getCode());
			return null;
		}
		List<String> resultList = new ArrayList<>();

		List<String> keyList = getBizKeyList(bizCode, expCmd, node, dataCtx);
		if (keyList == null)
			return null;
		ret.put(SdkCommonConstant.PG_LOG_MAP_KEY_BIZKEY, keyList);


		// 精简模式不读取 返回值和异常日志。
		if (!context.ifSimpleMode(bizCode)) {
			Expression expression = null;

			// 找到cache 中的out/exception expression，如果没有要新建并放入cache。
			if (exception) {
				expression = expCmd.getErrorExp();
			} else {
				expression = expCmd.getOutExp();
			}

			// 构建数据。并生成 command
			if (expression != null) {
				resultList = ExpressionHelper.fetchData(expression, dataCtx);
				if (resultList == null)
					return null;
			}
		}
		if (logger.isDebugEnabled()) //NOSONAR
			logger.debug("get the post process log: {}" , resultList);

		ret.put(SdkCommonConstant.PG_LOG_MAP_KEY_RESULT, resultList);
		return ret;
	}
	
	/**
	 * 根据业务关键字表达式， 取得 关键字列表。
	 * 返回null表示错误。不再处理。 
	 * 
	 * @param expCmd
	 * @param node
	 * @param dataCtx
	 * @return
	 */
	private List<String> getBizKeyList(Integer bizCode, NodeExpressionCommand expCmd,Node node,EvaluationContext dataCtx) {
		Expression expression = expCmd.getKeyExp();
		if (expression == null) {
			logger.error("key expression is null. {}", node.getCode());
			return null; //NOSONAR
		}
		// 构建数据。并生成 command
		List<String> keyList = ExpressionHelper.fetchData(expression, dataCtx);
		if (keyList == null)
			return null; //NOSONAR

		if (keyList.isEmpty()) {
			logger.error("no key generated. for biz: {}", bizCode);
			return null; //NOSONAR
		}
		
		if (logger.isDebugEnabled()) //NOSONAR
			logger.debug("get the key list : {}", keyList);
		return keyList;
	}

	/**
	 * 以业务关键字为准， 合并key，in，out，exception。构建监控日志command列表。
	 * 因为4个list的size可能不同。所以用key的size为准。
	 * 
	 * 
	 * @param node
	 * @param inList
	 * @param resultMap
	 * @param error
	 * @return
	 */
	private List<MonitorLogUnitCommand> mergeMonitorLog(Node node, List<String> inList, Map<String, List<String>> resultMap,
			boolean error) {
		List<MonitorLogUnitCommand> ret = new ArrayList<>();
		List<String> keyList = resultMap.get(SdkCommonConstant.PG_LOG_MAP_KEY_BIZKEY);
		List<String> resultList = resultMap.get(SdkCommonConstant.PG_LOG_MAP_KEY_RESULT);
		boolean inlogCopy = inList != null && inList.size() == 1; // 入参日志是 复制模式
		boolean resultCopy = resultList != null && resultList.size() == 1; // 结果日志是 复制模式
		int inlogSize = inList != null ? inList.size() : 0;
		int resultlogSize = resultList != null ? resultList.size() : 0;

		for (int i = 0; i < keyList.size(); i++) {
			MonitorLogUnitCommand mlc = new MonitorLogUnitCommand();
			ret.add(mlc);
			mlc.setCode(node.getCode());
			mlc.setKey(keyList.get(i));
			if (! CollectionUtils.isEmpty(inList)) {
				if (inlogCopy) {
					mlc.setInLog(inList.get(0));
				} else {
					if (i < inlogSize) {
						mlc.setInLog(inList.get(i));
					}
				}
			}
			if (! CollectionUtils.isEmpty(resultList)) {
				if (resultCopy) {
					if (error)
						mlc.setExceptionLog(resultList.get(0));
					else
						mlc.setOutLog(resultList.get(0));
				} else {
					if (i < resultlogSize) {
						if (error)
							mlc.setExceptionLog(resultList.get(i));
						else
							mlc.setOutLog(resultList.get(i));
					}
				}
			}
		}
		return ret;
	}

}
