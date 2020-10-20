package com.cao.commons.bean;

import java.io.Serializable;

public class ScaleBean implements Serializable {

    private float scale;
    private int nums;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}