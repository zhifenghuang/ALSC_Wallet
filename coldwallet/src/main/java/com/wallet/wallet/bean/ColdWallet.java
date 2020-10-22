package com.wallet.wallet.bean;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 多链钱包
 *
 * @author rainking
 */
public class ColdWallet implements Serializable {


    /**
     * 助记词
     */
    private List<String> mnemonicCode;

    /**
     * 钱包列表
     */
    private List<JnWallet> jnWallets;


    public ColdWallet(List<String> mnemonicCode, List<JnWallet> jnWallets) {
        this.mnemonicCode = mnemonicCode;
        this.jnWallets = jnWallets;
    }

    public List<String> getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(List<String> mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public List<JnWallet> getJnWallets() {
        return jnWallets;
    }

    public void setJnWallets(List<JnWallet> jnWallets) {
        this.jnWallets = jnWallets;
    }


    @NonNull
    @Override
    public String toString() {
        return "ColdWallet{" +
                "mnemonicCode='" + Arrays.toString(mnemonicCode.toArray()) + '\'' +
                ", jnWallets=" + Arrays.toString(jnWallets.toArray()) +
                '}';
    }
}
