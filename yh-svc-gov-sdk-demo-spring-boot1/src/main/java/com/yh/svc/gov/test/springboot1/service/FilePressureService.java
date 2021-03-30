//package com.yh.svc.gov.test.springboot1.service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Iterator;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import org.apache.http.client.config.CookieSpecs;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.UploadFileVo;
//import com.yh.infra.svc.gov.sdk.files.manager.FileS2Service;
//import com.yh.infra.svc.gov.sdk.files.utils.Result;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//
//@Service
//public class FilePressureService  implements InitializingBean{
//
//    private static final Logger logger = LoggerFactory.getLogger(FilePressureService.class);
//
//    @Autowired
//    private FileS2Service fileS2Service;
//
//    protected ExecutorService executorService;
//
//    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("fIle-pressure-pool-%d").build();
//
//    private Boolean flag = false;
//
//    private static ConcurrentHashMap<String, String> fileUrl = new ConcurrentHashMap<>();
//
//    //成功数量
//    private static AtomicInteger count = new AtomicInteger(0);
//    //失败数量
//    private static AtomicInteger failcount = new AtomicInteger(0);
//
//    private static CloseableHttpClient httpClient = null;
//    private static final int DEFAULT_TIMEOUT_SECOND = 20;
//
//    static RequestConfig defaultRequestConfig = null;
//
//    //缓存的文件资源
//    static{
//        defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
//        // 设置默认的配置
//        httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setConnectionTimeToLive(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS).build();
//        //50M文件
//        fileUrl.put("PCPROf7d97e9f-85e1-469b-88de-a538ba99b755PublicMsg.db", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPROf7d97e9f-85e1-469b-88de-a538ba99b755PublicMsg.db");
//        //8M文件
//        fileUrl.put("PCPRO691a333a-1905-4913-a100-8d33f566e5e1mongoPtReceiveResponse.csv", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO691a333a-1905-4913-a100-8d33f566e5e1mongoPtReceiveResponse.csv");
//        //600k
//        fileUrl.put("PCPROef56d383-8d95-40be-a866-1d3482dd36cbd01d5fb7-8554-48e2-bec9-7623a4b5ef46.zip", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPROef56d383-8d95-40be-a866-1d3482dd36cbd01d5fb7-8554-48e2-bec9-7623a4b5ef46.zip");
//        //2.3M
//        fileUrl.put("PCPRO50ae29f8-5511-441a-976a-b99a88fa7b0byanfabiaozhun.pptx", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO50ae29f8-5511-441a-976a-b99a88fa7b0byanfabiaozhun.pptx");
//        //1M
//        fileUrl.put("PCPRO1296d4f5-083b-46c8-8b84-7c49590438d5yingjiyuan.docx", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO1296d4f5-083b-46c8-8b84-7c49590438d5yingjiyuan.docx");
//        fileUrl.put("PCPROdb7f81eb-866d-4647-9002-027dc6837d7aeror.log", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPROdb7f81eb-866d-4647-9002-027dc6837d7aeror.log");
//        fileUrl.put("PCPRO1df219c7-864f-4661-a643-c93d3c105eadtest111.pdf", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO1df219c7-864f-4661-a643-c93d3c105eadtest111.pdf");
//        fileUrl.put("PCPRO0bbc7756-37e4-438e-92a2-5b97e3f15628HDTY-2951.doc", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO0bbc7756-37e4-438e-92a2-5b97e3f15628HDTY-2951.doc");
//        fileUrl.put("PCPRO772378b5-0637-4486-a145-abe3f63df5aatest.txt", "https://bztic-gs-files-prod.oss-cn-shanghai-internal.aliyuncs.com/prod/PCPRO772378b5-0637-4486-a145-abe3f63df5aatest.txt");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception{
//        initThreadPool(50,100);
//    }
//
//    //启动上传下载
//    @Async
//    public void start(String tenantId, String folder){
//        logger.info("启动上传文件、文件下载=====================");
//        //启动
//        flag = true;
//        //判断线程池是否可用
//        if (executorService.isShutdown()){
//            initThreadPool(50,100);
//        }
//        while (flag && !executorService.isShutdown()){
//            //遍历文件url   获取文件流上传
//            Iterator<Entry<String, String>> iterator = fileUrl.entrySet().iterator();
//            while(iterator.hasNext()){
//                Entry<String, String> next = iterator.next();
//                executorService.execute(new Runnable(){
//                    @Override
//                    public void run(){
//                        HttpURLConnection connection = null;
//                        String downFileUrl = null;
//                        int contentLength = 0;
//                        //上传
//                        try{
//                            connection = getInputStreamByUrl(next.getValue());
//                            if(null != connection){
//                                //上传文件的文件大小
//                                contentLength = connection.getContentLength();
//                                //上传文件 并返回上传成功的下载地址
//                                downFileUrl = uploadFile(tenantId, folder, connection.getInputStream(), (long)contentLength, next.getKey());
//                            }
//                        }catch (Exception e){
//                            logger.error("上传文件失败",e);
//                            failcount.incrementAndGet();
//                        }
//                        //下载
//                        if(null != downFileUrl){
//                            try{
//                                //根据返回的文件下载地址下载文件
//                                HttpGet request = new HttpGet(downFileUrl);
//                                // 返回值
//                                CloseableHttpResponse httpResponse = httpClient.execute(request);
//                                //下载成功
//                                if(null != httpResponse){
//                                    count.incrementAndGet();
//                                }
//                                httpResponse.close();
//                            }catch (Exception e){
//                                logger.error("下载文件失败：{}:",downFileUrl,e);
//                                failcount.incrementAndGet();
//                            }
//                        }
//                    }
//                });
//            }
//            try{
//                Thread.sleep(500);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //终止
//    public void stop(){
//        logger.info("终止上传文件、文件下载=====================");
//        flag = false;
//        try {
//            executorService.shutdown();
//            logger.info("线程池内已shutdown");
//            // (所有的任务都结束的时候，返回TRUE)
//            if(!executorService.awaitTermination(20000L, TimeUnit.MILLISECONDS)){
//                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
//                executorService.shutdownNow();
//            }
//            logger.info("线程池内已无任务，已关闭 :执行成功数量:{},执行失败数量:{}",count.get(),failcount.get());
//            count = new AtomicInteger(0);
//            failcount = new AtomicInteger(0);
//        } catch (InterruptedException e) {
//            logger.error("awaitTermination interrupted: " , e);
//            executorService.shutdownNow();
//        }
//    }
//
//    //修改线程池数量
//    public void updateThreadCount(int corePoolSize, int maximumPoolSize){
//        logger.info("修改线程池大小：corePoolSize:{} maximumPoolSize:{}",corePoolSize,maximumPoolSize);
//        this.stop();
//        initThreadPool(corePoolSize, maximumPoolSize);
//    }
//
//    //初始化线程池
//    private void initThreadPool(int corePoolSize, int maximumPoolSize){
//        logger.info("修改线程池初始化：corePoolSize:{} maximumPoolSize:{}",corePoolSize,maximumPoolSize);
//        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//    }
//
//    //上传文件并返回下载的url
//    private String uploadFile(String tenantId, String folder, InputStream is, Long size, String fileName) throws Exception{
//       Result<UploadFileVo> uploadFolder = fileS2Service.uploadFolder(tenantId, folder, is, size, fileName);
//       UploadFileVo data = null;
//       if(uploadFolder.code == 200){
//           data = uploadFolder.getData();
//       }
//       //返回下载地址，进行下载操作
//       if(null != data && null != data.getUrl()){
//           return data.getUrl();
//       }
//       return null;
//    }
//
//    //根据上传返回的url获取文件流   获取到后不做保存
//    private HttpURLConnection getInputStreamByUrl(String fileUrl) throws IOException{
//        //根据url获取输入流
//        URL url = new URL(fileUrl);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        //设置超时间为3秒
//        conn.setConnectTimeout(3*1000);
//        //防止屏蔽程序抓取而返回403错误
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        //得到输入流
//        InputStream inputStream = conn.getInputStream();
//        if(null == inputStream || inputStream.available()<=0 || null == conn || conn.getContentLength() <= 0){
//            logger.info("文件资源为空===============================");
//            return null;
//        }
//        return conn;
//    }
//
//}
