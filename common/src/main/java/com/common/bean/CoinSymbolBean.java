package com.common.bean;

public class CoinSymbolBean {

    private int resId;
    private String name;
    private String address;

    public CoinSymbolBean(int resId, String name, String address) {
        this.resId = resId;
        this.name = name;
        this.address = address;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
