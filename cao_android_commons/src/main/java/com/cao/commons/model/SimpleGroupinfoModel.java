package com.cao.commons.model;

public class SimpleGroupinfoModel {


    /**
     * id : 1
     * uid : 41097790
     * uids : 1,2,3
     * addtime : 2020-01-20
     * groupid : 1579514275
     * groupName : 测试
     */

    private int id;
    private String uid;
    private String uids;
    private String addtime;
    private String groupid;
    private String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
