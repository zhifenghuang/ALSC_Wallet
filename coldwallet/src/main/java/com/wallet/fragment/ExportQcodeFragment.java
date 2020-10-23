package com.wallet.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseFragment;
import com.cao.commons.util.QRCodeUtil;
import com.cold.wallet.R;
import com.cold.wallet.databinding.FragmentExportQcodeBinding;

public class ExportQcodeFragment extends BaseFragment {

    private FragmentExportQcodeBinding binding;
    private String keystore;

    public static ExportQcodeFragment newInstance(Bundle bundle) {
        ExportQcodeFragment fragment = new ExportQcodeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_export_qcode, viewGroup, false);
        return binding.getRoot();
    }

    @Override
    protected void onFragmentFirstVisible() {
        keystore = getArguments().getString("keystore", "");

        setQcode();
    }


    private void setQcode() {
        Bitmap bitmap = QRCodeUtil.createQRImage(mContext, keystore, null);
        binding.ivQcode.setImageBitmap(bitmap);
    }
}
