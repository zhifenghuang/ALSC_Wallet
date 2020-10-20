package com.alsc.alsc_wallet.fragment.message;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.UserInfoAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.common.dialog.MyDialogFragment;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.utils.Utils;

import java.util.ArrayList;

public class UserInfoFragment extends BaseFragment {

    private UserInfoAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_ta_fans, "158"));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<UserInfoItem> list = new ArrayList<>();
        list.add(new UserInfoItem(0));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        list.add(new UserInfoItem(1));
        getAdapter().setNewInstance(list);
    }

    private UserInfoAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new UserInfoAdapter(getActivity());
            mAdapter.addChildClickViewIds();
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position > 0) {
                        gotoPager(ViewpointDetailFragment.class);
                    }
                }
            });
            mAdapter.addChildClickViewIds(R.id.ll2, R.id.ll3, R.id.tvAllMsg, R.id.ivMore);
            mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    int viewId = view.getId();
                    if (viewId == R.id.tvAllMsg) {
                        gotoPager(ArticleDetailFragment.class);
                    } else if (viewId == R.id.ivMore) {
                        showReportDialog(view);
                    } else if (viewId == R.id.ll2) {
                        gotoPager(FollowedFragment.class);
                    } else if (viewId == R.id.ll3) {
                        gotoPager(FollowedFragment.class);
                    }
                }
            });
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    public static class UserInfoItem implements MultiItemEntity {

        public int itemType;

        public UserInfoItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
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
}
