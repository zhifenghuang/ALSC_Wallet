package com.cao.commons.model;

/**
 * 发表评论Model
 *
 * @author CJZ
 * @Time 2019/6/25
 */
public class CommentModel {


    private long rid;
    private String content;
    private String floor;
    private String addtime;
    private Quote quote;
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

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Quote getQuote() {
        return quote;
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

    public class Quote {

        private boolean deleted;

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean getDeleted() {
            return deleted;
        }

    }

}
