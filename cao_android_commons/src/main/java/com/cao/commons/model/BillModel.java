package com.cao.commons.model;

import java.util.List;

/**
 * 账单
 *
 * @author CJZ
 * @Time 2019/5/22
 */
public class BillModel {


    private List<Data> list;
    private int total;
    private int offset;

    public void setList(List<Data> list) {
        this.list = list;
    }

    public List<Data> getList() {
        return list;
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

        private int id;
        private String orderid;
        private int type;
        private String direct;
        private int currency;
        private int amount;
        private String remark;
        private String extend;
        private String addtime;
        private Extend_remark extend_remark;

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

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getDirect() {
            return direct;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public int getCurrency() {
            return currency;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }

        public String getExtend() {
            return extend;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setExtend_remark(Extend_remark extend_remark) {
            this.extend_remark = extend_remark;
        }

        public Extend_remark getExtend_remark() {
            return extend_remark;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", orderid='" + orderid + '\'' +
                    ", type=" + type +
                    ", direct='" + direct + '\'' +
                    ", currency=" + currency +
                    ", amount=" + amount +
                    ", remark='" + remark + '\'' +
                    ", extend='" + extend + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", extend_remark=" + extend_remark +
                    '}';
        }
    }

    public class Extend_remark {

        private long sender;
        private long receiver;
        private int giftid;
        private int price;
        private int ticket;
        private int num;
        private int liveid;

        public void setSender(long sender) {
            this.sender = sender;
        }

        public long getSender() {
            return sender;
        }

        public void setReceiver(long receiver) {
            this.receiver = receiver;
        }

        public long getReceiver() {
            return receiver;
        }

        public void setGiftid(int giftid) {
            this.giftid = giftid;
        }

        public int getGiftid() {
            return giftid;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public void setTicket(int ticket) {
            this.ticket = ticket;
        }

        public int getTicket() {
            return ticket;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setLiveid(int liveid) {
            this.liveid = liveid;
        }

        public int getLiveid() {
            return liveid;
        }

        @Override
        public String toString() {
            return "Extend_remark{" +
                    "sender=" + sender +
                    ", receiver=" + receiver +
                    ", giftid=" + giftid +
                    ", price=" + price +
                    ", ticket=" + ticket +
                    ", num=" + num +
                    ", liveid=" + liveid +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "list=" + list +
                ", total=" + total +
                ", offset=" + offset +
                '}';
    }
}
