package com.cao.commons.model;

import java.io.Serializable;

/**
 * 选择地址Model
 *
 * @author CJZ
 * @Time 2019/6/10
 */
public class AddressModel implements Serializable {


    public int flag;
    public String pcd;
    public String detail;


    @Override
    public String toString() {
        return pcd + ' ' + detail;
    }
}
