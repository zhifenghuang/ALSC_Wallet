package com.cao.commons.bean.user;

import java.util.List;

/**
 * 描述结果
 *
 * @author xyx on 2020/4/2 0002
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */
public class AwardBean {


    /**
     * award : [{"article_id":2,"content":"","content_en":"","title":"关于ALSC系统V1.0版本更新升级相关说明","title_en":"Notes on the update and upgrade of the ALSC system V1.0"}]
     * notice : {"article_id":7,"content":"","content_en":"","title":"关于FChain交易所提alsc币至ALSC V1.0系统","title_en":"About FChain exchange to bring alsc coins to ALSC V1.0 system"}
     */

    private AwardDetailBean notice;
    private List<AwardDetailBean> award;


    public AwardDetailBean getNotice() {
        return notice;
    }

    public void setNotice(AwardDetailBean notice) {
        this.notice = notice;
    }

    public List<AwardDetailBean> getAward() {
        return award;
    }

    public void setAward(List<AwardDetailBean> award) {
        this.award = award;
    }
}
