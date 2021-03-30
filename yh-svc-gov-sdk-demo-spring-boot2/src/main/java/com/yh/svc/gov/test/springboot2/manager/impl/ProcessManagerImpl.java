package com.yh.svc.gov.test.springboot2.manager.impl;

import com.yh.svc.gov.test.springboot2.command.OrderVo;
import com.yh.svc.gov.test.springboot2.manager.ProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luchao 2018-08-06
 */
@Service("processManager")
public class ProcessManagerImpl implements ProcessManager {
    private static final Logger logger = LoggerFactory.getLogger(ProcessManagerImpl.class);

    @Value("${sdk-demo-sb2.app-id}")
    private String curapp;


    @Override
    public String receiveData(String appId, String node, List<OrderVo> orders, String comment) {

        List<String> ordercode = orders.stream().map(x -> x.getOrderCode()).collect(Collectors.toList());

        logger.info("received {}, {}, {}, order code: {} ", appId, node, comment, ordercode);
        String ret = "";
        String com = comment + " -> " + appId + "." + node;

        for (OrderVo o : orders) {
        	internalCall(appId, orders.size(), o.getOrderCode());	
		}
        
        return ret;
    }


    private void internalCall(String appId, int orderSize, String oc) {
    	logger.debug("enter the internalCall {},{}, {}", appId, orderSize, oc);
    }

    public void voidMethod(String appId, int orderSize) {
    	logger.debug("enter the voidMethod {},{}", appId, orderSize);
    }

    @Override
    public List<OrderVo> transData(String appId, String node, List<OrderVo> orders, String comment) {
        List<String> ordercode = orders.stream().map(x -> x.getOrderCode()).collect(Collectors.toList());

        logger.info("received {}, {}, {}, order code: {} ", appId, node, comment, ordercode);
        String ret = "";
        String com = comment + " -> " + appId + "." + node;

        List<OrderVo> nlist = orders.stream().map(x -> {
            OrderVo vo = new OrderVo();
            vo.setAddress(x.getAddress());
            vo.setAmount(x.getAmount());
            vo.setDetails(x.getDetails());
            vo.setName(x.getName());
            vo.setOrderCode(x.getOrderCode() + "-" + curapp);
            vo.setStatus(x.getStatus());
            return vo;
        }).collect(Collectors.toList());

        return nlist;
    }

}
