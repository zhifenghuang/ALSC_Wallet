package com.alsc.alsc_wallet.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.MessageAdapter;
import com.alsc.alsc_wallet.fragment.message.ArticleDetailFragment;
import com.alsc.alsc_wallet.fragment.message.FastMsgDetailFragment;
import com.alsc.alsc_wallet.fragment.message.FollowedFragment;
import com.alsc.alsc_wallet.fragment.message.PublishViewPointFragment;
import com.alsc.alsc_wallet.fragment.message.UserInfoFragment;
import com.alsc.alsc_wallet.fragment.message.ViewpointDetailFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class MessageFragment extends BaseFragment {

    private MessageAdapter mAdapter;
    private int mMsgType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter().addData(list);
        setViewsOnClickListener(R.id.ivMyAvatar, R.id.ivSearch, R.id.ivPublish,
                R.id.llHotMsg, R.id.llFastMsg, R.id.llQuanMsg, R.id.tvHot,
                R.id.tvNewest, R.id.tvFollow, R.id.tvMyDynamic);
    }

    private MessageAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MessageAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (mMsgType == 0) {
                        gotoPager(ArticleDetailFragment.class);
                    } else if (mMsgType == 1) {
                        gotoPager(FastMsgDetailFragment.class);
                    } else {
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
        int id = v.getId();
        switch (id) {
            case R.id.ivMyAvatar:
                gotoPager(UserInfoFragment.class);
                break;
            case R.id.ivSearch:
                gotoPager(FollowedFragment.class);
                break;
            case R.id.ivPublish:
                gotoPager(PublishViewPointFragment.class);
                break;
            case R.id.llHotMsg:
                mMsgType = 0;
                resetBtns((LinearLayout) v, fv(R.id.llFastMsg), fv(R.id.llQuanMsg));
                break;
            case R.id.llFastMsg:
                mMsgType = 1;
                resetBtns((LinearLayout) v, fv(R.id.llHotMsg), fv(R.id.llQuanMsg));
                break;
            case R.id.llQuanMsg:
                mMsgType = 2;
                resetBtns((LinearLayout) v, fv(R.id.llHotMsg), fv(R.id.llFastMsg));
                break;
            case R.id.tvHot:
                resetBtns((TextView) v, fv(R.id.tvNewest), fv(R.id.tvFollow), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvNewest:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvFollow), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvFollow:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvMyDynamic:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvFollow));
                break;
        }
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2, LinearLayout ll3) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        ll1.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll2.getChildAt(1).setVisibility(View.INVISIBLE);
        ((TextView) ll3.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll3.getChildAt(1).setVisibility(View.INVISIBLE);
    }

    private void resetBtns(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        tv2.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        tv3.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        tv4.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
