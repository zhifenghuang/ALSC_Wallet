package com.alsc.alsc_wallet.bean.user;

public class FrequencyBean {


    /**
     * free : 20.00
     * add_income : 2000.00
     * add_time : 2020-02-03
     */

    private String free;
    private String add_income;
    private String add_time;
    private String total_alsc;
    private String alsc;

    public String getFree() {
        return free;
    }

    public String getTotal_alsc() {
        return total_alsc;
    }

    public void setTotal_alsc(String total_alsc) {
        this.total_alsc = total_alsc;
    }

    public String getAlsc() {
        return alsc;
    }

    public void setAlsc(String alsc) {
        this.alsc = alsc;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getAdd_income() {
        return add_income;
    }

    public void setAdd_income(String add_income) {
        this.add_income = add_income;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
