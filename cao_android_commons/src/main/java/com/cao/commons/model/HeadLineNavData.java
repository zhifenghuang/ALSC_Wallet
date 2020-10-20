package com.cao.commons.model;

/**
 * 首页导航
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class HeadLineNavData {

    private Android_list android_list;
    private boolean cached;

    public void setAndroid_list(Android_list android_list) {
        this.android_list = android_list;
    }

    public Android_list getAndroid_list() {
        return android_list;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean getCached() {
        return cached;
    }


    public class Android_list {

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
