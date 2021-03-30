package com.yh.svc.gov.test.springboot1.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.yh.infra.svc.gov.sdk.util.AESGeneralUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.svc.gov.test.springboot1.command.CommonLogCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Service
public class LogPressureService  implements InitializingBean{
    
    private static final Logger logger = LoggerFactory.getLogger(LogPressureService.class);
    
    protected ThreadPoolExecutor executorService;
    
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("fIle-pressure-pool-%d").build();

    private Boolean flag = false;
    
    //成功数量
    private static AtomicInteger count = new AtomicInteger(0);
    //失败数量
    private static AtomicInteger failcount = new AtomicInteger(0);
    
    private static final String BODY_STR = "testlogsttestlogstestlogsaptestlogsaptestlogsaptesttestlogstestlogsaptestlogsaptestlogsaptesttestlogstestlogsaptestlogsaptestlogsaptestestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapaptestlogsatestlogstestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapptestlogsaptestlogsatestlogstestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapaptestlogsatestlogstestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapptestlogsaptestlogsaptestlogsaptestlogsaptestlogsaptestlogsapptestlogsaptestlogsaptestlogsaptestlogsap";
    private static Map<String, Object> params = new HashMap<String, Object>();
    private static StringBuilder sb = new StringBuilder(BODY_STR);
    private static RestTemplate restTemplate = new RestTemplate();
    @Override
    public void afterPropertiesSet() throws Exception{
        initThreadPool(50,100);
        for(int i=0;i<1000;i++){
            sb.append(BODY_STR);
        }
        CommonLogCommand commonLogCommand = new CommonLogCommand();
        commonLogCommand.setCustomerCode("test-log");
        commonLogCommand.setLogType("pression_test_type");
        commonLogCommand.setOrgCode("test-log");
        commonLogCommand.setContent(sb.toString());
        commonLogCommand.setNote("test_log");
        commonLogCommand.setBusinessKey("test_log");
        commonLogCommand.setOperator("test_log");
        commonLogCommand.setExtraParam1("test_log");
        commonLogCommand.setExtraParam2("test_log");
        params.put("encryptStr", "test-log" + "!##!" + AESGeneralUtil.encrypt(JsonUtil.writeValue(commonLogCommand), "ec865a850d20db45"));
    }
    public static void main(String[] args){
        CommonLogCommand commonLogCommand = new CommonLogCommand();
        commonLogCommand.setCustomerCode("test-log");
        commonLogCommand.setLogType("pression_test_type");
        commonLogCommand.setOrgCode("test-log");
        commonLogCommand.setContent(sb.toString());
        commonLogCommand.setNote("test_log");
        commonLogCommand.setBusinessKey("test_log");
        commonLogCommand.setOperator("test_log");
        commonLogCommand.setExtraParam1("test_log");
        commonLogCommand.setExtraParam2("test_log");
        params.put("encryptStr", "test-log" + "!##!" + AESGeneralUtil.encrypt(JsonUtil.writeValue(commonLogCommand), "ec865a850d20db45"));
        System.out.println(params);
    }
    //启动写日志
    @Async
    public void start(){
        logger.info("启动写日志=====================");
        //启动
        flag = true;
        //判断线程池是否可用
        if (executorService.isShutdown()){
            initThreadPool(100,200);
        }
        while (flag && !executorService.isShutdown()){
            for(int i=0;i<50;i++){
                try{
                    executorService.execute(new Runnable(){
                        @Override
                        public void run(){
                            try{
                                ResponseEntity<Map> postForEntity = restTemplate.postForEntity("http://api-base.cloud.bz/api/log/log/syno", params, Map.class);
                                if(null != postForEntity && postForEntity.getStatusCodeValue() != 200){
                                    logger.error("log==============={}",postForEntity);
                                }
                            }catch (Exception e){
                                logger.error("log error==============={}",e);
                            }
                        }
                    });
                }catch (Exception e){
                    logger.error("send log error:",e);
                }
            }
            while(executorService.getActiveCount()>50){
                try{
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    //终止
    public void stop(){
        logger.info("终止写日志=====================");
        flag = false;
        try {  
            executorService.shutdown();  
            logger.info("线程池内已shutdown");
            // (所有的任务都结束的时候，返回TRUE)  
            if(!executorService.awaitTermination(20000L, TimeUnit.MILLISECONDS)){  
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。  
                executorService.shutdownNow();  
            }
            logger.info("线程池内已无任务，已关闭 :执行成功数量:{},执行失败数量:{}",count.get(),failcount.get());
            count = new AtomicInteger(0);
            failcount = new AtomicInteger(0);
        } catch (InterruptedException e) {  
            logger.error("awaitTermination interrupted: " , e);  
            executorService.shutdownNow();  
        } 
    }
    
    //修改线程池数量
    public void updateThreadCount(int corePoolSize, int maximumPoolSize){
        logger.info("修改线程池大小：corePoolSize:{} maximumPoolSize:{}",corePoolSize,maximumPoolSize);
        this.stop();
        initThreadPool(corePoolSize, maximumPoolSize);
    }
    
    //初始化线程池
    private void initThreadPool(int corePoolSize, int maximumPoolSize){
        logger.info("修改线程池初始化：corePoolSize:{} maximumPoolSize:{}",corePoolSize,maximumPoolSize);
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
    
}
