package com.alsc.alsc_wallet.bean.cold;

import android.content.ContentValues;

import com.alsc.alsc_wallet.db.IDBItemOperation;

public class TradeInfoBean extends IDBItemOperation {


    /**
     * 区块hash
     */
    private String blockHash;
    /**
     * 交易随机数
     */
    private int nonce;
    /**
     * 结果
     */
    private String result;
    /**
     * 区块编号
     */
    private int blockNumber;
    /**
     * gas值
     */
    private String gas;
    /**
     * 发送地址
     */
    private String fromAddress;
    /**
     * 接收地址
     */
    private String toAddress;
    /**
     * 金额
     */
    private String value;
    /**
     * 交易hash
     */
    private String hash;
    /**
     * 交易索引
     */
    private int transIndex;
    /**
     * 手续费
     */
    private String gasPrice;
    /**
     * 钱包类型
     */
    private String walletType;
    /**
     * 0 收账  1转账
     */
    private int sendType;
    /**
     * 账号
     */
    private String loginAccount;
    /**
     * 时间
     */
    private long createTime;
    /**
     * 备注
     */
    private String remarks;

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }


    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }


    public String isResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
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

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }



    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getTransIndex() {
        return transIndex;
    }

    public void setTransIndex(int transIndex) {
        this.transIndex = transIndex;
    }


    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    @Override
    public String toString() {
        return "EthDealDetails{" +
                "blockHash='" + blockHash + '\'' +
                ", nonce=" + nonce +
                ", result=" + result +
                ", blockNumber=" + blockNumber +
                ", gas=" + gas +
                ", from='" + fromAddress + '\'' +
                ", to='" + toAddress + '\'' +
                ", value=" + value +
                ", hash='" + hash + '\'' +
                ", transIndex=" + transIndex +
                ", gasPrice=" + gasPrice +
                '}';
    }

    @Override
    public String getPrimaryKeyName() {
        return "hash";
    }

    @Override
    public String getTableName() {
        return "trade";
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put("hash", hash);
        values.put("loginAccount", loginAccount);
        values.put("walletType", walletType);
        values.put("blockHash", blockHash);
        values.put("nonce", nonce);
        values.put("walletType", walletType);
        values.put("result", result);
        values.put("blockNumber", blockNumber);
        values.put("gas", gas);
        values.put("fromAddress", fromAddress);
        values.put("toAddress", toAddress);
        values.put("walletType", walletType);
        values.put("value", value);
        values.put("transIndex", transIndex);
        values.put("gasPrice", gasPrice);
        values.put("walletType", walletType);
        values.put("sendType", sendType);
        values.put("createTime", createTime);
        values.put("remarks", remarks);
        return values;
    }
}
