package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 我的票券Model
 *
 * @author CJZ
 * @Time 2019/6/15
 */
public class MyTicketModel implements Serializable {

    private List<Data> data;
    private int total;
    private int offset;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public class Data implements Serializable{
        private int id;
        private String orderid;
        private int matchid;
        private long userid;
        private String price;
        private int audit;
        private int is_pay;
        private int is_comment;
        private int status;
        private int ticketid;
        private Info info;

        private long sender;
        private String modtime;
        private String addtime;
        private String form;
        private int num;
        private int offset;

        private Ticket ticket;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setMatchid(int matchid) {
            this.matchid = matchid;
        }

        public int getMatchid() {
            return matchid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public long getUserid() {
            return userid;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public int getAudit() {
            return audit;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setTicketid(int ticketid) {
            this.ticketid = ticketid;
        }

        public int getTicketid() {
            return ticketid;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public Info getInfo() {
            return info;
        }

        public void setSender(long sender) {
            this.sender = sender;
        }

        public long getSender() {
            return sender;
        }

        public void setModtime(String modtime) {
            this.modtime = modtime;
        }

        public String getModtime() {
            return modtime;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }


    public class Info implements Serializable{

        private int matchid;
        private String title;
        private String award;
        private String extends_remark;
        private String starttime;
        private String endtime;
        private String resort_address;
        private String resort_time;
        private String objective_address;
        private String addtime;
        private String deleted;
        private String modtime;
        private String remark;
        private int member_limit;
        private String pic;
        private int catid;
        private String desc;
        private int ticket_type;
        private long sender;
        private int member_num;
        private String extends_form;

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

        public void setAward(String award) {
            this.award = award;
        }

        public String getAward() {
            return award;
        }

        public void setExtends_remark(String extends_remark) {
            this.extends_remark = extends_remark;
        }

        public String getExtends_remark() {
            return extends_remark;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setResort_address(String resort_address) {
            this.resort_address = resort_address;
        }

        public String getResort_address() {
            return resort_address;
        }

        public void setResort_time(String resort_time) {
            this.resort_time = resort_time;
        }

        public String getResort_time() {
            return resort_time;
        }

        public void setObjective_address(String objective_address) {
            this.objective_address = objective_address;
        }

        public String getObjective_address() {
            return objective_address;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setModtime(String modtime) {
            this.modtime = modtime;
        }

        public String getModtime() {
            return modtime;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setMember_limit(int member_limit) {
            this.member_limit = member_limit;
        }

        public int getMember_limit() {
            return member_limit;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic() {
            return pic;
        }

        public void setCatid(int catid) {
            this.catid = catid;
        }

        public int getCatid() {
            return catid;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setTicket_type(int ticket_type) {
            this.ticket_type = ticket_type;
        }

        public int getTicket_type() {
            return ticket_type;
        }

        public void setSender(long sender) {
            this.sender = sender;
        }

        public long getSender() {
            return sender;
        }

        public void setMember_num(int member_num) {
            this.member_num = member_num;
        }

        public int getMember_num() {
            return member_num;
        }

        public void setExtends_form(String extends_form) {
            this.extends_form = extends_form;
        }

        public String getExtends_form() {
            return extends_form;
        }

    }

    public class Ticket implements Serializable{
        private int id;
        private int matchid;
        private String title;
        private int ticket_type;
        private String price;
        private String remark;
        private String order_start_time;
        private String order_end_time;
        private String used_start_time;
        private String used_end_time;
        private int cat;
        private int member;
        private int audit;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMatchid() {
            return matchid;
        }

        public void setMatchid(int matchid) {
            this.matchid = matchid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTicket_type() {
            return ticket_type;
        }

        public void setTicket_type(int ticket_type) {
            this.ticket_type = ticket_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOrder_start_time() {
            return order_start_time;
        }

        public void setOrder_start_time(String order_start_time) {
            this.order_start_time = order_start_time;
        }

        public String getOrder_end_time() {
            return order_end_time;
        }

        public void setOrder_end_time(String order_end_time) {
            this.order_end_time = order_end_time;
        }

        public String getUsed_start_time() {
            return used_start_time;
        }

        public void setUsed_start_time(String used_start_time) {
            this.used_start_time = used_start_time;
        }

        public String getUsed_end_time() {
            return used_end_time;
        }

        public void setUsed_end_time(String used_end_time) {
            this.used_end_time = used_end_time;
        }

        public int getCat() {
            return cat;
        }

        public void setCat(int cat) {
            this.cat = cat;
        }

        public int getMember() {
            return member;
        }

        public void setMember(int member) {
            this.member = member;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }
    }


}
