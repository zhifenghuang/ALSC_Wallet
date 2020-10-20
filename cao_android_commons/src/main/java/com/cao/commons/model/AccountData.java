package com.cao.commons.model;

/**
 * 首页导航
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class AccountData {

    private Account account;
    private boolean cached;

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean getCached() {
        return cached;
    }


    public class Account {

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
