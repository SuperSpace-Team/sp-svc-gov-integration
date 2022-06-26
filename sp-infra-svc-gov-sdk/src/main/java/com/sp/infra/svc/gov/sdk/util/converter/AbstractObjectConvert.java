/*
 * AbstractObjectConvert.java Created On 2017年11月29日
 * Copyright(c) 2017 Yonghui Inc.
 * ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.util.converter;


/**
 * AbstractObjectConvert
 *
 * @time: 上午10:09:33
 * @author mazan
 */
public abstract class AbstractObjectConvert<T> implements Convert<T> {
    /**
     * 公共转换模板
     */
    @Override
    public T convert(Object source) throws ConvertException {
        if (source == null) {
            throw new ConvertException("source is null");
        }
        try {
            return parse(source);
        } catch (Exception e) {
            throw new ConvertException("source convert failed", e);
        }
    }
    
    /**
     * 具体的转换方法
     * @param source
     * @return
     */
    protected abstract T parse(Object source);
}

