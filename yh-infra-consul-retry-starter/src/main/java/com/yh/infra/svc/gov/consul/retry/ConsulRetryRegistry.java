package com.yh.infra.svc.gov.consul.retry;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.stereotype.Component;

import com.yh.infra.svc.gov.consul.retry.config.ConsulRetryProperties;
import com.yh.infra.svc.gov.consul.retry.thread.RetryThread;
import com.ecwid.consul.v1.ConsulClient;


/**
 * consul服务重新注册
 * 
 * 无论是否开启了  consul 重试。 都启动本线程。
 * 如果是关闭状态， 线程会空跑。
 * 
 */
@ConditionalOnClass({
        ConsulDiscoveryClient.class,
        ConsulAutoRegistration.class,
        ConsulServiceRegistry.class
})
public class ConsulRetryRegistry implements CommandLineRunner {
    private ConsulAutoRegistration consulAutoRegistration;
    private ConsulServiceRegistry consulServiceRegistry;
    private ConsulRetryProperties properties;
    private ConsulClient client;

    public ConsulRetryRegistry(
            ConsulAutoRegistration consulAutoRegistration,
            ConsulServiceRegistry consulServiceRegistry,
            ConsulRetryProperties properties,
            ConsulClient client
    ) {
        this.consulAutoRegistration = consulAutoRegistration;
        this.consulServiceRegistry = consulServiceRegistry;
        this.properties = properties;
        this.client = client;
    }

    @Override
    public void run(String... args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        RetryThread retryThread = new RetryThread(consulAutoRegistration,consulServiceRegistry,properties,client);
        executorService.execute(retryThread);
    }
}

