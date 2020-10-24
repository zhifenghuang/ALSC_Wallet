package com.wallet.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseFragment;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.R;
import com.cold.wallet.databinding.FragmentImportKeystoreBinding;
import com.wallet.event.WalletAddEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.WalletType;

import org.greenrobot.eventbus.EventBus;

public class ImportKeystoreFragment extends BaseFragment implements View.OnClickListener {
    private FragmentImportKeystoreBinding binding;
    private String mSymbol;

    public static ImportKeystoreFragment newInstance(Bundle bundle) {
        ImportKeystoreFragment fragment = new ImportKeystoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_import_keystore, viewGroup, false);
        binding.setClickListener(this);
        return binding.getRoot();
    }

    @Override
    protected void onFragmentFirstVisible() {
        mSymbol = getArguments().getString("symbol", "");

        binding.etContent.addTextChangedListener(textWatcher);
        binding.etPassword.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(binding.etContent.getText().toString().trim()) &&
                    !TextUtils.isEmpty(binding.etContent.getText().toString().trim())) {
                binding.btnNext.setBackgroundResource(R.drawable.corner_07bb99_13);
            } else {
                binding.btnNext.setBackgroundResource(R.drawable.corner_b2b2b2_13);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            String content = binding.etContent.getText().toString().toLowerCase().trim();
            String pass = binding.etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.toast(getString(R.string.c_cold_import_keystore_content));
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                ToastUtil.toast(getString(R.string.wc_code_input_wallet_pwd));
                return;
            }
            if (Utils.isFastDoubleClick()) {
                return;
            }
            binding.btnNext.setEnabled(false);
            showWithStatus();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JnWallet coldWallet = null;
                        if ("ETH".equals(mSymbol)) {
                            coldWallet = ColdWalletUtil.getEthColdWallet().importKeystore("ETH", pass, content, WalletType.ETH);
                        } else if ("A13".equals(mSymbol)) {
                            coldWallet = ColdWalletUtil.getEthColdWallet().importKeystore("A13", pass, content, WalletType.A13);
                        } else if ("USDT-ERC20".equals(mSymbol)) {
                            coldWallet = ColdWalletUtil.getEthColdWallet().importKeystore("ERC20", pass, content, WalletType.USDT_ERC20);
                        }
                        if (coldWallet != null) {
                            WalletDataBean bean = new WalletDataBean("ETH", Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    String.valueOf(coldWallet.getKeystore()), pass, "ETH", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            WalletDataBean bean1 = new WalletDataBean("A13", Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    String.valueOf(coldWallet.getKeystore()), pass, "A13", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            WalletDataBean bean2 = new WalletDataBean("USDT-ERC20", Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    String.valueOf(coldWallet.getKeystore()), pass, "USDT-ERC20", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            if (!bean.getAddress().equals(Utils.getAddress(mSymbol)) && DatabaseOperate.getInstance().insertWalletInfo(bean)) {
                                DatabaseOperate.getInstance().insertWalletInfo(bean1);
                                DatabaseOperate.getInstance().insertWalletInfo(bean2);
                                ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(1, coldWallet.getAddress(), mSymbol));
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissDialog();
                                        ToastUtil.toast(mContext.getString(R.string.save_success));
                                        EventBus.getDefault().post(new WalletAddEvent());
                                        getActivity().finish();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissDialog();
                                        ToastUtil.toast(mContext.getString(R.string.save_wallet_failure));
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissDialog();
                                binding.btnNext.setEnabled(true);
                                ToastUtil.toast(Utils.getToastMessage(mContext, e.toString()));
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
