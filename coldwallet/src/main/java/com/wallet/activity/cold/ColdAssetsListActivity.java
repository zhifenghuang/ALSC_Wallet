package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cold.wallet.databinding.ActivityColdAssetsListBinding;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.cold.wallet.R;
import com.wallet.entity.ColumnEditEntity;
import com.wallet.event.TransferEvent;
import com.wallet.event.WalletAddEvent;
import com.wallet.event.WalletAllEvent;
import com.wallet.event.WalletDeleteEvent;
import com.wallet.fragment.ColdAssetsListFragment;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;
import com.wallet.wallet.bean.ColdWallet;
import com.wallet.wallet.bean.JnWallet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 转账列表记录 冷钱包
 */
public class ColdAssetsListActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdAssetsListBinding binding;
    private MyPagerAdapter pagerAdapter;

    private String mSymbol;
    private String mAddress;
    private JnWallet mJnWallet;
    private WalletDataBean mWalletDataBean;
    private String mBalance;

    public static void startActivity(Context context, String type, JnWallet wallet) {
        Intent intent = new Intent(context, ColdAssetsListActivity.class);
        intent.putExtra("symbol", type);
        intent.putExtra("wallet", wallet);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, WalletDataBean wallet) {
        Intent intent = new Intent(context, ColdAssetsListActivity.class);
        intent.putExtra("walletData", wallet);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_assets_list);
        binding.setClickListener(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(isDarkFont)   //状态栏字体是深色，不写默认为亮色
                .init();

        mSymbol = getIntent().getStringExtra("symbol");
        mJnWallet = (JnWallet) getIntent().getSerializableExtra("wallet");
        mWalletDataBean = (WalletDataBean) getIntent().getSerializableExtra("walletData");
        if (mWalletDataBean != null) {
            mSymbol = mWalletDataBean.getWalletType();
        }
        EventBus.getDefault().register(this);

        List<ColumnEditEntity> columns = new ArrayList<>();
        columns.add(new ColumnEditEntity(0, getString(R.string.assets_list_tab1)));
        columns.add(new ColumnEditEntity(1, getString(R.string.assets_list_tab2)));
        columns.add(new ColumnEditEntity(2, getString(R.string.assets_list_tab3)));

        binding.toolbar.setContentInsetsAbsolute(0, 0);
        binding.tvTitle.setText(mSymbol.toUpperCase());
        if (!TextUtils.isEmpty(Utils.getTitleSum(mSymbol))) {
            binding.tvTitleSummary.setText(Utils.getTitleSum(mSymbol));
        } else {
            binding.tvTitleSummary.setText("");
        }
        if (mJnWallet != null) {
            mAddress = mJnWallet.getAddress();
        } else if (mWalletDataBean != null) {
            mAddress = mWalletDataBean.getAddress();
        }
        binding.ivImage.setImageResource(Utils.getImageResource3(mSymbol));
        binding.tvUrl.setText(mAddress);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), columns);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(columns.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMoney();
    }

    private void initMoney() {
        if (mJnWallet != null) {
            ArrayList<UserBean> list = DatabaseOperate.getInstance().getColdUserInfos();
            if (list == null || list.size() == 0) {
                return;
            }
            UserBean userBean = list.get(list.size() - 1);
            Gson mGson = new Gson();
            ColdWallet coldWallet = mGson.fromJson(userBean.getWalletContentMD(), ColdWallet.class);
            JnWallet jnWallet = Utils.getWallet(coldWallet, mSymbol);
            binding.tvPrice.setText("" + jnWallet.getMoney());
            binding.tvTousdt.setText("≈$" + jnWallet.getPrice());
        } else if (mWalletDataBean != null) {
            WalletDataBean walletDataBean = DatabaseOperate.getInstance().getWalletInfo(mWalletDataBean.getId());
            binding.tvPrice.setText("" + walletDataBean.getMoney());
            binding.tvTousdt.setText("≈$" + walletDataBean.getPrice());
        }
    }


    @Override
    public void onClick(View v) {
        if (Utils.isFastDoubleClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.fl_transfer) {
            if (mJnWallet != null) {
                ColdAssetsTransferActivity.startActivity(mContext, mSymbol, mJnWallet);
            } else if (mWalletDataBean != null) {
                ColdAssetsTransferActivity.startActivity(mContext, mWalletDataBean);
            }
        } else if (id == R.id.fl_qcode) {
            ColdAssetsQCodeActivity.startActivity(mContext, mSymbol, mAddress);
        } else if (id == R.id.fl_set) {
            if (mJnWallet != null) {
                ColdAssetsSetActivity.startActivity(mContext, mSymbol, mJnWallet);
            } else if (mWalletDataBean != null) {
                ColdAssetsSetActivity.startActivity(mContext, mWalletDataBean);
            }
        } else if (id == R.id.iv_copy) {
            Utils.copyData(mContext, binding.tvUrl.getText().toString());
            ToastUtil.toast(getString(R.string.user_copy_success));
        } else if (id == R.id.fl_item1) {
            binding.tvItem1.setBackgroundResource(R.drawable.corner_208cf7_2);
            binding.tvItem1.setTextColor(getResources().getColor(R.color.white));
            binding.tvItem2.setBackgroundDrawable(null);
            binding.tvItem2.setTextColor(getResources().getColor(R.color.color_929497));
            binding.tvItem3.setBackgroundDrawable(null);
            binding.tvItem3.setTextColor(getResources().getColor(R.color.color_929497));
            binding.viewPager.setCurrentItem(0);
        } else if (id == R.id.fl_item2) {
            binding.tvItem1.setBackgroundDrawable(null);
            binding.tvItem1.setTextColor(getResources().getColor(R.color.color_929497));
            binding.tvItem2.setBackgroundResource(R.drawable.corner_208cf7_2);
            binding.tvItem2.setTextColor(getResources().getColor(R.color.white));
            binding.tvItem3.setBackgroundDrawable(null);
            binding.tvItem3.setTextColor(getResources().getColor(R.color.color_929497));
            binding.viewPager.setCurrentItem(1);
        } else if (id == R.id.fl_item3) {
            binding.tvItem1.setBackgroundDrawable(null);
            binding.tvItem1.setTextColor(getResources().getColor(R.color.color_929497));
            binding.tvItem2.setBackgroundDrawable(null);
            binding.tvItem2.setTextColor(getResources().getColor(R.color.color_929497));
            binding.tvItem3.setBackgroundResource(R.drawable.corner_208cf7_2);
            binding.tvItem3.setTextColor(getResources().getColor(R.color.white));
            binding.viewPager.setCurrentItem(2);
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TransferEvent event) {
        initMoney();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAllEvent event) {
        initMoney();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAddEvent event) {
        initMoney();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletDeleteEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        List<ColumnEditEntity> columns;
        List<ColdAssetsListFragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<ColumnEditEntity> columns) {
            super(fm);
            this.columns = columns;
            fragments = new ArrayList<>();

            for (ColumnEditEntity entity : columns) {
                Bundle bundle = new Bundle();
                bundle.putString("symbol", mSymbol);
                bundle.putString("address", mAddress);
                bundle.putInt("type", entity.getNumber());
                fragments.add(ColdAssetsListFragment.newInstance(bundle));
            }
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return columns.get(position).getTypeName();
        }

        @Override
        public int getCount() {
            return columns.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        /**
         * 在调用notifyDataSetChanged()方法后，随之会触发该方法，根据该方法返回的值来确定是否更新
         * object对象为Fragment，具体是当前显示的Fragment和它的前一个以及后一个
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;   // 返回发生改变，让系统重新加载
            // 系统默认返回的是     POSITION_UNCHANGED，未改变
        }
    }
}
