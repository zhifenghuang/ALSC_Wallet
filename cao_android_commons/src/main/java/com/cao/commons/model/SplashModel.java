package com.cao.commons.model;

/**
 * 启动页详情
 *
 * @author CJZ
 * @Time 2019/5/30
 */
public class SplashModel {

        private String image;
        private String url;
        private int jumptime;
        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setJumptime(int jumptime) {
            this.jumptime = jumptime;
        }
        public int getJumptime() {
            return jumptime;
        }


}
