package com.alsc.alsc_wallet.fragment.online;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.common.fragment.BaseFragment;

public class TradeRecordFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_trade_record;
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
