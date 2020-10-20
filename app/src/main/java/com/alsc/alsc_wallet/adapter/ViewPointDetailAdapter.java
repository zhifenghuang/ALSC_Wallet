package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.common.activity.BaseActivity;
import com.alsc.alsc_wallet.fragment.message.ViewpointDetailFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPointDetailAdapter extends BaseMultiItemQuickAdapter<ViewpointDetailFragment.ViewPointItem, BaseViewHolder> {

    private Context mContext;

    public ViewPointDetailAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_viewpoint_0);
        addItemType(1, R.layout.item_viewpoint_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ViewpointDetailFragment.ViewPointItem item) {
        switch (helper.getItemViewType()) {
            case 0:
//                helper.getView(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((BaseActivity) mContext).showReportDialog(v);
//                    }
//                });
                helper.setImageResource(R.id.ivAvatar, R.mipmap.ic_launcher_round);
                RecyclerView recyclerView = helper.getView(R.id.picRecyclerView);
                MsgPicAdapter picAdapter;
                if (recyclerView.getAdapter() != null) {
                    picAdapter = (MsgPicAdapter) recyclerView.getAdapter();
                } else {
                    picAdapter = new MsgPicAdapter(mContext);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    picAdapter.onAttachedToRecyclerView(recyclerView);
                    recyclerView.setAdapter(picAdapter);
                }
                ArrayList<String> list = new ArrayList<>();
                list.add("1");
                list.add("1");
                list.add("1");
                picAdapter.setNewInstance(list);
                break;
            case 1:
                break;
        }
    }

}
