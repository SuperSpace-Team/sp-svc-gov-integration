package com.yh.infra.svc.gov.sdk.command;


import lombok.Data;

import java.io.Serializable;

/**
 * 响应对象VO基类
 *
 * @author alex.lu
 * @date 2021/1/30 12:19
 */
@Data
public class ResponseVO<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2805467424072824831L;

    /**
     * 是否成功标识
     */
    private Boolean success = false;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回响应码
     */
    private Integer code = 0;

    /**
     * 返回对象
     */
    private T data;

    public ResponseVO() {
    }

}
