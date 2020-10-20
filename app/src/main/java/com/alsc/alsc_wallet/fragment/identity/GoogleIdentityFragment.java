package com.alsc.alsc_wallet.fragment.identity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.alsc.alsc_wallet.R;
import com.common.fragment.BaseFragment;
import com.common.utils.QRCodeUtil;

public class GoogleIdentityFragment extends BaseFragment {

    private Bitmap mQrBmp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_google_identity;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvCopy, R.id.tvNextStep);
        mQrBmp = QRCodeUtil.createQRImage(getActivity(), "8B6QEDG100TREUNM", null);
        ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvNextStep:
                gotoPager(GoogleVerifyCodeFragment.class);
                break;
            case R.id.tvCopy:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mQrBmp != null) {
            mQrBmp.recycle();
            mQrBmp = null;
        }
    }


}
