package com.yh.infra.svc.gov.sdk.util;

import com.yh.infra.svc.gov.sdk.util.converter.ConvertException;
import com.yh.infra.svc.gov.sdk.util.converter.ConvertFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Date;

/**
 * SpringConvertTest
 *
 * @time: 上午10:05:10
 * @author mazan
 */
@Ignore
public class SpringConvertTest {

    protected ConfigurableConversionService conversionService = new DefaultConversionService();

    @Before
    public void setup() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testConvert() throws ConvertException {

        // GenericConverter converter
        System.out.println("----------------------my converter------------------------------");
        // Object -> Boolean
        System.out.println(ConvertFactory.convert(Boolean.class, "true"));
        // Object -> Byte
        System.out.println(ConvertFactory.convert(Byte.class, "-123"));
        // Object -> Char
        System.out.println(ConvertFactory.convert(Character.class, "a"));
        // Object -> Date
        System.out.println(ConvertFactory.convert(Date.class, "2019-03-04"));
        System.out.println(ConvertFactory.convert(Date.class, "2019-03-04 10:37"));
        System.out.println(ConvertFactory.convert(Date.class, "2019-03-04 10:37:02"));
        System.out.println(ConvertFactory.convert(Date.class, "2019/03/04"));
        System.out.println(ConvertFactory.convert(Date.class, "2019/03/04 10:38"));
        System.out.println(ConvertFactory.convert(Date.class, "2019/03/04 10:38:03"));
        // Object -> Double
        System.out.println(ConvertFactory.convert(Double.class, "3.1415926535"));
        // Object -> Float
        System.out.println(ConvertFactory.convert(Float.class, "3.1415926535"));
        // Object -> Integer
        System.out.println(ConvertFactory.convert(Integer.class, "123456789"));
        // Object -> Long
        System.out.println(ConvertFactory.convert(Long.class, "123456789123456789"));
        // Object -> Short
        System.out.println(ConvertFactory.convert(Short.class, "12345"));

    }



    @Test
    public void testSpringConvert() throws ConvertException {

        System.out.println("----------------------spring converter------------------------------");
        conversionService.addConverterFactory(new StringToDateConverterFactory());
        // Object -> Boolean
        System.out.println(conversionService.convert("true", Boolean.class));
        // Object -> Byte
        System.out.println(conversionService.convert("-123", Byte.class));
        // Object -> Char
        System.out.println(conversionService.convert("a", Character.class));
        // Object -> Date
        System.out.println(conversionService.convert("2019-03-04", Date.class));
        System.out.println(conversionService.convert("2019-03-04 10:37", Date.class));
        System.out.println(conversionService.convert("2019-03-04 10:37:02", Date.class));
        System.out.println(conversionService.convert("2019/03/04", Date.class));
        System.out.println(conversionService.convert("2019/03/04 10:38", Date.class));
        System.out.println(conversionService.convert("2019/03/04 10:38:03", Date.class));
        // Object -> Double
        System.out.println(conversionService.convert("3.1415926535", Double.class));
        // Object -> Float
        System.out.println(conversionService.convert("3.1415926535", Float.class));
        // Object -> Integer
        System.out.println(conversionService.convert("123456789", Integer.class));
        // Object -> Long
        System.out.println(conversionService.convert("123456789123456789", Long.class));
        // Object -> Short
        System.out.println(conversionService.convert("12345", Short.class));
    }


}

