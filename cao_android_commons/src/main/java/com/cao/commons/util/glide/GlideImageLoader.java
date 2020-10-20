package com.cao.commons.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by LiYang on 2018/8/20.
 */

public class GlideImageLoader extends ImageLoader {
    //private static final int ERROR_IMAGE = R.mipmap.chapin;
    //private static final int AVATAR_IMAGE = R.mipmap.chapin;
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(getRequestOptions())
                .into(imageView);

    }
    public static RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.centerCrop();//图片显示类型
        //options.placeholder(AVATAR_IMAGE);//加载中图片
        //options.error(ERROR_IMAGE);//加载错误的图片
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        return options;
    }
}
