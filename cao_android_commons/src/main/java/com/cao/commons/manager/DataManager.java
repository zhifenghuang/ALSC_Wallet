package com.cao.commons.manager;

import android.text.TextUtils;

import com.cao.commons.bean.ArticleBean;
import com.cao.commons.bean.AssetsBean;
import com.cao.commons.bean.chat.ChatSettingBean;
import com.cao.commons.bean.chat.ChatSubBean;
import com.cao.commons.bean.chat.FilterMsgBean;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.LabelBean;
import com.cao.commons.bean.chat.QuestionBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.user.MyUserInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager mDataManager;

    private UserBean mMyInfo;

    private Object mObject;

    private Gson mGson;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (mDataManager == null) {
            synchronized (TAG) {
                if (mDataManager == null) {
                    mDataManager = new DataManager();
                }
            }
        }
        return mDataManager;
    }

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    public String getToken() {
        return Preferences.getInstacne().getValues("token", "");
    }

    public void saveToken(String token) {
        Preferences.getInstacne().setValues("token", token == null ? "" : token);
    }


    public Object getObjectByKey(String key, Type tClass) {
        String str = Preferences.getInstacne().getValues(key, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, tClass);
    }

    public void saveObjectByKey(String key, Object object) {
        Preferences.getInstacne().setValues(key, getGson().toJson(object));
    }

    public void saveUser(UserBean userBean) {
        mMyInfo = userBean;
        Preferences.getInstacne().setValues("user", getGson().toJson(userBean));
    }

    public UserBean getUser() {
        if (mMyInfo == null) {
            String str = Preferences.getInstacne().getValues("user", "");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            mMyInfo = getGson().fromJson(str, UserBean.class);
        }
        return mMyInfo;
    }

    public long getUserId() {
        return getUser() == null ? 0 : getUser().getUserId();
    }

    public void saveMyUserInfoBean(MyUserInfoBean myUserInfoBean) {
        Preferences.getInstacne().setValues("myUserInfoBean", myUserInfoBean == null ? "" : getGson().toJson(myUserInfoBean));
    }

    public MyUserInfoBean getMyUserInfoBean() {
        String str = Preferences.getInstacne().getValues("myUserInfoBean", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, MyUserInfoBean.class);
    }

    public void saveLanguage(int language) {
        Preferences.getInstacne().setValues("language", language);
    }

    public int getLanguage() {
        return Preferences.getInstacne().getValues("language", 0);
    }


    public void saveTransferFee(float fee) {
        Preferences.getInstacne().setValues("transfer_fee", fee);
    }

    public float getTransferFee() {
        return Preferences.getInstacne().getValues("transfer_fee", 0.0f);
    }

    public void saveFriends(ArrayList<UserBean> list) {
        Preferences.getInstacne().setValues("friends", list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<UserBean> getFriends() {
        String str = Preferences.getInstacne().getValues("friends", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<UserBean>>() {
        }.getType());
    }

    public void saveLabels(List<LabelBean> list) {
        Preferences.getInstacne().setValues("labels", list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<LabelBean> getLabels() {
        String str = Preferences.getInstacne().getValues("labels", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<LabelBean>>() {
        }.getType());
    }

    public void saveGroups(ArrayList<GroupBean> list) {
        Preferences.getInstacne().setValues("groups", list == null ? "" : getGson().toJson(list));
    }

    public void saveKeyboardHeight(int keyboardHeight) {
        Preferences.getInstacne().setValues("keyboard_height", keyboardHeight);
    }

    public int getKeyboardHeight() {
        return Preferences.getInstacne().getValues("keyboard_height", 0);
    }

    public boolean isHasNewVerify(long time) {
        long lastTime = Preferences.getInstacne().getValues("last_verify_time", 0l);
        if (time > lastTime) {
            Preferences.getInstacne().setValues("last_verify_time", time);
            Preferences.getInstacne().setValues("isHadNew", true);
            return true;
        }
        return Preferences.getInstacne().getValues("isHadNew", false);
    }

    public void setNoNew() {
        Preferences.getInstacne().setValues("isHadNew", false);
    }

    /**
     * 保存阅后即焚信息
     *
     * @param types
     */
    public void saveMsgDeleteType(HashMap<Long, Integer> types) {
        Preferences.getInstacne().setValues("msg_delete_type", types == null ? "" : getGson().toJson(types));
    }

    public HashMap<Long, Integer> getMsgDeleteType() {
        String str = Preferences.getInstacne().getValues("msg_delete_type", "");
        if (TextUtils.isEmpty(str)) {
            return new HashMap<>();
        }
        return getGson().fromJson(str, new TypeToken<HashMap<Long, Integer>>() {
        }.getType());
    }

    public void saveGroupUsers(long groupId, ArrayList<UserBean> list) {
        Preferences.getInstacne().setValues("group_users_" + groupId, list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<UserBean> getGroupUsers(long groupId) {
        String str = Preferences.getInstacne().getValues("group_users_" + groupId, "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<UserBean>>() {
        }.getType());
    }

    public void setGroupFilterMsg(long groupId, List<FilterMsgBean> list) {
        Preferences.getInstacne().setValues("group_filter_" + groupId, list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<FilterMsgBean> getGroupFilterMsg(long groupId) {
        String str = Preferences.getInstacne().getValues("group_filter_" + groupId, "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<FilterMsgBean>>() {
        }.getType());
    }


    public ArrayList<GroupBean> getGroups() {
        String str = Preferences.getInstacne().getValues("groups", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<GroupBean>>() {
        }.getType());
    }

    public void saveQuestions(ArrayList<QuestionBean> list) {
        Preferences.getInstacne().setValues("questions", list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<QuestionBean> getQuestions() {
        String str = Preferences.getInstacne().getValues("questions", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<QuestionBean>>() {
        }.getType());
    }

    public void saveChatSetting(ChatSettingBean bean) {
        Preferences.getInstacne().setValues("chat_setting", bean == null ? "" : getGson().toJson(bean));
    }

    public ChatSettingBean getChatSetting() {
        String str = Preferences.getInstacne().getValues("chat_setting", "");
        if (TextUtils.isEmpty(str)) {
            return new ChatSettingBean();
        }
        return getGson().fromJson(str, ChatSettingBean.class);
    }

    public void saveChatSubSettings(HashMap<String, ChatSubBean> map) {
        Preferences.getInstacne().setValues("chat_sub_setting", map == null ? "" : getGson().toJson(map));
    }

    public HashMap<String, ChatSubBean> getChatSubSettings() {
        String str = Preferences.getInstacne().getValues("chat_sub_setting", "");
        if (TextUtils.isEmpty(str)) {
            return new HashMap<>();
        }
        return getGson().fromJson(str, new TypeToken<HashMap<String, ChatSubBean>>() {
        }.getType());
    }

    public void saveColdEye(boolean isOpen) {
        Preferences.getInstacne().setValues("coldEyeOpen", isOpen);
    }

    public boolean getColdEye() {
        return Preferences.getInstacne().getValues("coldEyeOpen", true);
    }

    public void saveMyAssets(AssetsBean bean) {
        Preferences.getInstacne().setValues("assets", bean == null ? "" : getGson().toJson(bean));
    }

    public AssetsBean getMyAssets() {
        String str = Preferences.getInstacne().getValues("assets", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, AssetsBean.class);
    }

    public void saveArticles(ArrayList<ArticleBean> list) {
        Preferences.getInstacne().setValues("articles", list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<ArticleBean> getArticles() {
        String str = Preferences.getInstacne().getValues("articles", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<ArticleBean>>() {
        }.getType());
    }

    public void loginOut() {
        mMyInfo = null;
        saveToken(null);
        Preferences.getInstacne().setValues("user", "");
        Preferences.getInstacne().setValues("myUserInfoBean", "");
        Preferences.getInstacne().setValues("friends", "");
        Preferences.getInstacne().setValues("groups", "");
        Preferences.getInstacne().setValues("msg_delete_type", "");
        Preferences.getInstacne().setValues("labels", "");
        Preferences.getInstacne().setValues("last_verify_time", 0l);
        Preferences.getInstacne().setValues("isHadNew", false);
        Preferences.getInstacne().setValues("chat_setting", "");
        Preferences.getInstacne().setValues("chat_sub_setting", "");
    }

    public void saveUserGesturePassword(String userId, String value) {
        Preferences.getInstacne().setValues("gesturePassword" + userId, value);
    }

    public void saveUserGesturePassword(String value) {
        if (getUser() != null) {
            Preferences.getInstacne().setValues("gesturePassword" + getUser().getUserId(), value);
        }
    }

    public String getUserGesturePassword() {
        if (getUser() != null) {
            return Preferences.getInstacne().getValues("gesturePassword" + getUser().getUserId(), "");
        } else {
            return "";
        }
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        this.mObject = object;
    }


    public static boolean isFirstStartApp() {
        return Preferences.getInstacne().getValues("isFirstStartApp", true);
    }

    public static void setFirstStartApp() {
        Preferences.getInstacne().setValues("isFirstStartApp", false);
    }

    public void saveColdUser(UserBean userBean) {
        Preferences.getInstacne().setValues("ColdUser", getGson().toJson(userBean));
    }

    public UserBean getColdUser() {
        String str = Preferences.getInstacne().getValues("ColdUser", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        UserBean bean = getGson().fromJson(str, UserBean.class);
        return bean;
    }
}
