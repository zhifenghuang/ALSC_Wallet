package com.wallet.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.log.Log;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.wallet.activity.MainColdActivity;
import com.wallet.activity.adapter.RestoreWalletAdapter;
import com.cold.wallet.databinding.ActivityRestoreWalletBinding;
import com.wallet.event.LoginEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RestoreWalletActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRestoreWalletBinding binding;
    private RestoreWalletAdapter confirmAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RestoreWalletActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restore_wallet);
        binding.setClickListener(this);
        canHideSoftKeyBoard = false;

        confirmAdapter = new RestoreWalletAdapter(mContext);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.recyclerView.setAdapter(confirmAdapter);

        confirmAdapter.add("trim");
        confirmAdapter.add("elevator");
        confirmAdapter.add("illegal");
        confirmAdapter.add("tube");
        confirmAdapter.add("hammer");
        confirmAdapter.add("globe");
        confirmAdapter.add("siege");
        confirmAdapter.add("small");
        confirmAdapter.add("infant");
        confirmAdapter.add("orphan");
        confirmAdapter.add("anxiety");
        confirmAdapter.add("primary");
        initListener();
    }

    private void initListener() {
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    binding.tvSend.setBackgroundResource(R.drawable.corner_d9d1d3_13);
                } else {
                    binding.tvSend.setBackgroundResource(R.drawable.corner_07bb99_4);
                }
            }
        });
        confirmAdapter.setOnItemClickListener(position -> {
            confirmAdapter.remove(position);
        });
        binding.etWalletName.addTextChangedListener(watcher);
        binding.etPassword.addTextChangedListener(watcher);
        binding.etPasswordRetry.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(binding.etWalletName.getText()) &&
                    !TextUtils.isEmpty(binding.etPassword.getText())&&
                    !TextUtils.isEmpty(binding.etPasswordRetry.getText())) {
                binding.btnNext.setBackgroundResource(R.drawable.corner_07bb99_13);
            } else {
                binding.btnNext.setBackgroundResource(R.drawable.corner_b2b2b2_13);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fl_content || id == R.id.tv_content) {
            if (binding.llBottom.getVisibility() != View.VISIBLE) {
                binding.llBottom.setVisibility(View.VISIBLE);
                binding.etContent.requestFocus();
                showSoftInput(binding.etContent);
            }
        } else if (id == R.id.ll_bottom) {
            binding.llBottom.setVisibility(View.GONE);
            hideSoftKeyboard();
        } else if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.tv_send) {
            addToData();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void addToData() {
        String content = binding.etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        confirmAdapter.add(content);
        if (confirmAdapter.getAllData().size() > 0) {
            binding.tvContent.setVisibility(View.GONE);
        } else {
            binding.tvContent.setVisibility(View.VISIBLE);
        }
        binding.etContent.setText("");
    }

    private void next() {
        String userName = binding.etWalletName.getText().toString();
        String pass = binding.etPassword.getText().toString();
        String passRetry = binding.etPasswordRetry.getText().toString();
        String remark = binding.etRemark.getText().toString();
        List<String> mnemonicCode = confirmAdapter.getAllData();
        if (TextUtils.isEmpty(userName)) {
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
        if (mnemonicCode.size() < 12) {
            ToastUtil.toast(getString(R.string.error_code_length));
            return;
        }


        binding.btnNext.setEnabled(false);
        showWithStatus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ColdWallet wallet = ColdWalletUtil.importMnemonic(userName, mnemonicCode, pass);
                    UserBean userBean = new UserBean();
                    userBean.setAccount(userName);
                    userBean.setPassword(pass);
                    userBean.setWalletType(1);
                    userBean.setUserId(-1);
                    userBean.setRemark(remark);
                    String walletContent = getGson().toJson(wallet);
                    userBean.setWalletContentMD(walletContent);
                    userBean.setCreateTime(System.currentTimeMillis());
                    DataManager.getInstance().saveColdUser(userBean);
                    DatabaseOperate.getInstance().insertOrUpdate(userBean);
                    ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(wallet));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            EventBus.getDefault().post(new LoginEvent());
                            finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            ToastUtil.toast(getString(R.string.saving_wallet_failure));
                            binding.btnNext.setEnabled(true);
                        }
                    });

                }

            }
        }).start();


    }

    private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }
}
