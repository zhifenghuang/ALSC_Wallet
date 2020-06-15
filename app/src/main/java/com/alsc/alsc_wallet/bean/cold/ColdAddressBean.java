package com.alsc.alsc_wallet.bean.cold;

import android.content.ContentValues;

import com.alsc.alsc_wallet.db.IDBItemOperation;


public class ColdAddressBean extends IDBItemOperation {

    private int id;
    /**
     * 关联用户表
     */
    private String loginAccount;
    /**
     * 姓名
     */
    private String name;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 添加地址 /账户
     */
    private String path;
    /**
     * 钱包类型
     */
    private String walletType;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 备用
     */
    private String others;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public String getTableName() {
        return "address";
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("loginAccount", loginAccount);
        values.put("name", name);
        values.put("remarks", remarks);
        values.put("path", path);
        values.put("walletType", walletType);
        values.put("createTime", createTime);
        values.put("others", others);
        return values;
    }
}
