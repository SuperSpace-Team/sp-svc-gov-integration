package com.sp.infra.svc.gov.sdk.exception;

import java.io.Serializable;

/**
 * 
 * @author Alex Lu
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 665112595508596601L;

    private int errorCode;

    private Serializable[] args;

    private String message;

    private BusinessException linkedException;

    public BusinessException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(int errorCode, Serializable[] args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    /**
     * add at 2018-8-1 15:46:15
     * @since 2.1.3
     * @param message
     * @param throwable
     */
    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Serializable[] args) {
        this.args = args;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException getLinkedException() {
        return linkedException;
    }

    public void setLinkedException(BusinessException linkedException) {
        this.linkedException = linkedException;
    }
}
