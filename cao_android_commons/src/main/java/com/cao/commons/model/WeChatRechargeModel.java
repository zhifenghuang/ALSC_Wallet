package com.cao.commons.model;

/**
 * 微信充值
 *
 * @author CJZ
 * @Time 2019/5/22
 */
public class WeChatRechargeModel {

    private String appid;
    private String noncestr;
    private String package1;
    private String partnerid;
    private String prepayid;
    private long timestamp;
    private String s;
    private String sign;
    private long orderid;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setNoncestr( String noncestr) {
        this.noncestr = noncestr;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setPackage1(String package1) {
        this.package1 = package1;
    }

    public String getPackage1() {
        return package1;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPrepayid( String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getOrderid() {
        return orderid;
    }

}
