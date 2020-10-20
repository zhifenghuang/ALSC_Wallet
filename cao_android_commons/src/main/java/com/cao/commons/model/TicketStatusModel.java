package com.cao.commons.model;

import java.io.Serializable;

/**
 * 票券状态Model
 *
 * @author CJZ
 * @Time 2019/6/17
 */
public class TicketStatusModel implements Serializable{

    private String status;
    private String Is_pay;
    private String audit;
    private String Is_comment;

    public TicketStatusModel(String status, String is_pay, String audit, String is_comment) {
        this.status = status;
        Is_pay = is_pay;
        this.audit = audit;
        Is_comment = is_comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_pay() {
        return Is_pay;
    }

    public void setIs_pay(String is_pay) {
        Is_pay = is_pay;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getIs_comment() {
        return Is_comment;
    }

    public void setIs_comment(String is_comment) {
        Is_comment = is_comment;
    }
}
