package com.example.demo.system.enums;

public enum ResultCode {
    SUCCESS(1, "操作成功"),
    FAILURE(0, "操作失败"),
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数异常"),

    USER_NOT_EXIST(2001, "用户不存在"),
    USER_NOT_FORBIDDEN(2002, "帐号已经禁用"),
    USER_NOT_EXISTED(2003, "用户已经存在"),
    USER_NOT_LOGIN_IN(2004, "用户没有登陆"),

    NOT_KNOW_ERRE(0001, "不知异常"),
    NOT_KNOW_TOKEN(0002, "token 无效"),

    ROLE_NOT_PERMISSION(0000, "不允许访问");


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
