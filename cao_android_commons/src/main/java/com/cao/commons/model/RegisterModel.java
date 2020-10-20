package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 注册
 *
 * @author CJZ
 * @Time 2019/5/20
 */
public class RegisterModel implements Serializable{

    private long uid;
    private int vid;
    private String nickname;
    private String avatar;
    private String signature;
    private boolean founder;
    private String gender;
    private String birth;
    private String location;
    private String region;
    private boolean verified;
    private Verifiedinfo verifiedinfo;
    private int exp;
    private int level;
    private List<String> medal;
    private String vs_school;
    private int vip;
    private String va_reason;
    private String token;
    private String channel;
    private String community_id;
    private boolean L2_cached;

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public boolean isL2_cached() {
        return L2_cached;
    }

    public void setL2_cached(boolean l2_cached) {
        L2_cached = l2_cached;
    }

    public boolean isFounder() {
        return founder;
    }

    public boolean isVerified() {
        return verified;
    }

    public long getUid() {
        return uid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getVid() {
        return vid;
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

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setFounder(boolean founder) {
        this.founder = founder;
    }

    public boolean getFounder() {
        return founder;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
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

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setMedal(List<String> medal) {
        this.medal = medal;
    }

    public List<String> getMedal() {
        return medal;
    }

    public void setVs_school(String vs_school) {
        this.vs_school = vs_school;
    }

    public String getVs_school() {
        return vs_school;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip() {
        return vip;
    }

    public void setVa_reason(String va_reason) {
        this.va_reason = va_reason;
    }

    public String getVa_reason() {
        return va_reason;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
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
