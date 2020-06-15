package com.alsc.alsc_wallet.bean.file;

import java.io.Serializable;
import java.util.ArrayList;

public class FileListInfo implements Serializable {

    private int count;
    private ArrayList<Video> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Video> getData() {
        return data;
    }

    public void setData(ArrayList<Video> data) {
        this.data = data;
    }


}
