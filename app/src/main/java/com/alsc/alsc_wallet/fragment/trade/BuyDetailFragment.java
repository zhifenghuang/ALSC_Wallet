package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import com.common.fragment.BaseFragment;

public class BuyDetailFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvCancelOrder, R.id.tvMarkPayed);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvCancelOrder:
                final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_cancel_order_dialog);
                dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                    @Override
                    public void initView(View view) {
                        dialogFragment.setDialogViewsOnClickListener(view, R.id.tv1, R.id.tv2);
                    }

                    @Override
                    public void onViewClick(int viewId) {

                    }
                });
                dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
                break;
            case R.id.tvMarkPayed:
                break;
        }
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }


}
