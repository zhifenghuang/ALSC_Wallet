package com.alsc.alsc_wallet.fragment.message;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.QuanziAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.common.dialog.MyDialogFragment;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.utils.Utils;

import java.util.ArrayList;

public class QuanziFragment extends BaseFragment {

    private QuanziAdapter mAdapter;

//    private ArrayList<BaseFragment> mBaseFragment;
//    private Fragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quanzi;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter().addData(list);

        setViewsOnClickListener(R.id.ivPublish, R.id.tvHot,
                R.id.tvNewest, R.id.tvFollow, R.id.tvAtMe, R.id.tvMyDynamic);
//        initFragments();
//        switchFragment(mBaseFragment.get(0));
    }

    private QuanziAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new QuanziAdapter(getActivity());
            mAdapter.addChildClickViewIds(R.id.ivAvatar, R.id.ivMore);
            mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    int viewId = view.getId();
                    if (viewId == R.id.ivAvatar) {
                        gotoPager(UserInfoFragment.class);
                    } else if (viewId == R.id.ivMore) {
                        showReportDialog(view);
                    }
                }
            });
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    gotoPager(ViewpointDetailFragment.class);
                }
            });
        }
        return mAdapter;
    }

//    private void initFragments() {
//        mBaseFragment = new ArrayList<>();
//        mBaseFragment.add(new HotArticleFragment());
//        mBaseFragment.add(new HotArticleFragment());
//        mBaseFragment.add(new HotArticleFragment());
//    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivPublish:
                gotoPager(PublishViewPointFragment.class);
                break;
            case R.id.tvHot:
                resetBtns((TextView) v, fv(R.id.tvNewest), fv(R.id.tvFollow), fv(R.id.tvAtMe), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvNewest:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvFollow), fv(R.id.tvAtMe), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvFollow:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvAtMe), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvAtMe:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvFollow), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvMyDynamic:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvAtMe), fv(R.id.tvFollow));
                break;
        }
    }

    private void resetBtns(TextView... tvs) {
        tvs[0].setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        for (int i = 1; i < tvs.length; ++i) {
            tvs[i].setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        }
    }

    public void showReportDialog(final View locationView) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_report_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.llReport, R.id.ll, R.id.paddingView);
                View llReport = view.findViewById(R.id.llReport);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llReport.getLayoutParams();
                int[] location = new int[2];
                locationView.getLocationOnScreen(location);
                lp.topMargin = location[1] + view.getHeight() + Utils.dip2px(getActivity(), 20);
                llReport.setLayoutParams(lp);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.llReport:
                        showSelectReportReasonDialog();
                        break;
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }


    private void showSelectReportReasonDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_select_report_reseaon_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView,
                        R.id.tvCancel,
                        R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tv1:
                    case R.id.tv2:
                    case R.id.tv3:
                    case R.id.tv4:
                        break;
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
