package com.alsc.alsc_wallet.bean.user;

public class LanguageBean {
    private int image;
    private String name;

    public LanguageBean() {
    }

    public LanguageBean(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
