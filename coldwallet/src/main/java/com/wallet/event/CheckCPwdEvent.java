package com.wallet.event;

public class CheckCPwdEvent {
    private String tag;

    public CheckCPwdEvent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
