package com.alsc.alsc_wallet.bean.chat;

import java.io.Serializable;

public class TransferFeeBean implements Serializable {
    private float fee;
    private int type;

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}