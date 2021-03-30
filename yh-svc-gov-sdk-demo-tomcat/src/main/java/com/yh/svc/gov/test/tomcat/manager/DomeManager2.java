package com.yh.svc.gov.test.tomcat.manager;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service("domeManager2")
public class DomeManager2 {
    public void test(Map<String, Object> map) {
        System.out.println(map.size());
    }
}
