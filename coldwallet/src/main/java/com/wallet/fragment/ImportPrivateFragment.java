package com.wallet.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.cao.commons.base.BaseFragment;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.R;
import com.cold.wallet.databinding.FragmentImportPrivateBinding;
import com.wallet.event.WalletAddEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.WalletType;

import org.greenrobot.eventbus.EventBus;

public class ImportPrivateFragment extends BaseFragment implements View.OnClickListener {
    private FragmentImportPrivateBinding binding;
    private String mSymbol;
    /**
     * 0 隔离见证   1 普通
     */
    private int mPathType = 1;

    public static ImportPrivateFragment newInstance(Bundle bundle) {
        ImportPrivateFragment fragment = new ImportPrivateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_import_private, viewGroup, false);
        binding.setClickListener(this);
        return binding.getRoot();
    }

    @Override
    protected void onFragmentFirstVisible() {
        mSymbol = getArguments().getString("symbol", "");

        if ("BTC".equals(mSymbol) || "USDT-OMNI".equals(mSymbol)) {
            binding.llOmni.setVisibility(View.VISIBLE);
        } else {
            binding.llOmni.setVisibility(View.GONE);
        }
        binding.etContent.addTextChangedListener(textWatcher);
        binding.etWalletName.addTextChangedListener(textWatcher);
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
                    !TextUtils.isEmpty(binding.etContent.getText().toString().trim()) &&
                    !TextUtils.isEmpty(binding.etWalletName.getText().toString().trim())) {
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
        int id = v.getId();
        if (id == R.id.ll_choose_address) {
            showDialog();
        } else if (id == R.id.ll_address1) {
            mPathType = 0;
            binding.ivItem1.setVisibility(View.VISIBLE);
            binding.ivItem2.setVisibility(View.INVISIBLE);
        } else if (id == R.id.ll_address2) {
            mPathType = 1;
            binding.ivItem1.setVisibility(View.INVISIBLE);
            binding.ivItem2.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_next) {
            next();
        }
    }

    private void next() {
        String content = binding.etContent.getText().toString().trim();
        String name = binding.etWalletName.getText().toString().trim();
        String pass = binding.etPassword.getText().toString().trim();
        String passRetry = binding.etPasswordRetry.getText().toString().trim();
        String remark = binding.etRemark.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.toast(getString(R.string.c_wallet_private_hint));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast(getString(R.string.wc_code_input_account));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.toast(getString(R.string.wc_code_input_wallet_pwd));
            return;
        }
        if (!pass.equals(passRetry)) {
            ToastUtil.toast(getString(R.string.create_wallet_pwd_confirm_input_tips));
            return;
        }
        if (pass.length() < 8) {
            ToastUtil.toast(getString(R.string.saving_wallet_password_length));
            return;
        }
        if (Utils.isFastDoubleClick()) {
            return;
        }
        binding.btnNext.setEnabled(false);
//        content = "hotel mechanic hazard size isolate spot fancy satoshi glare waste celery resist";
        showWithStatus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JnWallet coldWallet = null;
                    if ("BTC".equals(mSymbol)) {
                        coldWallet = ColdWalletUtil.getBtcColdWallet().importPrivateKey(name, content, WalletType.BTC);
                    } else if ("USDT-OMNI".equals(mSymbol)) {
                        coldWallet = ColdWalletUtil.getBtcColdWallet().importPrivateKey(name, content, WalletType.USDT_OMNI);
                    } else if ("ETH".equals(mSymbol)) {
                        coldWallet = ColdWalletUtil.getEthColdWallet().importPrivateKey(name, pass, content, WalletType.ETH);
                    } else if ("USDT-ERC20".equals(mSymbol)) {
                        coldWallet = ColdWalletUtil.getEthColdWallet().importPrivateKey(name, pass, content, WalletType.USDT_ERC20);
                    } else if ("A13".equals(mSymbol)) {
                        coldWallet = ColdWalletUtil.getEthColdWallet().importPrivateKey(name, pass, content, WalletType.A13);
                    }
                    if (coldWallet != null) {
                        WalletDataBean bean, bean1, bean2 = null;
                        if ("ETH".equals(mSymbol) || "A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                            bean = new WalletDataBean(coldWallet.getName(), mPathType, Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    pass, "ETH", remark, DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            bean1 = new WalletDataBean(coldWallet.getName(), mPathType, Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    pass, "A13", remark, DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            bean2 = new WalletDataBean(coldWallet.getName(), mPathType, Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    pass,  "USDT-ERC20", remark, DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                        } else {
                            bean = new WalletDataBean(coldWallet.getName(), mPathType, Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    pass, "BTC", remark, DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                            bean1 = new WalletDataBean(coldWallet.getName(), mPathType, Utils.getMnemonicCode(coldWallet.getMnemonicCode()), coldWallet.getPrivateKey(), coldWallet.getPublicKey(), coldWallet.getAddress(),
                                    pass, "USDT-OMNI", remark, DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                        }

                        if (!bean.getAddress().equals(Utils.getAddress(mSymbol)) && DatabaseOperate.getInstance().insertWalletInfo(bean)) {
                            DatabaseOperate.getInstance().insertWalletInfo(bean1);
                            if (bean2 != null) {
                                DatabaseOperate.getInstance().insertWalletInfo(bean2);
                            }
                            ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(mPathType, coldWallet.getAddress(), mSymbol));
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

    /**
     * 弹窗
     */
    private void showDialog() {
        AlertDialog builder = new AlertDialog.Builder(ActivityUtils.getTopActivity())
                .setTitle(R.string.c_isolation_title)
                .setMessage(R.string.c_isolation_message)
                .setPositiveButton(R.string.btn_sure, ((dialog, which) -> {
                    dialog.dismiss();
                })).create();
        builder.show();
        if (builder.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }
    }
}
