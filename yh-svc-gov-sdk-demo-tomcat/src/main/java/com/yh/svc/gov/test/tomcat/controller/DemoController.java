package com.yh.svc.gov.test.tomcat.controller;

import com.yh.svc.gov.test.tomcat.manager.DomeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    DomeManager manager;

    @RequestMapping(value = "/test")
    @ResponseBody
    public Object test() {
    	System.out.println("-----------DemoController.test");
        return manager.testManager();
    }

}
