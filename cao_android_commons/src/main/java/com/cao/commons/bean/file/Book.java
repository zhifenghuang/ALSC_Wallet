package com.cao.commons.bean.file;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    private long id;
    private String title;
    private String title_en;
    private String path;
    private String desc;
    private String desc_en;
    private int num;
    private String logo;
    private String logo_en;
    private String read;
    private int vip;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        if (desc == null) {
            desc = "";
        }
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_en() {
        if (desc_en == null) {
            desc_en = "";
        }
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
