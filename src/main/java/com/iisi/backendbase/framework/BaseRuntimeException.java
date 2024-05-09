package com.iisi.backendbase.framework;



public class BaseRuntimeException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private Object data;

    public BaseRuntimeException(StatusCode statusCode) {
        super(statusCode.getMsg());
        errorCode = statusCode.getCode();
        errorMessage = statusCode.getMsg();
    }

    public BaseRuntimeException(StatusCode statusCode, Object data) {
        super(statusCode.getMsg());
        errorCode = statusCode.getCode();
        errorMessage = statusCode.getMsg();
        this.data = data;
    }

    public BaseRuntimeException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseRuntimeException(String errorCode, String errorMessage, Object data) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public BaseRuntimeException(String errorMessage) {
        super(errorMessage);
        errorCode = StatusCode.SYS_ERROR.getCode();
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}