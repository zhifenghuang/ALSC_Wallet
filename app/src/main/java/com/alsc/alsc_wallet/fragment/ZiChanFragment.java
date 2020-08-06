package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.CoinSymbolZichanAdapter;
import com.alsc.alsc_wallet.bean.CoinSymbolBean;
import com.alsc.alsc_wallet.fragment.cold.BtcWalletFragment;

public class ZiChanFragment extends BaseFragment {

    private CoinSymbolZichanAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zi_chan;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.rl);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eth,"ETH","1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_usdt,"USDT","1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eos,"EOS","1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_ltc,"LTC","1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_dash,"DASH","1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_zec,"ZEC","1FyMFyFyFy......h4FyAVz"));
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl:
                gotoPager(BtcWalletFragment.class);
                break;
        }
    }

    private CoinSymbolZichanAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new CoinSymbolZichanAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
