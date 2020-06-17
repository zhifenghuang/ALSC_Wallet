package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;

public class BankPaySettingFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bank_pay_setting;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvSave);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvSave:
                gotoPager(BuyDetailFragment.class);
                break;
        }
    }


}
