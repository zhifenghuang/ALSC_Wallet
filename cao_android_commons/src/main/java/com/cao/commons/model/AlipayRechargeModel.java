package com.cao.commons.model;

/**
 * 支付宝充值
 *
 * @author CJZ
 * @Time 2019/5/23
 */
public class AlipayRechargeModel {

    private long orderid;
    private String alipayStr;

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public String getAlipayStr() {
        return alipayStr;
    }

    public void setAlipayStr(String alipayStr) {
        this.alipayStr = alipayStr;
    }
}
