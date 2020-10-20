package com.cao.commons.bean.file;

import java.io.Serializable;

public class Ad implements Serializable {

    private String img;
    private String en_img;
    private String imgPath;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEn_img() {
        return en_img;
    }

    public void setEn_img(String en_img) {
        this.en_img = en_img;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
