package com.alsc.alsc_wallet.fragment.cold;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;

public class BtcTradeRecordFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_btc_trade_record;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.wallet_record);

    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }


}
