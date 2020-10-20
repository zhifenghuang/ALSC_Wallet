package com.cao.commons.bean.cold;

import android.content.ContentValues;

import com.cao.commons.db.IDBItemOperation;

public class WalletDataBean extends IDBItemOperation {

    private int id;
    /**
     * 关联用户表
     */
    private String loginAccount;
    /**
     * 钱包名称
     */
    private String name;
    /**
     * 选择路径
     */
    private int pathType;
    /**
     * 输入助记词单词，并使用空格分开
     */
    private String mnemonicCode;
    /**
     * 输入明文私钥
     */
    private String privateKey;
    private String publicKey;
    /**
     * 钱包地址
     */
    private String address;
    /**
     * Keystore 文件内容
     */
    private String keystore;
    /**
     * 钱包密码
     */
    private String password;
    /**
     * 钱包类型 "BTC", "ETH", "USDT-OMNI", "USDT-ERC20", "ALSC"
     */
    private String walletType;
    /**
     * 密码提示信息（可选）
     */
    private String information;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 对应币的数量
     */
    private String money;
    /**
     * 行情价格
     */
    private String price;



    private String currentPrice;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public WalletDataBean() {
    }

    public WalletDataBean(String name, String mnemonicCode, String privateKey, String publicKey, String address, String keystore, String password, String walletType,  String loginAccount, long createTime) {
        this.name = name;
        this.mnemonicCode = mnemonicCode;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
        this.keystore = keystore;
        this.password = password;
        this.walletType = walletType;
        this.loginAccount = loginAccount;
        this.createTime = createTime;
    }

    public WalletDataBean(String name, int pathType,String mnemonicCode, String privateKey, String publicKey, String address, String password, String walletType, String information, String loginAccount, long createTime) {
        this.name = name;
        this.pathType = pathType;
        this.mnemonicCode = mnemonicCode;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
        this.password = password;
        this.walletType = walletType;
        this.information = information;
        this.loginAccount = loginAccount;
        this.createTime = createTime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }


    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public String getTableName() {
        return "wallet";
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("loginAccount", loginAccount);
        values.put("name", name);
        values.put("pathType", pathType);
        values.put("mnemonicCode", mnemonicCode);
        values.put("walletType", walletType);
        values.put("privateKey", privateKey);
        values.put("publicKey", publicKey);
        values.put("address", address);
        values.put("keystore", keystore);
        values.put("password", password);
        values.put("walletType", walletType);
        values.put("information", information);
        values.put("createTime", createTime);
        values.put("money", money);
        values.put("price", price);
        values.put("remarks", remarks);
        return values;
    }
}
