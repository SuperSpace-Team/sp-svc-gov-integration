package com.sp.infra.svc.gov.sdk.util.converter;
/**
 * ConvertException
 *
 * @time: 上午9:52:52
 * @author mazan
 */
public class ConvertException extends Exception {

    /**
     * 类型转换异常
     */
    private static final long serialVersionUID = 1L;

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable e) {
        super(message, e);
    }
}

