package com.yh.svc.gov.test.springboot1.manager.impl;

import com.yh.svc.gov.test.springboot1.command.MessageVo;
import com.yh.svc.gov.test.springboot1.command.OrderVo;
import com.yh.svc.gov.test.springboot1.model.OrderCancel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/30/21 2:54 PM
 */
@Service("testProcessManager")
public class TestProcessManagerImpl extends AbstractManagerImpl<OrderCancel>{
    @Override
    public String receiveDataNested(String appId, String node, List<OrderVo> orders, String comment, int nested) {
        return "test";
    }

    @Override
    public String receiveData(String appId, String node, List<OrderVo> orders, String comment, int pInt) {
        return "test";
    }

    @Override
    public List<OrderVo> transData(String appId, String node, List<OrderVo> orders, String comment) {
        return new ArrayList<>();
    }

    @Override
    public void voidMethod(String appId, int orderSize) {

    }

    @Override
    public void testObjectHasList(MessageVo messageVo) {

    }

    @Override
    public void internalCall(OrderCancel orderCancel) {

    }
}
