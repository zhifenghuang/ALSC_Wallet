package com.alsc.chat.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.GroupUserAdapter;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.alsc.chat.manager.UPYFileUploadManger;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UploadAvatarEvent;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

public class GroupInfoFragment extends ChatBaseFragment {

    private GroupBean mGroup;
    private ArrayList<UserBean> mGroupUsers;


    private static final int ALBUM_REQUEST_CODE = 10002;

    private GroupUserAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_info;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.chat_detail));
        EventBus.getDefault().register(this);
        setTopStatusBarStyle(view);
        mGroup = (GroupBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        mGroupUsers = (ArrayList<UserBean>) getArguments().getSerializable(Constants.BUNDLE_EXTRA_2);

        for (UserBean userBean : mGroupUsers) {
            if (userBean.getGroupRole() == 3) {
                mGroupUsers.remove(userBean);
                mGroupUsers.add(0, userBean);
                break;
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        resetData();

        boolean isShowAddButton = false;
        if (mGroup.getJoinStint() == 0) {
            if (mGroup.getJoinType() == 0 && mGroup.getGroupRole() == 3) {  //只有群主能拉人
                isShowAddButton = true;
            } else if (mGroup.getJoinType() == 1) {
                isShowAddButton = true;
            }
        }
        view.findViewById(R.id.tvAddMember).setVisibility(isShowAddButton ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.tvRemoveMember).setVisibility(mGroup.getGroupRole() == 3 ? View.VISIBLE : View.GONE);
        setViewsOnClickListener(R.id.tvAddMember, R.id.tvRemoveMember);
    }

    private GroupUserAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new GroupUserAdapter(getActivity(), this);
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {
        getAdapter().setGroupRole(mGroup, DataManager.getInstance().getUser().getUserId());
        Object object = DataManager.getInstance().getObject();
        if (object instanceof ArrayList) {
            mGroupUsers.addAll((ArrayList<UserBean>) object);
            DataManager.getInstance().saveGroupUsers(mGroup.getGroupId(), mGroupUsers);
        }
        DataManager.getInstance().setObject(null);
        resetData();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvAddMember) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, SelectFriendFragment.FROM_GROUP_DETAIL);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupUsers);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_3, mGroup);
            gotoPager(SelectFriendFragment.class, bundle);
        } else if (id == R.id.tvRemoveMember) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(GroupBean bean) {
        if (getView() != null) {
            mGroup = bean;
            getAdapter().notifyDataSetChanged();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarFile(File file) {
        if (getView() != null) {
            UPYFileUploadManger.getInstance().uploadFile(file);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarUrl(UploadAvatarEvent avatar) {
        if (getView() != null && avatar.isSuccess()) {
            mGroup.setIcon(avatar.getUrl());
            EventBus.getDefault().post(mGroup);
            saveNewGroup(mGroup);
            ChatHttpMethods.getInstance().updateGroupIcon(String.valueOf(mGroup.getGroupId()), avatar.getUrl(), new HttpObserver(new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o, String msg) {

                }
            }, getActivity(), (BaseActivity) getActivity()));
        }
    }

    private void saveNewGroup(GroupBean bean) {
        ArrayList<GroupBean> list = DataManager.getInstance().getGroups();
        if (list == null || list.isEmpty()) {
            return;
        }
        int index = 0;
        for (GroupBean group : list) {
            if (group.getGroupId() == bean.getGroupId()) {
                list.set(index, bean);
                DataManager.getInstance().saveGroups(list);
                return;
            }
            ++index;
        }
    }

    private void resetData() {
        ArrayList<GroupUserItem> list = new ArrayList<>();
        list.add(new GroupUserItem(0));
        for (UserBean userBean : mGroupUsers) {
            list.add(new GroupUserItem(1, userBean));
        }
        getAdapter().setNewData(list);
        getAdapter().notifyDataSetChanged();
    }

    public void showSelectPhotoDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_select_photo_type);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btnTakePhoto, R.id.btnAlbum, R.id.btnCancel);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btnTakePhoto) {
                    if (!Utils.isGrantPermission(getActivity(),
                            Manifest.permission.CAMERA)) {
                        ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.CAMERA);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, CameraFragment.FOR_AVATAR);
                        gotoPager(CameraFragment.class, bundle);
                    }
                } else if (viewId == R.id.btnAlbum) {
                    if (!Utils.isGrantPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");//相片类型
                        startActivityForResult(intent, ALBUM_REQUEST_CODE);
                    }
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ALBUM_REQUEST_CODE) {
                try {
                    String filePath;
                    int sdkVersion = Build.VERSION.SDK_INT;
                    if (sdkVersion >= 19) { // api >= 19
                        filePath = ((BaseActivity) getActivity()).getRealPathFromUriAboveApi19(data.getData());
                    } else { // api < 19
                        filePath = ((BaseActivity) getActivity()).getRealPathFromUriBelowAPI19(data.getData());
                    }
                    onReceiveAvatarFile(new File(filePath));
                } catch (Exception e) {

                }
            }
        }
    }

    public static class GroupUserItem implements MultiItemEntity {

        private int itemType;
        private UserBean userBean;

        public GroupUserItem(int itemType) {
            this.itemType = itemType;
        }


        public GroupUserItem(int itemType, UserBean userBean) {
            this.itemType = itemType;
            this.userBean = userBean;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public UserBean getUserBean() {
            return userBean;
        }

        public void setUserBean(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }


}
