package com.yh.svc.gov.test.springboot2.controller;

import com.yh.svc.gov.test.springboot2.command.MessageVo;
import com.yh.svc.gov.test.springboot2.command.OrderVo;
import com.yh.svc.gov.test.springboot2.manager.ProcessManager;
import com.yh.svc.gov.test.springboot2.util.DateTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class EntryController {

	private static final Logger logger = LoggerFactory.getLogger(EntryController.class);

	@Autowired
	private ProcessManager mgr;

	@RequestMapping("/hello")
	public String hello() {
		return "hello world " + DateTimeHelper.toString(new Date());
	}
	@RequestMapping("/start")
	public String start(@RequestParam(value = "appId") String appId, @RequestParam(value = "node") String node, @RequestParam(value = "codes") String codes) {
		logger.debug("get the data: {} , {} ,{}", appId, node, codes);
		if (codes == null) {
			codes = "";
		}
		String[] orderCodes = codes.split("-");
		List<OrderVo> orders = new ArrayList<OrderVo>();
		
        logger.debug("OrderVo classloader is {}", OrderVo.class.getClassLoader());

        
		for (String c : orderCodes) {
			OrderVo vo = new OrderVo();
			vo.setOrderCode(c);
			orders.add(vo);
		}
		String ret = mgr.receiveData(appId, node, orders, "auto generated");
		logger.info("auto gen result: {}", ret);
		return ret;
	}

	@PostMapping("/receive")
	public String receive(@RequestBody MessageVo msg) {
		String ret = mgr.receiveData(msg.getAppId(), msg.getNode(), msg.getOrders(), msg.getComment());
		logger.info("result: {}", ret);
		return ret;
	}

	
	@RequestMapping("/start-t")
	public String startTrans(@RequestParam(value = "appId") String appId, @RequestParam(value = "node") String node, @RequestParam(value = "codes") String codes) {
		if (codes == null){
			codes = "";
		}

		String[] orderCodes = codes.split("-");
		List<OrderVo> orders = new ArrayList<OrderVo>();
		for (String c : orderCodes) {
			OrderVo vo = new OrderVo();
			vo.setOrderCode(c);
			orders.add(vo);
		}
		List<OrderVo> ret = mgr.transData(appId, node, orders, "auto generated trans");
		logger.info("auto gen result size: {}", ret.size());
		return "success";
	}

	@PostMapping("/trans")
	public List<OrderVo> trans(@RequestBody MessageVo msg) {
		List<OrderVo> ret = mgr.transData(msg.getAppId(), msg.getNode(), msg.getOrders(), msg.getComment());
		logger.info("result: {}", ret);
		return ret;
	}

	@GetMapping("/void/method")
	public String voidMethod() {
		mgr.voidMethod("test-app",1002);
		return "success";
	}
}
