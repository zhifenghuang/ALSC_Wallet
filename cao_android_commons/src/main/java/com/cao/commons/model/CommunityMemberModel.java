package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 当前页面Activity
 *
 * @author CJZ
 * @Time 2019/7/4
 */
public class CommunityMemberModel implements Serializable {


    private List<Data> data;
    private int total;
    private int offset;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public class Data {

        private int id;
        private int cid;
        private String userid;
        private String addtime;
        private Userinfo userinfo;

        private boolean select;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getCid() {
            return cid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserid() {
            return userid;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setUserinfo(Userinfo userinfo) {
            this.userinfo = userinfo;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public Userinfo getUserinfo() {
            return userinfo;
        }

    }

    public class Userinfo {

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
        private int community_id;
        private boolean L2_cached;

        public void setUid(long uid) {
            this.uid = uid;
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

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setL2_cached(boolean L2_cached) {
            this.L2_cached = L2_cached;
        }

        public boolean getL2_cached() {
            return L2_cached;
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
