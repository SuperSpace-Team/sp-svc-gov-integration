package com.sp.infra.svc.gov.sdk.alm.daemon;

import com.sp.infra.svc.gov.sdk.alm.command.FusingBucket;
import com.sp.infra.svc.gov.sdk.alm.command.FusingStatus;
import com.sp.infra.svc.gov.sdk.alm.command.ResponseStatusCommand;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.context.ResponseStatusEnum;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import com.sp.infra.svc.gov.sdk.util.WaitUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.*;


/**
 * 
 * 测试特殊情况使用
 * 
 * @author luchao  2019-02-15
 *
 */
public class FusingManager_1Test {

    // OPEN(0), CLOSE(1), HALF(2);
    private FusingManager fusingManager;
    private MonitorGlobalContext ctx;
    
    @Mock
    private MonitorGlobalContext mockCtx;

    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;
    
    
    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        //  设置config
        AppRegConfig AppRegConfig = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        
        AlmConfig almconfig = new AlmConfig(); 
        // 超时等待时间
        almconfig.setFuseCoolTimeout(1);
        // 错误率熔断阀值60%
        almconfig.setFuseThreshold(60);

        doReturn(AppRegConfig).when(mockCtx).getPgConfig();
        doReturn(almconfig).when(mockCtx).getAlmConfig();
        oriLogger = null;
        //  设置context
        fusingManager = new FusingManager(mockCtx);
        
        BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);
    }

    @After
    public void tearDown() throws Exception {
        fusingManager.setExit();
        fusingManager.join();
    	if (oriLogger != null)
    		TestReflectionUtils.setStaticValue(FusingManager.class, "logger", oriLogger, Logger.class);
    }

    @Test
    public void test_no_data_in_queue() throws InterruptedException {
    	oriLogger = TestReflectionUtils.setStaticValue(FusingManager.class, "logger", mockLogger, Logger.class);

    	FusingStatus fs = new FusingStatus(30);
    	FusingBucket header = fs.getHeader();
    	when(mockCtx.getFusingStatus()).thenReturn(fs);
    	when(mockCtx.getStatusCommand(anyInt())).thenReturn(null);
    	
        fusingManager.start();
//        Thread.sleep(4000);
        await().atMost(4000,MILLISECONDS);

        // header 没变。 说明一直在  if (cmd == null) 中执行。
        assertTrue(header == fs.getHeader());
        verify(mockLogger, times(0)).debug(startsWith("get a new command : "));
    }

    @Test
    public void test_abandon_expire_data() throws InterruptedException {
    	oriLogger = TestReflectionUtils.setStaticValue(FusingManager.class, "logger", mockLogger, Logger.class);

    	FusingStatus fs = new FusingStatus(30);
    	FusingBucket header = fs.getHeader();
    	doReturn(fs).when(mockCtx).getFusingStatus();
    	
    	final ResponseStatusCommand cmd = new ResponseStatusCommand();
    	cmd.setTimeStamp(System.currentTimeMillis() - 10000);
    	Answer answer = new Answer() {
    		private boolean first = false;
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				// 只在第一次返回cmd，其他返回null，为了防止 test的时候，出现一大堆 的log
				if (! first) {
					first = true;
					return cmd;
				}
				else 
					return null;
			}
    	};
    	doAnswer(answer).when(mockCtx).getStatusCommand(anyInt());
    	
        fusingManager.start();
//        Thread.sleep(3000);
        WaitUtil.wait(3000);

        // header 没变。 说明  没有走到  “更新bucket的时间戳。”  的地方
        //  检查  logger.debug("command is abandoned {}", cmd);   日志
        assertTrue(header == fs.getHeader());
        
        verify(mockLogger, times(1)).info(startsWith("command is abandoned"));
    }
}