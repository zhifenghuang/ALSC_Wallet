package com.alsc.alsc_wallet.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.alsc.alsc_wallet.bean.chat.BasicMessage;
import com.alsc.alsc_wallet.bean.chat.GroupMessageBean;
import com.alsc.alsc_wallet.bean.chat.MessageBean;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String strDBName) {
        super(context, strDBName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createMsgTable(db);
        String sql = "create table User(" +   //用户表
                "userId long primary key," +
                "userLevel int," +
                "userType int," +   //用户类型
                "walletType int," +//钱包类型
                "avatarUrl text," +
                "nickName text," +
                "loginAccount text," +
                "account text," +
                "password text," +
                "userState int," +
                "bindMobile text," +
                "gender int," +
                "token text," +
                "district text," +
                "createTime long," +
                "walletContentMD text," +
                "remarks text" +
                ")";
        db.execSQL(sql);
        sql = "create table wallet(" +   //钱包表
                "id integer primary key autoincrement," +
                "name text," +
                "loginAccount text," +  //关联用户表
                "pathType int," +
                "mnemonicCode text," +
                "privateKey text," +
                "publicKey text," +
                "address text," +
                "keystore text," +
                "password text," +
                "walletType text," +
                "information text," +
                "money text," +
                "price text," +
                "createTime long," +
                "remarks text" +
                ")";
        db.execSQL(sql);
        sql = "create table address(" +   //地址表
                "id integer primary key autoincrement," +
                "loginAccount text," +  //关联用户表
                "name text," +
                "remarks text," +
                "path text," +
                "walletType text," +
                "createTime long," +
                "others text" +
                ")";
        db.execSQL(sql);
        sql = "create table trade(" +   //交易
                "hash varchar(100) primary key," +
                "loginAccount text," +  //关联用户表
                "walletType text," +
                "blockHash text," +
                "nonce int," +
                "result text," +
                "blockNumber int," +
                "gas text," +
                "fromAddress text," +
                "toAddress text," +
                "value text," +
                "transIndex int," +
                "gasPrice text," +
                "sendType int," +
                "createTime long," +
                "remarks text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DatabaseHelper", "onUpgrade: " + oldVersion + ", " + newVersion);
        if (oldVersion == 1) {   //数据库修改主键，删除表建新表，插入数据
            ArrayList<BasicMessage> msgList = getAll(db, "message", MessageBean.class);
            ArrayList<BasicMessage> groupMsgList = getAll(db, "group_message", GroupMessageBean.class);
            String sql = "DROP TABLE message";
            db.execSQL(sql);
            sql = "DROP TABLE group_message";
            db.execSQL(sql);
            createMsgTable(db);
            try {
                db.beginTransaction();
                if (!msgList.isEmpty()) {
                    for (BasicMessage msg : msgList) {
                        insert(db, msg);
                    }
                }

                if (!groupMsgList.isEmpty()) {
                    for (BasicMessage msg : groupMsgList) {
                        insert(db, msg);
                    }
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {

            } finally {
                db.endTransaction();
            }
        }
    }

    public void createMsgTable(SQLiteDatabase db) {
        String sql = "create table message(" +   //消息表
                "messageId varchar(100)," +
                "cmd int," +
                "msgType int," +
                "fromId long," +
                "toId long," +
                "sendStatus int," +
                "receiveStatus int," +
                "content text," +
                "translate text," +
                "url text," +
                "createTime long," +
                "expire long," +
                "isDel byte," +
                "isRead byte," +
                "fileProgress int," +
                "extra text," +
                "owerId long," +  //消息所属id
                "tag varchar(100)," +  //两者id组合，用来做group by
                "primary key(messageId,owerId)" +
                ")";
        db.execSQL(sql);
        sql = "create table group_message(" +   //消息表
                "messageId varchar(100)," +
                "cmd int," +
                "msgType int," +
                "fromId long," +
                "groupId long," +
                "sendStatus int," +
                "receiveStatus int," +
                "content text," +
                "translate text," +
                "url text," +
                "createTime long," +
                "expire long," +
                "isDel byte," +
                "isRead byte," +
                "fileProgress int," +
                "owerId long," +  //消息所属id
                "extra text," +
                "primary key(messageId,owerId)" +
                ")";
        db.execSQL(sql);
    }

    /**
     * 插入一条数据 IDBItemOperation
     *
     * @return
     */
    private long insert(SQLiteDatabase db, IDBItemOperation dbItem) {
        long result = db.insert(dbItem.getTableName(), null, dbItem.getValues());
        return result;
    }


    private <T extends IDBItemOperation> ArrayList<T> getAll(SQLiteDatabase db, String tableName, Class cls) {
        String sql = "select * from " + tableName + " where isdel=0";
        ArrayList<T> list = getList(db, sql, cls);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    private <T extends IDBItemOperation> ArrayList<T> getList(SQLiteDatabase db, String strSql, Class<T> clz) {
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor == null) {
            return null;
        }
        ArrayList<T> dbObjectList = new ArrayList<T>();
        try {
            T dbObject;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                    .moveToNext()) {
                dbObject = clz.newInstance();
                dbObject.setDataByCursor(cursor);
                dbObjectList.add(dbObject);
            }
            if (dbObjectList.size() > 0) {
                return dbObjectList;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }


}
