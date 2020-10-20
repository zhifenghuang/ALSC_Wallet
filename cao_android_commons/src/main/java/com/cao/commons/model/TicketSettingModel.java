package com.cao.commons.model;

import java.io.Serializable;

/**
 * 票种设置Model
 *
 * @author CJZ
 * @Time 2019/6/10
 */
public class TicketSettingModel implements Serializable {

    public int id;
    public int ticket_type;//0免费 1收费
    public String title;//票种名称
    public int member;//票种数量
    public int price;//票种价格
    public String remark;//说明
    public int audit;//是否审计 0不审计 1审计
    public String order_start_time;//可订票开始时间
    public String order_end_time;//可订票结束时间
    public String used_start_time;//有效期开始时间
    public String used_end_time;//有效期结束时间

    public TicketSettingModel() {
    }

    public TicketSettingModel(int ticket_type, String title, int member,int price, String remark, int audit, String order_start_time, String order_end_time, String used_start_time, String used_end_time) {
        this.ticket_type = ticket_type;
        this.title = title;
        this.member = member;
        this.price = price;
        this.remark = remark;
        this.audit = audit;
        this.order_start_time = order_start_time;
        this.order_end_time = order_end_time;
        this.used_start_time = used_start_time;
        this.used_end_time = used_end_time;
    }

    @Override
    public String toString() {
        return "TicketSettingModel{" +
                "ticket_type=" + ticket_type +
                ", title='" + title + '\'' +
                ", member=" + member +
                ", remark='" + remark + '\'' +
                ", audit=" + audit +
                ", order_start_time='" + order_start_time + '\'' +
                ", order_end_time='" + order_end_time + '\'' +
                ", used_start_time='" + used_start_time + '\'' +
                ", used_end_time='" + used_end_time + '\'' +
                '}';
    }
}
