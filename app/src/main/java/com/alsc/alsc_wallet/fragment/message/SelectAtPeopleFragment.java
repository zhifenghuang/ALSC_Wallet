package com.alsc.alsc_wallet.fragment.message;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.PublishPicAdapter;
import com.alsc.alsc_wallet.adapter.SelectAtPeopleAdapter;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

public class SelectAtPeopleFragment extends BaseFragment {

    private SelectAtPeopleAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_at_people;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData(new SelectAtPeopleItem(0,""));
        getAdapter().addData(new SelectAtPeopleItem(1,getString(R.string.wallet_latest_at)));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(1,getString(R.string.wallet_my_follow)));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        getAdapter().addData(new SelectAtPeopleItem(2,""));
        setViewsOnClickListener(R.id.tvRight);
    }

    private SelectAtPeopleAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SelectAtPeopleAdapter(getActivity());
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
            case R.id.tvRight:
                goBack();
                break;
        }
    }

    public static class SelectAtPeopleItem implements MultiItemEntity {

        public int itemType;
        public String text;

        public SelectAtPeopleItem(int itemType, String text) {
            this.itemType = itemType;
            this.text = text;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }


}
