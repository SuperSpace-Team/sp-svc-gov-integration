package com.yh.infra.svc.gov.sdk.testhelper;

import java.util.Map;

public class MockCallback {
    public boolean validate(Map<String, Object> data) {
    	return true;
    }

    public void process(Map<String, Object> data) {
    }
}
