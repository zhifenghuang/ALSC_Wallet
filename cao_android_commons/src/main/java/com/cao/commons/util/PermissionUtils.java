package com.cao.commons.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.cao.commons.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.List;


/**
 * 获取权限工具类
 *
 * @author CJZ
 * @Time 2018/11/23
 */
public class PermissionUtils {

    /**
     * 权限组申请
     */
    public static void requestPermission(final Context context, final PermissionCallBack callBack, final String[]... paramPermissions) {
        AndPermission.with(context)
                .runtime()
                .permission(paramPermissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        callBack.onSuccess();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {//不在提示时触发
                            showSettingDialog(context, permissions, paramPermissions, callBack);
                        } else {
                            callBack.onFailure();
                        }
                    }
                })
                .start();
    }

    /**
     * 单个权限申请
     */
    public static void requestPermission(final Context context, final PermissionCallBack callBack, final String... paramPermission) {

        AndPermission.with(context)
                .runtime()
                .permission(paramPermission)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        callBack.onSuccess();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {//不在提示时触发
                            showSettingDialog(context, permissions, paramPermission, callBack);
                        } else {
                            callBack.onFailure();
                        }
                    }
                })
                .start();
    }


    /**
     * Display setting dialog.
     */
    public static void showSettingDialog(final Context context, final List<String> permissions, final String[] paramPermissions, final PermissionCallBack callBack) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //SettingPermissionIntent.startPermissIntent(context);
                        setPermission(context, paramPermissions, callBack);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onFailure();
                    }
                })
                .show();
    }

    /**
     * Display setting dialog.
     */
    public static void showSettingDialog(final Context context, final List<String> permissions, final String[][] paramPermissions, final PermissionCallBack callBack) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //SettingPermissionIntent.startPermissIntent(context);
                        setPermission(context, paramPermissions, callBack);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onFailure();
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private static void setPermission(final Context context, final String[] permissions, final PermissionCallBack callBack) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        requestPermission(context, callBack, permissions);
                    }
                })
                .start();
    }

    /**
     * Set permissions.
     */
    private static void setPermission(final Context context, final String[][] permissions, final PermissionCallBack callBack) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        requestPermission(context, callBack, permissions);
                    }
                })
                .start();
    }

    /**
     * 回调
     */
    public interface PermissionCallBack {
        void onSuccess();

        void onFailure();
    }

}
