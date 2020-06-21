package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.fragment.message.ArticleDetailFragment;
import com.alsc.alsc_wallet.fragment.message.FollowedFragment;
import com.alsc.alsc_wallet.fragment.message.UserInfoFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserInfoAdapter extends BaseMultiItemQuickAdapter<UserInfoFragment.UserInfoItem, BaseViewHolder> {

    private Context mContext;

    public UserInfoAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_userinfo_0);
        addItemType(1, R.layout.item_userinfo_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, UserInfoFragment.UserInfoItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.getView(R.id.ll2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).gotoPager(FollowedFragment.class);
                    }
                });
                helper.getView(R.id.ll3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).gotoPager(FollowedFragment.class);
                    }
                });
                break;
            case 1:
                helper.getView(R.id.tvAllMsg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).gotoPager(ArticleDetailFragment.class);
                    }
                });
                helper.getView(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).showReportDialog(v);
                    }
                });
                helper.setImageResource(R.id.ivAvatar, R.mipmap.ic_launcher_round);
                RecyclerView recyclerView = helper.getView(R.id.picRecyclerView);
                MsgPicAdapter picAdapter;
                if (recyclerView.getAdapter() != null) {
                    picAdapter = (MsgPicAdapter) recyclerView.getAdapter();
                } else {
                    picAdapter = new MsgPicAdapter(mContext);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    picAdapter.onAttachedToRecyclerView(recyclerView);
                    recyclerView.setAdapter(picAdapter);
                }
                ArrayList<String> list = new ArrayList<>();
                list.add("1");
                list.add("1");
                list.add("1");
                picAdapter.setNewInstance(list);

                recyclerView = helper.getView(R.id.commentRecyclerView);
                MsgCommentAdapter commentAdapter;
                if (recyclerView.getAdapter() != null) {
                    commentAdapter = (MsgCommentAdapter) recyclerView.getAdapter();
                } else {
                    commentAdapter = new MsgCommentAdapter(mContext);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    commentAdapter.onAttachedToRecyclerView(recyclerView);
                    recyclerView.setAdapter(commentAdapter);
                }
                list = new ArrayList<>();
                list.add("1");
                list.add("1");
                list.add("1");
                commentAdapter.setNewInstance(list);
                break;
        }
    }

}
