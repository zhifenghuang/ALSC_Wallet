package com.cao.commons.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.cao.commons.SPConstants;
import com.cao.commons.bean.cold.ColdAddressBean;
import com.cao.commons.bean.cold.TradeInfoBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.bean.chat.BasicMessage;
import com.cao.commons.bean.chat.GroupMessageBean;
import com.cao.commons.bean.chat.MessageBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangwei.ma on 15-12-23.
 */


public class DatabaseOperate extends DBOperate {

    private static final String TAG = "DataManager";

    private static final String DB_NAME = "alsc.db";

    private static DatabaseOperate mDBManager = null;

    private static Context mContext;

    private DatabaseOperate(SQLiteDatabase db) {
        super(db);
    }

    public static void setContext(Context context) {
        mContext = context;
    }


    public static DatabaseOperate getInstance() {
        if (mDBManager == null) {
            synchronized (TAG) {
                if (mDBManager == null) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(mContext, DB_NAME);
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    mDBManager = new DatabaseOperate(db);

                    // ---------  升级数据库
                    mDBManager.upgradeDb();
                }
            }
        }
        return mDBManager;
    }

    public static void reset() {
        mDBManager = null;
    }

    public void upgradeDb() {
    }

    public <T extends IDBItemOperation> ArrayList<T> getAll(String tableName, Class cls) {
        String sql = "select * from " + tableName + " where isdel=0";
        ArrayList<T> list = mDBManager.getList(sql, cls);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public ArrayList<UserBean> getUserInfos() {
        String sql = "select * from user order by createTime desc";
        ArrayList<UserBean> list = mDBManager.getList(sql, UserBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public ArrayList<UserBean> getColdUserInfos() {
        String sql = "select * from user where walletType = 1";
        ArrayList<UserBean> list = mDBManager.getList(sql, UserBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public UserBean getUserInfos(long userId) {
        String sql = "select * from user where userId=" + userId;
        ArrayList<UserBean> list = mDBManager.getList(sql, UserBean.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void changeUserLevel(long userId, int level) {
        UserBean userBean = getUserInfos(userId);
        if (userBean != null) {
            userBean.setLevel(level);
            userBean.setUserLevel(level);
            DatabaseOperate.getInstance().insertOrUpdate(userBean);
        }
    }

    public ArrayList<MessageBean> getAllUserChatMsg(long myId, long chatUserId) {
        String sql = String.format("select * from message where owerId=%d and (expire=0 or expire>%d) and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d)) order by createTime desc",
                myId, System.currentTimeMillis(), myId, chatUserId, chatUserId, myId);

        ArrayList<MessageBean> list = mDBManager.getList(sql, MessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void deleteAllExprieMsg() {
        String sql = "delete from message where (expire!=0 and expire<" + System.currentTimeMillis() + ") or tag like '0_%'";
        mDBManager.execSQL(sql);

        sql = String.format("delete from group_message where expire!=0 and expire<%d", System.currentTimeMillis());
        mDBManager.execSQL(sql);
    }


    public ArrayList<MessageBean> getUserChatMsg(long myId, long chatUserId, long time, int limit) {
        String sql;
        if (time == 0l) {
            sql = String.format("select * from message where owerId=%d and (expire=0 or expire>%d) and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d)) order by createTime desc limit 0,%d",
                    myId, System.currentTimeMillis(), myId, chatUserId, chatUserId, myId, limit);
        } else {
            sql = String.format("select * from message where owerId=%d and (expire=0 or expire>%d) and createTime<%d and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d)) order by createTime",
                    myId, System.currentTimeMillis(), time, myId, chatUserId, chatUserId, myId);
        }
        ArrayList<MessageBean> list = mDBManager.getList(sql, MessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public ArrayList<GroupMessageBean> getAllGroupMsg(long myId, long groupId) {
        String sql = String.format("select * from group_message where owerId=%d and (expire=0 or expire>%d) and groupId=%d order by createTime desc",
                myId, System.currentTimeMillis(), groupId);
        ArrayList<GroupMessageBean> list = mDBManager.getList(sql, GroupMessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public ArrayList<GroupMessageBean> getGroupMsg(long myId, long groupId, long time, int limit) {
        String sql;
        if (time == 0l) {
            sql = String.format("select * from group_message where owerId=%d and (expire=0 or expire>%d) and groupId=%d order by createTime desc limit 0,%d",
                    myId, System.currentTimeMillis(), groupId, limit);
        } else {
            sql = String.format("select * from group_message where owerId=%d and (expire=0 or expire>%d) and groupId=%d and createTime<%d order by createTime",
                    myId, System.currentTimeMillis(), groupId, time);
        }
        ArrayList<GroupMessageBean> list = mDBManager.getList(sql, GroupMessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setAllMsgRead(long myId, long chatUserId) {
        String sql = String.format("update message set expire=expire-createTime+%d where owerId=%d and expire>0 and isRead=0 and fromId=%d and toId=%d",
                System.currentTimeMillis(), myId, chatUserId, myId);
        mDBManager.execSQL(sql);
        sql = String.format("update message set isRead=1 where owerId=%d and fromId=%d and toId=%d",
                myId, chatUserId, myId);
        mDBManager.execSQL(sql);
    }

    public void setAllGroupMsgRead(long myId, long groupId) {
        String sql = String.format("update group_message set isRead=1 where owerId=%d and groupId=%d",
                myId, groupId);
        mDBManager.execSQL(sql);
    }

    public void deleteUserChatRecord(long myId, long chatUserId) {
        String sql = String.format("delete from message where owerId=%d and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d))",
                myId, myId, chatUserId, chatUserId, myId);
        mDBManager.execSQL(sql);
    }

    public void deleteAllChatRecord(long myId) {
        String sql = String.format("delete from message where owerId=%d", myId);
        mDBManager.execSQL(sql);
    }

    public void deleteGroupChatRecord(long myId, long groupId) {
        String sql = String.format("delete from group_message where owerId=%d and groupId=%d",
                myId, groupId);
        mDBManager.execSQL(sql);
    }

    public void handleNotSendMsg(long myId) {
        String sql = String.format("update message set sendStatus=-1 where owerId=%d and fromId=%d and sendStatus=0",
                myId, myId);
        mDBManager.execSQL(sql);
        sql = String.format("update group_message set sendStatus=-1 where owerId=%d and fromId=%d and sendStatus=0",
                myId, myId);
        mDBManager.execSQL(sql);
    }

    public ArrayList<BasicMessage> getAllUnSendMsg(long myId) {
        ArrayList<BasicMessage> list = new ArrayList<>();
        String sql = String.format("select * from message where owerId=%d and fromId=%d and sendStatus<1",
                myId, myId);
        ArrayList<MessageBean> list1 = mDBManager.getList(sql, MessageBean.class);
        if (list1 != null) {
            list.addAll(list1);
        }
        sql = String.format("select * from group_message where owerId=%d and fromId=%d and sendStatus<1",
                myId, myId);
        ArrayList<GroupMessageBean> list2 = mDBManager.getList(sql, GroupMessageBean.class);
        if (list2 != null) {
            list.addAll(list2);
        }
        return list;
    }


    public ArrayList<MessageBean> searchChatRecordByText(long myId, long chatUserId, String text, int msgType) {
        String sql = String.format("select * from message where owerId=%d and msgType=%d and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d))",
                myId, msgType, myId, chatUserId, chatUserId, myId);
        sql += " and content like '%" + text + "%'";
        ArrayList<MessageBean> list = mDBManager.getList(sql, MessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public int searchChatRecordNum(long myId, long chatUserId, String text, int msgType) {
        String sql = String.format("select count(*) as cmd from message where owerId=%d and msgType=%d and ((fromId=%d and toId=%d) or (fromId=%d and toId=%d))",
                myId, msgType, myId, chatUserId, chatUserId, myId);
        sql += " and content like '%" + text + "%'";
        MessageBean messaage = mDBManager.getOne(sql, MessageBean.class);
        if (messaage == null) {
            return 0;
        }
        return messaage.getCmd();
    }

    public ArrayList<GroupMessageBean> searchGroupChatRecordByText(long myId, long groupId, String text, int msgType) {
        String sql = String.format("select * from group_message where owerId=%d and msgType=%d and groupId=%d",
                myId, msgType, groupId);
        sql += " and content like '%" + text + "%'";
        ArrayList<GroupMessageBean> list = mDBManager.getList(sql, GroupMessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public int searchGroupChatRecordNum(long myId, long groupId, String text, int msgType) {
        String sql = String.format("select count(*) as cmd from group_message where owerId=%d and msgType=%d and groupId=%d",
                myId, msgType, groupId);
        sql += " and content like '%" + text + "%'";
        GroupMessageBean bean = mDBManager.getOne(sql, GroupMessageBean.class);
        if (bean == null) {
            return 0;
        }
        return bean.getCmd();
    }

    public ArrayList<MessageBean> getUserChatList(long myId) {
        String sql = String.format("select tag,messageId,fromId,toId ,msgType,content,createTime,extra,sendStatus,sum(case when isRead<1 and owerId=%d and fromId!=%d then 1 else 0 end) as unReadNum from message where owerId=%d group by tag", myId, myId, myId);
        ArrayList<MessageBean> list = mDBManager.getList(sql, MessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public int getUnReadMsgNum(long myId) {
        String sql = String.format("select count(*) as unReadNum from message where owerId=%d and isRead=0 and fromId!=%d", myId, myId);
        MessageBean bean = mDBManager.getOne(sql, MessageBean.class);
        if (bean == null) {
            return 0;
        }
        return bean.getUnReadNum();
    }

    public int getGroupUnReadMsgNum(long myId) {
        String sql = String.format("select count(*) as unReadNum from group_message where owerId=%d and isRead=0 and fromId!=%d", myId, myId);
        GroupMessageBean bean = mDBManager.getOne(sql, GroupMessageBean.class);
        if (bean == null) {
            return 0;
        }
        return bean.getUnReadNum();
    }

    public int getUnReadNum(long myId, long chatUserId) {
        String sql = String.format("select count(*) as unReadNum from message where owerId=%d and isRead=0 and fromId=%d and toId=%d", myId, chatUserId, myId);
        MessageBean bean = mDBManager.getOne(sql, MessageBean.class);
        if (bean == null) {
            return 0;
        }
        return bean.getUnReadNum();
    }

    public int getGroupUnReadNum(long myId, long groupId) {
        String sql = String.format("select count(*) as unReadNum from group_message where owerId=%d and isRead=0 and fromId!=%d and groupId=%d", myId, myId, groupId);
        GroupMessageBean bean = mDBManager.getOne(sql, GroupMessageBean.class);
        if (bean == null) {
            return 0;
        }
        return bean.getUnReadNum();
    }

    public ArrayList<GroupMessageBean> getChatGroupList(long myId) {
        String sql = String.format("select groupId,messageId,msgType,fromId,content,createTime,extra,sendStatus,sum(case when isRead<1 and owerId=%d and fromId!=%d then 1 else 0 end) as unReadNum from group_message where owerId=%d group by groupId", myId, myId, myId);
        ArrayList<GroupMessageBean> list = mDBManager.getList(sql, GroupMessageBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void deleteAll(List<BasicMessage> dbItems, long myId) {
        if (dbItems == null || dbItems.isEmpty()) {
            return;
        }
        String strTBName = dbItems.get(0).getTableName();
        String strKey = dbItems.get(0).getPrimaryKeyName();
        String sql = "delete from " + strTBName + " where owerId=" + myId + " and ";
        int size = dbItems.size();
        for (int i = 0; i < size; ++i) {
            sql += (strKey + " like '" + dbItems.get(i).getMessageId()) + "'";
            if (i < size - 1) {
                sql += " or ";
            }
        }
        mDBManager.execSQL(sql);
    }

    public void deleteOne(BasicMessage item, long myId) {
        String strTBName = item.getTableName();
        String strKey = item.getPrimaryKeyName();
        String sql = "delete from " + strTBName + " where owerId=" + myId + " and "
                + (strKey + " like '" + item.getMessageId()) + "'";
        mDBManager.execSQL(sql);
    }

    public ArrayList<WalletDataBean> getWalletInfos() {
        ArrayList<WalletDataBean> list = null;
        if (DataManager.getInstance().getUser() != null) {
            String sql = String.format("select * from wallet where loginAccount='%s'", DataManager.getInstance().getUser().getAccount());
            list = mDBManager.getList(sql, WalletDataBean.class);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public boolean insertWalletInfo(WalletDataBean bean) {
        ArrayList<WalletDataBean> list = null;
        if (DataManager.getInstance().getUser() != null) {
            String sql = String.format("select * from wallet where address='%s' and walletType='%s'", bean.getAddress(), bean.getWalletType());
            list = mDBManager.getList(sql, WalletDataBean.class);
        }
        if (list == null || list.size() == 0) {
            DatabaseOperate.getInstance().insertWithNoPrimaryKey(bean);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取钱包信息
     *
     * @param walletType 钱包类型
     * @return
     */
    public ArrayList<WalletDataBean> getWalletInfos(String walletType) {
        ArrayList<WalletDataBean> list = null;
        if (DataManager.getInstance().getUser() != null) {
            String sql = String.format("select * from wallet where walletType='%s' and loginAccount='%s'", walletType, DataManager.getInstance().getUser().getAccount());
            list = mDBManager.getList(sql, WalletDataBean.class);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 获取钱包信息
     *
     * @return
     */
    public WalletDataBean getWalletInfo(int id) {
        ArrayList<WalletDataBean> list = null;
        if (DataManager.getInstance().getUser() != null) {
            String sql = String.format("select * from wallet where  id='%d'", id);

            list = mDBManager.getList(sql, WalletDataBean.class);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        }

        return null;
    }

    /**
     * 获取地址信息
     *
     * @return
     */
    public ArrayList<ColdAddressBean> getAddressInfos(String walletType) {
        ArrayList<ColdAddressBean> list = null;
        if (DataManager.getInstance().getUser() != null) {
            String sql;
            if (TextUtils.isEmpty(walletType)) {
                sql = String.format("select * from address where  loginAccount='%s'", DataManager.getInstance().getUser().getAccount());
            } else {
                sql = String.format("select * from address where  loginAccount='%s' and walletType = '%s'", DataManager.getInstance().getUser().getAccount(), walletType);
            }
            list = mDBManager.getList(sql, ColdAddressBean.class);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }


    /**
     * 获取交易信息
     *
     * @param hash 钱包类型
     * @return
     */
    public TradeInfoBean getTradeInfo(String hash) {
        String sql = String.format("select * from trade where hash='%s' ", hash);
        ArrayList<TradeInfoBean> list = mDBManager.getList(sql, TradeInfoBean.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取所有交易信息
     *
     * @return
     */
    public ArrayList<TradeInfoBean> getAllTradeInfo() {
        String sql = String.format("select * from trade where loginAccount='%s' ", DataManager.getInstance().getUser().getAccount());
        ArrayList<TradeInfoBean> list = mDBManager.getList(sql, TradeInfoBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
