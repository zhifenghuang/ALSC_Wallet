package com.cao.commons.model;

/**
 * 首页导航
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class HeadLineBannerData {

    private App_banner app_banner;
    private boolean cached;

    public void setApp_banner(App_banner app_banner) {
        this.app_banner = app_banner;
    }

    public App_banner getApp_banner() {
        return app_banner;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean getCached() {
        return cached;
    }


    public class App_banner {

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
