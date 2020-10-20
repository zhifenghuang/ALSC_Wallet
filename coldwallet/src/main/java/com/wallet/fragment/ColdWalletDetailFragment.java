//package com.wallet.fragment;
//
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.entity.MultiItemEntity;
//import com.chad.library.adapter.base.listener.OnItemClickListener;
//import com.common.fragment.BaseFragment;
//
//import java.util.ArrayList;
//
//public class ColdWalletDetailFragment extends BaseFragment {
//
// //   private OnlineWalletDetailAdapter mAdapter;
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_cold_wallet_detail;
//    }
//
//    @Override
//    protected void onViewCreated(View view) {
//        setTopStatusBarStyle(R.id.llTop);
//        setText(R.id.tvTitle, R.string.wallet_btc_wallet);
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        getAdapter().onAttachedToRecyclerView(recyclerView);
//        recyclerView.setAdapter(getAdapter());
////        ArrayList<BtcWalletItem> list = new ArrayList<>();
////        list.add(new BtcWalletItem(0));
////        list.add(new BtcWalletItem(1));
////        list.add(new BtcWalletItem(1));
////        list.add(new BtcWalletItem(1));
////        getAdapter().setNewInstance(list);
//    }
//
//    private OnlineWalletDetailAdapter getAdapter() {
//        if (mAdapter == null) {
//            mAdapter = new OnlineWalletDetailAdapter(getActivity());
//            mAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                    if (position > 0) {
//                        gotoPager(TradeRecordFragment.class);
//                    }
//                }
//            });
//        }
//        return mAdapter;
//    }
//
//
//    @Override
//    public void updateUIText() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//    }
//
//    protected boolean isNeedSetTopStyle() {
//        return false;
//    }
//
//
//
//}
