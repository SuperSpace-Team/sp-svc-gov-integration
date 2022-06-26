package com.sp.infra.svc.gov.sdk.testhelper;


import com.sp.infra.svc.gov.sdk.init.callback.CallbackService;

import java.util.Map;

public class DemoCallbackService implements CallbackService {

	boolean v = true;
	
	
	
	
	@Override
	public String getCallbackName() {
		return "demo_callback_service";
	}

	@Override
	public boolean validate(Map<String, Object> data) {
		return v;
	}

	@Override
	public void process(Map<String, Object> data) {
	}

}
