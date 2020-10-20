package com.cao.commons.manager;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * 缺省页管理
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class DefaultLayoutManager {

    private int DEFAULT_LAYOUT = TYPE_DEFAULT;
    /*** Search View Type */
    public static final int TYPE_DEFAULT = 1;
    /*** title View Type */
    public static final int TYPE_PRIVACY = 2;

    private Context mContext;
    private View mView;
    private ViewStub mViewStub;

    /** 缺省 */
    private ImageView mDefaultIamge;
    private TextView mDefaultText;
    private LinearLayout mDefaultLayout;
    /** 隐私 */
    private LinearLayout mPrivacyLayout;

    public DefaultLayoutManager(Context context,ViewStub viewStub) {
        super();
        this.mContext = context;
        this.mViewStub = viewStub;
    }

    private void initView(){
        mView = mViewStub.inflate();
        /* 缺省 */
        mDefaultIamge = mView.findViewById(R.id.defaultImage);
        mDefaultText =  mView.findViewById(R.id.defaultText);
        mDefaultLayout = mView.findViewById(R.id.defaultLayout);
        /* 隐私 */
        mPrivacyLayout =  mView.findViewById(R.id.privacyLayout);
    }

    public void init(){
        if (mView == null){
            initView();
        }
    }

    public DefaultLayoutManager setLayoutType(int type) {
        init();
        setButLayout(this.DEFAULT_LAYOUT = type);
        return this;
    }

    private void setButLayout(int type) {
        mDefaultLayout.setVisibility(type == TYPE_PRIVACY ? View.GONE : View.VISIBLE);
        mPrivacyLayout.setVisibility(type == TYPE_DEFAULT ? View.GONE : View.VISIBLE);
    }


}
