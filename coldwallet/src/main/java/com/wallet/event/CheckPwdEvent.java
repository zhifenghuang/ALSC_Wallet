package com.wallet.event;

public class CheckPwdEvent {
    private String tag;

    public CheckPwdEvent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
