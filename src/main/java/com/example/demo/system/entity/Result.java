package com.example.demo.system.entity;

import com.example.demo.system.enums.ResultCode;

import java.io.Serializable;

public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
    }

    public static Result success() {
        Result result = new Result(ResultCode.SUCCESS);
        return result;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public static Result success(Object object) {
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(object);
        return result;
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static Result failure() {
        Result result = new Result(ResultCode.FAILURE);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object object) {
        Result result = new Result(resultCode);
        result.setData(object);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(" code=").append(code);
        sb.append(", message=").append(message);
        sb.append(", data=").append(data);
        sb.append("]");
        return sb.toString();
    }
}
