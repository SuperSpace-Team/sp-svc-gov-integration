package com.yh.infra.svc.gov.sdk.util;

import org.junit.After;
import org.junit.Test;

import java.util.Date;

/**
 * DateTimeHelperTest
 *
 * @time: 下午5:59:58
 * @author mazan
 */
public class DateTimeHelperTest {

    @After
    public void tearDown() throws Exception {}

    @Test
    public void test() {
        Date d = new Date();
        
        String dstr = DateTimeHelper.toString(d);
        System.out.println(dstr);
        
        Date dparse = DateTimeHelper.getDate(dstr);
        System.out.println(dparse);
        
        
    }

}

