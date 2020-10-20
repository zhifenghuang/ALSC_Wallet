package com.cao.commons.model;

import java.io.Serializable;

public class AddReplyModel implements Serializable {

    private long rid;
    private String content;
    private String floor;
    private int reply;
    private int praise;
    private String addtime;
    private User user;

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getRid() {
        return rid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloor() {
        return floor;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getReply() {
        return reply;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getPraise() {
        return praise;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public class User {

        private long uid;
        private String nickname;
        private String avatar;
        private boolean verified;
        private Verifiedinfo verifiedinfo;

        public void setUid(long uid) {
            this.uid = uid;
        }

        public long getUid() {
            return uid;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public boolean getVerified() {
            return verified;
        }

        public void setVerifiedinfo(Verifiedinfo verifiedinfo) {
            this.verifiedinfo = verifiedinfo;
        }

        public Verifiedinfo getVerifiedinfo() {
            return verifiedinfo;
        }

    }

    public class Verifiedinfo {

        private String credentials;
        private int type;
        private String realname;

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getRealname() {
            return realname;
        }

    }
}
