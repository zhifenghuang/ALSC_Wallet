package com.cao.commons.util.baidu;

import java.io.Serializable;

/**
 * Created by caojianzhen on 2018/6/12.
 */
public class LocationEntity implements Serializable{

    public double lat;
    public double lon;
    public float radius;
    public String address;

    public LocationEntity(double lat, double lon,float radius,String address) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.address = address;
    }

}
