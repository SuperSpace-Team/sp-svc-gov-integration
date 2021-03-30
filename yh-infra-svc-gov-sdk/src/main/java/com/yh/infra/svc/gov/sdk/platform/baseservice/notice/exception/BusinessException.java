package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import sun.reflect.Reflection;

@SuppressWarnings("restriction")
public class BusinessException extends RuntimeException {

//	final Logger logger = LoggerFactory.getLogger(BusinessException.class);
	
    private static final long serialVersionUID = 665112595508596601L;

    private int errorCode;

    private Object[] args;

    private String message;

    private BusinessException linkedException;

    public BusinessException(int errorCode) {
        super();
        this.errorCode = errorCode;
       // logger.error("error of class:" + Reflection.getCallerClass(2).getName() + " - " + this.errorCode);
    }

    public BusinessException(String message) {
        this.message = message;
       // logger.error("error of class:" + Reflection.getCallerClass(2).getName() + " - " + this.message);
    }

    public BusinessException(int errorCode, Object[] args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
       // logger.error("error of class:" + Reflection.getCallerClass(2).getName() + " - " + this.errorCode);
    }

    public BusinessException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
      //  logger.error("error of class:" + Reflection.getCallerClass(2).getName() + " - " + this.errorCode);
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

    public void setArgs(Object[] args) {
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
