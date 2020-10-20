package com.cao.commons.model;

/**
 * 首页推荐
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class HeadLineRecommendModel {

    private Recommend recommend;
    private boolean cached;

    public void setRecommend(Recommend app_banner) {
        this.recommend = app_banner;
    }

    public Recommend getRecommend() {
        return recommend;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean getCached() {
        return cached;
    }


    public class Recommend {

        private String value;
        private int expire;

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public int getExpire() {
            return expire;
        }

    }
}
