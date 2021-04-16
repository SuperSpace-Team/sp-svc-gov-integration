package com.yh.infra.svc.gov.consul.retry.thread;

import com.yh.infra.svc.gov.consul.retry.config.ConsulRetryProperties;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

import java.util.List;

public class RetryThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RetryThread.class);

	private ConsulAutoRegistration consulAutoRegistration;
	private ConsulServiceRegistry consulServiceRegistry;
	private ConsulRetryProperties properties;
	private ConsulClient client;
	private boolean exit = false;

	public RetryThread(ConsulAutoRegistration consulAutoRegistration, ConsulServiceRegistry consulServiceRegistry, ConsulRetryProperties properties, ConsulClient client) {
		this.consulAutoRegistration = consulAutoRegistration;
		this.consulServiceRegistry = consulServiceRegistry;
		this.properties = properties;
		this.client = client;
	}

	@Override
	public void run() {
		final NewService service = this.consulAutoRegistration.getService();
		logger.info("consul服务监测已启动 {}, 启用状态 {} ", service.toString(),String.valueOf(properties.isEnable()));
		while (!exit) {
			try {
				long initialInterval = this.properties.getRetryInterval() < 1000 ? 1000 : this.properties.getRetryInterval();
				Thread.sleep(initialInterval);
				if (properties.isEnable() && !this.checkStatus(service)) {
					// 重新注册
					consulServiceRegistry.register(consulAutoRegistration);
					logger.info("consul服务重新注册成功【{}】", service);
				}
			} catch (Exception e) {
				logger.error("consul服务当前注册失败，准备下一次注册【{}】", service, e);
			}
		}
	}

	/**
	 * 检查服务状态
	 * 
	 * @param service 服务
	 * @return 返回布尔值，正常true，异常false
	 */
	private boolean checkStatus(NewService service) {
		// 检测标志
		boolean flag = false;
		try {
			Response<List<Check>> response = client.getHealthChecksForService(consulAutoRegistration.getServiceId(), QueryParams.DEFAULT);
			List<Check> checks = response.getValue();
			if (checks.size() == 0) {
				return false;
			}
			for (Check check : checks) {
				if (check.getServiceId().equals(service.getId())) {
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("consul服务心跳检测发生异常", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("consul服务心跳检测结束，检测结果为：{}", flag ? "正常" : "异常");
		}
		return flag;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
}
