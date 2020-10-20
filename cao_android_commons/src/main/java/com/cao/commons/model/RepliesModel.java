package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

public class RepliesModel implements Serializable {


    private List<Replies> replies;
    private int total;
    private String offset;

    public void setReplies(List<Replies> replies) {
        this.replies = replies;
    }

    public List<Replies> getReplies() {
        return replies;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOffset() {
        return offset;
    }


    public class Replies {

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

    }

    public class User {

        private long uid;
        private String nickname;
        private String avatar;
        private String verified;
        private String verifiedinfo;

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

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerifiedinfo(String verifiedinfo) {
            this.verifiedinfo = verifiedinfo;
        }

        public String getVerifiedinfo() {
            return verifiedinfo;
        }

    }

}
