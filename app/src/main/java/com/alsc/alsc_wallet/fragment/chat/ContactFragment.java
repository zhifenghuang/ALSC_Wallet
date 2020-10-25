package com.alsc.alsc_wallet.fragment.chat;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.ContactAdapter;
import com.alsc.chat.fragment.ApplyListFragment;
import com.alsc.chat.fragment.GroupListFragment;
import com.alsc.chat.fragment.LabelFragment;
import com.alsc.chat.fragment.StarFriendFragment;
import com.alsc.chat.fragment.UserInfoFragment;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private ContactAdapter mAdapter;
    private ArrayList<UserBean> mFriendList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycleview;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData(new ContactItem(0, getString(R.string.wallet_new_friend), R.drawable.wallet_new_friend));
        getAdapter().addData(new ContactItem(0, getString(R.string.wallet_my_group), R.drawable.wallet_group));
        getAdapter().addData(new ContactItem(0, getString(R.string.wallet_star_friend), R.drawable.wallet_star_friend));
        getAdapter().addData(new ContactItem(0, getString(R.string.wallet_label_list), R.drawable.wallet_label));

    }

    private ContactAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (position == 0) {
                        DataManager.getInstance().setNoNew();
                        setNewVerify(false);
                        gotoPager(ApplyListFragment.class);
                    } else if (position == 1) {
                        gotoPager(GroupListFragment.class);
                    } else if (position == 2) {
                        gotoPager(StarFriendFragment.class);
                    } else if (position == 3) {
                        gotoPager(LabelFragment.class);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, mAdapter.getItem(position).getFriend());
                        gotoPager(UserInfoFragment.class, bundle);
                    }
                }
            });
        }
        return mAdapter;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void updateUIText() {
    }

    @Override
    public void onClick(View v) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(HashMap<String, Object> map) {
        if (getView() != null && map != null) {
            if (map.containsKey(Constants.EDIT_FRIEND)) {
                UserBean userBean = (UserBean) map.get(Constants.EDIT_FRIEND);
                editFriend(userBean);
            }
        }
    }

    public void setData(ArrayList<UserBean> friendList) {
        mFriendList = friendList;
        resort(mFriendList);
        if (getView() != null) {
            getAdapter().setNewData(getNewList(mFriendList));
            getAdapter().notifyDataSetChanged();
        }
    }

    public void setNewVerify(boolean isHasNewVerify) {
        if (getView() != null) {
            getAdapter().setNew(isHasNewVerify);
        }
    }

    private void editFriend(UserBean bean) {
        if (mFriendList == null || mFriendList.isEmpty()) {
            return;
        }
        int index = 0;
        for (UserBean userBean : mFriendList) {
            if (userBean.getBlock() == 1) {
                ++index;
                continue;
            }
            if (userBean.getContactId() == bean.getContactId()) {
                mFriendList.set(index, bean);
                DataManager.getInstance().saveFriends(mFriendList);
                List<ContactItem> items = getAdapter().getData();
                index = 0;
                for (ContactItem item : items) {
                    if (item.getItemType() == 1
                            && item.getFriend().getContactId() == bean.getContactId()) {
                        item.setFriend(bean);
                        getAdapter().notifyDataSetChanged();
                        return;
                    }
                    ++index;
                }
                return;
            }
            ++index;
        }
    }

    private ArrayList<ContactItem> getNewList(ArrayList<UserBean> list) {
        ArrayList<ContactItem> newList = new ArrayList<>();
        ContactItem item = new ContactItem(0, getString(R.string.wallet_new_friend), R.drawable.wallet_new_friend);
        newList.add(item);
        item = new ContactItem(0, getString(R.string.wallet_my_group), R.drawable.wallet_group);
        newList.add(item);
        item = new ContactItem(0, getString(R.string.wallet_star_friend), R.drawable.wallet_star_friend);
        newList.add(item);
        item = new ContactItem(0, getString(R.string.wallet_label_list), R.drawable.wallet_label);
        newList.add(item);
        if (list != null) {
            for (UserBean bean : list) {
                if (bean.getBlock() == 1) {
                    continue;
                }
                item = new ContactItem(1, bean);
                newList.add(item);
            }
        }
        return newList;
    }

    private void resort(List<UserBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean o1, UserBean o2) {
                String name1 = o1.getPinyinName();
                String name2 = o2.getPinyinName();
                if (name1.startsWith("#") && name2.startsWith("#")) {
                    return name1.compareTo(name2);
                }
                if (name1.startsWith("#")) {
                    return 1;
                }
                if (name2.startsWith("#")) {
                    return -1;
                }
                return name1.compareTo(name2);
            }
        });
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }

    public static class ContactItem implements MultiItemEntity {

        public int itemType;
        public int iconResId;
        public String name;
        public UserBean friend;

        public ContactItem(int itemType, UserBean friend) {
            this.itemType = itemType;
            this.friend = friend;
        }

        public ContactItem(int itemType, String name, int iconResId) {
            this.itemType = itemType;
            this.name = name;
            this.iconResId = iconResId;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setFriend(UserBean friend) {
            this.friend = friend;
        }

        public UserBean getFriend() {
            return friend;
        }
    }
}
