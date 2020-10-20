package com.alsc.chat.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alsc.chat.R;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.dialog.InputPasswordDialog;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.http.HttpMethods;
import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.http.SubscriberOnNextListener;
import com.alsc.chat.manager.ConfigManager;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;
import com.cao.commons.bean.chat.EnvelopeBean;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.GroupMessageBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.google.gson.Gson;
import com.zhangke.websocket.WebSocketHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Fragment基类提供公共的页面跳转方面，公共弹窗等方法
 *
 * @author xiangwei.ma
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    // 标示是否第一次执行onStart页面
    private boolean mIsFirstOnStart = true;
    private DisplayMetrics mDisplaymetrics;
    protected Context mContext;

    protected boolean mIsToAnotherPage;

    protected abstract int getLayoutId();

    /**
     * 构造函数，不能使用带有参数的构造函数，因为系统自动回收后，会调用没有参数的构造函数
     */
    public BaseFragment() {
        super();
    }

    protected void setTopStatusBarStyle(View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(0, getStatusBarHeight() + topView.getPaddingTop(), 0, 0);
        }
    }

    protected void setTopStatusBarStyle(int viewId) {
        View topView = fv(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(topView.getPaddingLeft(), getStatusBarHeight() + topView.getPaddingTop(), topView.getPaddingRight(), topView.getPaddingBottom());
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    protected void setEditTextHint(int id, String text) {
        EditText et = (EditText) getView().findViewById(id);
        et.setHint(text == null ? "" : text);
    }


    protected void setText(TextView tv, String text) {
        tv.setText(text == null ? "" : text);
    }

    protected void setText(int id, String text) {
        TextView tv = (TextView) getView().findViewById(id);
        tv.setText(text == null ? "" : text);
    }

    protected void setText(int id, int textId) {
        TextView tv = getView().findViewById(id);
        tv.setText(getString(textId));
    }

    protected void setTextColor(int id, int clorId) {
        TextView tv = getView().findViewById(id);
        tv.setTextColor(getResources().getColor(clorId));
    }

    protected void setImage(int id, int drawableId) {
        ImageView iv = (ImageView) getView().findViewById(id);
        iv.setImageResource(drawableId);
    }

    protected void setBackground(int id, int drawableId) {
        View view = getView().findViewById(id);
        view.setBackgroundResource(drawableId);
    }


    protected String getTextById(int resId) {
        return ((TextView) getView().findViewById(resId)).getText().toString().trim();
    }

    protected void setViewVisible(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.VISIBLE);
        }
    }

    protected void setViewGone(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.GONE);
        }
    }

    protected void setViewInvisible(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.INVISIBLE);
        }
    }

    protected void setEditTextMaxLength(int etId, int maxLength) {
        ((EditText) getView().findViewById(etId)).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void hideKeyBoard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) {
            if (getActivity().getCurrentFocus() != null) {
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } else {
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyBoard(View view) {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    protected <VT extends View> VT fv(View parent, int id) {
        return (VT) parent.findViewById(id);
    }

    protected <VT extends View> VT fv(int id) {
        return (VT) getView().findViewById(id);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFirstOnStart = true;
    }


    public int[] getInScreen(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location;
    }

    /**
     * 跳转到新的界面
     *
     * @param pagerClass
     */
    public void gotoPager(final Class<?> pagerClass) {
        gotoPager(pagerClass, null);
    }

    /**
     * 跳转到新的界面
     *
     * @param pagerClass
     * @param bundle
     */
    public void gotoPager(final Class<?> pagerClass, final Bundle bundle) {
        if (mIsToAnotherPage) {
            return;
        }
        if (getActivity() instanceof ChatBaseActivity) {
            mIsToAnotherPage = true;
            ((ChatBaseActivity) getActivity()).gotoPager(pagerClass, bundle);
        }
    }


    /**
     * 返回，如果stack中还有Fragment的话，则返回stack中的fragment，否则 finish当前的Activity
     */
    public void goBack() {
        hideKeyBoard(null);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutId() == 0) {
            ((Activity) mContext).finish();
            return null;
        }
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (view == null) {
            return;
        }
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    public void setViewsOnClickListener(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setOnClickListener(this);
        }
    }

    /**
     * fragment的View创建好后调用
     */
    protected abstract void onViewCreated(View view);

    @Override
    public void onStop() {
        mIsFirstOnStart = true;
        super.onStop();

    }


    @Override
    public void onStart() {
        super.onStart();
        if (getView() == null) {
            return;
        }
        if (mIsFirstOnStart) {
            updateUIText();
            mIsFirstOnStart = false;
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        if (getView() == null) {
            return;
        }
        mIsToAnotherPage = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        if (getView() == null) {
            return;
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (getView() == null) {
            return;
        }
        super.onDestroyView();
    }


    /**
     * 接受到更改语言设置后执行的方法
     */
    public abstract void updateUIText();


    public DisplayMetrics getDisplaymetrics() {
        if (mDisplaymetrics == null) {
            mDisplaymetrics = new DisplayMetrics();
            ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplaymetrics);
        }
        return mDisplaymetrics;
    }

    public void onEditKeyListener(EditText et) {
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                        case KeyEvent.KEYCODE_DPAD_UP:
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    public void onReturnResult(int requestCode, int resultCode, Intent data) {
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    public void showToast(int textId) {
        Toast.makeText(getActivity(), getString(textId), Toast.LENGTH_LONG).show();
    }

    protected void showPayInGroupDialog(final GroupBean group, final UserBean inviteUser) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.chat_the_group_need_pay_xxx_alsc, String.valueOf(group.getPayAmount())));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_i_think));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_pay_enter_group_1));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    showInputPayPasswordDialog(group, inviteUser);
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    private void showInputPayPasswordDialog(final GroupBean group, final UserBean inviteUser) {
        InputPasswordDialog dialog = new InputPasswordDialog(mContext, -1, null, null);
        dialog.setOnInputPasswordListener(new InputPasswordDialog.OnInputPasswordListener() {
            @Override
            public void afterCheckPassword() {
                if (getView() == null) {
                    return;
                }
                payEnterGroup(group, inviteUser);
            }
        });
        dialog.show();
    }

    private void payEnterGroup(final GroupBean group, final UserBean inviteUser) {
        ChatHttpMethods.getInstance().envelopeSend(group.getPayAmount(), 1, "", 6, -1, group.getGroupId(),
                new HttpObserver(new SubscriberOnNextListener<EnvelopeBean>() {
                    @Override
                    public void onNext(EnvelopeBean bean, String msg) {
                        ArrayList<UserBean> list = new ArrayList<>();
                        list.add(DataManager.getInstance().getUser());
                        sendInviteToGroupMsg(group, inviteUser, list);
                        if (bean == null) {
                            return;
                        }
                        showToast(R.string.chat_pay_enter_group_success);
                        addGroupInList(group);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, group);
                        gotoPager(GroupChatFragment.class, bundle);
                        ((ChatBaseActivity) getActivity()).finishAllOtherActivity();
                    }
                }, getActivity(), (ChatBaseActivity) getActivity()));
    }

    protected void removeFromGroupInList(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                groups.remove(bean);
                DataManager.getInstance().saveGroups(groups);
                break;
            }
        }
    }


    protected void addGroupInList(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        boolean isHad = false;
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                isHad = true;
                break;
            }
        }
        if (!isHad) {
            groups.add(group);
            DataManager.getInstance().saveGroups(groups);
        }
    }

    protected void updateGroup(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                groups.remove(bean);
                groups.add(group);
                DataManager.getInstance().saveGroups(groups);
                break;
            }
        }
    }

    protected boolean isInGroup(long groupId) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == groupId) {
                return true;
            }
        }
        return false;
    }

    public void sendInviteToGroupMsg(GroupBean group, UserBean inviteUser, List<UserBean> list) {
        GroupMessageBean groupMessageBean = new GroupMessageBean();
        groupMessageBean.setCmd(2100);
        groupMessageBean.setMsgType(inviteUser == null ? MessageType.TYPE_IN_GROUP_BY_QRCODE.ordinal() : MessageType.TYPE_INVITE_TO_GROUP.ordinal());
        groupMessageBean.setGroupId(group.getGroupId());
        if (inviteUser != null) {
            groupMessageBean.setContent(new Gson().toJson(inviteUser.toMap()));
        }
        groupMessageBean.setFromId(DataManager.getInstance().getUserId());
        ArrayList<HashMap<String, Object>> userList = new ArrayList<>();
        for (UserBean bean : list) {
            if (bean != null) {
                userList.add(bean.toMap());
            }
        }
        groupMessageBean.setExtra(new Gson().toJson(userList));
        if (TextUtils.isEmpty(groupMessageBean.getExtra())) {
            return;
        }
        WebSocketHandler.getDefault().send(groupMessageBean.toJson());
        groupMessageBean.setSendStatus(1);
        DatabaseOperate.getInstance().insert(groupMessageBean);
        EventBus.getDefault().post(groupMessageBean);
    }


    public void sendUpdateGroupMsg(GroupBean group, int msgType, String text) {
        GroupMessageBean groupMessageBean = new GroupMessageBean();
        groupMessageBean.setCmd(2100);
        groupMessageBean.setMsgType(msgType);
        groupMessageBean.setGroupId(group.getGroupId());
        groupMessageBean.setContent(text);
        groupMessageBean.setFromId(DataManager.getInstance().getUserId());
        ArrayList<HashMap<String, Object>> userList = new ArrayList<>();
        userList.add(DataManager.getInstance().getUser().toMap());
        groupMessageBean.setExtra(new Gson().toJson(userList));
        WebSocketHandler.getDefault().send(groupMessageBean.toJson());
        groupMessageBean.setSendStatus(1);
        DatabaseOperate.getInstance().insert(groupMessageBean);
        EventBus.getDefault().post(groupMessageBean);
    }


}
