package com.cao.commons.model;

import java.io.Serializable;

/**
 * 票券列表Model
 *
 * @author CJZ
 * @Time 2019/7/1
 */
public class TicketListModel implements Serializable {

    public int id;
    public int matchid;
    public String title;
    public int ticket_type;
    public String price;
    public int cat;
    public int member;
    public String remark;
    public String order_start_time;
    public String order_end_time;
    public String used_start_time;
    public String used_end_time;
    public int audit;
    public int is_del;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMatchid(int matchid) {
        this.matchid = matchid;
    }

    public int getMatchid() {
        return matchid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTicket_type(int ticket_type) {
        this.ticket_type = ticket_type;
    }

    public int getTicket_type() {
        return ticket_type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public int getCat() {
        return cat;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public int getMember() {
        return member;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setOrder_start_time(String order_start_time) {
        this.order_start_time = order_start_time;
    }

    public String getOrder_start_time() {
        return order_start_time;
    }

    public void setOrder_end_time(String order_end_time) {
        this.order_end_time = order_end_time;
    }

    public String getOrder_end_time() {
        return order_end_time;
    }

    public void setUsed_start_time(String used_start_time) {
        this.used_start_time = used_start_time;
    }

    public String getUsed_start_time() {
        return used_start_time;
    }

    public void setUsed_end_time(String used_end_time) {
        this.used_end_time = used_end_time;
    }

    public String getUsed_end_time() {
        return used_end_time;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getAudit() {
        return audit;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getIs_del() {
        return is_del;
    }

}
