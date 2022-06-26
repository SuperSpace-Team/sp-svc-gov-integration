package com.sp.infra.svc.gov.consul.retry.config;

import com.sp.infra.svc.gov.consul.retry.ConsulRetryRegistry;
import com.ecwid.consul.v1.ConsulClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * consul重试自动配置
 * @author xsx
 * @date 2019/10/30
 * @since 1.8
 */
@Configuration
@ConditionalOnClass({
        ConsulDiscoveryClient.class,
        ConsulAutoRegistration.class,
        ConsulServiceRegistry.class
})
@ConditionalOnProperty(name = "spring.cloud.consul.discovery.register", havingValue = "true", matchIfMissing = true)
@Import({ConsulRetryProperties.class})
public class ConsulRetryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({ConsulRetryRegistry.class})
    public ConsulRetryRegistry consulRetryRegistry(
            ConsulAutoRegistration consulAutoRegistration,
            ConsulServiceRegistry consulServiceRegistry,
            ConsulRetryProperties properties,
            ConsulClient client
    ) {
        return new ConsulRetryRegistry(
                consulAutoRegistration,
                consulServiceRegistry,
                properties,
                client
        );
    }
}
