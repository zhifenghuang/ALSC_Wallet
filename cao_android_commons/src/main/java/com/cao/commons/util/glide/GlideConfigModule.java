package com.cao.commons.util.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.load.engine.cache.LruResourceCache;
//import com.bumptech.glide.module.AppGlideModule;

/**
 * Glide Module扩展配置
 */
@GlideModule
public class GlideConfigModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
