package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

public class CommunityDynamicsModel implements Serializable {


        private List<Data> data;
        private int offset;
        public void setData(List<Data> data) {
            this.data = data;
        }
        public List<Data> getData() {
            return data;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
        public int getOffset() {
            return offset;
        }

    public class Data {

        private Replay replay;
        private List<Images> images;
        private List<Videos> videos;
        private int type;
        private int flag;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public void setReplay(Replay replay) {
            this.replay = replay;
        }
        public Replay getReplay() {
            return replay;
        }

        public void setImages(List<Images> images) {
            this.images = images;
        }
        public List<Images> getImages() {
            return images;
        }

        public void setVideos(List<Videos> videos) {
            this.videos = videos;
        }
        public List<Videos> getVideos() {
            return videos;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

    }

    public class Images {

        private int seqid;
        private int imageid;
        private long uid;
        private String url;
        private String content;
        private int width;
        private int height;
        private String point;
        private String province;
        private String city;
        private String district;
        private String location;
        private String deleted;
        private String extends1;
        private String addtime;
        private String iscapture;
        private long rid;
        public void setSeqid(int seqid) {
            this.seqid = seqid;
        }
        public int getSeqid() {
            return seqid;
        }

        public void setImageid(int imageid) {
            this.imageid = imageid;
        }
        public int getImageid() {
            return imageid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }
        public long getUid() {
            return uid;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return height;
        }

        public void setPoint(String point) {
            this.point = point;
        }
        public String getPoint() {
            return point;
        }

        public void setProvince(String province) {
            this.province = province;
        }
        public String getProvince() {
            return province;
        }

        public void setCity(String city) {
            this.city = city;
        }
        public String getCity() {
            return city;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
        public String getDistrict() {
            return district;
        }

        public void setLocation(String location) {
            this.location = location;
        }
        public String getLocation() {
            return location;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }
        public String getDeleted() {
            return deleted;
        }

        public void setExtends1(String extends1) {
            this.extends1 = extends1;
        }
        public String getExtends1() {
            return extends1;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
        public String getAddtime() {
            return addtime;
        }

        public void setIscapture(String iscapture) {
            this.iscapture = iscapture;
        }
        public String getIscapture() {
            return iscapture;
        }

        public void setRid(long rid) {
            this.rid = rid;
        }
        public long getRid() {
            return rid;
        }

    }

    public class Videos {

        private int videoid;
        private long uid;
        private String title;
        private String sn;
        private String partner;
        private String point;
        private String province;
        private String city;
        private String district;
        private String location;
        private int width;
        private int height;
        private String virtual;
        private int status;
        private String subtitle;
        private String extends1;
        private String deleted;
        private String addtime;
        private String endtime;
        private String modtime;
        private String replayurl;
        private String replay;
        private String record;
        private String region;
        private String cover;
        private String stime;
        private String etime;
        private String pullurl;
        private String privacy;
        private int linktype;
        private int streamtype;
        private long rid;
        public void setVideoid(int videoid) {
            this.videoid = videoid;
        }
        public int getVideoid() {
            return videoid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }
        public long getUid() {
            return uid;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
        public String getSn() {
            return sn;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }
        public String getPartner() {
            return partner;
        }

        public void setPoint(String point) {
            this.point = point;
        }
        public String getPoint() {
            return point;
        }

        public void setProvince(String province) {
            this.province = province;
        }
        public String getProvince() {
            return province;
        }

        public void setCity(String city) {
            this.city = city;
        }
        public String getCity() {
            return city;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
        public String getDistrict() {
            return district;
        }

        public void setLocation(String location) {
            this.location = location;
        }
        public String getLocation() {
            return location;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return height;
        }

        public void setVirtual(String virtual) {
            this.virtual = virtual;
        }
        public String getVirtual() {
            return virtual;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
        public String getSubtitle() {
            return subtitle;
        }

        public void setExtends1(String extends1) {
            this.extends1 = extends1;
        }
        public String getExtends1() {
            return extends1;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }
        public String getDeleted() {
            return deleted;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
        public String getAddtime() {
            return addtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
        public String getEndtime() {
            return endtime;
        }

        public void setModtime(String modtime) {
            this.modtime = modtime;
        }
        public String getModtime() {
            return modtime;
        }

        public void setReplayurl(String replayurl) {
            this.replayurl = replayurl;
        }
        public String getReplayurl() {
            return replayurl;
        }

        public void setReplay(String replay) {
            this.replay = replay;
        }
        public String getReplay() {
            return replay;
        }

        public void setRecord(String record) {
            this.record = record;
        }
        public String getRecord() {
            return record;
        }

        public void setRegion(String region) {
            this.region = region;
        }
        public String getRegion() {
            return region;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
        public String getCover() {
            return cover;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }
        public String getStime() {
            return stime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }
        public String getEtime() {
            return etime;
        }

        public void setPullurl(String pullurl) {
            this.pullurl = pullurl;
        }
        public String getPullurl() {
            return pullurl;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }
        public String getPrivacy() {
            return privacy;
        }

        public void setLinktype(int linktype) {
            this.linktype = linktype;
        }
        public int getLinktype() {
            return linktype;
        }

        public void setStreamtype(int streamtype) {
            this.streamtype = streamtype;
        }
        public int getStreamtype() {
            return streamtype;
        }

        public void setRid(long rid) {
            this.rid = rid;
        }
        public long getRid() {
            return rid;
        }

    }

    public class Replay {

        private long rid;
        private String content;
        private String floor;
        private int reply;
        private int praise;
        private String addtime;
        private Quote quote;
        private User user;

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

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
