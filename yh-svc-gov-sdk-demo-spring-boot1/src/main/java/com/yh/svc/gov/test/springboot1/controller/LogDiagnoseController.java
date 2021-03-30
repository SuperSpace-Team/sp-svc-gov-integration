package com.yh.svc.gov.test.springboot1.controller;

import com.yh.svc.gov.test.springboot1.command.OrderVo;
import com.yh.svc.gov.test.springboot1.manager.LogDiagnoseManager;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 15:05 2019/12/30
 * @Modified by:
 */
@RestController
@RequestMapping("/log/diagnose")
public class LogDiagnoseController {

    @Resource
    private LogDiagnoseManager logDiagnoseManager;

    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id){
        OrderVo orderVo = new OrderVo();
        orderVo.setAddress("china");
        return logDiagnoseManager.process(id,orderVo);
    }
}
