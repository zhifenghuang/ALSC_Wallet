package com.cao.commons.model;

/**
 * 可提现金额
 *
 * @author CJZ
 * @Time 2019/7/5
 */
public class WithdrawPriceModel {

    private int ticket;
    private int total_price;
    private int price;
    private int medal;
    private Percent percent;

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setMedal(int medal) {
        this.medal = medal;
    }

    public int getMedal() {
        return medal;
    }

    public void setPercent(Percent percent) {
        this.percent = percent;
    }

    public Percent getPercent() {
        return percent;
    }

    public class Percent {

        private double author;
        private int family;
        private int employe;
        private int author_percent_medal;

        public void setAuthor(double author) {
            this.author = author;
        }

        public double getAuthor() {
            return author;
        }

        public void setFamily(int family) {
            this.family = family;
        }

        public int getFamily() {
            return family;
        }

        public void setEmploye(int employe) {
            this.employe = employe;
        }

        public int getEmploye() {
            return employe;
        }

        public void setAuthor_percent_medal(int author_percent_medal) {
            this.author_percent_medal = author_percent_medal;
        }

        public int getAuthor_percent_medal() {
            return author_percent_medal;
        }

    }
}
