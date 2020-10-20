package com.cao.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 公告Model
 *
 * @author CJZ
 * @Time 2019/7/5
 */
public class AnnouncementModel implements Serializable {

    private List<Data> data;
    private int total;
    private int offset;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }


    public class Data {

        private int id;
        private int communityid;
        private String title;
        private String content;
        private String addtime;
        private String sender;
        private int is_del;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setCommunityid(int communityid) {
            this.communityid = communityid;
        }

        public int getCommunityid() {
            return communityid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSender() {
            return sender;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getIs_del() {
            return is_del;
        }

    }

}
