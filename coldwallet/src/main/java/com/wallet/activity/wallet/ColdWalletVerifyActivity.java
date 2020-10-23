package com.wallet.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.ArrayUtils;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.wallet.activity.MainColdActivity;
import com.wallet.activity.adapter.ColdWalletVerifyAdapter;
import com.cold.wallet.databinding.ActivityColdWalletVerifyBinding;
import com.wallet.retrofit.ColdInterface;
import com.wallet.widget.dialog.ColdWalletTextDialog;
import com.wallet.event.LoginEvent;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证
 */
public class ColdWalletVerifyActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdWalletVerifyBinding binding;
    private ColdWalletTextDialog mDialog;
    private ColdWalletVerifyAdapter adapter;
    private ColdWallet mColdWallet;
    private UserBean mUserBean;

    public static void startActivity(Context context, ColdWallet wallet, UserBean userBean) {
        Intent intent = new Intent(context, com.wallet.activity.wallet.ColdWalletVerifyActivity.class);
        intent.putExtra("wallet", wallet);
        intent.putExtra("userBean", userBean);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_wallet_verify);
        binding.setClickListener(this);
        mColdWallet = (ColdWallet) getIntent().getSerializableExtra("wallet");
        mUserBean = (UserBean) getIntent().getSerializableExtra("userBean");

        adapter = new ColdWalletVerifyAdapter(mContext);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        binding.recyclerView.setAdapter(adapter);

        List<String> strings = new ArrayList<>();
        strings.addAll(mColdWallet.getMnemonicCode());
        ArrayUtils.shuffle(strings);
        adapter.addAll(strings);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String string = adapter.getAllData().get(position);
                String content = binding.tvContent.getText().toString();
                Integer integer = position;
                if (adapter.getSelected().contains(integer)) {
                    return;
                } else {
                    adapter.getSelected().add(integer);
                    adapter.notifyDataSetChanged();
                }
                if (!TextUtils.isEmpty(content)) {
                    content += " ";
                }
                content += string;
                binding.tvContent.setText(content);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.iv_revoke) {
            revokeData();
        } else if (id == R.id.tv_reset) {
            if (mDialog == null) {
                mDialog = new ColdWalletTextDialog(mContext);
            }
            mDialog.showDialog(getString(R.string.wv_reset));
            mDialog.getmOkButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ColdWallet wallet = ColdWalletUtil.createColdWallet(mUserBean.getAccount(), mUserBean.getPassword());
                        ColdWalletBackupActivity.startActivity(mContext, wallet, mUserBean);
                        mDialog.dismiss();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.toast(getString(R.string.saving_wallet_failure));
                    }
                }
            });
            mDialog.getmCancelButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    /**
     * c撤销
     */
    private void revokeData() {
        String content = binding.tvContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            StringBuilder resultStr = new StringBuilder();
            String lastWord = "";
            if (content.contains(" ")) {
                String[] strings = content.split(" ");
                lastWord = strings[strings.length - 1];
                for (int i = 0; i < strings.length - 1; i++) {
                    if (i > 0) {
                        resultStr.append(" ");
                    }
                    resultStr.append(strings[i]);
                }
            } else {
                lastWord = content;
            }
            adapter.getSelected().remove(adapter.getSelected().size()-1);
            adapter.notifyDataSetChanged();
            binding.tvContent.setText(resultStr);
        }
    }

    /**
     * 下一步
     */
    private void next() {
        if (TextUtils.isEmpty(binding.tvContent.getText())) {
            return;
        }
        showWithStatus();
        String content = binding.tvContent.getText().toString();
        if (content.contains(" ")) {
            String[] strings = content.split(" ");
            boolean isAllSame = true;
            if (strings.length != mColdWallet.getMnemonicCode().size()) {
                isAllSame = false;
            } else {
                for (int i = 0; i < strings.length; i++) {
                    if (!strings[i].equals(mColdWallet.getMnemonicCode().get(i))) {
                        isAllSame = false;
                        break;
                    }
                }
            }
            if (isAllSame) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String walletContent = getGson().toJson(mColdWallet);
//                String walletContentMD5 = MD5Utils.encryptMD5(walletContent);
                        mUserBean.setWalletContentMD(walletContent);
                        mUserBean.setCreateTime(System.currentTimeMillis());
                        DataManager.getInstance().saveUser(mUserBean);
                        DatabaseOperate.getInstance().insertOrUpdate(mUserBean);
                        ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(mColdWallet));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissDialog();
                                MainColdActivity.startActivity(mContext,true);
                                EventBus.getDefault().post(new LoginEvent());
                                finish();
                            }
                        });
                    }
                }).start();
            } else {
                ToastUtil.toast(getString(R.string.verify_wallet_password_failure));
                dismissDialog();
            }
        } else {
            ToastUtil.toast(getString(R.string.verify_wallet_password_failure));
            dismissDialog();
        }
    }

    private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }
}
