package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.base.BaseFragment;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityExportKeystoreBinding;
import com.wallet.entity.ColumnEditEntity;
import com.wallet.fragment.ExportKeystoreFragment;
import com.wallet.fragment.ExportQcodeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出 Keystore
 */
public class ExportKeystoreActivity extends BaseActivity implements View.OnClickListener {
    private ActivityExportKeystoreBinding binding;
    private MyPagerAdapter pagerAdapter;
    private String mKeystore;

    public static void startActivity(Context context, String keystore) {
        Intent intent = new Intent(context, ExportKeystoreActivity.class);
        intent.putExtra("keystore", keystore);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_export_keystore);
        binding.setClickListener(this);
        mKeystore = getIntent().getStringExtra("keystore");

        List<ColumnEditEntity> columns = new ArrayList<>();
        columns.add(new ColumnEditEntity(0, getString(R.string.export_keystore_tab1)));
        columns.add(new ColumnEditEntity(2, getString(R.string.export_keystore_tab2)));

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), columns);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(columns.size());
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

            Bundle bundle = new Bundle();
            bundle.putString("keystore", mKeystore);
            fragments.add(ExportKeystoreFragment.newInstance(bundle));

            Bundle bundle1 = new Bundle();
            bundle1.putString("keystore", mKeystore);
            fragments.add(ExportQcodeFragment.newInstance(bundle1));
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
