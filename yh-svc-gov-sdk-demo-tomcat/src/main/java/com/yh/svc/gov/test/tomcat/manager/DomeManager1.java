package com.yh.svc.gov.test.tomcat.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("domeManager1")
public class DomeManager1 {
    @Autowired
    DomeManager2 manager2;

    public void test(List<String> params) {
        System.out.println(params.size());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","a");
        map.put("2",2);
        map.put("3",new Date());
        manager2.test(map);
    }
}
