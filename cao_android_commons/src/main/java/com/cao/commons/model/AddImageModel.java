package com.cao.commons.model;

/**
 * 上传图片Model
 *
 * @author CJZ
 * @Time 2019/6/25
 */
public class AddImageModel {

    public long imageid;
    public String addtime;

    public long getImageid() {
        return imageid;
    }

    public void setImageid(long imageid) {
        this.imageid = imageid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
