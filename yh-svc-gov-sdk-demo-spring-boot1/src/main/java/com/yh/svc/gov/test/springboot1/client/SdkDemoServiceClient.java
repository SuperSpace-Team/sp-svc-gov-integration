/**
 *
 */
package com.yh.svc.gov.test.springboot1.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yh.svc.gov.test.springboot1.command.MessageVo;

@FeignClient(name = "${sdk-demo.chain.next.service.id}",url = "${sdk-demo.chain.next.service.url}")
public interface SdkDemoServiceClient {

	@PostMapping("/receive")
	String receive(@RequestBody MessageVo msg);

	@PostMapping("/trans")
	String trans(@RequestBody MessageVo msg);
}
