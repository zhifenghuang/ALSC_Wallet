package com.alsc.alsc_wallet.bean.chat;

public class ChatBean {
    public UserBean chatUser;
    public GroupBean group;
    public BasicMessage lastMsg;
    public int unReadNum;
    public ChatSubBean chatSubBean;

    public boolean isTopChat() {
        return chatSubBean != null && chatSubBean.getIsTop() == 1;
    }

    public boolean isNotInterupt() {
        return chatSubBean != null && chatSubBean.getNotInterupt() == 1;
    }
}
