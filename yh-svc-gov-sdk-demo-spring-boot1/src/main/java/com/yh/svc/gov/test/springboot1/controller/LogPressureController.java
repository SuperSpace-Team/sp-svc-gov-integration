package com.yh.svc.gov.test.springboot1.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yh.common.utilities.http.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yh.svc.gov.test.springboot1.command.CommonLogCommand;
import com.yh.svc.gov.test.springboot1.utils.AESGeneralUtil;
import com.yh.svc.gov.test.springboot1.service.LogPressureService;

@RestController
@RequestMapping("/log")
public class LogPressureController{
    
//    private static final Logger logger = LoggerFactory.getLogger(LogPressureController.class);
    
    @Autowired
    private LogPressureService logRemoteManager;
    
    @GetMapping("/start")
    public String start(){
        logRemoteManager.start();
        return "ok";
    }
    
    @GetMapping("/stop")
    public String stop(){
        logRemoteManager.stop();
        return "ok";
    }
    
    @GetMapping("/update")
    public String updateThreadCount(@RequestParam int corePoolSize,@RequestParam int maximumPoolSize){
        logRemoteManager.updateThreadCount(corePoolSize,maximumPoolSize);
        return "ok";
    }
    
    private static RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/test")
    public String test(){
        CommonLogCommand commonLogCommand = new CommonLogCommand();

            commonLogCommand.setCustomerCode("test-log");
            commonLogCommand.setLogType("pression_test_type");
            commonLogCommand.setOrgCode("test-log");
            commonLogCommand.setContent("test_log1");
            commonLogCommand.setNote("test_log");
            commonLogCommand.setBusinessKey("test_log");
            commonLogCommand.setOperator("test_log");
            commonLogCommand.setExtraParam1("test_log");
            commonLogCommand.setExtraParam2("test_log");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("encryptStr", "test-log" + "!##!" + AESGeneralUtil.encrypt(JsonUtil.writeValue(commonLogCommand), "ec865a850d20db45"));
        String result = null;
        try{
            result = HttpJsonClient.postJsonDataByJson("http://api-base.cloud.bz/api/log/log/syno", params, 20);
        }catch (ClientProtocolException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("result"+result);
        ResponseEntity<Map> postForEntity = restTemplate.postForEntity("http://api-base.cloud.bz/api/log/log/syno", params, Map.class);
        //Map<String,Object> body = postForEntity.getBody();
        System.out.println(postForEntity.getStatusCodeValue());
        System.out.println("map"+postForEntity.getBody());
        return result;
    }
   
    
}
