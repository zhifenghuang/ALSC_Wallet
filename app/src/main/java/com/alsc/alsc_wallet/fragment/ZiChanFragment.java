package com.alsc.alsc_wallet.fragment;

import android.view.View;
import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.cold.BtcWalletFragment;

public class ZiChanFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zi_chan;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvBtcWallet);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvBtcWallet:
                gotoPager(BtcWalletFragment.class);
                break;
        }
    }
}
