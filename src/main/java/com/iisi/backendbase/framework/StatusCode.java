package com.iisi.backendbase.framework;

public enum StatusCode {
    SUCCESS("0", "成功"), //
    LOGIN_ERROR("ER0001", "使用者或密碼錯誤"),
    AUTH_ERROR("ER0002", "認證錯誤"),//
    SYS_ERROR("ER0100", "系統錯誤"), //
    ARG_ERROR("ER0101", "參數錯誤"), //
    LOGIC_ERROR("ER0102", "邏輯錯誤"), //
    INSERT_ERROR("ER0110", "新增失敗"), //
    DELETE_ERROR("ER0120", "刪除失敗"), //
    UPDATE_ERROR("ER0130", "修改失敗"), //
    QUERY_ERROR("ER0140", "查詢失敗"), //
    NO_DATA("ER0150", "查無資料");
    private final String code;
    private final String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}