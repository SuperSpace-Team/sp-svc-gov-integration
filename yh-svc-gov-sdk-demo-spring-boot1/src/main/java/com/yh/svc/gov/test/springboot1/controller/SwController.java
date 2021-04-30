//package com.yh.svc.gov.test.springboot1.controller;
//
//import java.util.Date;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yh.svc.gov.test.springboot1.command.OrderVo;
//import com.yh.svc.gov.test.springboot1.manager.SwManager;
//
//@RestController
//@RequestMapping("/sw")
//public class SwController {
//	/**
//	 * 自定义trigger
//	 */
//	@Autowired
//	private SwManager swManager;
//
//	Logger logger = LoggerFactory.getLogger(SwController.class);
//
//
//	@RequestMapping("/receive")
//	public String receive(@RequestParam(value = "bizKey") String bizKey) throws InterruptedException {
//
//		logger.info("enter into receive {}", bizKey);
//
//		return "success!";
//	}
//	@GetMapping("/stack")
//	public String stack() {
//		new Exception("test stack").printStackTrace();
//		return "成功stack " + new Date();
//	}
//
//	@GetMapping("/responseTest")
//	public String responseTest(@RequestParam int type, HttpServletResponse response) {
//		OrderVo orderVo = new OrderVo();
//		orderVo.setOrderCode("abc");
//		orderVo.setAddress("address");
//		if (type == 1) {
//			response.setStatus(HttpStatus.OK.value());
//		} else {
//			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		}
//		return JsonUtil.writeValue(orderVo);
//	}
//
//	@GetMapping("/trace")
//	public String trace(@RequestParam(value = "code") String code) {
//		logger.info("received code {}", code);
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpGet httpget = new HttpGet("http://gxfw-yh02-dev.yh-union-gateway.devgw.yonghui.cn/api/pg/test/echo?code=" + code);
//		try(CloseableHttpResponse response = httpclient.execute(httpget)) {
//			if (response.getStatusLine().getStatusCode() == 200) {
//				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
//				logger.info("response : {}", content);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error " + e.getMessage();
//		}
//
//		return "ok! " + new Date();
//
//	}
//	@GetMapping("/trace2/{code}")
//	public String trace2(@PathVariable(name = "code") String code) {
//		logger.info("trace2 received code {}", code);
//
//		swManager.swTest(100, "http://localhost:8381/sw/trace?code=" + code);
//
//		return "trace2 ok! " + new Date();
//
//	}
//}
