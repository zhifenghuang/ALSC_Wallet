package com.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseFragment;
import com.cold.wallet.R;
import com.cold.wallet.databinding.FragmentExportKeystoreBinding;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

public class ExportPrivateFragment extends BaseFragment implements View.OnClickListener  {

    private FragmentExportKeystoreBinding binding;
    private String keystore;

    public static ExportPrivateFragment newInstance(Bundle bundle) {
        ExportPrivateFragment fragment = new ExportPrivateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_export_keystore, viewGroup, false);
        binding.setClickListener(this);
        return binding.getRoot();
    }

    @Override
    protected void onFragmentFirstVisible() {
        keystore = getArguments().getString("keystore", "");

        binding.tvTitle.setText(getString(R.string.export_private_message));
        binding.tvMessage.setText(keystore);
        binding.btnNext.setText(getString(R.string.export_copy_private));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            Utils.copyData(mContext, keystore);
            ToastUtil.toast(getString(R.string.user_copy_success));
        }
    }
}
