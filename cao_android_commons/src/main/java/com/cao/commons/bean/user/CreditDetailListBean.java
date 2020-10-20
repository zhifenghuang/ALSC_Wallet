package com.cao.commons.bean.user;

import java.util.List;

public class CreditDetailListBean {

    private List<CreditDetailBean> list;
    private String total;
    /**
     * page : {"counts":0,"pages":0,"currPage":"1","pageSize":"10"}
     */

    private PageBean page;


    public List<CreditDetailBean> getList() {
        return list;
    }

    public void setList(List<CreditDetailBean> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * counts : 0
         * pages : 0
         * currPage : 1
         * pageSize : 10
         */

        private String counts;

        public String getCounts() {
            return counts;
        }

        public void setCounts(String counts) {
            this.counts = counts;
        }
    }
}
