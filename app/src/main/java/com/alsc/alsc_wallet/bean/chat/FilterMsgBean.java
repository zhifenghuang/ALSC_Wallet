package com.alsc.alsc_wallet.bean.chat;

import java.io.Serializable;

public class FilterMsgBean implements Serializable {

    private long blockId;
    private String content;

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
