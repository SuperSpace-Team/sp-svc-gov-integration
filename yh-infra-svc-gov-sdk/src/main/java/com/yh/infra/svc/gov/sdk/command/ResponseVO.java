package com.yh.infra.svc.gov.sdk.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应对象VO基类
 * 适用于与前台界面交互响应属性定义
 *
 * @author alex.lu
 * @date 2021/1/30 12:19
 */
@Data
public class ResponseVO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -2217657233996590214L;

    /**
     * 是否成功标识
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回响应码
     * 当编码为20000~30000范围默认为成功标识
     */
    private Integer code;

    /**
     * 返回对象
     */
    private Object data;

    public ResponseVO(){}

    public ResponseVO(Integer code,String msg){
        this.msg = msg;
        this.code = code;
    }

    public ResponseVO(Boolean success,String msg, Integer code){
        this.msg = msg;
        this.code = code;
        this.success = success;
    };

    public ResponseVO(String msg,int code,Object data){
        this(code,msg);
        this.data = data;
    };

    public ResponseVO(boolean success,String msg, Integer code,Object data){
        this(success, msg, code);
        this.data = data;
    }
}
