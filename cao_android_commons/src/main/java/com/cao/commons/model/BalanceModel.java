package com.cao.commons.model;

/**
 * 我的余额
 *
 * @author CJZ
 * @Time 2019/5/21
 */
public class BalanceModel {

    private int coin;//币
    private int diamond;//钻
    private int cash;//现金
    private int ticket;//券


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}
