package com.alsc.chat.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alsc.chat.R;
import com.alsc.chat.manager.ConfigManager;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UserBean;
import com.alsc.chat.dialog.CommonProgressDialog;
import com.alsc.chat.fragment.BaseFragment;
import com.alsc.chat.fragment.ChatListFragment;
import com.alsc.chat.fragment.FriendListFragment;
import com.alsc.chat.fragment.MyDialogFragment;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.http.OnHttpErrorListener;
import com.alsc.chat.http.SubscriberOnNextListener;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.utils.Utils;
import com.cao.commons.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;


public abstract class ChatBaseActivity extends BaseActivity implements OnHttpErrorListener {

    private DisplayMetrics mDisplaymetrics;

    private MyDialogFragment mErrorDialog;

    private CommonProgressDialog mProgressDialog;

    private static final ArrayList<ChatBaseActivity> mActivityList = new ArrayList<>();

    protected ArrayList<UserBean> mFriendList;
    protected ArrayList<GroupBean> mGroupList;

    private boolean mIsGetFriend;
    private boolean mIsGetGroup;

    protected ChatListFragment mChatListFragment;
    protected FriendListFragment mFriendListFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
    }

    public void requestPermission(int permissionReqCode, String... permissions) {
        ArrayList<String> uncheckPermissions = null;
        for (String permission : permissions) {
            if (!Utils.isGrantPermission(this, permission)) {
                //进行权限请求
                if (uncheckPermissions == null) {
                    uncheckPermissions = new ArrayList<>();
                }
                uncheckPermissions.add(permission);
            }
        }
        if (uncheckPermissions != null && !uncheckPermissions.isEmpty()) {
            String[] array = new String[uncheckPermissions.size()];
            ActivityCompat.requestPermissions(this, uncheckPermissions.toArray(array), permissionReqCode);
        }
    }

    protected void setTopStatusBarStyle(View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(0, getStatusBarHeight() + topView.getPaddingTop(), 0, 0);
        }
    }

    /**
     * 判断是否从splashActivity过来
     *
     * @return true将被当作从后台到前台处理
     */
    protected boolean isComeFromSplash() {
        return getIntent().getBooleanExtra("key_come_from_splash", false);
    }


    public DisplayMetrics getDisplaymetrics() {
        if (mDisplaymetrics == null) {
            mDisplaymetrics = new DisplayMetrics();
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplaymetrics);
        }
        return mDisplaymetrics;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(0,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void openLeftMenu() {

    }

    public void showOneBtnDialog(final String title, final String msg, final String btnText) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_one_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                if (TextUtils.isEmpty(title)) {
                    view.findViewById(R.id.tv1).setVisibility(View.GONE);
                } else {
                    ((TextView) view.findViewById(R.id.tv1)).setText(title);
                }
                ((TextView) view.findViewById(R.id.tv2)).setText(msg);
                ((TextView) view.findViewById(R.id.btn2)).setText(btnText);
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {

            }
        });
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    public void showToastDialog(final String text) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_toast_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv)).setText(text);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.dismiss();
                    }
                }, 1000);
            }

            @Override
            public void onViewClick(int viewId) {

            }
        });
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");

    }

    public void errorCodeDo(final int errorCode, final String message) {
        if (!TextUtils.isEmpty(message)) {
            mErrorDialog = new MyDialogFragment(R.layout.layout_one_btn_dialog);
            mErrorDialog.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                @Override
                public void initView(View view) {
                    view.findViewById(R.id.tv1).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.tv2)).setText(message);
                    ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_ok));
                    mErrorDialog.setDialogViewsOnClickListener(view, R.id.btn2);
                }

                @Override
                public void onViewClick(int viewId) {

                }
            });
            mErrorDialog.show(getSupportFragmentManager(), "MyDialogFragment");
            mErrorDialog.setOnDismiss(new MyDialogFragment.IDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        }
    }

    /**
     * 页面跳转，如果返回true,则基类已经处理，否则没有处理
     *
     * @param pagerClass
     * @return
     */
    public void gotoPager(Class<?> pagerClass) {
        gotoPager(pagerClass, null);
    }


    /**
     * 页面跳转，如果返回true,则基类已经处理，否则没有处理
     *
     * @param pagerClass
     * @param bundle
     * @return
     */
    public void gotoPager(Class<?> pagerClass, Bundle bundle) {
        if (Activity.class.isAssignableFrom(pagerClass)) {
            Intent intent = new Intent(this, pagerClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        } else {
            String name = pagerClass.getName();
            Intent intent = new Intent(this, EmptyActivity.class);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.putExtra("FRAGMENT_NAME", name);
            startActivity(intent);
        }
    }

    /**
     * 返回，如果stack中还有Fragment的话，则返回stack中的fragment，否则 finish当前的Activity
     */
    public void goBack() {
        finish();
    }


    public BaseFragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {
            return null;
        }
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment instanceof BaseFragment && fragment.isVisible())
                return (BaseFragment) fragment;
        }
        return null;
    }

    public void onResume() {
        super.onResume();
        if (mChatListFragment != null) {
            mFriendList = DataManager.getInstance().getFriends();
            mGroupList = DataManager.getInstance().getGroups();
            mChatListFragment.setData(mFriendList, mGroupList);
            if (mFriendListFragment != null) {
                mFriendListFragment.setData(mFriendList);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public void finishAllOtherActivity() {
        for (BaseActivity activity : mActivityList) {
            if (activity instanceof EmptyActivity) {
                activity.finish();
            }
        }
    }

    public void finishAllActivity() {
        for (ChatBaseActivity activity : mActivityList) {
            activity.finish();
        }
    }


    @Override
    public void onConnectError(Throwable e) {
//        if (!NetUtil.isConnected(this)) {
//
//        } else if (e instanceof UnknownHostException
//                || e instanceof JSONException
//                || e instanceof retrofit2.HttpException) {
//
//        } else if (e instanceof SocketTimeoutException
//                || e instanceof ConnectException
//                || e instanceof TimeoutException) {
//
//        } else {
//
//        }
        showToast(R.string.chat_net_work_error);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param uri 图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    public String getRealPathFromUriAboveApi19(Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param uri 图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public String getRealPathFromUriBelowAPI19(Uri uri) {
        return getDataColumn(uri, null, null);
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    @Override
    public synchronized void onServerError(int errorCode, String errorMsg) {
        //   stopHttpLoad();
        if (errorCode == 401) {
            ConfigManager.getInstance().showLoginOutDialog();
            return;
        }
        errorCodeDo(errorCode, errorMsg);
    }

    public void onBackClick(View view) {
        goBack();
    }


    public void showToast(int textId) {
        Toast.makeText(this, getString(textId), Toast.LENGTH_LONG).show();
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new CommonProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public void getFriendFromServer() {
        if (mIsGetFriend) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetFriend = true;
        ChatHttpMethods.getInstance().getFriends(new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
            @Override
            public void onNext(ArrayList<UserBean> list, String msg) {
                DataManager.getInstance().saveFriends(list);
                mFriendList = list;
                if (mChatListFragment != null) {
                    mChatListFragment.setData(mFriendList, mGroupList);
                }
                if (mFriendListFragment != null) {
                    mFriendListFragment.setData(mFriendList);
                }
                mIsGetFriend = false;
            }
        }, this, false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                mIsGetFriend = false;
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                mIsGetFriend = false;
                if (errorCode == 401) {
                    ConfigManager.getInstance().showLoginOutDialog();
                }
            }
        }));
    }

    public void getGroupFromServer() {
        if (mIsGetGroup) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetGroup = true;
        ChatHttpMethods.getInstance().getGroups(1, Integer.MAX_VALUE - 1,
                new HttpObserver(new SubscriberOnNextListener<ArrayList<GroupBean>>() {
                    @Override
                    public void onNext(ArrayList<GroupBean> list, String msg) {
                        DataManager.getInstance().saveGroups(list);
                        mGroupList = list;
                        if (mChatListFragment != null) {
                            mChatListFragment.setData(mFriendList, mGroupList);
                        }
                        mIsGetGroup = false;
                    }
                }, this, false, new OnHttpErrorListener() {
                    @Override
                    public void onConnectError(Throwable e) {
                        mIsGetGroup = false;
                    }

                    @Override
                    public void onServerError(int errorCode, String errorMsg) {
                        mIsGetGroup = false;
                        if (errorCode == 401) {
                            ConfigManager.getInstance().showLoginOutDialog();
                        }
                    }
                }));
    }

}