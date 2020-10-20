package com.cao.commons.model;

/**
 * 评价标签
 *
 * @author CJZ
 * @Time 2019/6/25
 */
public class CommentFlagModel {

    private Comment_tag comment_tag;
    private boolean cached;

    public Comment_tag getComment_tag() {
        return comment_tag;
    }

    public void setComment_tag(Comment_tag comment_tag) {
        this.comment_tag = comment_tag;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public class Comment_tag {

        private String value;
        private int expire;

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public int getExpire() {
            return expire;
        }

    }

}
