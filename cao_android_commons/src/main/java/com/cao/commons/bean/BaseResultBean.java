package com.cao.commons.bean;

public class BaseResultBean<T>  {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
