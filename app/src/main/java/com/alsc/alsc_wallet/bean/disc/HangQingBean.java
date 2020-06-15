package com.alsc.alsc_wallet.bean.disc;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.Serializable;

public class HangQingBean implements Serializable {


    private String symbol;
    private String high;
    private String vol;
    private String last;
    private String low;
    private String buy;
    private String sell;
    private String rose;

    public HangQingBean(JSONObject object) {
        symbol = object.optString("symbol");
        if (!TextUtils.isEmpty(symbol)) {
            symbol = symbol.substring(0, symbol.length() - 4).toUpperCase();
        }
        high = object.optString("high");
        vol = object.optString("vol");
        last = object.optString("last");
        low = object.optString("low");
        buy = object.optString("buy");
        sell = object.optString("sell");
        rose = object.optString("rose");
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getLast() {
        if (!TextUtils.isEmpty(last)) {
            last = String.format("%.2f", Float.parseFloat(last));
        }
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getRose() {
        if (!TextUtils.isEmpty(rose)) {
            return String.format("%.2f", Float.parseFloat(rose) * 100);
        }
        return rose;
    }

    public void setRose(String rose) {
        this.rose = rose;
    }
}
