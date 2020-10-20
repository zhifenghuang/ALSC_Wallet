package com.cao.commons.model;

import java.io.Serializable;

/**
 * 报名表单Model
 *
 * @author CJZ
 * @Time 2019/6/13
 */
public class SignUpModel implements Serializable{

    public String name;
    public String phone;
    public String mail;

    public SignUpModel() {
    }

    public SignUpModel(String name, String phone, String mail) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "SignUpModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
