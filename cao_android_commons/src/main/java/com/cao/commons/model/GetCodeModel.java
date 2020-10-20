package com.cao.commons.model;

/**
 * 获取验证码
 *
 * @author CJZ
 * @Time 2019/5/20
 */
public class GetCodeModel {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetCodeModel{" +
                "id=" + id +
                '}';
    }
}
