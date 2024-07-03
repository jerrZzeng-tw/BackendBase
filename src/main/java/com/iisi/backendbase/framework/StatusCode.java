package com.iisi.backendbase.framework;

public enum StatusCode {
    SUCCESS(0, "成功"), //
    NO_DATA(1, "查無資料"),
    SYS_ERROR(99, "系統錯誤"), //
    LOGIN_ERROR(100, "使用者或密碼錯誤"),
    AUTH_ERROR(101, "認證錯誤"),
    ACCESS_ERROR(102, "權限不足"),//
    ARG_ERROR(200, "參數驗證錯誤"), //
    LOGIC_ERROR(201, "操作失敗"), //
    ;
    private final int code;
    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}