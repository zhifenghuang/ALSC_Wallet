package com.alsc.alsc_wallet.fragment.message;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.FastMsgDetailAdapter;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

public class FastMsgDetailFragment extends BaseFragment {

    private FastMsgDetailAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_fast_msg));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<FastMsgDetailItem> list = new ArrayList<>();
        list.add(new FastMsgDetailItem(0));
        getAdapter().setNewInstance(list);
    }


    private FastMsgDetailAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new FastMsgDetailAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    public static class FastMsgDetailItem implements MultiItemEntity {

        public int itemType;

        public FastMsgDetailItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

}
