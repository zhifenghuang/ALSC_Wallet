package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 首页推荐列表
 *
 * @author CJZ
 * @Time 2019/5/16
 */
public class HomeRecommendListModel implements Serializable {

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

    public class Data {

        private int matchid;
        private String title;
        private String award;
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
        private int sender;
        private int member_num;
        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getMember_num() {
            return member_num;
        }

        public void setMember_num(int member_num) {
            this.member_num = member_num;
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

        public void setAward(String award) {
            this.award = award;
        }

        public String getAward() {
            return award;
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

        public void setSender(int sender) {
            this.sender = sender;
        }

        public int getSender() {
            return sender;
        }

    }


}
