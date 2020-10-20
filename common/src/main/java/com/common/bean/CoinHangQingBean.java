package com.common.bean;

public class CoinHangQingBean {

    private int resId;
    private String name;
    private String totalValue;
    private String cnyPrice;
    private String price;
    private String rose;


    public CoinHangQingBean(int resId, String name,String totalValue, String cnyPrice, String price, String rose) {
        this.resId = resId;
        this.name = name;
        this.totalValue=totalValue;
        this.cnyPrice = cnyPrice;
        this.price = price;
        this.rose = rose;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
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

    public String getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRose() {
        return rose;
    }

    public void setRose(String rose) {
        this.rose = rose;
    }
}
