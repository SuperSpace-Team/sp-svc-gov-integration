package com.sp.infra.svc.gov.sdk.uac.account;

import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.AppRegLauncher;
import org.junit.Before;
import org.junit.Ignore;

@Ignore
public class SaasTenantServiceTest {

    @Before
    public void setUp() {
        System.out.println("=======================SaasTenantServiceTest.setUp");
        AppRegConfig AppRegConfig = new AppRegConfig();
        AppRegConfig.setEnableVersionChecker(false);

        AppRegLauncher AppRegLauncher = new AppRegLauncher();
        AppRegLauncher.setEnabled(true);
        AppRegLauncher.setAppKey("gs-archive-sit");
        AppRegLauncher.setAppSecret("123456qwer");
        AppRegLauncher.setUnionGatewayUrl("http://sit-api-base.superspace.cn/api");
        AppRegLauncher.setAppRegConfig(AppRegConfig);
        AppRegLauncher.init();
    }
//
//    @Test
//    public void add() {
//        SaasTenantService saasTenantService = new SaasTenantService();
//
//        String saasTenantCode = "test-code";
//        String saasTenantName = "test-name";
//        BaseResponseEntity<String> response = saasTenantService.add(saasTenantCode, saasTenantName);
//        System.out.println(response.getIsSuccess());
//    }
//
//    @Test
//    public void getTenantTokenList() {
//        SaasTenantService saasTenantService = new SaasTenantService();
//
//        BaseResponseEntity<List<SaasTenantToken>> response = saasTenantService.getTenantTokenList();
//        System.out.println(response.getData());
//        System.out.println(response.getIsSuccess());
//    }
}
