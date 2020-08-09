package com.alsc.alsc_wallet.fragment.message;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.SelectAtPeopleAdapter;
import com.alsc.alsc_wallet.adapter.SelectPublishTypeAdapter;
import com.alsc.alsc_wallet.bean.KeyValueBean;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SelectPublishTypeFragment extends BaseFragment {

    private SelectPublishTypeAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_publish_type;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_select_publish_type));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        int keyId, valueId;
        for (int i = 0; i < 4; ++i) {
            keyId = getResources().getIdentifier("wallet_publish_type_" + i, "string", getActivity().getPackageName());
            valueId = getResources().getIdentifier("wallet_publish_type_content_" + i, "string", getActivity().getPackageName());
            getAdapter().addData(new KeyValueBean(getString(keyId), getString(valueId)));
        }
    }

    private SelectPublishTypeAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SelectPublishTypeAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }


}
