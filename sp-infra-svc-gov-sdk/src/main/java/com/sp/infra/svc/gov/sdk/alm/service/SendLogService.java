package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.writer.LogWriter;
import com.sp.infra.svc.gov.sdk.alm.writer.impl.ArchLogWriter;
import com.sp.infra.svc.gov.sdk.alm.writer.impl.LocalFileLogWriter;
import com.sp.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.sp.infra.svc.gov.sdk.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志发送/写入。 仅限于全链路日志的使用。
 *
 * @author luchao
 * @date 2021/4/25 8:31 下午
 */
public class SendLogService {
	private static final Logger logger = LoggerFactory.getLogger(SendLogService.class);
	
	private List<LogWriter> writerList = null;
	private MonitorGlobalContext context;
	
	
	public SendLogService(MonitorGlobalContext ctx, FusingProxyService fps) {
		writerList = new ArrayList<LogWriter>();
		writerList.add(new ArchLogWriter(fps));
		writerList.add(new LocalFileLogWriter());
		context = ctx;
	}
	
	
	/**
	 * 使用配置的logwriter发送日志。 可以有多个目的地。 适用于 全链路日志
	 * 
	 * @param commandList
	 */
	public void writeLog(List<? extends LogMessage> commandList) {
		List<LogTarget> logTargets = context.getLogTarget();
		if (CollectionUtils.isEmpty(logTargets)) {
			logger.error("cannot find the target to write log.");
			return;
		}

		// 按照业务维度分组
		Map<Integer, List<MonitorLogMessage>> bizmap = new HashMap<Integer, List<MonitorLogMessage>>();
		for (LogMessage lm : commandList) {
			MonitorLogMessage lmc = (MonitorLogMessage)lm;
			List<MonitorLogMessage> lmclist = bizmap.get(lmc.getBizCode());
			if (lmclist == null) {
				lmclist = new ArrayList<MonitorLogMessage>();
				bizmap.put(lmc.getBizCode(), lmclist);
			}
			lmclist.add(lmc);
		}
		
			
		for (Integer biz : bizmap.keySet()) {
			if (! sendBizFactorLogs(logTargets, biz, bizmap.get(biz))) {
				logger.warn("no output writer found for bizcode {}", biz);
			}
		}
	}
	
	
	/**
	 * 发送一个业务维度所属的 日志信息列表。对该业务维度配置的所有日志 目的地， 都要发送。
	 * 
	 * @param logTargets
	 * @param biz
	 * @param msgList
	 * @return
	 */
	private boolean sendBizFactorLogs(List<LogTarget> logTargets, Integer biz, List<MonitorLogMessage> msgList) {
		boolean found = false;
		for (LogWriter writer : writerList) {
			LogTarget lt = writer.match(logTargets, biz);
			if (lt != null) {
				found = true;
				if (logger.isDebugEnabled()) //NOSONAR
					logger.debug("output log by writer : {}, {}", writer.getClass().getSimpleName(), lt);
				writer.writeLog(msgList, lt);
			}
		}
		return found;
	}
	
}
