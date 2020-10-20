package com.cao.commons.model;

/**
 * 导航条具体数据
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class NavModel {

    public String key;
    public String value;

    public NavModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "NavModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
