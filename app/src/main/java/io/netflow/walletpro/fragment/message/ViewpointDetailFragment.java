package io.netflow.walletpro.fragment.message;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.netflow.walletpro.R;
import io.netflow.walletpro.adapter.ViewPointDetailAdapter;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

public class ViewpointDetailFragment extends BaseFragment {

    private ViewPointDetailAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewpoint_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_viewpoint_detail));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<ViewPointItem> list = new ArrayList<>();
        list.add(new ViewPointItem(0));
        list.add(new ViewPointItem(1));
        list.add(new ViewPointItem(1));
        list.add(new ViewPointItem(1));
        list.add(new ViewPointItem(1));
        getAdapter().setNewInstance(list);
    }

    private ViewPointDetailAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewPointDetailAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    public static class ViewPointItem implements MultiItemEntity {

        public int itemType;

        public ViewPointItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
