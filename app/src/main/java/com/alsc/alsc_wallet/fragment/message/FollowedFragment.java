package com.alsc.alsc_wallet.fragment.message;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.adapter.FollowedAdapter;
import com.alsc.alsc_wallet.adapter.SharePlatfomAdapter;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.utils.BitmapUtil;
import com.alsc.alsc_wallet.utils.ScreenShot;

import java.util.ArrayList;

public class FollowedFragment extends BaseFragment {

    private FollowedAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_followed;
    }

    @Override
    protected void onViewCreated(View view) {
        ScreenShot.getInstance().register(getActivity(), new ScreenShot.CallbackListener() {
            @Override
            public void onShot(String path) {
                Log.e("aaaaaaaaaa", "path: " + path);
                Bitmap bmp = BitmapUtil.getBitmapFromFile(path);
                showShareDialog(bmp);
            }
        });
        setText(R.id.tvTitle, getString(R.string.wallet_ta_fans, "158"));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("1sddfvf3");
        list.add("2wwwwerrt");
        list.add("3ecccszzz");
        list.add("4ybbvfdss");
        list.add("5fffrvtgyy");
        getAdapter().setNewInstance(list);
    }

    private FollowedAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new FollowedAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    private void showShareDialog(final Bitmap bmp) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_share_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.tvCancel, R.id.iv);
                ((ImageView) view.findViewById(R.id.iv)).setImageBitmap(bmp);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                SharePlatfomAdapter adapter = new SharePlatfomAdapter(getActivity());
                recyclerView.setAdapter(adapter);
                ArrayList<SharePlatformInfo> list = new ArrayList<>();
                list.add(new SharePlatformInfo(R.drawable.wallet_share_platform_0, getString(R.string.wallet_share_platform_0)));
                list.add(new SharePlatformInfo(R.drawable.wallet_share_platform_1, getString(R.string.wallet_share_platform_1)));
                list.add(new SharePlatformInfo(R.drawable.wallet_share_platform_2, getString(R.string.wallet_share_platform_2)));
                list.add(new SharePlatformInfo(R.drawable.wallet_share_platform_3, getString(R.string.wallet_share_platform_3)));
                list.add(new SharePlatformInfo(R.drawable.wallet_share_platform_4, getString(R.string.wallet_share_platform_4)));
                adapter.setNewInstance(list);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tv1:

                        break;
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
        dialogFragment.setOnDismiss(new MyDialogFragment.IDismissListener() {
            @Override
            public void onDismiss() {
                if (bmp != null) {
                    bmp.recycle();
                }
            }
        });
    }

    public static class SharePlatformInfo {
        public int srcId;
        public String name;

        SharePlatformInfo(int srcId, String name) {
            this.srcId = srcId;
            this.name = name;
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ScreenShot.getInstance().unregister();
    }
}
