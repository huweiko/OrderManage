package com.order.manage.http;

public class SeverRequestException extends Exception {

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String errorMsg;

    public SeverRequestException(int code, String msg)
    {
        super(msg);
        this.errorCode = code;
        this.errorMsg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
