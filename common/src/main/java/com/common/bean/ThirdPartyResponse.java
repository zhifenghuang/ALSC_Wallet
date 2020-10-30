package com.common.bean;

import java.io.Serializable;

public class ThirdPartyResponse<T> implements Serializable {
    private int result;
    private String errmsg;
    private T data;

    public boolean isSuccess() {
        // TODO Auto-generated method stub
        return result == 0;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
