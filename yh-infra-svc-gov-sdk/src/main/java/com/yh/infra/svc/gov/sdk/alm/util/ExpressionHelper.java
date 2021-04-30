package com.yh.infra.svc.gov.sdk.alm.util;

import com.yh.infra.svc.gov.sdk.alm.command.NodeExpressionCommand;
import com.yh.infra.svc.gov.sdk.alm.command.TransformLogUnitCommand;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.CollectionUtils;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;

import java.util.*;

/**
 * 用于根据表达式，从参数中抓取数据。
 *
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:32 下午
 */
public class ExpressionHelper {
	private static final Logger logger = LoggerFactory.getLogger(ExpressionHelper.class.getName());

	private ExpressionHelper(){
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 读取数据，并生成转换 日志的列表。
	 * 
	 * 
	 * @param expCmd
	 * @param tNodeCode
	 * @param dataCtx
	 * @return
	 */
	public static List<TransformLogUnitCommand> generateTransformLog(NodeExpressionCommand expCmd, int tNodeCode, EvaluationContext dataCtx) {
		// 找到cache 中的src expression，
		Expression expression = expCmd.getSrcExp();
		if (expression == null) {
			logger.error("src expression is null. " + tNodeCode);
			return Collections.emptyList();
		}
		// 生成src 数据
		List<String> srcList = fetchData(expression, dataCtx);
		if (CollectionUtils.isEmpty(srcList))
			return Collections.emptyList();

		if (logger.isDebugEnabled()) {
			//NOSONAR
			logger.debug("fetched the src list. {}", srcList);
		}
			
		
		// 找到cache 中的target expression，
		expression = expCmd.getTargetExp();
		if (expression == null) {
			logger.error("target expression is null. " + tNodeCode);
			return Collections.emptyList();
		}
		List<String> targetList = fetchData(expression, dataCtx);
		if (targetList == null)
			return Collections.emptyList();
		if (logger.isDebugEnabled()) {
			//NOSONAR
			logger.debug("fetched the target list. {}", targetList);
		}

		// 构建数据。并生成 command
		return generateTransformCommand(tNodeCode, srcList, targetList);
	}
	/**
	 * 几种情况：
	 * 1. src=1， target=n
	 *    结果是n， 每个中的src 相同。
	 * 2. src=n，target=1
	 *    结果是n，每个中的target相同。   
	 * 3. src=m，target=n，  
	 *    结果是m、n中的大的一个，比如n
	 *    前m个结果的src从srclist中读取， target从targetlist中读取。
	 *    后n-m个结果。src为空，target从targetlist读取。
	 *    
	 * 第三种情况实际是不合理的情况。
	 *    
	 * @param tNodeCode
	 * @param srcList
	 * @param targetList
	 * @return
	 */
	private static List<TransformLogUnitCommand> generateTransformCommand(int tNodeCode,List<String> srcList, List<String> targetList) {
		List<TransformLogUnitCommand> ret = new ArrayList<>();
		int size = srcList.size() > targetList.size() ? srcList.size() : targetList.size();  //NOSONAR
		for (int i = 0; i < size; i++) {
			TransformLogUnitCommand tlc = new TransformLogUnitCommand();
			ret.add(tlc);
			tlc.setCode(tNodeCode);
			if (srcList.size() == 1) {
				tlc.setSrcKey(srcList.get(0));
			} else {
				if (i < srcList.size())
					tlc.setSrcKey(srcList.get(i));
			}

			if (targetList.size() == 1) {
				tlc.setTargetKey(targetList.get(0));
			} else {
				if (i < targetList.size())
					tlc.setTargetKey(targetList.get(i));
			}
		}
		if ((srcList.size() != targetList.size()) && (srcList.size() > 1) && (targetList.size() > 1)) {
			//针对情况3. 需要写warn日志。
			logger.warn("abnormal situation 3. the src list: {}, target list:{}", srcList, targetList);
		}
		return ret;
	}

	/**
	 * 根据EL表达式提取数据。返回的是list格式。
	 * 不能使用set，否则数据顺序会变。
	 * 
	 * @param expression
	 * @param dataCtx
	 * @return
	 */
	public static List<String> fetchData(Expression expression, EvaluationContext dataCtx) {
		List<String> retList = new ArrayList<>();
		String expString = expression.getExpressionString();
		try {
			Object retObj = expression.getValue(dataCtx);
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("original obj got by EL {}", retObj);
			List<String> keyList = getCollectionByObject(retObj, 0);
			
			// 手工去重。
			Set<String> keySet = new HashSet<>();
			for (String k : keyList) {
				if (keySet.contains(k))
					continue;
				retList.add(k);
			}
		} catch (EvaluationException ee) {
			logger.error("expression syntax error " + expString);
			return null; //NOSONAR
		} catch (Exception e) {
			logger.error(expString, e);
			return null; //NOSONAR
		}
		return retList;
	}
	
	/**
	 * 取得一个object的 所有 collection的拉平后的结果。
	 * recurLevel是递归深度，做控制。
	 * 
	 * @param obj
	 * @param recurLevel
	 * @return
	 */
	private static List<String> getCollectionByObject(Object obj, int recurLevel) {
		List<String> ret = new ArrayList<>();
		// 超过递归深度 ，不做任何处理。
		if (recurLevel >= SdkCommonConstant.MAX_RECURSION_LEVEL)
			return ret;
		
		// 这个object是个集合。则递归处理集合。
		if ((obj instanceof Collection) || obj.getClass().isArray()) {
			return flatCollection(obj, recurLevel + 1);
		} else {
			// 直接按string处理
			if (! StringUtils.isEmpty(obj.toString()))
				ret.add(obj.toString());
			return ret;
		}
	}
	
	/**
	 * 将多层嵌套的collection/array 拉平成一层。
	 * recurLevel是递归深度，本方法中不做控制。
	 * 
	 * 
	 * @param obj
	 * @param recurLevel
	 * @return
	 */
	private static List<String> flatCollection(Object obj, int recurLevel) {
		List<String> retList = new ArrayList<>();
		if (obj instanceof Collection) {
			Iterator c = ((Collection) obj).iterator();
			while (c.hasNext()) {
				retList.addAll(getCollectionByObject(c.next(), recurLevel));
			}
		} else {
			Object[] objArr = (Object[])obj;
			for (int i = 0; i < objArr.length; i++) {
				retList.addAll(getCollectionByObject(objArr[i], recurLevel));
			}
		}
		return retList;
	}
	
	
}
