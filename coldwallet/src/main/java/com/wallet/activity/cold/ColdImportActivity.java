package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.base.BaseFragment;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdImportBinding;
import com.wallet.entity.ColumnEditEntity;
import com.wallet.fragment.ImportKeystoreFragment;
import com.wallet.fragment.ImportMnemonicFragment;
import com.wallet.fragment.ImportPrivateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ColdImportActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdImportBinding binding;
    private MyPagerAdapter pagerAdapter;
    private String mSymbol;

    public static void startActivity(Context context,String symbol) {
        Intent intent = new Intent(context, ColdImportActivity.class);
        intent.putExtra("symbol",symbol);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_import);
        binding.setClickListener(this);
        mSymbol=  getIntent().getStringExtra("symbol");
        binding.tvToolbarTitle.setText(mSymbol);

        List<ColumnEditEntity> columns = new ArrayList<>();
        if ("A13".equals(mSymbol) || "ETH".equals(mSymbol)|| "USDT-ERC20".equals(mSymbol)){
            columns.add(new ColumnEditEntity(10, getString(R.string.c_cold_import_tab1)));
        }
        columns.add(new ColumnEditEntity(11, getString(R.string.c_cold_import_tab2)));
        columns.add(new ColumnEditEntity(3, getString(R.string.c_cold_import_tab3)));

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), columns);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(4);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_back) {
            finish();
        }
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        List<ColumnEditEntity> columns;
        List<BaseFragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<ColumnEditEntity> columns) {
            super(fm);
            this.columns = columns;
            fragments = new ArrayList<>();

            for (ColumnEditEntity entity : columns) {
                Bundle bundle = new Bundle();
                bundle.putString("symbol",mSymbol);
                if (getString(R.string.c_cold_import_tab1).equals(entity.getTypeName())){
                    fragments.add(ImportKeystoreFragment.newInstance(bundle));
                }else if (getString(R.string.c_cold_import_tab2).equals(entity.getTypeName())){
                    fragments.add(ImportMnemonicFragment.newInstance(bundle));
                }else if (getString(R.string.c_cold_import_tab3).equals(entity.getTypeName())){
                    fragments.add(ImportPrivateFragment.newInstance(bundle));
                }
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

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);

        }
    }
}
