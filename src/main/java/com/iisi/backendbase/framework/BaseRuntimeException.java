package com.iisi.backendbase.framework;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseRuntimeException extends RuntimeException {

    private Integer errorCode;
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

    public BaseRuntimeException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseRuntimeException(Integer errorCode, String errorMessage, Object data) {
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
    
}