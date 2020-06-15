package com.alsc.alsc_wallet.bean.file;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchFileResult implements Serializable {

    private int count;
    private ArrayList<SearchFile> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<SearchFile> getData() {
        return data;
    }

    public void setData(ArrayList<SearchFile> data) {
        this.data = data;
    }

    public static class SearchFile implements Serializable{
        private int id;
        private String title;
        private String title_en;
        private String logo;
        private String logo_en;
        private int vip;
        private int hits;
        private int type;
        private int cate;  //1.视频，2.附件 3.文章

        public int getCate() {
            return cate;
        }

        public void setCate(int cate) {
            this.cate = cate;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
    }

}
