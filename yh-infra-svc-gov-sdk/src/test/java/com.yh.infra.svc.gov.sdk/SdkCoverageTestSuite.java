package com.yh.infra.svc.gov.sdk;

import com.yh.infra.svc.gov.sdk.alm.callback.LocalAlmCallbackServiceImplTest;
import com.yh.infra.svc.gov.sdk.alm.daemon.FusingManagerTest;
import com.yh.infra.svc.gov.sdk.alm.daemon.FusingManager_1Test;
import com.yh.infra.svc.gov.sdk.alm.daemon.LogSenderManagerTest;
import com.yh.infra.svc.gov.sdk.alm.init.AlmComponentInitTest;
import com.yh.infra.svc.gov.sdk.alm.service.*;
//import com.yh.infra.svc.gov.sdk.log4j.callback.LogCallbackServiceImplTest;
//import com.yh.infra.svc.gov.sdk.log4j.service.LogService1Test;
//import com.yh.infra.svc.gov.sdk.log4j.service.LogService2Test;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxyImplTest;
import com.yh.infra.svc.gov.sdk.init.AppRegLauncherTest;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistryProxyTest;
import com.yh.infra.svc.gov.sdk.init.daemon.VersionCheckerTest;
import com.yh.infra.svc.gov.sdk.init.service.ConfigServiceTest;
import com.yh.infra.svc.gov.sdk.init.service.SendReceiveServiceTest;
import com.yh.infra.svc.gov.sdk.init.service.SendReceiveService_1Test;
import com.yh.infra.svc.gov.sdk.uac.UacServiceOpTest;
import com.yh.infra.svc.gov.sdk.util.NetUtilsTest;
import com.yh.infra.svc.gov.sdk.util.SpringConvertTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Ignore
@RunWith(Suite.class)
@Suite.SuiteClasses({
	/// alm ///
	LocalAlmCallbackServiceImplTest.class,
	FusingManager_1Test.class,
	FusingManagerTest.class,
	LogSenderManagerTest.class,
	AlmComponentInitTest.class,
	FusingProxyServiceTest.class,
	MonitorService_1Test.class,
	MonitorService_2Test.class,
	MonitorServiceTest.class,
	SendLogService_2Test.class,
	SendLogServiceTest.class,
	
	///// pg/////
//	LogCallbackServiceImplTest.class,
	HttpClientProxyImplTest.class,
	BeanRegistryProxyTest.class,
	VersionCheckerTest.class,
	ConfigServiceTest.class,
    AppRegLauncherTest.class,
    NetUtilsTest.class,
    SendReceiveService_1Test.class,
    SendReceiveServiceTest.class,
//    LogService1Test.class,
//    LogService2Test.class,
    UacServiceOpTest.class,
    SpringConvertTest.class,
    
})
public class SdkCoverageTestSuite {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {}


}

