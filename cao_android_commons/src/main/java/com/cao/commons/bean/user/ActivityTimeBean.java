package com.cao.commons.bean.user;

import java.util.ArrayList;

/**
 * 活动弹窗信息
 */
public class ActivityTimeBean {
    private int is_pop;
    private long pop_start;
    private long pop_end;
    private long curr_time;
    private ArrayList<ActivityContent> list;

    public long getCurr_time() {
        return curr_time;
    }

    public void setCurr_time(long curr_time) {
        this.curr_time = curr_time;
    }

    public int getIs_pop() {
        return is_pop;
    }

    public void setIs_pop(int is_pop) {
        this.is_pop = is_pop;
    }

    public long getPop_start() {
        return pop_start;
    }

    public void setPop_start(long pop_start) {
        this.pop_start = pop_start;
    }

    public long getPop_end() {
        return pop_end;
    }

    public void setPop_end(long pop_end) {
        this.pop_end = pop_end;
    }

    public ArrayList<ActivityContent> getList() {
        return list;
    }

    public void setList(ArrayList<ActivityContent> list) {
        this.list = list;
    }

    public static class ActivityContent {
        private String title;
        private String title_en;
        private String content;
        private String content_en;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_en() {
            return title_en;
        }

        public void setTitle_en(String title_en) {
            this.title_en = title_en;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_en() {
            return content_en;
        }

        public void setContent_en(String content_en) {
            this.content_en = content_en;
        }
    }
}
