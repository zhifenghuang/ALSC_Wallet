package com.wallet.activity.cold;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.cold.ColdAddressBean;
import com.cao.commons.db.DatabaseOperate;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityAddressBinding;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wallet.activity.adapter.AddressAdapter;
import com.wallet.widget.dialog.ColdWalletTextDialog;

import java.util.ArrayList;

/**
 * 地址簿
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private final static int REQUESTCODE_ADD = 100;
    private ActivityAddressBinding binding;
    private AddressAdapter adapter;
    private String mSymbol;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, String symbol,int requestCode) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra("symbol",symbol);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        binding.setClickListener(this);
        if (getIntent()!=null){
            mSymbol = getIntent().getStringExtra("symbol");
        }

        binding.tvAdd.setVisibility(View.GONE);
        binding.flEmpty.setVisibility(View.GONE);

        adapter = new AddressAdapter(mContext);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ColdAddressBean bean = adapter.getAllData().get(position);
                if (!TextUtils.isEmpty(mSymbol)){
                    Intent intent = getIntent();
                    intent.putExtra("address",bean.getPath());
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    ColdAssetsTransferActivity.startActivity(mContext, bean.getWalletType(), bean.getPath());
                }
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                ColdAddressBean bean = adapter.getAllData().get(position);
                showDeleteDialog(position, bean);
                return false;
            }
        });
    }

    private ColdWalletTextDialog mDialog;
    private void showDeleteDialog(int position, ColdAddressBean userBean) {
        if (mDialog == null) {
            mDialog = new ColdWalletTextDialog(mContext);
        }
        mDialog.showDialog(mContext.getString(R.string.d_delete));
        mDialog.getmOkButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOperate.getInstance().delete(userBean);
                getData();
                mDialog.dismiss();
            }
        });
        mDialog.getmCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        ArrayList<ColdAddressBean> list = DatabaseOperate.getInstance().getAddressInfos(mSymbol);
        if (list.size() == 0) {
            binding.tvAdd.setVisibility(View.GONE);
            binding.flEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.tvAdd.setVisibility(View.VISIBLE);
            binding.flEmpty.setVisibility(View.GONE);
            adapter.clear();
            adapter.addAll(list);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next || id == R.id.tv_add) {
            AddAddressActivity.startActivity(this, REQUESTCODE_ADD);
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

}
