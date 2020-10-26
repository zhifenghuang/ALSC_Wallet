package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.TradeInfoBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.log.Log;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cold.wallet.databinding.ActivityColdAssetsListBinding;
import com.common.dialog.MyDialogFragment;
import com.common.utils.QRCodeUtil;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.gyf.immersionbar.ImmersionBar;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wallet.activity.adapter.ColdAssetsDetailAdapter;
import com.wallet.activity.adapter.ColdWalletDetailAdapter;
import com.wallet.entity.ColumnEditEntity;
import com.wallet.event.TransferEvent;
import com.wallet.event.WalletAddEvent;
import com.wallet.event.WalletAllEvent;
import com.wallet.event.WalletDeleteEvent;
import com.wallet.fragment.ColdAssetsListFragment;
import com.wallet.retrofit.ColdInterface;
import com.wallet.retrofit.HttpInfoRequest;
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
    private ColdAssetsDetailAdapter adapter;

    private String mSymbol;
    private String mAddress;
    private JnWallet mJnWallet;
    private WalletDataBean mWalletDataBean;
    private String mBalance;
    private int page = 1;
    private ColdWalletDetailAdapter mAdapter;

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


        binding.toolbar.setContentInsetsAbsolute(0, 0);
        binding.tvTitle.setText(mSymbol.toUpperCase()+"钱包");
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
        binding.tvCoinName.setText(mSymbol);

        adapter = new ColdAssetsDetailAdapter(mContext, mSymbol);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setAdapter(adapter);
        initListener();

        ArrayList<TradeInfoBean> list = DatabaseOperate.getInstance().getAllTradeInfo();
        Log.e("ColdAssetsListFragment", "trade size = " + list.size());
        getData(1);
    }

    private void getData(int pages) {
        ColdInterface.getTradeList(mAddress, 10, pages,mSymbol,adapter.getType(), mContext, Tag, new HttpInfoRequest<List<TransferListBean>>() {
            @Override
            public void onSuccess(List<TransferListBean> model) {
                if (pages == 1) {
                    adapter.clear();
                }
                adapter.addAll(model);
            }

            @Override
            public void onError(int eCode) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMoney();
    }

    private void initMoney() {
        if (mJnWallet != null) {
            UserBean userBean = DataManager.getInstance().getColdUser();
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

    private void initListener(){
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                binding.recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData(page);
                    }
                }, 1000);
            }

            @Override
            public void onMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error_foot, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setNoMore(R.layout.view_nomore_black);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = adapter.getAllData().get(position).getId();
                ColdAssetsDetailActivity.startActivity(mContext, id, mAddress);
            }
        });
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
            showDialog(mAddress);
        } else if (id == R.id.fl_set) {
            if (mJnWallet != null) {
                ColdAssetsSetActivity.startActivity(mContext, mSymbol, mJnWallet);
            } else if (mWalletDataBean != null) {
                ColdAssetsSetActivity.startActivity(mContext, mWalletDataBean);
            }
        } else if (id == R.id.iv_copy) {
            Utils.copyData(mContext, binding.tvUrl.getText().toString());
            ToastUtil.toast(getString(R.string.user_copy_success));
        } else if (id == R.id.fl_back) {
            finish();
        }else if (id == R.id.llFilter) {
            showFilterDialog();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TransferEvent event) {
        initMoney();
        page = 1;
        getData(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAllEvent event) {
        initMoney();
        page = 1;
        getData(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAddEvent event) {
        initMoney();
        page = 1;
        getData(1);
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

    private ColdWalletDetailAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ColdWalletDetailAdapter(mContext,mAddress);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position > 0) {

                    }
                }
            });
        }
        return mAdapter;
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

    private Bitmap mQrBmp;
    private void showDialog(String mAddress){
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.dialog_my_qrcode);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.tvCopy);
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = QRCodeUtil.createQRImage(mContext, mAddress, null);
                ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.tvCopy) {
                    Utils.copyData(mContext, mAddress);
                    ToastUtil.toast(getString(R.string.user_copy_success));
                }
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
        dialogFragment.setOnDismiss(new MyDialogFragment.IDismissListener() {
            @Override
            public void onDismiss() {
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = null;
            }
        });
    }


    public void showFilterDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.dialog_filter_record);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.llParent, R.id.ll, R.id.tvAll, R.id.tvTransferIn, R.id.tvTransferOut);
                View llReport = view.findViewById(R.id.ll);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llReport.getLayoutParams();
                int[] location = new int[2];
                binding.llFilter.getLocationOnScreen(location);
                lp.topMargin = location[1] + view.getHeight();
                llReport.setLayoutParams(lp);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.tvAll) {
                    adapter.setType(0);
                } else if (viewId == R.id.tvTransferIn) {
                    adapter.setType(2);
                } else if (viewId == R.id.tvTransferOut) {
                    adapter.setType(1);
                }
                page = 1;
                getData(page);
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    public static class BtcWalletItem implements MultiItemEntity {

        public int itemType;

        public BtcWalletItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

}
