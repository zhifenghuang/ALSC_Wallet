package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.MoneyAccountAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

public class MoneyAccountFragment extends BaseFragment {

    private MoneyAccountAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_account;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.wallet_money_account);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<AccountItem> list = new ArrayList<>();
        list.add(new AccountItem(0));
        list.add(new AccountItem(1));
        list.add(new AccountItem(1));
        getAdapter().addData(list);

    }

    private MoneyAccountAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MoneyAccountAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }


    public static class AccountItem implements MultiItemEntity {

        public int itemType;

        public AccountItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }


}
