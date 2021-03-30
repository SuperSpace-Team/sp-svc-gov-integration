package com.yh.svc.gov.test.springboot1.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.command.OrderVo;
import com.yh.svc.gov.test.springboot1.manager.BaseManager;
import com.yh.svc.gov.test.springboot1.model.OrderCancel;

@RestController
@RequestMapping("/entry3")
public class Entry3Controller {

	private static final Logger logger = LoggerFactory.getLogger(Entry2Controller.class);

	@Autowired
	private BaseManager<OrderCancel> mgr;

	@RequestMapping("/start")
	public String start(@RequestParam(value = "appId") String appId, @RequestParam(value = "node") String node, @RequestParam(value = "codes") String codes) {
		logger.info("get the data: {} , {} ,{}", appId, node, codes);
		Exception e = new Exception("test for stack trace.");
		e.printStackTrace();
		
		
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

		return ret + " from controller";
	}
}
