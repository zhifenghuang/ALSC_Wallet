package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.alsc_wallet.adapter.ColdWalletAdapter;
import com.alsc.alsc_wallet.adapter.ImportWalletAddressAdapter;
import com.common.fragment.BaseFragment;
import com.common.bean.CoinSymbolBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

public class ColdWalletFragment extends BaseFragment {

    private ColdWalletAdapter mAdapter;

    private ImportWalletAddressAdapter mAdapter1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cold_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvOnlineWallet);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eth, "ETH", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_usdt_2, "USDT", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eos, "EOS", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_ltc, "LTC", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_dash, "DASH", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_zec, "ZEC", "1FyMFyFyFy......h4FyAVz"));

        recyclerView = view.findViewById(R.id.recyclerView1);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter1().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter1());
        getAdapter1().addData("");
        getAdapter1().addData("");
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvOnlineWallet:
                ((MainActivity) getActivity()).setWalletType(0);
                break;
        }
    }

    private ColdWalletAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ColdWalletAdapter(getActivity());
        }
        return mAdapter;
    }

    private ImportWalletAddressAdapter getAdapter1() {
        if (mAdapter1 == null) {
            mAdapter1 = new ImportWalletAddressAdapter(getActivity());
            mAdapter1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                }
            });
        }
        return mAdapter1;
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
