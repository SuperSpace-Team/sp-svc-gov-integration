package com.yh.infra.svc.gov.sdk.net;

import com.yh.infra.svc.gov.sdk.alm.service.SendLogService;
import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 运维使用。
 */
@Ignore
public class HttpClientOpsTest {
	private static final Logger logger = LoggerFactory.getLogger(SendLogService.class.getName());
	
    HttpClientProxy httpClient = new HttpClientProxyImpl();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test_SendLog() throws InterruptedException {
    	String url = "http://10.45.91.143:2510/monitorData";
    	String msgBody = "[{\"appId\":\"pg-app-demo1\",\"bizCode\":13,\"cfgVersion\":5,\"elaspedTime\":184,\"hostName\":\"BZF9NK5K21\",\"ip\":\"10.45.71.18\",\"logType\":1,\"monitorLogList\":[{\"code\":2,\"inLog\":\"测试用，入参日志\",\"key\":\"JD55555\",\"outLog\":\"测试用，返回值日志\"},{\"code\":2,\"inLog\":\"测试用，入参日志\",\"key\":\"TB99945\",\"outLog\":\"测试用，返回值日志\"}],\"sdkVersion\":\"0.0.6-SNAPSHOT\",\"threadName\":\"http-nio-9001-exec-8\",\"timeStamp\":1558506077498}]";
		Map<String, String> retMap = httpClient.postJson(url, msgBody, SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		String error = retMap.get("error");
		String status = retMap.get("status");
		String result = retMap.get("result");

		if (StringUtils.isEmpty(error)) {
			BaseResponseEntity bre = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
			if (bre == null) {
				logger.error("JSON parse fail. {} " , result);
			} else {
				boolean ret = SdkCommonConstant.HTTP_STATUS_OK.equals(status) && bre.getIsSuccess();
				if (! ret) {
					logger.error("send failed. sending result: {}, {}, {} ", status, result, error);
				}
			}
		} else {
			logger.error("status:{}, error:{}", status, error);
		}
	}

}
