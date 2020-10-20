package com.cao.commons.bean.user;

public class TransferListDetailBean {


    /**
     * alsc : 1.00000000
     * add_time : 2020-01-10 00:31:02
     * status : 1
     * type : 2
     * usdt : 0.00000000
     * hash : null
     * miner_fee : 0.00000000
     * fee : 0
     */

    private String alsc;
    private String add_time;
    private int status;
    private int type;
    private String usdt;
    private String hash;
    private String miner_fee;
    private String alsc_url;
    private String fee;
    private String to;
    private String txid;
    private String amount;
    private String symbol;


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

    public String getAmount() {
        return amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getHash() {
        if (hash!=null) {
            return hash;
        } else {
            return "";
        }
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMiner_fee() {
        return miner_fee;
    }

    public void setMiner_fee(String miner_fee) {
        this.miner_fee = miner_fee;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}
