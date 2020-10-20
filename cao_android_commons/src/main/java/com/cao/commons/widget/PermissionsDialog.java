package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * PermissionsDialog
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class PermissionsDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    /** 标题、内容*/
    private TextView mTitle;
    private TextView mMsg;
    /** 取消、确定*/
    private Button mCancleBut;
    private Button mDetermineBut;
    private OnPermissionsDialogListener listener;

    public static PermissionsDialog builder(Context context) {
        return new PermissionsDialog(context);
    }

    public PermissionsDialog setLeftButName(String msg){
        mCancleBut.setText(msg);
        return this;
    }

    public PermissionsDialog setRightButName(String msg){
        mDetermineBut.setText(msg);
        return this;
    }

    public PermissionsDialog setContent( String msg){
        mMsg.setText(Html.fromHtml(msg));
        return this;
    }

    public PermissionsDialog setTitle( String msg){
        mTitle.setText(msg);
        return this;
    }

    public PermissionsDialog setListener(OnPermissionsDialogListener listener){
        this.listener = listener;
        return this;
    }

    public PermissionsDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.permissions_dialog,null);
        this.mContext = context;
        init();
    }

    private void init(){
        setContentView(mView);
        setCancelable(false);
        findViewByIds();
        setListener();
    }

    private void findViewByIds(){
        mTitle = mView.findViewById(R.id.title);
        mMsg =  mView.findViewById(R.id.msg);
        mCancleBut =  mView.findViewById(R.id.cancleBut);
        mDetermineBut = mView.findViewById(R.id.determineBut);
    }

    private void setListener(){
        mCancleBut.setOnClickListener(this);
        mDetermineBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancleBut) {
            listener.cancleOnClick(PermissionsDialog.this);
        } else if (i == R.id.determineBut) {
            listener.determineOnClick(PermissionsDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDismiss();
    }

    public interface OnPermissionsDialogListener {
        void cancleOnClick(PermissionsDialog dialog);
        void determineOnClick(PermissionsDialog dialog);
        void onDismiss();
    }
}
