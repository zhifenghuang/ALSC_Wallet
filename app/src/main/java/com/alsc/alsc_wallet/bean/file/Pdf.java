package com.alsc.alsc_wallet.bean.file;

import java.io.Serializable;
import java.util.ArrayList;

public class Pdf implements Serializable {

    private int count;
    private ArrayList<Book> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Book> getData() {
        return data;
    }

    public void setData(ArrayList<Book> data) {
        this.data = data;
    }

}
