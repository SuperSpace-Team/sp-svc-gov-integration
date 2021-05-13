package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public class BackEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean resultFlag;

    private String errorMsg;

    private int errorCode;

    private String data;

    public boolean isResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
