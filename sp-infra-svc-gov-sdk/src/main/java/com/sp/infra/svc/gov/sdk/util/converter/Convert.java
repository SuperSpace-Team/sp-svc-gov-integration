/*
 * Converter.java Created On 2017年11月28日
 * Copyright(c) 2017 SuperSpace Inc.
 * ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.util.converter;

/**
 * Convert
 * 转换参数类型
 * @see org.springframework.core.convert.converter.Converter
 * 
 * @see org.apache.commons.beanutils.Converter
 * @time: 下午7:35:36
 * @author mazan
 * public interface Convert<S, T> -->S==Object
 */
public interface Convert<T> {
    /**
     * 对象转换方法
     * @param source
     * @return
     * @throws ConvertException
     */
    T convert(Object source) throws ConvertException;  
}

