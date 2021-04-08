//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.yh.svc.gov.test.springboot1.client.SdkDemoServiceClient;
//import com.yh.svc.gov.test.springboot1.command.MessageVo;
//import com.yh.svc.gov.test.springboot1.command.OrderVo;
//import com.yh.svc.gov.test.springboot1.model.OrderCancel;
//import com.yh.infra.svc.gov.sdk.platform.baseservice.message.common.MessageCommond;
//import com.yh.infra.svc.gov.sdk.platform.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
//import com.yh.infra.svc.gov.sdk.util.DateTimeHelper;
//
///** RMQ下发功能测试
// * @author luchao 2018-08-06
// */
//@Service("processManager")
//@Transactional
//public class ProcessManagerImpl extends AbstractManagerImpl<OrderCancel> {
//    private static final Logger logger = LoggerFactory.getLogger(ProcessManagerImpl.class);
//
//    @Value("${sdk-demo.chain.next.msg-type}")
//    private String type;
//    @Value("${sdk-demo.chain.node.name}")
//    private String curnode;
//    @Value("${sdk-demo.chain.mq.topic.forward}")
//    private String mqtopic;
//    @Value("${sdk-demo.app-id}")
//    private String curapp;
//
//    @Autowired
//    private SdkDemoServiceClient client;
//    @Autowired
//    private RocketMQProducerServer producerServer;
//
//    @Autowired
//    private TxManagerImpl txMgr;
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public String receiveDataNested(String appId, String node, List<OrderVo> orders, String comment, int nested) {
//        logger.info("nested call : {}, {}", this.getClass().getName(), this);
//    	if (nested <= 0) {
//    		logger.error("nested call. " , new Exception());
//    		return "exceed nested limit !!";
//    	}
//    	return txMgr.receiveDataNested(appId, node, orders, comment, nested-1);
//    }
//
//    @Override
//    public String receiveData(String appId, String node, List<OrderVo> orders, String comment, int pInt) {
//        List<String> ordercode = orders.stream().map(x -> x.getOrderCode()).collect(Collectors.toList());
//        logger.info("received data: {}, {}, {}, order code: {} , class:{}, {}", appId, node, comment, ordercode, this.getClass().getName(), this);
//
//        String ret = "";
//        String com = comment + " -> " + appId + "." + node;
//
//        MessageVo mv = new MessageVo(curapp, curnode, com, orders);
//        if ("mq".equalsIgnoreCase(type)) {
//            String nowStr = DateTimeHelper.toString(new Date());
//
//            MessageCommond reqmsg = new MessageCommond();
//            reqmsg.setMsgBody(JsonUtil.writeValue(mv));
//            reqmsg.setMsgId(curapp + "-" + curnode + "-" + nowStr + "-" + UUID.randomUUID().toString());
//            reqmsg.setMsgType(mqtopic);
//            reqmsg.setSendTime(nowStr);
//            reqmsg.setTags("common");
//            reqmsg.setIsMsgBodySend(true);
//            producerServer.sendDataMsgConcurrently(mqtopic, "common", reqmsg);
//            logger.info("mq. forward request. {},{},{},{}", reqmsg.getMsgId(), type, node, mqtopic);
//            ret = "MQ sent";
//        } else if ("http".equalsIgnoreCase(type)) {
//            ret = client.receive(mv);
//            ret = "HTTP sent";
//        } else {
//            logger.info("the invoke path is: {}", com + " -> " + curapp + "." + curnode);
//            ret = "chain end";
//        }
//        return ret;
//    }
//
//
//    private void internalCall(String appId, int orderSize, String oc) {
//    	logger.debug("enter the internalCall {},{}, {}", appId, orderSize, oc);
//    }
//
//    public void internalCall(OrderCancel oc) {
//    	logger.info(oc.getBsOrderCode());
//    }
//
//    public void voidMethod(String appId, int orderSize) {
//    	logger.debug("enter the voidMethod {},{}", appId, orderSize);
//    }
//
//    @Override
//    public void testObjectHasList(MessageVo messageVo) {
//        logger.debug("messageVo:[{}]",messageVo);
//        System.out.println(messageVo);
//    }
//
//    @Override
//    public List<OrderVo> transData(String appId, String node, List<OrderVo> orders, String comment) {
//        List<String> ordercode = orders.stream().map(x -> x.getOrderCode()).collect(Collectors.toList());
//
//        logger.info("received {}, {}, {}, order code: {} ", appId, node, comment, ordercode);
//
//        for (OrderVo o : orders) {
//			logger.info("source order code {}", o.getOrderCode());
//		}
//
//
//        List<OrderVo> nlist = one2one(orders);
//        for (OrderVo o : nlist) {
//			logger.info("one2one order code {}", o.getOrderCode());
//		}
//        nlist = one2manyAll(nlist);
//        for (OrderVo o : nlist) {
//			logger.info("one2many order code {}", o.getOrderCode());
//		}
//
//        nlist = many2oneAll(nlist);
//        for (OrderVo o : nlist) {
//			logger.info("many2oneAll order code {}", o.getOrderCode());
//		}
//        return nlist;
//    }
//    /**
//     * 多对1转换
//     *
//     * @return
//     */
//    private List<OrderVo> many2oneAll(List<OrderVo> orders) {
//
//    	Map<String, List<OrderVo>> om = new HashMap<>();
//    	orders.forEach(x -> {
//    		List<OrderVo> olist = om.computeIfAbsent(x.getPfCode(), omx->new ArrayList<>());
//    		olist.add(x);
//        });
//    	List<OrderVo> ret = new ArrayList<>();
//    	for (Entry<String, List<OrderVo>> ent : om.entrySet()) {
//    		ret.add(many2one(ent.getValue()));
//		}
//
//
//    	return ret;
//    }
//
//    private OrderVo many2one(List<OrderVo> orders) {
//		OrderVo x = orders.get(0);
//		OrderVo vo = new OrderVo();
//        vo.setAddress(x.getAddress() + "-many2one");
//        vo.setAmount(x.getAmount());
//        vo.setDetails(x.getDetails());
//        vo.setName(x.getName());
//        vo.setPfCode(x.getPfCode());
//        String tmp = x.getOrderCode();
//        vo.setOrderCode(tmp.substring(0, tmp.length()-2) + "-m2o");
//        vo.setStatus(x.getStatus());
//    	return vo;
//    }
//
//
//    /**
//     * 1对多转换
//     *
//     * @return
//     */
//    private List<OrderVo> one2manyAll(List<OrderVo> orders) {
//    	List<OrderVo> ret = new ArrayList<>();
//    	orders.forEach(x -> {
//            ret.addAll(one2many(x));
//        });
//    	return ret;
//    }
//    /**
//     * 1对多转换
//     *
//     * @return
//     */
//    private List<OrderVo> one2many(OrderVo order) {
//    	List<OrderVo> ret = new ArrayList<>();
//        OrderVo vo = new OrderVo();
//        vo.setAddress(order.getAddress() + "-one2many");
//        vo.setAmount(order.getAmount());
//        vo.setDetails(order.getDetails());
//        vo.setName(order.getName());
//        vo.setPfCode(order.getPfCode());
//        vo.setOrderCode(order.getOrderCode() + "-o2m-1");
//        vo.setStatus(order.getStatus());
//        ret.add(vo);
//
//        vo = new OrderVo();
//        vo.setAddress(order.getAddress() + "-one2many");
//        vo.setAmount(order.getAmount());
//        vo.setDetails(order.getDetails());
//        vo.setName(order.getName());
//        vo.setPfCode(order.getPfCode());
//        vo.setOrderCode(order.getOrderCode() + "-o2m-2");
//        vo.setStatus(order.getStatus());
//        ret.add(vo);
//    	return ret;
//    }
//
//    /**
//     * 1对1转换
//     *
//     * @param orders
//     * @return
//     */
//    private List<OrderVo> one2one(List<OrderVo> orders) {
//        return orders.stream().map(x -> {
//            OrderVo vo = new OrderVo();
//            vo.setAddress(x.getAddress());
//            vo.setAmount(x.getAmount());
//            vo.setDetails(x.getDetails());
//            vo.setName(x.getName());
//            vo.setPfCode(x.getPfCode());
//            vo.setOrderCode(x.getOrderCode() + "-" + curapp);
//            vo.setStatus(x.getStatus());
//            return vo;
//        }).collect(Collectors.toList());
//
//    }
//}
