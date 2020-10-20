package com.cao.commons.bean.login;

import java.io.Serializable;

public class PicCodeResultBean implements Serializable {

    private String code;
    private String sid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
