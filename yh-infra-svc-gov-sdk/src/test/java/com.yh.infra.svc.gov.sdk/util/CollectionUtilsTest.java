/*
 * CollectionUtilsTest.java Created On 2019年3月5日
 * Copyright(c) 2019 Baozun Inc.
 * ALL Rights Reserved.
 */
package com.yh.infra.svc.gov.sdk.util;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * CollectionUtilsTest
 *
 * @time: 下午3:38:08
 * @author mazan
 */
public class CollectionUtilsTest {

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testStringList() {
        List<String> list = new ArrayList<String>();
        list.add("DEBUG");
        list.add("INFO");
        list.add("WARN");
        list.add("ERROR");
        
        
        String str = CollectionUtils.List2String(list);
        System.out.println(str);
        
        List<String> list2 = CollectionUtils.String2List(str);
        
        System.out.println(list2);
        
        
    }

}

