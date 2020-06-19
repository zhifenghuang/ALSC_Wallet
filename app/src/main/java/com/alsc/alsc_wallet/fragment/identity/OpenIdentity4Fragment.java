package com.alsc.alsc_wallet.fragment.identity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.utils.Constants;

public class OpenIdentity4Fragment extends BaseFragment {

    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_identity4;
    }

    @Override
    protected void onViewCreated(View view) {
        mType = getArguments().getInt(Constants.BUNDLE_EXTRA, 0);
        if (mType == 0) {
            setViewGone(R.id.ll4, R.id.ll5, R.id.ll6);
            setText(R.id.tvTip, R.string.wallet_upload_photo_tip_1);
            setViewsOnClickListener(R.id.ll1, R.id.ll2, R.id.ll3, R.id.tvSubmit);
        } else {
            setViewGone(R.id.ll1, R.id.ll2, R.id.ll3);
            setText(R.id.tvTip, R.string.wallet_upload_photo_tip_2);
            setViewsOnClickListener(R.id.ll4, R.id.ll5, R.id.ll6, R.id.tvSubmit);
        }
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll1:
            case R.id.ll2:
            case R.id.ll3:
            case R.id.ll4:
            case R.id.ll5:
            case R.id.ll6:
                showSelectPhotoDialog();
                break;
            case R.id.tvSubmit:
                break;
        }
    }

    private void resetUI() {
        if (mType == 0) {
            resetLL(fv(R.id.ll1), R.string.wallet_upload_photo_1_success);
            resetLL(fv(R.id.ll2), R.string.wallet_upload_photo_2_success);
            resetLL(fv(R.id.ll3), R.string.wallet_upload_photo_3_success);
        } else {
            resetLL(fv(R.id.ll4), R.string.wallet_upload_photo_4_success);
            resetLL(fv(R.id.ll5), R.string.wallet_upload_photo_5_success);
            resetLL(fv(R.id.ll6), R.string.wallet_upload_photo_6_success);
        }
    }

    private void resetLL(LinearLayout ll, int newTextId) {
        ll.setBackgroundResource(R.drawable.bg_wallet_credentials_select);
        ll.getChildAt(0).setVisibility(View.GONE);
        ((TextView) ll.getChildAt(1)).setText(getString(newTextId));
        ((TextView) ll.getChildAt(1)).setTextColor(
                ContextCompat.getColor(getActivity(), R.color.color_07_bb_99));
    }

    private void showSelectPhotoDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_select_photo_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView,
                        R.id.tvCancel,
                        R.id.tvTakePhoto, R.id.tvAlbum);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvTakePhoto:
                    case R.id.tvAlbum:
                        resetUI();
                        break;
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
    }


}
