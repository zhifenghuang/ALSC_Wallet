package com.alsc.alsc_wallet.bean.user;

public class HighTransferBean {



    private long id;
    private long user_id;
    private String amount;
    private int status;
    private String add_time;
    private String exchage_id;
    private String exchage_name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getExchage_id() {
        return exchage_id;
    }

    public void setExchage_id(String exchage_id) {
        this.exchage_id = exchage_id;
    }

    public String getExchage_name() {
        return exchage_name;
    }

    public void setExchage_name(String exchage_name) {
        this.exchage_name = exchage_name;
    }
}
