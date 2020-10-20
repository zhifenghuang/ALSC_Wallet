package com.cao.commons.model;

import java.io.Serializable;

/**
 * 搜索小区Model
 *
 * @author CJZ
 * @Time 2019/7/4
 */
public class SearchCommunityModel implements Serializable {


    private String name;
    private Location location;
    private String address;
    private String province;
    private String city;
    private String area;
    private String street_id;
    private int detail;
    private String uid;
    private String id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public int getDetail() {
        return detail;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public class Location {

        private double lat;
        private double lng;

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLat() {
            return lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLng() {
            return lng;
        }

    }


}
