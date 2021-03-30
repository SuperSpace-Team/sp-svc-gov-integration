package com.yh.svc.gov.test.springboot1.aop;

import com.yh.common.utilities.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yh.svc.gov.test.springboot1.command.BaseResponseEntity;
import com.yh.svc.gov.test.springboot1.exception.BusinessException;

@ControllerAdvice
public class CustomerApiExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	BaseResponseEntity handleBusinessException(BusinessException ex) {
		BaseResponseEntity response = new BaseResponseEntity();
		response.setCode(ex.getErrorCode() + "");
		response.setMsg(JSON.toJSONString(ex.getArgs()));
		logger.error("Exception----response:" + JsonUtil.writeValue(response), ex);
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	BaseResponseEntity handleException(Exception e) {
		BaseResponseEntity response = new BaseResponseEntity();
		response.setMsg(e.getMessage());
		logger.error("Exception----response:" + JsonUtil.writeValue(response), e);
		return response;
	}
}
