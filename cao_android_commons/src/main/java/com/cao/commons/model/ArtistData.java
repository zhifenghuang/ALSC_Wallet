package com.cao.commons.model;

import java.util.List;

/**
 * Created by admin on 2016/3/2.
 */
public class ArtistData {

    private int start;
    private int totalRow;
    private int totalPage;
    private int currentPage;
    private int pageSize;
    private List<ResultsEntity> results;

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getStart() {
        return start;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private String name;
        private int id;
        private Object userId;
        private String recommend;
        private int ctgId;
        private String idNo;
        private String idnoPic;
        private String phone;
        private String email;
        private String sex;
        private Object photo;
        private String jobName;
        private String resident;
        private String answerTime;
        private int praiseNum;
        private int operId;
        private String status;
        private String intoduction;
        private String attachContent;
        private int followersNum;
        private int addFollowers;
        private String activityRole;
        private String createDate;
        private String updateDate;
        private List<String> pictures;
        private int level;
        private int commentsNum;

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public void setCommentsNum(int commentsNum) {
            this.commentsNum = commentsNum;
        }

        public int getCommentsNum() {
            return commentsNum;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public void setCtgId(int ctgId) {
            this.ctgId = ctgId;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public void setIdnoPic(String idnoPic) {
            this.idnoPic = idnoPic;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public void setResident(String resident) {
            this.resident = resident;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public void setOperId(int operId) {
            this.operId = operId;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setIntoduction(String intoduction) {
            this.intoduction = intoduction;
        }

        public void setAttachContent(String attachContent) {
            this.attachContent = attachContent;
        }

        public void setFollowersNum(int followersNum) {
            this.followersNum = followersNum;
        }

        public void setAddFollowers(int addFollowers) {
            this.addFollowers = addFollowers;
        }

        public void setActivityRole(String activityRole) {
            this.activityRole = activityRole;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public Object getUserId() {
            return userId;
        }

        public String getRecommend() {
            return recommend;
        }

        public int getCtgId() {
            return ctgId;
        }

        public String getIdNo() {
            return idNo;
        }

        public String getIdnoPic() {
            return idnoPic;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getSex() {
            return sex;
        }

        public Object getPhoto() {
            return photo;
        }

        public String getJobName() {
            return jobName;
        }

        public String getResident() {
            return resident;
        }

        public String getAnswerTime() {
            return answerTime;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public int getOperId() {
            return operId;
        }

        public String getStatus() {
            return status;
        }

        public String getIntoduction() {
            return intoduction;
        }

        public String getAttachContent() {
            return attachContent;
        }

        public int getFollowersNum() {
            return followersNum;
        }

        public int getAddFollowers() {
            return addFollowers;
        }

        public String getActivityRole() {
            return activityRole;
        }

        public String getCreateDate() {
            return createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public List<String> getPictures() {
            return pictures;
        }

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", userId=" + userId +
                    ", recommend='" + recommend + '\'' +
                    ", ctgId=" + ctgId +
                    ", idNo='" + idNo + '\'' +
                    ", idnoPic='" + idnoPic + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", sex='" + sex + '\'' +
                    ", photo=" + photo +
                    ", jobName='" + jobName + '\'' +
                    ", resident='" + resident + '\'' +
                    ", answerTime='" + answerTime + '\'' +
                    ", praiseNum=" + praiseNum +
                    ", operId=" + operId +
                    ", status='" + status + '\'' +
                    ", intoduction='" + intoduction + '\'' +
                    ", attachContent='" + attachContent + '\'' +
                    ", followersNum=" + followersNum +
                    ", addFollowers=" + addFollowers +
                    ", activityRole='" + activityRole + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", pictures=" + pictures +
                    ", level=" + level +
                    ", commentsNum=" + commentsNum +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "start=" + start +
                ", totalRow=" + totalRow +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", results=" + results +
                '}';
    }
}
