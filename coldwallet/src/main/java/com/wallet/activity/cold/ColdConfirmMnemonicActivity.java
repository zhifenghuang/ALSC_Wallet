package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.ArrayUtils;
import com.cao.commons.widget.toast.ConfirmToast;
import com.cold.wallet.R;
import com.wallet.activity.adapter.ColdBackupMnemonicAdapter;
import com.wallet.activity.adapter.ColdConfirmMnemonicAdapter;
import com.cold.wallet.databinding.ActivityColdConfirmMnemonicBinding;
import com.wallet.event.WalletAddEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wallet.wallet.bean.JnWallet;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认助记词
 */
public class ColdConfirmMnemonicActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdConfirmMnemonicBinding binding;
    private ColdBackupMnemonicAdapter adapter;
    private ColdConfirmMnemonicAdapter confirmAdapter;
    private String mSymbol;
    private JnWallet mColdWallet;
    private String mPass;
    private String mRemark;
    private ArrayList<String> mMnemonicList;

    public static void startActivity(Context context, String symbol, JnWallet coldWallet, String pass, String remark) {
        Intent intent = new Intent(context, ColdConfirmMnemonicActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("coldWallet", coldWallet);
        intent.putExtra("pass", pass);
        intent.putExtra("remark", remark);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, ArrayList<String> list) {
        Intent intent = new Intent(context, ColdConfirmMnemonicActivity.class);
        intent.putExtra("list", list);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_confirm_mnemonic);
        binding.setClickListener(this);
        mSymbol = getIntent().getStringExtra("symbol");
        mColdWallet = (JnWallet) getIntent().getSerializableExtra("coldWallet");
        mPass = getIntent().getStringExtra("pass");
        mRemark = getIntent().getStringExtra("remark");
        mMnemonicList = getIntent().getStringArrayListExtra("list");

        adapter = new ColdBackupMnemonicAdapter(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(adapter);

        confirmAdapter = new ColdConfirmMnemonicAdapter(mContext);
        binding.recyclerViewWord.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.recyclerViewWord.setAdapter(confirmAdapter);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            strings.add("");
        }
        adapter.addAll(strings);

        List<String> list = new ArrayList<>();
        if (mColdWallet != null) {
            list.addAll(mColdWallet.getMnemonicCode());
        } else {
            list.addAll(mMnemonicList);
        }
        ArrayUtils.shuffle(list);
        confirmAdapter.addAll(list);
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnClickDeleteListener(new ColdBackupMnemonicAdapter.OnClickDeleteListener() {
            @Override
            public void delete(String data) {
                adapter.remove(data);
                adapter.add("");
                adapter.notifyDataSetChanged();

                List<String> list = confirmAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(data)) {
                        confirmAdapter.getSelectList().remove((Integer) i);
                        confirmAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        confirmAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Integer p = position;
                if (confirmAdapter.getSelectList().contains(p)) {
                    confirmAdapter.getSelectList().remove(p);
                } else {
                    confirmAdapter.getSelectList().add(p);
                }
                confirmAdapter.notifyItemChanged(position);

                List<String> list = adapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    if (i < confirmAdapter.getSelectList().size()) {
                        list.set(i, confirmAdapter.getAllData().get(confirmAdapter.getSelectList().get(i)));
                    } else {
                        list.set(i, "");
                    }
                }
                adapter.clear();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void next() {
        boolean isAllSame = true;

        if (mColdWallet != null) {
            for (int i = 0; i < mColdWallet.getMnemonicCode().size(); i++) {
                String key = adapter.getAllData().get(i);
                if (!mColdWallet.getMnemonicCode().get(i).equals(key)) {
                    isAllSame = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < mMnemonicList.size(); i++) {
                String key = adapter.getAllData().get(i);
                if (!mMnemonicList.get(i).equals(key)) {
                    isAllSame = false;
                    break;
                }
            }
        }

        if (isAllSame) {
            ConfirmToast.showSuccess(mContext);
            if (mColdWallet != null) {
                WalletDataBean bean, bean1, bean2 = null;
                if ("ETH".equals(mSymbol) || "A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                    bean = new WalletDataBean(mColdWallet.getName(), Utils.getMnemonicCode(mColdWallet.getMnemonicCode()), mColdWallet.getPrivateKey(), mColdWallet.getPublicKey(), mColdWallet.getAddress(),
                            String.valueOf(mColdWallet.getKeystore()), mPass, "ETH", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                    bean.setInformation(mRemark);
                    bean1 = new WalletDataBean(mColdWallet.getName(), Utils.getMnemonicCode(mColdWallet.getMnemonicCode()), mColdWallet.getPrivateKey(), mColdWallet.getPublicKey(), mColdWallet.getAddress(),
                            String.valueOf(mColdWallet.getKeystore()), mPass, "A13", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                    bean1.setInformation(mRemark);
                    bean2 = new WalletDataBean(mColdWallet.getName(), Utils.getMnemonicCode(mColdWallet.getMnemonicCode()), mColdWallet.getPrivateKey(), mColdWallet.getPublicKey(), mColdWallet.getAddress(),
                            String.valueOf(mColdWallet.getKeystore()), mPass, "USDT-ERC20", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                    bean2.setInformation(mRemark);
                } else {
                    bean = new WalletDataBean(mColdWallet.getName(), Utils.getMnemonicCode(mColdWallet.getMnemonicCode()), mColdWallet.getPrivateKey(), mColdWallet.getPublicKey(), mColdWallet.getAddress(),
                            String.valueOf(mColdWallet.getKeystore()), mPass, "BTC", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                    bean.setInformation(mRemark);
                    bean1 = new WalletDataBean(mColdWallet.getName(), Utils.getMnemonicCode(mColdWallet.getMnemonicCode()), mColdWallet.getPrivateKey(), mColdWallet.getPublicKey(), mColdWallet.getAddress(),
                            String.valueOf(mColdWallet.getKeystore()), mPass, "USDT-OMNI", DataManager.getInstance().getUser().getAccount(), System.currentTimeMillis());
                    bean1.setInformation(mRemark);
                }

                if (DatabaseOperate.getInstance().insertWalletInfo(bean)) {
                    ToastUtil.toast(mContext.getString(R.string.save_success));
                    DatabaseOperate.getInstance().insertWalletInfo(bean1);
                    if (bean2 != null) {
                        DatabaseOperate.getInstance().insertWalletInfo(bean2);
                    }
                    ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(1, bean.getAddress(), mSymbol));
                    EventBus.getDefault().post(new WalletAddEvent());
                    finish();
                } else {
                    ToastUtil.toast(mContext.getString(R.string.save_wallet_failure));
                }
            } else {
                String code = Utils.getMnemonicCode(mMnemonicList);
                Utils.copyData(mContext, code);
                finish();
            }
        } else {
            ConfirmToast.showFailure(mContext);

            List<String> list = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                list.add("");
            }
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            confirmAdapter.getSelectList().clear();
            confirmAdapter.notifyDataSetChanged();
        }
    }
}
