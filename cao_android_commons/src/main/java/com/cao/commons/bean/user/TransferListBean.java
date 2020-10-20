package com.cao.commons.bean.user;

import java.io.Serializable;

public class TransferListBean implements Serializable {

    /**
     * alsc : 780.00
     * add_time : 2019-11-30 09:30:39
     * status : 3
     * type : 2
     * usdt : 0.00
     * hash : 0xa028b48e467c9cd3dfeb3985d57966156ba42d717ff6eca3fcbf1c0b4af6802a
     */

    private String id;
    private String alsc;
    private String add_time;
    private int status;
    private int type;
    private int tag;
    private String usdt;
    private String hash;
    private String fee;
    private String alsc_url;
    private String amount;
    private String bili;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlsc() {
        return alsc;
    }

    public void setAlsc(String alsc) {
        this.alsc = alsc;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAlsc_url() {
        return alsc_url;
    }

    public void setAlsc_url(String alsc_url) {
        this.alsc_url = alsc_url;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBili() {
        return bili;
    }

    public void setBili(String bili) {
        this.bili = bili;
    }
}
