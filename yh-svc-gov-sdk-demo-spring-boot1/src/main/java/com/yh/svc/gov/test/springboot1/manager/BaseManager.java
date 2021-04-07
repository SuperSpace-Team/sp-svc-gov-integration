package com.yh.svc.gov.test.springboot1.manager;

import java.util.List;
import com.yh.svc.gov.test.springboot1.command.MessageVo;
import com.yh.svc.gov.test.springboot1.command.OrderVo;

public interface BaseManager<T> {

	public String receiveDataNested(String appId, String node, List<OrderVo> orders, String comment, int nested);
	public String receiveData(String appId, String node, List<OrderVo> orders, String comment, int pInt);

    public List<OrderVo> transData(String appId, String node, List<OrderVo> orders, String comment);

    void voidMethod(String appId, int orderSize);
    void service(T t);
    void genericTypeTest(T t);

    void testObjectHasList(MessageVo messageVo);
}
