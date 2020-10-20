package com.cao.commons.model;

/**
 * 描述结果
 *
 * @author xyx on 2020/1/20 0020
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */
public class SimpleUserinfoModel {


    /**
     * userName : 13426374937
     * userPortrait : https://static.xqcsh.com/default/timg.jpg
     * createTime : 2020-01-04 11:47:18
     * userid : 41081792
     */

    private String userName;
    private String userPortrait;
    private String createTime;
    private String userid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
