package com.cao.commons.model;

import java.io.Serializable;

public class DynamicsPicModel implements Serializable {

    /**
     * type 0:图片
     *      1:视频
     */

    private String url;
    private int type;


    public DynamicsPicModel() {
    }

    public DynamicsPicModel(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
