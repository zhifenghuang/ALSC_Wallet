package com.cao.commons.model;

import java.io.Serializable;

/**
 * 小区信息Model
 *
 * @author CJZ
 * @Time 2019/7/3
 */
public class CommunityInfoModel implements Serializable {

    private int id;
    private String name;
    private String province;
    private String city;
    private String district;
    private String address;
    private String logo;
    private String audit;
    private String is_del;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getAudit() {
        return audit;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getIs_del() {
        return is_del;
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
