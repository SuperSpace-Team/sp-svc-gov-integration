package com.yh.svc.gov.test.tomcat.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("domeManager")
public class DomeManager {
    @Autowired
    DomeManager1 manager1;
    public String testManager() {
    	System.out.println("-----------DomeManager.testManager");

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        manager1.test(list);
        return "test manager";
    }
}
