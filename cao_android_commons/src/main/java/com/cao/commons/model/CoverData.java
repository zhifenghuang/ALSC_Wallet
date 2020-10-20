package com.cao.commons.model;

/**
 * 启动页
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class CoverData {

    private Cover cover;
    private boolean cached;

    public void setAccount(Cover account) {
        this.cover = account;
    }

    public Cover getAccount() {
        return cover;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean getCached() {
        return cached;
    }


    public class Cover {

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
