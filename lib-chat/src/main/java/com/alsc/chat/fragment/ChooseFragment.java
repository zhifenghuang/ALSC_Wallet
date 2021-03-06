package com.alsc.chat.fragment;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.ChooseAdapter;
import com.alsc.chat.utils.Constants;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ChooseFragment extends ChatBaseFragment {

    public static final int CHOOSE_GENDER = 0;
    public static final int CHOOSE_COUNTRY = 1;
    public static final int CHOOSE_DELETE_TYPE = 2;
    public static final int CHOOSE_LANGUAGE = 3;

    private int mChooseType;
    private int mCurrentSelect;
    private ChooseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose;
    }

    @Override
    protected void onViewCreated(View view) {
        mChooseType = getArguments().getInt(Constants.BUNDLE_EXTRA, CHOOSE_GENDER);
        mCurrentSelect = getArguments().getInt(Constants.BUNDLE_EXTRA_2, 0);
        setTopStatusBarStyle(view);
        if (mChooseType == CHOOSE_GENDER) {
            setText(R.id.tvTitle, R.string.chat_choose_gender);
        } else if (mChooseType == CHOOSE_COUNTRY) {
            setText(R.id.tvTitle, R.string.chat_choose_area);
        } else if (mChooseType == CHOOSE_LANGUAGE) {
            setText(R.id.tvTitle, R.string.chat_switch_language);
        } else {
            setText(R.id.tvTitle, R.string.chat_delete_after_read);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        initData();
    }

    private ChooseAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ChooseAdapter(getActivity(), mChooseType);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HashMap<Integer, ChooseType> map = new HashMap<>();
                    map.put(mChooseType, getAdapter().getItem(position));
                    EventBus.getDefault().post(map);
                    goBack();
                }
            });
        }
        return mAdapter;
    }


    private void initData() {
        ChooseType type;
        ArrayList<ChooseType> list = new ArrayList<>();
        if (mChooseType == CHOOSE_GENDER) {
            for (int i = 0; i < 2; ++i) {
                type = new ChooseType();
                type.type = i;
                type.typeName = getString(i == 1 ? R.string.chat_male : R.string.chat_female);
                type.isSelect = i == mCurrentSelect;
                list.add(type);
            }
        } else if (mChooseType == CHOOSE_COUNTRY) {
            int index = 0;
            for (String str : Locale.getISOCountries()) {
                type = new ChooseType();
                type.type = index++;
                type.typeName = new Locale("", str).getDisplayCountry();
                type.isSelect = false;
                list.add(type);
            }
        } else if (mChooseType == CHOOSE_DELETE_TYPE) {
            int strId;
            for (int i = 0; i < 5; ++i) {
                type = new ChooseType();
                type.type = i;
                strId = getResources().getIdentifier("chat_delete_after_read_type_" + i, "string", getActivity().getPackageName());
                type.typeName = getString(strId);
                type.isSelect = i == mCurrentSelect;
                list.add(type);
            }
        } else if (mChooseType == CHOOSE_LANGUAGE) {
            int strId;
            for (int i = 0; i < 5; ++i) {
                type = new ChooseType();
                type.type = i;
                strId = getResources().getIdentifier("chat_language_" + i, "string", getActivity().getPackageName());
                type.typeName = getString(strId);
                type.drawableId = getResources().getIdentifier("chat_language_" + i, "drawable", getActivity().getPackageName());
                type.isSelect = i == mCurrentSelect;
                list.add(type);
            }
        }
        getAdapter().setNewData(list);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }

    public static class ChooseType {
        public int type;
        public String typeName;
        public boolean isSelect;
        public int drawableId = -1;
    }

}
