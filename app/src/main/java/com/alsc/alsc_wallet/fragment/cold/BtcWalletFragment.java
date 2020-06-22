package com.alsc.alsc_wallet.fragment.cold;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.BtcWalletAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class BtcWalletFragment extends BaseFragment {

    private BtcWalletAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_btc_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(R.id.llTop);
        setText(R.id.tvTitle, R.string.wallet_btc_wallet);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<BtcWalletItem> list = new ArrayList<>();
        list.add(new BtcWalletItem(0));
        list.add(new BtcWalletItem(1));
        list.add(new BtcWalletItem(1));
        list.add(new BtcWalletItem(1));
        getAdapter().setNewInstance(list);
    }

    private BtcWalletAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new BtcWalletAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position > 0) {
                        gotoPager(BtcTradeRecordFragment.class);
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

    protected boolean isNeedSetTopStyle() {
        return false;
    }


    public static class BtcWalletItem implements MultiItemEntity {

        public int itemType;

        public BtcWalletItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }


}
