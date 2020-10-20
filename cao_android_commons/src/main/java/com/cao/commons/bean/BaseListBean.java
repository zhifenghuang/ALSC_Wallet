package com.cao.commons.bean;

public class BaseListBean<T> {

    private T list;
    private String total;
    private PageBean page;

    public T getList() {
        return list;
    }

    public void setList(T list) {
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
}
