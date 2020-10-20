package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * CommonDialog
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class SelectCameraDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    private TextView tv_video;
    private TextView tv_picture;
    private OnCommonDialogListener listener;

    public static SelectCameraDialog builder(Context context) {
        return new SelectCameraDialog(context);
    }

    public SelectCameraDialog setListener(OnCommonDialogListener listener){
        this.listener = listener;
        return this;
    }

    public SelectCameraDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.select_camera_dialog,null);
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
        tv_video = mView.findViewById(R.id.tv_video);
        tv_picture =  mView.findViewById(R.id.tv_picture);
    }

    private void setListener(){
        tv_video.setOnClickListener(this);
        tv_picture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_video) {
            listener.selectVideo(SelectCameraDialog.this);
        }else if(i == R.id.tv_picture){
            listener.selectPicture(SelectCameraDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    public interface OnCommonDialogListener {
        void selectVideo(SelectCameraDialog dialog);
        void selectPicture(SelectCameraDialog dialog);
    }
}
