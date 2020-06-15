package com.alsc.alsc_wallet.bean.user;

public class MyUserInfoBean {


    /**
     * user_info : {"uname":"hsj111","avatar":"http://www.alsc1319.vip/uploads/20191205/38d4a0ea83468204dc1c808f97b07f3e.png","uid":10017,"real_name":null,"lev":33,"email":"hsj2388679178@163.com","referrer_id":10016,"is_super":0,"total_price":"1100.00","is_bang_phone":1,"is_bang_email":1,"level":0,"alsc_url":"0xaffeb7fbcb09567b3d79e2b61c68badaef1037d2","usdt":"8910.44","alsc":"0.00","Investment":"1100.00","reputation":"2200.00","total_profit":"0.00"}
     * totalJackpot : 75681.12
     * contri : 1100.00
     * surplusRep : 2200.00
     * sinvestment : 0.00
     * max_sinvestment : 22800.00
     * share_total : 0.00
     * fenx_total : 0.00
     */

    private UserInfoBean user_info;
    /**
     * totalJackpot : 75717.12
     * contri : 0.00
     * surplusRep : 0.00
     * sinvestment : 0.00
     * max_sinvestment : 22800.00
     * high_jiaoyi : 0
     * usdt_fee : 0.0454
     * btc_fee : 0.00985
     * eth_fee : 0.0052
     * share_total : 0.00
     * fenx_total : 0.00
     */

    private String totalJackpot;
    private String contri;
    private String surplusRep;
    private String sinvestment;
    private String max_sinvestment;
    private String high_jiaoyi;
    private String usdt_fee;
    private String btc_fee;
    private String eth_fee;
    private String share_total;
    private String fenx_total;
    private int open_consensus;


    public int getOpen_consensus() {
        return open_consensus;
    }

    public void setOpen_consensus(int open_consensus) {
        this.open_consensus = open_consensus;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getTotalJackpot() {
        return totalJackpot;
    }

    public void setTotalJackpot(String totalJackpot) {
        this.totalJackpot = totalJackpot;
    }

    public String getContri() {
        return contri;
    }

    public void setContri(String contri) {
        this.contri = contri;
    }

    public String getSurplusRep() {
        return surplusRep;
    }

    public void setSurplusRep(String surplusRep) {
        this.surplusRep = surplusRep;
    }

    public String getSinvestment() {
        return sinvestment;
    }

    public void setSinvestment(String sinvestment) {
        this.sinvestment = sinvestment;
    }

    public String getMax_sinvestment() {
        return max_sinvestment;
    }

    public void setMax_sinvestment(String max_sinvestment) {
        this.max_sinvestment = max_sinvestment;
    }

    public String getHigh_jiaoyi() {
        return high_jiaoyi;
    }

    public void setHigh_jiaoyi(String high_jiaoyi) {
        this.high_jiaoyi = high_jiaoyi;
    }

    public String getUsdt_fee() {
        return usdt_fee;
    }

    public void setUsdt_fee(String usdt_fee) {
        this.usdt_fee = usdt_fee;
    }

    public String getBtc_fee() {
        return btc_fee;
    }

    public void setBtc_fee(String btc_fee) {
        this.btc_fee = btc_fee;
    }

    public String getEth_fee() {
        return eth_fee;
    }

    public void setEth_fee(String eth_fee) {
        this.eth_fee = eth_fee;
    }

    public String getShare_total() {
        return share_total;
    }

    public void setShare_total(String share_total) {
        this.share_total = share_total;
    }

    public String getFenx_total() {
        return fenx_total;
    }

    public void setFenx_total(String fenx_total) {
        this.fenx_total = fenx_total;
    }
}
