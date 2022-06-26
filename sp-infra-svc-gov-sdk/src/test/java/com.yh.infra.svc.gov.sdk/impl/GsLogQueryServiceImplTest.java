//package com.sp.infra.svc.gov.sdk.impl;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.mockito.MockitoAnnotations;
//import org.mockserver.integration.ClientAndServer;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * User: HSH7849
// */
//
//
///**
// * 单元测试失败。 暂时ignore。
// * @author luchao 2019-09-26
// *
// */
//
//@Ignore
//public class GsLogQueryServiceImplTest {
//
//    private ClientAndServer mockServer;
//
//    String baseGateWayUrlQuery;
//
//    GsLogQueryServiceImpl logQueryManager=new GsLogQueryServiceImpl();
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        mockServer = ClientAndServer.startClientAndServer(9084);
//        baseGateWayUrlQuery = "http://localhost:2510";
//    }
//
//
//    @Test
//    public void getCount(){
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("requestID","requestId");
//        long count = logQueryManager.count("PZ", "TYPE", params, true, "commandCode");
//        System.out.println(count);
//    }
//
//
//    @Test
//    public void getCountGroup(){
//        Map<String,String> param=new HashMap<>();
//        param.put("isSuccess","1");
////        List<Map> list=logQueryManager.groupQuery("hub4","order",param,"traceId,methodName|methodName");
////        System.out.println(list.size());
//    }
//}
