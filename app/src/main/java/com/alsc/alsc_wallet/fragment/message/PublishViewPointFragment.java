package com.alsc.alsc_wallet.fragment.message;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.PublishPicAdapter;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.BaseFragment;

import java.util.ArrayList;

public class PublishViewPointFragment extends BaseFragment {

    private PublishPicAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish_viewpoint;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.wallet_publish_viewpoint);
        TextView btnRight = view.findViewById(R.id.btnRight);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(R.drawable.bg_wallet_publish_disable);
        btnRight.setText(getString(R.string.wallet_publish));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add(null);
        getAdapter().setNewInstance(list);
        setViewsOnClickListener(R.id.ivLeft, R.id.btnRight, R.id.ivPhoto, R.id.ivAt);
    }

    private PublishPicAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new PublishPicAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivLeft:
                showExitDialog();
                break;
            case R.id.btnRight:
                goBack();
                break;
            case R.id.ivPhoto:
                gotoPager(SelectPublishTypeFragment.class);
                break;
            case R.id.ivAt:
                gotoPager(SelectAtPeopleFragment.class);
                break;
        }
    }

    private void showExitDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                view.findViewById(R.id.tv1).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.wallet_exit_publish));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.wallet_ok));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.wallet_cancel));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn1) {
                    goBack();
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }


}
