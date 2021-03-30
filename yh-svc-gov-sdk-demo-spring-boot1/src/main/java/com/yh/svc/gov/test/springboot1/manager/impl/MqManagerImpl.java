//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yh.svc.gov.test.springboot1.command.MessageVo;
//import com.yh.svc.gov.test.springboot1.command.OrderVo;
//import com.yh.svc.gov.test.springboot1.manager.BaseManager;
//import com.yh.svc.gov.test.springboot1.manager.MqManager;
//import com.yh.svc.gov.test.springboot1.model.OrderCancel;
//import com.yh.infra.svc.gov.sdk.platform.baseservice.message.common.MessageCommond;
//
///**
// * @author luchao 2018-08-06
// */
//@Service("mqManager")
//public class MqManagerImpl implements MqManager {
//	private static final Logger logger = LoggerFactory.getLogger(MqManagerImpl.class);
//
//
//	@Autowired
//	private BaseManager<OrderCancel> processManager;
//
//	@Override
//	public void receiveMq(MessageCommond msg) {
//		logger.info("received message. {}, {}", msg.getMsgId(), msg.getMsgBody());
//
//		MessageVo mv = JsonUtil.readValue(msg.getMsgBody(), MessageVo.class);
//
//		String ret = processManager.receiveData(mv.getAppId(), mv.getNode(), mv.getOrders(), mv.getComment(), 1);
//		logger.info("MQ result: {}", ret);
//	}
//	@Override
//	public void receiveTransMq(MessageCommond msg) {
//		logger.info("receive Trans message. {}, {}", msg.getMsgId(), msg.getMsgBody());
//
//		MessageVo mv = JsonUtil.readValue(msg.getMsgBody(), MessageVo.class);
//
//		List<OrderVo> ret = processManager.transData(mv.getAppId(), mv.getNode(), mv.getOrders(), mv.getComment());
//		logger.info("MQ result list size : {}", ret.size());
//	}
//
//
//}
