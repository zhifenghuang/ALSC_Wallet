package com.alsc.chat.fragment;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alsc.chat.R;
import com.common.activity.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;

public class AddFriendFragment extends ChatBaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_friend;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_add_friend);
        setViewsOnClickListener(R.id.llSearch, R.id.llScan);
        UserBean bean = DataManager.getInstance().getUser();
        setText(R.id.tvMyId, getString(R.string.chat_my_id, String.valueOf(bean.getUserId())));
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llSearch) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_EXTRA, SearchFragment.SEARCH_SERVER_FRIEND);
            gotoPager(SearchFragment.class, bundle);
        } else if (id == R.id.llScan) {
            if (!Utils.isGrantPermission(getActivity(),
                    Manifest.permission.CAMERA)) {
                ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.CAMERA);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alsc://capture"));
                startActivity(intent);
            }
        }
    }

}
