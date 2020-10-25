package com.alsc.alsc_wallet.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.ChatMsgFragment;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.manager.ChatManager;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.bean.AssetsBean;
import com.common.fragment.BaseFragment;
import com.alsc.alsc_wallet.fragment.ColdWalletFragment;
import com.alsc.alsc_wallet.fragment.QuotationFragment;
import com.alsc.alsc_wallet.fragment.NewsFragment;
import com.alsc.alsc_wallet.fragment.OnlineWalletFragment;
import com.common.http.HttpMethods;
import com.common.http.HttpObserver;
import com.common.http.OnHttpErrorListener;
import com.common.http.SubscriberOnNextListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ChatBaseActivity {

    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    private int mCurrentWalletType = 0;//0表示热钱包，1表示冷钱包

    private boolean mIsGetFriend;
    private boolean mIsGetGroup;
    private ArrayList<UserBean> mFriendList;
    private ArrayList<GroupBean> mGroupList;

    private ChatMsgFragment mChatMsgFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mChatMsgFragment = new ChatMsgFragment();
        mBaseFragment.add(mChatMsgFragment);
        mBaseFragment.add(new NewsFragment());
        mBaseFragment.add(new QuotationFragment());
        mBaseFragment.add(new OnlineWalletFragment());
        mBaseFragment.add(new ColdWalletFragment());
    }

    public void setWalletType(int walletType) {
        mCurrentWalletType = walletType;
        switchFragment(mBaseFragment.get(walletType == 0 ? 3 : 4));
    }

    private void initViews() {
        switchFragment(mBaseFragment.get(0));
        resetBottomBar(0);
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llBottom.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    if (tag == 4) {
                        if (mCurrentWalletType == 0) {
                            switchFragment(mBaseFragment.get(4));
                        } else {
                            switchFragment(mBaseFragment.get(5));
                        }
                    } else {
                        switchFragment(mBaseFragment.get(tag));
                    }
                    resetBottomBar(tag);
                }
            });
        }
    }

    private void resetBottomBar(int currentPos) {
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        ViewGroup itemView;
        for (int i = 0; i < count; ++i) {
            itemView = (ViewGroup) llBottom.getChildAt(i);
            (((ImageView) itemView.getChildAt(0))).setImageResource(getResIdByIndex(i, currentPos == i));
            (((TextView) itemView.getChildAt(1))).
                    setTextColor(ContextCompat.getColor(this, currentPos == i ? R.color.color_07_bb_99 : R.color.color_a0_ac_c0));
        }
    }

    private int getResIdByIndex(int index, boolean isCheck) {
        int id = 0;
        switch (index) {
            case 0:
                id = isCheck ? R.drawable.wallet_chat_select : R.drawable.wallet_chat_unselect;
                break;
            case 1:
                id = isCheck ? R.drawable.wallet_msg_select : R.drawable.wallet_msg_unselect;
                break;
            case 2:
                id = isCheck ? R.drawable.wallet_trade_select : R.drawable.wallet_trade_unselect;
                break;
            case 3:
                id = isCheck ? R.drawable.wallet_hq_select : R.drawable.wallet_hq_unselect;
                break;
            case 4:
                id = isCheck ? R.drawable.wallet_zc_select : R.drawable.wallet_zc_unselect;
                break;
        }
        return id;
    }

    public void onResume() {
        super.onResume();
        getMyAssets();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        if (mChatMsgFragment != null) {
            mFriendList = DataManager.getInstance().getFriends();
            mGroupList = DataManager.getInstance().getGroups();
            mChatMsgFragment.setData(mFriendList, mGroupList);
        }
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.add(R.id.fl, to, to.toString()).commit();
            } else {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.show(to).commit();
            }
        }
        mCurrentFragment = to;
    }


    public void getFriendFromServer() {
        if (mIsGetFriend) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetFriend = true;
        ChatHttpMethods.getInstance().getFriends(new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
            @Override
            public void onNext(ArrayList<UserBean> list, String msg) {
                DataManager.getInstance().saveFriends(list);
                mFriendList = list;
                if (mChatMsgFragment != null) {
                    mChatMsgFragment.setData(mFriendList, mGroupList);
                }
                mIsGetFriend = false;
            }
        }, this, false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                mIsGetFriend = false;
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                mIsGetFriend = false;
                if (errorCode == 401) {
                    ChatManager.getInstance().showLoginOutDialog();
                }
            }
        }));
    }

    public void getGroupFromServer() {
        if (mIsGetGroup) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetGroup = true;
        ChatHttpMethods.getInstance().getGroups(1, Integer.MAX_VALUE - 1,
                new HttpObserver(new SubscriberOnNextListener<ArrayList<GroupBean>>() {
                    @Override
                    public void onNext(ArrayList<GroupBean> list, String msg) {
                        DataManager.getInstance().saveGroups(list);
                        mGroupList = list;
                        if (mChatMsgFragment != null) {
                            mChatMsgFragment.setData(mFriendList, mGroupList);
                        }
                        mIsGetGroup = false;
                    }
                }, this, false, new OnHttpErrorListener() {
                    @Override
                    public void onConnectError(Throwable e) {
                        mIsGetGroup = false;
                    }

                    @Override
                    public void onServerError(int errorCode, String errorMsg) {
                        mIsGetGroup = false;
                        if (errorCode == 401) {
                            ChatManager.getInstance().showLoginOutDialog();
                        }
                    }
                }));
    }

    public void getMyAssets() {
        HttpMethods.getInstance().assets(new HttpObserver(new SubscriberOnNextListener<AssetsBean>() {
            @Override
            public void onNext(AssetsBean bean, String msg) {
                if (bean == null) {
                    return;
                }
                DataManager.getInstance().saveMyAssets(bean);
                ((OnlineWalletFragment) mBaseFragment.get(3)).setAssetBean(bean);
            }
        }, this, false, this));
    }
}
