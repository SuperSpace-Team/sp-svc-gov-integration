package com.yh.svc.gov.test.springboot2.manager;

import com.yh.svc.gov.test.springboot2.command.OrderVo;

import java.util.List;

public interface ProcessManager {
    public String receiveData(String appId, String node, List<OrderVo> orders, String comment);

    public List<OrderVo> transData(String appId, String node, List<OrderVo> orders, String comment);

    void voidMethod(String appId, int orderSize);
}
