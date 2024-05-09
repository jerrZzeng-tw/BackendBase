package com.iisi.backendbase.framework;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResponseData {

  // @ApiModelProperty(value = "狀態，成功:Y/失敗:N")
  private String status;
  // @ApiModelProperty(value = "狀態代碼")
  private String statusCode;
  // @ApiModelProperty(value = "訊息")
  private String message;
  // @ApiModelProperty(value = "回應時間")
  // @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone =
  // "GMT+8")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dateTime;

  private Object data;

  public ResponseData(String status, String statusCode, String message, Object data) {
    this.status = status;
    this.statusCode = statusCode;
    this.message = message;
    this.dateTime = LocalDateTime.now();
    this.data = data;
  }

  public ResponseData(String status, StatusCode statusCode, Object data) {
    this.status = status;
    this.statusCode = statusCode.getCode();
    this.message = statusCode.getMsg();
    this.dateTime = LocalDateTime.now();
    this.data = data;
  }


}