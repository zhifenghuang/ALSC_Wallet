package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cold.wallet.R;
import com.wallet.activity.adapter.ColdBackupMnemonicAdapter;
import com.cold.wallet.databinding.ActivityColdBackupMnemonicBinding;
import com.wallet.utils.MyItemDecoration;
import com.wallet.wallet.bean.JnWallet;

import java.util.ArrayList;

/**
 * 备份助记词
 */
public class ColdBackupMnemonicActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdBackupMnemonicBinding binding;
    private ColdBackupMnemonicAdapter adapter;
    private String mSymbol;
    private JnWallet mColdWallet;
    private String mPass;
    private String mRemark;
    private ArrayList<String> mMnemonicList;

    public static void startActivity(Context context, String symbol, JnWallet coldWallet, String pass, String remark) {
        Intent intent = new Intent(context, ColdBackupMnemonicActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("coldWallet", coldWallet);
        intent.putExtra("pass", pass);
        intent.putExtra("remark", remark);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, ArrayList<String> list) {
        Intent intent = new Intent(context, ColdBackupMnemonicActivity.class);
        intent.putExtra("list", list);
        context.startActivity(intent);
    }


    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_backup_mnemonic);
        binding.setClickListener(this);
        mSymbol = getIntent().getStringExtra("symbol");
        mPass = getIntent().getStringExtra("pass");
        mRemark = getIntent().getStringExtra("remark");
        mColdWallet = (JnWallet) getIntent().getSerializableExtra("coldWallet");
        mMnemonicList = getIntent().getStringArrayListExtra("list");

        adapter = new ColdBackupMnemonicAdapter(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.addItemDecoration(new MyItemDecoration(mContext, gridLayoutManager.getOrientation(), 3));
        binding.recyclerView.setAdapter(adapter);

        if (mColdWallet != null) {
            adapter.addAll(mColdWallet.getMnemonicCode());
        } else {
            adapter.addAll(mMnemonicList);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            if (mColdWallet != null) {
                ColdConfirmMnemonicActivity.startActivity(mContext, mSymbol, mColdWallet, mPass, mRemark);
            } else {
                ColdConfirmMnemonicActivity.startActivity(mContext, mMnemonicList);
            }
            finish();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }


}
