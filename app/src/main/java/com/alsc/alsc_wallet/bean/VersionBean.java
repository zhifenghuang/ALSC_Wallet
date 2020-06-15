package com.alsc.alsc_wallet.bean;

public class VersionBean {


    /**
     * type : 2
     * url : http://uuyj.alsc1319.vip/
     * desc : 强更理由
     * azUrl :
     * iosUrl :
     */

    /**
     * 0不升级  1升级  2 强制升级
     */
    private int type;
    private String url;
    private String desc;
    private String azUrl;
    private String iosUrl;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAzUrl() {
        return azUrl;
    }

    public void setAzUrl(String azUrl) {
        this.azUrl = azUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }
}
