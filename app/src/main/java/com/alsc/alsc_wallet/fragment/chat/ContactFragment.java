package com.alsc.alsc_wallet.fragment.chat;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.ContactAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ContactFragment extends BaseFragment {

    private ContactAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycleview;
    }

    @Override
    protected void onViewCreated(View view) {
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


        getAdapter().addData(new ContactItem(1, "ABC"));
        getAdapter().addData(new ContactItem(1, "ABD"));
        getAdapter().addData(new ContactItem(1, "ACD"));
        getAdapter().addData(new ContactItem(1, "AEF"));
        getAdapter().addData(new ContactItem(1, "AGK"));

        getAdapter().addData(new ContactItem(1, "BBC"));
        getAdapter().addData(new ContactItem(1, "BBD"));
        getAdapter().addData(new ContactItem(1, "BCD"));
        getAdapter().addData(new ContactItem(1, "BEF"));
        getAdapter().addData(new ContactItem(1, "BGK"));


        getAdapter().addData(new ContactItem(1, "CBC"));
        getAdapter().addData(new ContactItem(1, "CBD"));
        getAdapter().addData(new ContactItem(1, "CCD"));
        getAdapter().addData(new ContactItem(1, "CEF"));
        getAdapter().addData(new ContactItem(1, "CGK"));
    }

    private ContactAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }

    public static class ContactItem implements MultiItemEntity {

        public int itemType;
        public int iconResId;
        public String name;

        public ContactItem(int itemType, String name) {
            this.itemType = itemType;
            this.name = name;
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
    }
}
