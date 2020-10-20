package com.alsc.alsc_wallet.fragment.online;

import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.dialog.PasswordDialog;
import com.common.fragment.BaseFragment;

public class TransferFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_transfer;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.wallet_btc_transfer);
        setViewsOnClickListener(R.id.tvTransfer);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvTransfer:
                PasswordDialog dialog = new PasswordDialog(getActivity());
                dialog.show();
                break;
        }
    }


}
