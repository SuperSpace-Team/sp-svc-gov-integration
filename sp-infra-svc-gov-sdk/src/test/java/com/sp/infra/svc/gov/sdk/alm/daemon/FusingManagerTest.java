package com.sp.infra.svc.gov.sdk.alm.daemon;

import com.sp.infra.svc.gov.sdk.alm.command.FusingBucket;
import com.sp.infra.svc.gov.sdk.alm.command.FusingStatus;
import com.sp.infra.svc.gov.sdk.alm.command.ResponseStatusCommand;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.constant.FusingStatusEnum;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.init.context.ResponseStatusEnum;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import com.sp.infra.svc.gov.sdk.util.WaitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class FusingManagerTest {

    // OPEN(0), CLOSE(1), HALF(2);
    private FusingManager fusingManager;
    private MonitorGlobalContext pgContext;

    @Before
    public void setUp() throws Exception {
        //  设置config
        AppRegConfig AppRegConfig = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        
        AlmConfig almconfig = new AlmConfig();
        // 超时等待时间
        almconfig.setFuseCoolTimeout(1);
        // 错误率熔断阀值60%
        almconfig.setFuseThreshold(60);


        BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);

        //  设置context
        pgContext = new MonitorGlobalContext(almconfig, AppRegConfig);
        fusingManager = new FusingManager(pgContext);
        fusingManager.start();

    }

    class YhCallable implements Callable<Boolean> {
        FusingManager fusing;

        public YhCallable(FusingManager fusing) {
            this.fusing = fusing;
        }

        @Override
        public Boolean call() {
            LinkedBlockingQueue dq = (LinkedBlockingQueue) TestReflectionUtils.getValue(fusing, "discoveryLogQueue");
            return dq.size() == 0;
        }
    }

    @After
    public void tearDown() throws Exception {
        fusingManager.setExit();
        fusingManager.join();
    }

    @Test
    public void test_SetExit() {

        fusingManager.setExit();
        WaitUtil.wait(50);

        Assert.assertFalse(fusingManager.isAlive());
    }

    @Test
    public void test_Run_Open_To_Half() {
        final FusingStatus status = pgContext.getFusingStatus();
        // 改变状态
        status.open();
        // 熔断等待时间为1秒，等待2秒状态会置为half
        await().atMost(3, SECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return FusingStatusEnum.HALF.name().equals(status.getStatus().name());
            }
        });

        Assert.assertEquals(status.getStatus().name(), FusingStatusEnum.HALF.name());
    }

    @Test
    public void test_Run_Half_To_Open() {
        final FusingStatus status = pgContext.getFusingStatus();
        status.halfOpen();

        pgContext.addStatus(ResponseStatusCommand.success());
        await().atMost(1500, MILLISECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return FusingStatusEnum.CLOSE.name().equals(status.getStatus().name());
            }
        });
        Assert.assertEquals(status.getStatus().name(), FusingStatusEnum.CLOSE.name());
    }

    @Test
    public void test_Run_Half_To_Close() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        status.halfOpen();

        pgContext.addStatus(ResponseStatusCommand.fail());
//        Thread.sleep(500);
        WaitUtil.wait(500);
        Assert.assertEquals(status.getStatus().name(), FusingStatusEnum.OPEN.name());
    }

    @Test
    public void test_Run_Close_Less_Threshold() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        status.close();
        pgContext.addStatus(ResponseStatusCommand.fail());
        for (int i = 0; i < 5; i++) {
            pgContext.addStatus(ResponseStatusCommand.success());
        }
        for (int i = 0; i < 4; i++) {
            pgContext.addStatus(ResponseStatusCommand.fail());
        }

//        Thread.sleep(1500);
        await().pollDelay(1500, MILLISECONDS);

        Assert.assertEquals(status.getStatus().name(), FusingStatusEnum.CLOSE.name());
    }

    @Test
    public void test_Run_Close_Over_Threshold() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        status.close();
        for (int i = 0; i < 7; i++) {
            pgContext.addStatus(ResponseStatusCommand.fail());
        }
        for (int i = 0; i < 4; i++) {
            pgContext.addStatus(ResponseStatusCommand.success());
        }
//        Thread.sleep(500);
        await().pollDelay(500, MILLISECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return true;
            }
        });

        Assert.assertEquals(status.getStatus().name(), FusingStatusEnum.OPEN.name());
    }

    @Test
    public void test_Insert_Command_NO_NEED_TO_CLEAR() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        mockFusingBucket(status.getHeader(), 100000);
        ResponseStatusCommand rsc = ResponseStatusCommand.fail();
        rsc.setTimeStamp(80010);
        FusingBucket header = ReflectionTestUtils.invokeMethod(fusingManager, "insertCommand", status.getHeader(), rsc, 100010L);
        FusingBucket h = header;
        long c = 100000;
        do {
            assertEquals(c, h.getTimeStamp());
            c -= 1000;
            if (h.getTimeStamp() == 80000) {
                assertEquals(3, h.getFailNum());
            } else {
                assertEquals(2, h.getFailNum());
            }
            assertEquals(3, h.getSuccessNum());
            h = h.getNext();
        }
        while (h != header);
        assertEquals(70000, c);
    }

    @Test
    public void test_Insert_Command_CLEAR() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        mockFusingBucket(status.getHeader(), 100000);
        ResponseStatusCommand rsc = ResponseStatusCommand.fail();
        rsc.setTimeStamp(90020);
        FusingBucket header = ReflectionTestUtils.invokeMethod(fusingManager, "insertCommand", status.getHeader(), rsc, 110010L);
        FusingBucket h = header;
        int c = 110000;
        do {
            assertEquals(c, h.getTimeStamp());
            c -= 1000;
            if (h.getTimeStamp() == 90000) {
                assertEquals(3, h.getFailNum());
                assertEquals(3, h.getSuccessNum());
            } else {
                if (h.getTimeStamp() > 100000) {
                    assertEquals(0, h.getFailNum());
                    assertEquals(0, h.getSuccessNum());
                } else {
                    assertEquals(2, h.getFailNum());
                    assertEquals(3, h.getSuccessNum());
                }
            }
            h = h.getNext();
        }
        while (h != header);
        assertEquals(80000, c);
    }

    @Test
    public void test_Insert_Command_CLEAR_whole_cycle() throws InterruptedException {
        FusingStatus status = pgContext.getFusingStatus();
        mockFusingBucket(status.getHeader(), 100000);
        ResponseStatusCommand rsc = ResponseStatusCommand.fail();
        rsc.setTimeStamp(158020);

        // 无论是msg还是end time， 都超过  链条中 任一个节点， 所以   更新bucket的while循环   会  执行 超过 一圈。
        FusingBucket header = ReflectionTestUtils.invokeMethod(fusingManager, "insertCommand", status.getHeader(), rsc, 160010L);
        FusingBucket h = header;
        int c = 160000;
        do {
            assertEquals(c, h.getTimeStamp());
            c -= 1000;
            if (h.getTimeStamp() == 158000) {
                assertEquals(1, h.getFailNum());
            } else {
                assertEquals(0, h.getFailNum());
            }
            assertEquals(0, h.getSuccessNum());
            h = h.getNext();
        }
        while (h != header);
        assertEquals(130000, c);
    }

    private void mockFusingBucket(FusingBucket header, long start) {
        FusingBucket p = header;

        // 30个桶，放入时间戳，  100。。。71
        do {
            p.setTimeStamp(start);
            //每个里面放2个fail，3个success
            p.increase(ResponseStatusEnum.FAIL);
            p.increase(ResponseStatusEnum.FAIL);
            p.increase(ResponseStatusEnum.SUCCESS);
            p.increase(ResponseStatusEnum.SUCCESS);
            p.increase(ResponseStatusEnum.SUCCESS);
            start -= 1000;
            p = p.getNext();
        }
        while (p != header);

    }


    @Test
    public void test_clearBucket() throws InterruptedException {

        FusingStatus status = pgContext.getFusingStatus();

        long starth = System.currentTimeMillis();
        starth = starth - (starth % 1000);
        long statusTs = starth - 3000;// 假定 half的时间 跟 当前有3s 间隔。

        // 先把half 时间点之前  所有的bucket 赋值 为  fail=3
        FusingBucket header = status.getHeader();
        FusingBucket h = header;
        while (h.getNext() != header) {
            if (starth < statusTs) {
                h.increase(ResponseStatusEnum.FAIL);
                h.increase(ResponseStatusEnum.FAIL);
                h.increase(ResponseStatusEnum.FAIL);
            }
            h.setTimeStamp(starth);
            starth -= 1000;
            h = h.getNext();
        }


        //状态变为half
        status.halfOpen();
        TestReflectionUtils.setValue(status, "changeTime", statusTs);


//        Thread.sleep(1000);
        await().pollDelay(1000, MILLISECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        // 构建一个success的。
        ResponseStatusCommand rsc = ResponseStatusCommand.success();
        rsc.setTimeStamp(statusTs + 1020);
        pgContext.addStatus(rsc);


        // 等待线程执行clear方法
//        Thread.sleep(3000);
        await().pollDelay(3000, MILLISECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });


        // header已经不是之前的了。 因为中间执行过insertCommand方法， header变了。
        header = status.getHeader();
        h = header;
        while (h.getNext() != header) {
            // 只有那个success 的节点是1
            if (h.getTimeStamp() == (statusTs + 1000))
                assertEquals(1, h.getSuccessNum());
            else
                assertEquals(0, h.getSuccessNum());

            assertEquals(0, h.getFailNum());
            h = h.getNext();
        }
    }


}