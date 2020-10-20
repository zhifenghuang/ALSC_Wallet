package com.cao.commons.bean.chat;

public class ChatSubBean {

    private int isTop = 0;  //是否置顶
    private int notInterupt = 0;  //消息免打扰
    private int isShowMemberNick = 0;  //是否显示群成员昵称


    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getNotInterupt() {
        return notInterupt;
    }

    public void setNotInterupt(int notInterupt) {
        this.notInterupt = notInterupt;
    }

    public int getIsShowMemberNick() {
        return isShowMemberNick;
    }

    public void setIsShowMemberNick(int isShowMemberNick) {
        this.isShowMemberNick = isShowMemberNick;
    }
}
