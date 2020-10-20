package com.cao.commons.model;

import java.io.Serializable;

/**
 * 首页banner
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class HomeBannerModel implements Serializable{

        private int bannerid;
        private String region;
        private int type;
        private String title;
        private String image;
        private int target;
        private String value;
        private String starttime;
        private String endtime;
        private int score;
        private String platform;
        public void setBannerid(int bannerid) {
            this.bannerid = bannerid;
        }
        public int getBannerid() {
            return bannerid;
        }

        public void setRegion(String region) {
            this.region = region;
        }
        public String getRegion() {
            return region;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setTarget(int target) {
            this.target = target;
        }
        public int getTarget() {
            return target;
        }

        public void setValue(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }
        public String getStarttime() {
            return starttime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
        public String getEndtime() {
            return endtime;
        }

        public void setScore(int score) {
            this.score = score;
        }
        public int getScore() {
            return score;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
        public String getPlatform() {
            return platform;
        }



}
