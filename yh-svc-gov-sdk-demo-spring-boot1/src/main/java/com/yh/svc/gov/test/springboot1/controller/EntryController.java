package com.yh.svc.gov.test.springboot1.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yh.svc.gov.test.springboot1.command.MessageVo;
import com.yh.svc.gov.test.springboot1.command.OrderVo;
import com.yh.svc.gov.test.springboot1.manager.BaseManager;
import com.yh.svc.gov.test.springboot1.manager.impl.NonTxManagerImpl;
import com.yh.svc.gov.test.springboot1.model.OrderCancel;
import com.yh.infra.svc.gov.sdk.util.DateTimeHelper;

@RestController
public class EntryController {

	private static final Logger logger = LoggerFactory.getLogger(EntryController.class);

	@Autowired
	private BaseManager<OrderCancel> mgr;

	@Autowired
	private NonTxManagerImpl nonTxMgr;
	
	@RequestMapping("/hello")
	public String hello() {
		logger.info("this is hello world... ");
		return "hello world " + DateTimeHelper.toString(new Date());
	}
	@RequestMapping("/start")
	public String start(@RequestParam(value = "appId") String appId, @RequestParam(value = "node") String node, @RequestParam(value = "codes") String codes) {
		logger.debug("get the data: {} , {} ,{}", appId, node, codes);
		if (codes == null)
			codes = "";
		String[] orderCodes = codes.split("-");
		List<OrderVo> orders = new ArrayList<OrderVo>();
 
		for (String c : orderCodes) {
			OrderVo vo = new OrderVo();
			vo.setOrderCode(c);
			vo.setAddress(c + " address");
			orders.add(vo);
		}

		logger.info("before receiveData. {}", mgr.getClass().getName());
		String ret = mgr.receiveData(appId, node, orders, "auto generated", 3);
		logger.info("after receiveData. auto gen result: {}", ret);
		
//        OrderCancel oc = new OrderCancel();
//        oc.setBsOrderCode(orderCodes[0]);
//        mgr.service(oc);
//
//        nonTxMgr.service(orders.get(0));
		return ret;
	}
	@RequestMapping("/start-t")
	public String startTrans(@RequestParam(value = "appId") String appId, @RequestParam(value = "node") String node, @RequestParam(value = "codes") String codes) {
		if (codes == null)
			codes = "";
		String[] orderCodes = codes.split("-");
		List<OrderVo> orders = new ArrayList<OrderVo>();
		for (String c : orderCodes) {
			OrderVo vo = new OrderVo();
			vo.setOrderCode(c);
			vo.setPfCode(c);
			orders.add(vo);
		}
		List<OrderVo> ret = mgr.transData(appId, node, orders, "auto generated trans");
		logger.info("auto gen result size: {}", ret.size());
		return "success";
	}

	
	@PostMapping("/receive")
	public String receive(@RequestBody MessageVo msg) {
		String ret = mgr.receiveData(msg.getAppId(), msg.getNode(), msg.getOrders(), msg.getComment(), 1);
		logger.info("result: {}", ret);
		return ret;
	}

	@GetMapping("/testList")
	public String testList() {
		MessageVo messageVo = new MessageVo();
		messageVo.setAppId("appid");
		OrderVo vo1 = new OrderVo();
		vo1.setOrderCode("123");
		OrderVo vo2 = new OrderVo();
		vo2.setOrderCode("456");
		OrderVo vo3 = new OrderVo();
		vo3.setOrderCode("789");
		List<OrderVo> orders = Arrays.asList(vo1,vo2,vo3);
//		messageVo.setOrders(orders);
		mgr.testObjectHasList(messageVo);
		return messageVo.toString();
	}

	@PostMapping("/trans")
	public List<OrderVo> trans(@RequestBody MessageVo msg) {
		List<OrderVo> ret = mgr.transData(msg.getAppId(), msg.getNode(), msg.getOrders(), msg.getComment());
		logger.info("result: {}", ret);
		return ret;
	}
}
