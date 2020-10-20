package com.cao.commons.bean.user;

public class LogDetailBean {


    /**
     * amount : null
     * add_time : 2020-02-04 21:26:46
     * alsc : 16.00
     * usdt : 1.00
     * type : 6
     */

    private String amount;
    private String add_time;
    private String alsc;
    private String alsc_num;
    private String usdt;
    private String price;
    private int type;
    private String gs;
    private String jc;
    private String balance;
    private String power;

    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAlsc() {
        if (alsc!=null) {
            return alsc;
        } else {
            return "";
        }
    }

    public void setAlsc(String alsc) {
        this.alsc = alsc;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlsc_num() {
        if (alsc_num!=null) {
            return alsc_num;
        } else {
            return "";
        }
    }

    public void setAlsc_num(String alsc_num) {
        this.alsc_num = alsc_num;
    }
}
