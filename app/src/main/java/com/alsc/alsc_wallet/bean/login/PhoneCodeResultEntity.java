package com.alsc.alsc_wallet.bean.login;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 */

public class PhoneCodeResultEntity implements Serializable {


    private List<PhoneCodeInfo> phone_code;

    public List<PhoneCodeInfo> getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(List<PhoneCodeInfo> phone_code) {
        this.phone_code = phone_code;
    }
}
