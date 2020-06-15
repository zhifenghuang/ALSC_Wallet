package com.alsc.alsc_wallet.bean.user;

public class RecommmendDetailBean {


    /**
     * user_id : 100097
     * level : 0
     * account : xyx002
     * total_price : 0.00
     * yeji : 0.00
     */

    private String user_id;
    private int level;
    private String account;
    private String total_price;
    private String yeji;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getYeji() {
        return yeji;
    }

    public void setYeji(String yeji) {
        this.yeji = yeji;
    }
}
