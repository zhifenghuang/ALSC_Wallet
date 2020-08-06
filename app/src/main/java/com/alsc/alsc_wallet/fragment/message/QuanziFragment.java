package com.alsc.alsc_wallet.fragment.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.QuanziAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

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
                R.id.tvNewest, R.id.tvFollow, R.id.tvMyDynamic);
//        initFragments();
//        switchFragment(mBaseFragment.get(0));
    }

    private QuanziAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new QuanziAdapter(getActivity());
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
                resetBtns((TextView) v, fv(R.id.tvNewest), fv(R.id.tvFollow), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvNewest:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvFollow), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvFollow:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvMyDynamic));
                break;
            case R.id.tvMyDynamic:
                resetBtns((TextView) v, fv(R.id.tvHot), fv(R.id.tvNewest), fv(R.id.tvFollow));
                break;
        }
    }

    private void resetBtns(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        tv2.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        tv3.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        tv4.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
    }

//    /**
//     * @param to 马上要切换到的Fragment，一会要显示
//     */
//    private void switchFragment(Fragment to) {
//        if (mCurrentFragment != to) {
//            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//            if (!to.isAdded()) {
//                if (mCurrentFragment != null) {
//                    ft.hide(mCurrentFragment);
//                }
//                ft.add(R.id.fl, to, to.toString()).commit();
//            } else {
//                if (mCurrentFragment != null) {
//                    ft.hide(mCurrentFragment);
//                }
//                ft.show(to).commit();
//            }
//        }
//        mCurrentFragment = to;
//    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
