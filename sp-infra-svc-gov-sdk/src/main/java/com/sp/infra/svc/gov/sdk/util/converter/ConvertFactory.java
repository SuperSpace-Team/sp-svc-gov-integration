/*
 * ConvertFactory.java Created On 2017年11月28日 Copyright(c) 2017 Yonghui Inc. ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.util.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * ConvertFactory 工厂模式
 *
 * @author mazan
 * @time: 下午7:41:32
 * @see http://314858770.iteye.com/blog/1634559
 */
public class ConvertFactory {

    /**
     * 策略if...else --> map.get
     */
    public static Map<String, Convert<?>> convertHandlers = new HashMap<>();

    private ConvertFactory() {
    }

    /**
     * @see org.springframework.core.convert.support.DefaultConversionService
     */
    static {
        addDefaultConverters(Character.class, new ObjectToCharConvert());
        addDefaultConverters(Short.class, new ObjectToShortConvert());
        addDefaultConverters(Integer.class, new ObjectToIntegerConvert());
        addDefaultConverters(Long.class, new ObjectToLongConvert());
        addDefaultConverters(Float.class, new ObjectToFloatConvert());
        addDefaultConverters(Double.class, new ObjectToDoubleConvert());
        addDefaultConverters(Byte.class, new ObjectToByteConvert());
        addDefaultConverters(Boolean.class, new ObjectToBooleanConvert());
        addDefaultConverters(Date.class, new ObjectToDateConvert());
        
        addDefaultConverters(char.class, new ObjectToCharConvert());
        addDefaultConverters(short.class, new ObjectToShortConvert());
        addDefaultConverters(int.class, new ObjectToIntegerConvert());
        addDefaultConverters(long.class, new ObjectToLongConvert());
        addDefaultConverters(float.class, new ObjectToFloatConvert());
        addDefaultConverters(double.class, new ObjectToDoubleConvert());
        addDefaultConverters(byte.class, new ObjectToByteConvert());
        addDefaultConverters(boolean.class, new ObjectToBooleanConvert());
        
    }

    /**
     * 初始化
     * @param clazz
     * @param converter
     */
    private static void addDefaultConverters(Class<?> targetClazz, Convert<?> converter) {
        convertHandlers.put(String.class.getName() + "To" + targetClazz.getName(), converter);
    }
    /**
     * 选择相应的转换策略
     *
     * @param clazz target
     * @param val   source
     * @return
     * @throws ConvertException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> clazz, Object val) throws ConvertException {
        Convert<?> cv = convertHandlers.get(val.getClass().getName() + "To" + clazz.getName());
        if (cv == null) {
            throw new ConvertException(val.getClass().getSimpleName() + "To" + clazz.getSimpleName() + " convert failed: unsupport type");
        }
        return (T) cv.convert(val);
    }
}

/**
 * Object -> Boolean
 *
 * @author zan.ma
 */
class ObjectToBooleanConvert extends AbstractObjectConvert<Boolean> {

    @Override
    protected Boolean parse(Object source) {
        return Boolean.parseBoolean(source.toString().trim());
    }

}


/**
 * Object -> Byte
 *
 * @author zan.ma
 */
class ObjectToByteConvert extends AbstractObjectConvert<Byte> {

    @Override
    protected Byte parse(Object source) {
        return Byte.parseByte(source.toString().trim());
    }

}

/**
 * Object -> Char
 *
 * @author zan.ma
 */
class ObjectToCharConvert extends AbstractObjectConvert<Character> {

    @Override
    protected Character parse(Object source) {
        return Character.valueOf(source.toString().trim().charAt(0));
    }

}

/**
 * Object -> Date
 *
 * @author zan.ma
 */
class ObjectToDateConvert extends AbstractObjectConvert<Date> {

    Object[][] patterns = {
            {Pattern.compile("^\\d+-\\d+-\\d+$"), "yyyy-MM-dd"},
            {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+$"), "yyyy-MM-dd HH:mm"},
            {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+:\\d+$"), "yyyy-MM-dd HH:mm:ss"},
            {Pattern.compile("^\\d+/\\d+/\\d+$"), "yyyy/MM/dd"},
            {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+$"), "yyyy/MM/dd HH:mm"},
            {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+:\\d+$"), "yyyy/MM/dd HH:mm:ss"}
    };

    @Override
    protected Date parse(Object source) {
        String val = source.toString().trim();
        //确定日期格式
        String format = null;
        Pattern p;
        for (Object[] item : patterns) {
            p = (Pattern) item[0];
            if (p.matcher(val).matches()) {
                format = item[1].toString();
                break;
            }
        }
        //日期格式未定义
        if (format == null) {
            throw new IllegalArgumentException("ObjectToDateConvert failed, unsupport format: " + source);
        }

        // 日期转换
        try {
            return new SimpleDateFormat(format).parse(val);
        } catch (ParseException e) {
            throw new IllegalArgumentException("ObjectToDateConvert failed, bad value: " + source, e);
        }
    }
}

/**
 * Object -> Double
 *
 * @author zan.ma
 */
class ObjectToDoubleConvert extends AbstractObjectConvert<Double> {

    @Override
    protected Double parse(Object source) {
        return Double.parseDouble(source.toString().trim());
    }

}

/**
 * Object -> Float
 *
 * @author zan.ma
 */
class ObjectToFloatConvert extends AbstractObjectConvert<Float> {

    @Override
    protected Float parse(Object source) {
        return Float.parseFloat(source.toString().trim());
    }

}

/**
 * Object -> Integer
 *
 * @author zan.ma
 */
class ObjectToIntegerConvert extends AbstractObjectConvert<Integer> {

    @Override
    protected Integer parse(Object source) {
        return Integer.parseInt(source.toString().trim());
    }

}

/**
 * Object -> Long
 *
 * @author zan.ma
 */
class ObjectToLongConvert extends AbstractObjectConvert<Long> {

    @Override
    protected Long parse(Object source) {
        return Long.parseLong(source.toString().trim());
    }

}

/**
 * Object -> Short
 *
 * @author zan.ma
 */
class ObjectToShortConvert extends AbstractObjectConvert<Short> {

    @Override
    protected Short parse(Object source) {
        return Short.parseShort(source.toString().trim());
    }

}
