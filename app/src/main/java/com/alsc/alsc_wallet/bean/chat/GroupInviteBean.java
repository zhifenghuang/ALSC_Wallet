package com.alsc.alsc_wallet.bean.chat;

public class GroupInviteBean {
    private String fromUser;
    private GroupBean group;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }
}
