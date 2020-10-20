package com.cao.commons.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cao.commons.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 图片辅助类
 */
public class GlideUtil {

//    private static final int ERROR_IMAGE = R.mipmap.mine_pic;
//    private static final int AVATAR_IMAGE = R.mipmap.mine_pic;
//    private static final int ERRORIN_IMAGE = R.mipmap.buyin_add;
//    private static final int AVATARIN_IMAGE = R.mipmap.buyin_add;

    /**
     * 显示资源图片
     * 显示资源图片
     *
     * @param context    上下文
     * @param imageView  图片view
     * @param resourceId 图片资源id
     */
    public static void displayImage(Context context, final ImageView imageView, int resourceId) {
        Glide.with(context)
                .load(resourceId)
                .transition(new DrawableTransitionOptions().dontTransition())
                .apply(getRequestInOptions(resourceId))
                .into(imageView);

    }
//    public static void displayImage(Context context, ImageView imageView, String url) {
//        GlideApp.with(context)
//                .load(url)
//                .transition(new_message2 DrawableTransitionOptions().dontTransition())
//                .apply(getRequestOptions())
//                .into(imageView);
//    }

    /**
     * 显示图片
     *
     * @param context   上下文
     * @param imageView 图片view
     * @param url       图片路径
     */
    public static void displayImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().dontTransition())
                .apply(getRequestOptions())
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param context   上下文
     * @param imageView 图片view
     * @param url       图片路径
     */
    public static void displayAvatar(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(context.getResources().getDrawable(R.drawable.round_white))
                .error(context.getResources().getDrawable(R.drawable.round_white))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .apply(getRequestOptionsAvatar(context))
                .into(imageView);
    }

    public static void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

    public static void displayImage(final Context context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).asBitmap().load(url).into(simpleTarget);
    }

    public static void displayImage(Context context, String url, int resourceId, ImageView targetImageView) {
        //如果传进来的参数空了
        Glide.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().dontTransition())
                .apply(getRequestInOptions(resourceId))
                .into(targetImageView);
    }

    public static RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.centerCrop();//图片显示类型
        //options.placeholder(AVATAR_IMAGE);//加载中图片
        //options.error(ERROR_IMAGE);//加载错误的图片
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        //        options.error(new_message2 ColorDrawable(Color.RED));//或者是个颜色值
        //        options.priority(Priority.HIGH);//设置请求优先级
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        //        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);//仅缓存原图片
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);//全部缓存
        //        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);缓存缩略过的
        //        options.onlyRetrieveFromCache(true);//仅从缓存加载
        //        options.skipMemoryCache(true);//禁用缓存,包括内存和磁盘
        //        options.diskCacheStrategy(DiskCacheStrategy.NONE);仅跳过磁盘缓存
        //        options.override(200,200);加载固定大小的图片
        //        options.dontTransform();不做渐入渐出的转换
        //        options.transition(new_message2 DrawableTransitionOptions().dontTransition());//同上
        //        options.circleCrop();设置成圆形头像<这个是V4.0新增的>
        //        options.transform(new_message2 RoundedCorners(10));设置成圆角头像<这个是V4.0新增的>

        return options;
    }

    public static RequestOptions getRequestInOptions(int resourceId) {
        RequestOptions options = new RequestOptions();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.centerCrop();//图片显示类型
        options.placeholder(resourceId);//加载中图片
        options.error(resourceId);//加载错误的图片
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        //        options.error(new_message2 ColorDrawable(Color.RED));//或者是个颜色值
        //        options.priority(Priority.HIGH);//设置请求优先级
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        //        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);//仅缓存原图片
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);//全部缓存
        //        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);缓存缩略过的
        //        options.onlyRetrieveFromCache(true);//仅从缓存加载
        //        options.skipMemoryCache(true);//禁用缓存,包括内存和磁盘
        //        options.diskCacheStrategy(DiskCacheStrategy.NONE);仅跳过磁盘缓存
        //        options.override(200,200);加载固定大小的图片
        //        options.dontTransform();不做渐入渐出的转换
        //        options.transition(new_message2 DrawableTransitionOptions().dontTransition());//同上
        //        options.circleCrop();设置成圆形头像<这个是V4.0新增的>
        //        options.transform(new_message2 RoundedCorners(10));设置成圆角头像<这个是V4.0新增的>

        return options;
    }

    public static RequestOptions getRequestOptionsAvatar(Context context) {
        RequestOptions options = new RequestOptions();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.centerCrop();//图片显示类型
        options.placeholder(context.getResources().getDrawable(android.R.color.white));//加载中图片
        options.error(context.getResources().getDrawable(android.R.color.white));//加载错误的图片
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        //        options.error(new_message2 ColorDrawable(Color.RED));//或者是个颜色值
        //        options.priority(Priority.HIGH);//设置请求优先级
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        //        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);//仅缓存原图片
        //        options.diskCacheStrategy(DiskCacheStrategy.ALL);//全部缓存
        //        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);缓存缩略过的
        //        options.onlyRetrieveFromCache(true);//仅从缓存加载
        //        options.skipMemoryCache(true);//禁用缓存,包括内存和磁盘
        //        options.diskCacheStrategy(DiskCacheStrategy.NONE);仅跳过磁盘缓存
        //        options.override(200,200);加载固定大小的图片
        //        options.dontTransform();不做渐入渐出的转换
        //        options.transition(new_message2 DrawableTransitionOptions().dontTransition());//同上
                options.circleCrop();//设置成圆形头像<这个是V4.0新增的>
        //        options.transform(new_message2 RoundedCorners(10));设置成圆角头像<这个是V4.0新增的>

        return options;
    }

    /**
     * 把batmap 转file
     *
     * @param bitmap
     * @param filepath
     */
    public static File BitmapToFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
