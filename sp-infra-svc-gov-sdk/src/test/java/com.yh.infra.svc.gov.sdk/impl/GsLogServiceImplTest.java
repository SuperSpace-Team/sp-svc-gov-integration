package com.sp.infra.svc.gov.sdk.impl;

import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.AppRegLauncher;
import com.sp.infra.svc.gov.sdk.log.command.CommonLogDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 测试用例有问题。 暂时ignore。
 *
 */
@Ignore
public class GsLogServiceImplTest {

    @Before
    public void setUp() {
        AppRegConfig appRegConfig = new AppRegConfig();
        appRegConfig.setEnableVersionChecker(false);

        AppRegLauncher appRegLauncher = new AppRegLauncher();
        appRegLauncher.setEnabled(true);
        appRegLauncher.setAppKey("noa-dev");
        appRegLauncher.setAppSecret("12345678");
        appRegLauncher.setUnionGatewayUrl("http://localhost:8100");
        appRegLauncher.setAppRegConfig(appRegConfig);
        appRegLauncher.init();
    }

//    @Test
//    public void log() {
//        GsLogService gsLogService = new GsLogServiceImpl();
//        CommonLogDto commonLogDto = new CommonLogDto();
//
////        String str = FileUtil.readUtf8String("D:\\test.txt");
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("content", "straaaaaaaaaaaaa");
//        params.put("businessKey", "12345678a");
//        gsLogService.log("test-11", params);
//    }
//
//    @Test
//    public void queryDetail() {
//        GsLogService gsLogService = new GsLogServiceImpl();
//        Map<String, String> map = gsLogService.queryDetail("5ef3191cef938b6f0fb016e9", "PAC-TEST-MONITOR", "SO_CREATE_ERROR");
//        System.out.println(map.get("result"));
//    }
//
//    @Test
//    public void queryLimitOne() {
//        GsLogService gsLogService = new GsLogServiceImpl();
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("searchKey", "businessKey");
//        map.put("filterKey", "equal");
//        map.put("selectValue", "734786556109488128");
//        list.add(map);
//        Map<String, String> resp = gsLogService.queryLimitOne(1L, System.currentTimeMillis(), "Sales-Server-Master", "HUB-SALES", list);
//        System.out.println(resp.get("result"));
//    }
}
