package com.cao.commons.model;

import java.io.Serializable;

/**
 * Created by tfwin2 on 2018/11/9.
 */

public class ApiModel<T> implements Serializable{
    /**状态码,返回数据基类，data是页面需要关心的数据，可能list，可能对象*/
    private int code; // 返回的结果标志 0成功
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "ApiModel{" +
                "code=" + code +
                ", msg=" + msg +
                ", data=" + data +
                '}';
    }
}
