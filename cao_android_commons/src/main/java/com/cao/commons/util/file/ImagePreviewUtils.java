package com.cao.commons.util.file;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

/**
 * 跳转查看大图
 *
 * @author yc
 * @Time 2018/12/5
 */

public class ImagePreviewUtils {
    public static void startIntentImgPreview(Context context,List<String> mImageList){
        final List<ImageInfo> imageInfoList=new ArrayList<>();
        if(null!=mImageList&&mImageList.size()>0){
            for(int i=0;i<mImageList.size();i++){
                ImageInfo imageInfo = new ImageInfo();
                // 原图地址（必填）
                imageInfo.setOriginUrl(mImageList.get(i));
                imageInfo.setThumbnailUrl(mImageList.get(i));
                imageInfoList.add(imageInfo);
                imageInfo = null;
            }
        }
        ImagePreview.getInstance().setContext(context).setIndex(1).setImageInfoList(imageInfoList).setEnableDragClose(true).start();
    }
}
