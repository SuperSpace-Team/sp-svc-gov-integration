package com.yh.svc.gov.test.springboot1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.manager.impl.NonTxManagerImpl;
import com.yh.svc.gov.test.springboot1.manager.impl.TxManagerImpl;

@RestController
public class TxController {

	private static final Logger logger = LoggerFactory.getLogger(TxController.class);

	@Autowired
	private TxManagerImpl txMgr;
	@Autowired
	private NonTxManagerImpl nonTxMgr;
	
	@RequestMapping("/rollback")
	public boolean testRollback(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
		logger.info("testRollback {}, {}, {}, {}", type, uuid, bizKey, timeout);
		return txMgr.testRollback(type, uuid, bizKey, timeout);
	}
	@RequestMapping("/rollback2")
	public boolean testRollback2(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
		logger.info("testRollback {}, {}, {}, {}", type, uuid, bizKey, timeout);
		return txMgr.testRollback2(type, uuid, bizKey, timeout);
	}
	@RequestMapping("/tx")
	public boolean testTx(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
		logger.info("testTx {}, {}, {}, {}", type, uuid, bizKey, timeout);
		return txMgr.testTx(type, uuid, bizKey, timeout);
	}
	@RequestMapping("/nontx")
	public boolean testNonTx(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
		logger.info("testNonTx {}, {}, {}, {}", type, uuid, bizKey, timeout);
		return txMgr.testNonTx(type, uuid, bizKey, timeout);
	}
	@RequestMapping("/nontx2")
	public boolean testNonTx2(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
		logger.info("testNonTx {}, {}, {}, {}", type, uuid, bizKey, timeout);
		return nonTxMgr.testNonTx(type, uuid, bizKey, timeout);
	}
}
