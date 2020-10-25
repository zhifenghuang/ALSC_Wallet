package com.cao.commons.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AssetsBean implements Serializable {

    private ArrayList<ItemBean> list;
    private String total;

    public ArrayList<ItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<ItemBean> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static class ItemBean implements Serializable {
        private int id;
        private String name;
        private String tousdt;
        private String total;
        private String allname;
        private String address_wallet;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTousdt() {
            return tousdt;
        }

        public void setTousdt(String tousdt) {
            this.tousdt = tousdt;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }

        public String getAddress_wallet() {
            return address_wallet;
        }

        public void setAddress_wallet(String address_wallet) {
            this.address_wallet = address_wallet;
        }
    }


}
