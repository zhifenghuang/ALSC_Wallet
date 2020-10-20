package com.cao.commons.bean.cold;

import com.google.gson.annotations.SerializedName;

public class EthgasBean {


    /**
     * fast : 80.0
     * fastest : 150.0
     * safeLow : 12.0
     * average : 40.0
     * block_time : 12.512195121951219
     * blockNum : 9546190
     * speed : 0.6761287544489365
     * safeLowWait : 18.4
     * avgWait : 3.4
     * fastWait : 0.4
     * fastestWait : 0.4
     * gasPriceRange : {"150":0.4,"145":0.4,"140":0.4,"135":0.4,"130":0.4,"125":0.4,"120":0.4,"115":0.4,"110":0.4,"105":0.4,"100":0.4,"95":0.4,"90":0.4,"85":0.4,"80":0.4,"75":0.6,"70":0.6,"65":0.6,"60":0.6,"55":0.8,"50":0.8,"45":3.4,"40":3.4,"35":6.5,"30":7.4,"25":8.6,"20":10.5,"15":18.4,"10":28,"8":208.5,"6":208.5,"4":208.5,"12":18.4}
     */

    private double fast;
    private double fastest;
    private double safeLow;
    private double average;


    public double getFast() {
        return fast;
    }

    public void setFast(double fast) {
        this.fast = fast;
    }

    public double getFastest() {
        return fastest;
    }

    public void setFastest(double fastest) {
        this.fastest = fastest;
    }

    public double getSafeLow() {
        return safeLow;
    }

    public void setSafeLow(double safeLow) {
        this.safeLow = safeLow;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }


}
