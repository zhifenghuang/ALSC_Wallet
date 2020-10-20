package com.cao.commons.bean.user;

import java.util.List;

public class SettlePoolListBean {


    /**
     * total_income : 0.00
     * total_alsc : 0.00
     * list : []
     */

    private String total_income;
    private String total_alsc;
    private String total_ach;
    private List<SettlePoolBean> list;

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }

    public String getTotal_alsc() {
        return total_alsc;
    }

    public void setTotal_alsc(String total_alsc) {
        this.total_alsc = total_alsc;
    }

    public List<SettlePoolBean> getList() {
        return list;
    }

    public void setList(List<SettlePoolBean> list) {
        this.list = list;
    }

    public String getTotal_ach() {
        return total_ach;
    }

    public void setTotal_ach(String total_ach) {
        this.total_ach = total_ach;
    }
}
