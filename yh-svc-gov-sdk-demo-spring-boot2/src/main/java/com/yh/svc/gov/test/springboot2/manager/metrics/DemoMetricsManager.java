/**
 * 
 */
package com.yh.svc.gov.test.springboot2.manager.metrics;

import com.yh.infra.svc.gov.metrics.constant.MetricsType;
import com.yh.infra.svc.gov.metrics.meter.MetricsHelper;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author luchao  2020-12-10
 *
 */
@Component
public class DemoMetricsManager  {
	private static final Logger logger = LoggerFactory.getLogger(DemoMetricsManager.class);

	@Autowired
	private MetricsHelper helper;
	
	@Timed(value = "yh.infra.demo.createorder.timer.test1", extraTags = {"app", "demo-sb2", "project", "YhInfra"})
	@Counted(value = "yh.infra.demo.createorder.counter.test1", extraTags = {"app", "demo-sb2", "project", "YhInfra"})
	public void createOrder(int sleepMs) {
		logger.debug("create order ... {}", sleepMs);
		
		
		////////////////////////////
		//这里放 要统计执行时间   的 业务逻辑代码 
		////////////////////////////
		
		
		//下面是mock，用于生成测试数据。
		try {
			Thread.sleep(sleepMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// mock 结束
	}

	/**
	 * 自定义使用timer 指标
	 * 
	 * @param sleepMs
	 */
	public void createDo(int sleepMs) {
		logger.debug("create do ... {}", sleepMs);
		
		long start = System.currentTimeMillis();
		
		////////////////////////////
		//这里放 要统计执行时间   的 业务逻辑代码 
		////////////////////////////

		
		//下面是mock，用于生成测试数据。
		try {
			Thread.sleep(sleepMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// mock 结束
		
		
		long end = System.currentTimeMillis();
		
		
		// 记录timer 指标的值
		helper.update("yh.infra.demo.bz.timer.test1.dev", MetricsType.TIMER, 0, end-start, TimeUnit.MILLISECONDS, "app", "demo-sb2", "project", "YhInfra", "function", "demo-sb2-createDo");
	}
	
	
	double max = 0;
	double sum = 0;
	long count = 0;
	/**
	 * 自定义使用summary指标
	 * 
	 * @param sleepMs
	 */
	public void createSo() {
		
		double summaryValue = 0;
		
		////////////////////////////
		//这里放 要写入summary 指标的 代码
		// summaryValue = .....
		////////////////////////////
		
		
		//下面是mock，用于生成测试数据。
		Random r = new Random();
		summaryValue = Math.abs(r.nextInt(100));
		double s2 = Math.abs(r.nextInt(100));
		double s3 = Math.abs(r.nextInt(100));
		double s4 = Math.abs(r.nextInt(100));
		logger.debug("create so ... {}, {}, {}, {}", summaryValue, s2, s3,s4);
		count += 4;
		max = Math.max(max, summaryValue);
		max = Math.max(max, s2);
		max = Math.max(max, s3);
		max = Math.max(max, s4);
		sum += (summaryValue + s2 + s3 + s4);
		logger.debug("create so ... {}, {}, {}, {}, count : {}, max: {}, sum: {}", summaryValue, s2, s3,s4, count, max, sum);
		// mock 结束


		// 记录summary 指标的值
		helper.update("yh_infra_demo_yh_summary_test1_dev", MetricsType.SUMMARY, summaryValue, 0, null, "app", "demo-sb2", "project", "YhInfra");

		
		
		//下面是mock，用于一次生成多条测试数据。
		helper.update("yh_infra_demo_yh_summary_test1_dev", MetricsType.SUMMARY, s2, 0, null, "app", "demo-sb2", "project", "YhInfra");
		helper.update("yh_infra_demo_yh_summary_test1_dev", MetricsType.SUMMARY, s3, 0, null, "app", "demo-sb2", "project", "YhInfra");
		helper.update("yh_infra_demo_yh_summary_test1_dev", MetricsType.SUMMARY, s4, 0, null, "app", "demo-sb2", "project", "YhInfra");

		helper.update("yh_infra_demo_yh_summary_test2_dev", MetricsType.SUMMARY, summaryValue, 0, null);
		helper.update("yh_infra_demo_yh_summary_test2_dev", MetricsType.SUMMARY, s2, 0, null);
		helper.update("yh_infra_demo_yh_summary_test2_dev", MetricsType.SUMMARY, s3, 0, null);
		helper.update("yh_infra_demo_yh_summary_test2_dev", MetricsType.SUMMARY, s4, 0, null);
		// mock 结束
	}
	
	
}
