package com.alsc.alsc_wallet.fragment.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.FollowedAdapter;
import com.alsc.alsc_wallet.adapter.UserInfoAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class UserInfoFragment extends BaseFragment {

    private UserInfoAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_ta_fans, "158"));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<UserInfoItem> list = new ArrayList<>();
        list.add(new UserInfoItem(0));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        getAdapter().setNewInstance(list);
    }

    private UserInfoAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new UserInfoAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position > 0) {
                        gotoPager(ViewpointDetailFragment.class);
                    }
                }
            });
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    public static class UserInfoItem implements MultiItemEntity {

        public int itemType;

        public UserInfoItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
