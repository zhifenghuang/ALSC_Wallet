package com.alsc.alsc_wallet.bean.chat;


import java.util.ArrayList;

public class ServiceMessageBean extends MessageBean {


    private ArrayList<QuestionBean> questions;

    private ArrayList<UserBean> mServices;

    public ArrayList<QuestionBean> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionBean> questions) {
        this.questions = questions;
    }

    public ArrayList<UserBean> getServices() {
        return mServices;
    }

    public void setServices(ArrayList<UserBean> services) {
        this.mServices = services;
    }
}
