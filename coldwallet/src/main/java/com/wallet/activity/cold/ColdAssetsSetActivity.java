package com.wallet.activity.cold;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdAssetsSetBinding;
import com.wallet.widget.dialog.ChangeNameDialog;
import com.wallet.widget.dialog.ContributeColdDialog;
import com.wallet.widget.dialog.CustomDialog;
import com.wallet.widget.dialog.RiskDialog;
import com.wallet.event.ChangeWalletNameEvent;
import com.wallet.event.CheckCPwdEvent;
import com.wallet.event.WalletDeleteEvent;
import com.wallet.utils.Utils;
import com.wallet.wallet.bean.ColdWallet;
import com.wallet.wallet.bean.JnWallet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 设置
 */
public class ColdAssetsSetActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdAssetsSetBinding binding;
    private ChangeNameDialog changeNameDialog;
    private ContributeColdDialog mDialog;
    private String mSymbol;
    private JnWallet mJnWallet;
    private WalletDataBean mWalletDataBean;
    private int currentExport = 0;
    private String password;

    public static void startActivity(Context context, String symbol, JnWallet wallet) {
        Intent intent = new Intent(context, ColdAssetsSetActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("wallet", wallet);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, WalletDataBean wallet) {
        Intent intent = new Intent(context, ColdAssetsSetActivity.class);
        intent.putExtra("walletData", wallet);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_assets_set);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
        mSymbol = getIntent().getStringExtra("symbol");
        mJnWallet = (JnWallet) getIntent().getSerializableExtra("wallet");
        mWalletDataBean = (WalletDataBean) getIntent().getSerializableExtra("walletData");
        if (mJnWallet != null) {
            binding.tvName.setText(mJnWallet.getName());
            binding.tvAddress.setText(mJnWallet.getAddress());
            binding.tvInformation.setVisibility(View.GONE);
            binding.tvDelete.setVisibility(View.GONE);
            setKeystore(String.valueOf(mJnWallet.getKeystore()));
        } else if (mWalletDataBean != null) {
            mSymbol = mWalletDataBean.getWalletType();
            binding.tvName.setText(mWalletDataBean.getName());
            binding.tvAddress.setText(mWalletDataBean.getAddress());
            if (!TextUtils.isEmpty(mWalletDataBean.getInformation())) {
                binding.tvInformation.setVisibility(View.VISIBLE);
            } else {
                binding.tvInformation.setVisibility(View.GONE);
            }
            binding.tvDelete.setVisibility(View.VISIBLE);
            setKeystore(mWalletDataBean.getKeystore());
            if (!TextUtils.isEmpty(mWalletDataBean.getMnemonicCode())){
                binding.tvExport.setVisibility(View.VISIBLE);
            }else {
                binding.tvExport.setVisibility(View.GONE);
            }
            password = mWalletDataBean.getPassword();
        }
        binding.ivImage.setImageResource(Utils.getImageResource1(mSymbol));
    }

    private void setKeystore(String keystore) {
        if (!TextUtils.isEmpty(keystore) && !"null".equals(keystore)) {
            binding.tvExportKeystore.setVisibility(View.VISIBLE);
        } else {
            binding.tvExportKeystore.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();//            case R.id.ll_name:
//                showNameDialog();
//                break;
        if (id == R.id.tv_information) {
            ColdPasswordInformationActivity.startActivity(mContext, mWalletDataBean.getInformation());
        } else if (id == R.id.tv_export) {
            currentExport = 0;
            showPasswordDialog();
        } else if (id == R.id.tv_export_keystore) {
            currentExport = 1;
            showPasswordDialog();
        } else if (id == R.id.tv_export_private) {
            currentExport = 2;
            showPasswordDialog();
        } else if (id == R.id.tv_delete) {
            showRiskDialog();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void showRiskDialog() {
        RiskDialog dialog = new RiskDialog(mContext);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showCustomDialog();
            }
        });
    }

    private CustomDialog customDialog;

    private void showCustomDialog() {
        if (customDialog == null) {
            customDialog = new CustomDialog(mContext);
        }
        customDialog.showDialog(101);
        customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExport = 3;
                showPasswordDialog();
            }
        });
    }


    private void showNameDialog() {
        if (changeNameDialog == null) {
            changeNameDialog = new ChangeNameDialog(mContext);
        }
        changeNameDialog.showDialog(binding.tvName.getText().toString());
        changeNameDialog.getmOkButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = changeNameDialog.et_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                if (mJnWallet!=null){
                    UserBean userBean = DataManager.getInstance().getUser();
                    String walletContentMD = userBean.getWalletContentMD();
                    Gson mGson = new Gson();
                    ColdWallet  mColdWallet = mGson.fromJson(walletContentMD, ColdWallet.class);
                    for (JnWallet wallet : mColdWallet.getJnWallets()){
                        if (wallet.getWalletType()==mJnWallet.getWalletType()){
                            wallet.setName(content);
                            break;
                        }
                    }
                    userBean.setWalletContentMD(walletContentMD);
                    DataManager.getInstance().saveUser(userBean);
                    DatabaseOperate.getInstance().insertOrUpdate(userBean);
                    EventBus.getDefault().post(new ChangeWalletNameEvent());
                }else if (mWalletDataBean!=null){
                    mWalletDataBean.setName(content);
                    binding.tvName.setText(mWalletDataBean.getName());
                    DatabaseOperate.getInstance().update(mWalletDataBean);
                }

                changeNameDialog.dismiss();
            }
        });
        changeNameDialog.getmCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNameDialog.dismiss();
            }
        });
    }

    private void showPasswordDialog() {
        if (mDialog == null) {
            mDialog = new ContributeColdDialog(mContext);
        }
        mDialog.showDialog(Tag,password);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CheckCPwdEvent event) {
        if (event != null && !TextUtils.isEmpty(event.getTag()) && Tag.equals(event.getTag())) {
            if (currentExport == 0) {
                ArrayList<String> list = new ArrayList<>();
                if (mJnWallet != null) {
                    list.addAll(mJnWallet.getMnemonicCode());
                } else if (mWalletDataBean != null) {
                    String[] strings = mWalletDataBean.getMnemonicCode().split(" ");
                    List<String> stringList = Arrays.asList(strings);
                    list.addAll(stringList);
                }
                ColdBackupMnemonicActivity.startActivity(mContext, list);
            } else if (currentExport == 1) {
                if (mJnWallet != null) {
                    ExportKeystoreActivity.startActivity(mContext, String.valueOf(mJnWallet.getKeystore()));
                } else if (mWalletDataBean != null) {
                    ExportKeystoreActivity.startActivity(mContext, mWalletDataBean.getKeystore());
                }
            } else if (currentExport == 2) {
                if (mJnWallet != null) {
                    ExportPrivateActivity.startActivity(mContext, mJnWallet.getPrivateKey());
                } else if (mWalletDataBean != null) {
                    ExportPrivateActivity.startActivity(mContext, mWalletDataBean.getPrivateKey());
                }
            } else if (currentExport == 3) {
                if (mWalletDataBean != null) {
                    DatabaseOperate.getInstance().delete(mWalletDataBean);
                    EventBus.getDefault().post(new WalletDeleteEvent());
                    finish();
                }
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }
}
