package com.example.demo.system.entity;

import com.example.demo.system.entity.RespPage;

public class ResponseData extends RespPage {
    private Integer code = 200;
    private String MSG;
    private Object object;

    public ResponseData() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static ResponseData successful(Object object) {
        ResponseData responseData = new ResponseData();
        responseData.setMSG("" + object);
        return responseData;
    }

    public static ResponseData successful(String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setMSG(msg);
        return responseData;
    }

    public static ResponseData successful(Object obj, String str) {
        ResponseData responseData = new ResponseData();
        responseData.setMSG("successful");
        responseData.setObject(obj);
        return responseData;
    }

    public static ResponseData successful(Object obj, String msg, int total) {
        ResponseData responseData = new ResponseData();
        responseData.setMSG(msg);
        responseData.setObject(obj);
        responseData.setTotal(total);
        return responseData;
    }

    public static ResponseData fail(String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setMSG(msg);
        responseData.setObject(null);
        return responseData;
    }
}
