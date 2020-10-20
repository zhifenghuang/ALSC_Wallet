package com.alsc.chat.fragment;


import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alsc.chat.R;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.http.SubscriberOnNextListener;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class LeaveMsgFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave_msg;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_please_leave_your_msg);
        setViewsOnClickListener(R.id.tvSubmit);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvSubmit) {
            String text = getTextById(R.id.etMsg);
            if (TextUtils.isEmpty(text)) {
                ((ChatBaseActivity) getActivity()).showToastDialog(getString(R.string.chat_not_input_your_problem));
                return;
            }

            ChatHttpMethods.getInstance().suggest(text, "", null, new HttpObserver(new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o, String msg) {
                    if (getView() == null) {
                        return;
                    }
                    setText(R.id.etMsg, "");
                    ((ChatBaseActivity) getActivity()).showToastDialog(getString(R.string.chat_leave_msg_successful));
                    getView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goBack();
                        }
                    },1100);
                }
            }, getActivity(), (ChatBaseActivity) getActivity()));
        }
    }


}
