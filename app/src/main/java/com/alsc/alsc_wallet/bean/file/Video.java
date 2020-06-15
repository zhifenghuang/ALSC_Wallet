package com.alsc.alsc_wallet.bean.file;

import android.text.TextUtils;

import java.io.Serializable;

public class Video implements Serializable {
    private long id;
    private String title;
    private String title_en;
    private int num;
    private String logo;
    private String logo_en;
    private int vip;
    private String path;
    private String add_time;
    private String desc;
    private String desc_en;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_en() {
        if (TextUtils.isEmpty(desc_en)) {
            desc_en = desc;
        }
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo_en() {
        return logo_en;
    }

    public void setLogo_en(String logo_en) {
        this.logo_en = logo_en;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
