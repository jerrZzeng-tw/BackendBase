package com.iisi.backendbase.framework;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseData {
    // @ApiModelProperty(value = "狀態代碼")
    private Integer statusCode;
    // @ApiModelProperty(value = "訊息")
    private String message;
    // @ApiModelProperty(value = "回應時間")
    // @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone =
    // "GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    private Object data;

    public ResponseData(Integer statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.dateTime = LocalDateTime.now();
        this.data = data;
    }

    public ResponseData(StatusCode statusCode, Object data) {
        this.statusCode = statusCode.getCode();
        this.message = statusCode.getMsg();
        this.dateTime = LocalDateTime.now();
        this.data = data;
    }

    public ResponseData(StatusCode statusCode) {
        this.statusCode = statusCode.getCode();
        this.message = statusCode.getMsg();
        this.dateTime = LocalDateTime.now();
    }

}