package com.haoshi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haoshi.R;
import com.haoshi.view.GlideCircleTransform;
import com.haoshi.view.GlideRoundTransform;

import java.io.File;

/**
 * @author Yang Shihao
 *         <p>
 *         Glide图片管理类
 */

public class ImageManager {

    public static void loadImageObject(Context context, Object url, ImageView iv) {
        if (url instanceof Bitmap) {
            loadImage(context, (Bitmap) url, iv);
        } else {
            Glide.with(context).load(url).crossFade().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(iv);
        }
    }

    public static void loadImageObject(Context context, Object url, int emptyImg, int errorImg, ImageView iv) {
        if (url instanceof Bitmap) {
            loadImage(context, (Bitmap) url, errorImg, emptyImg, iv);
        } else {
            Glide.with(context).load(url).crossFade().placeholder(emptyImg).error(errorImg).into(iv);
        }
    }

    /**
     * 加载网络图片
     */
    public static void loadImage(Context context, String url, int errorImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(url).crossFade().placeholder(emptyImg).error(errorImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(iv);
    }

    /**
     * 加载本地图片
     */
    public static void loadImage(Context context, File file, int errorImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(file).crossFade().placeholder(emptyImg).error(errorImg).into(iv);
    }

    public static void loadImage(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).crossFade().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(imageView);
    }

    /**
     * 加载资源图片
     */
    public static void loadImage(Context context, int resourceId, int errorImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(resourceId).placeholder(emptyImg).error(errorImg).into(iv);
    }

    public static void loadImage(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(imageView);
    }

    /**
     * 加载Bitmap
     */
    private static void loadImage(Context context, Bitmap bitmap, int errorImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(bitmap).asBitmap().placeholder(emptyImg).error(errorImg).into(iv);
    }

    private static void loadImage(Context context, Bitmap bitmap, ImageView iv) {
        Glide.with(context).load(bitmap).asBitmap().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(iv);
    }

    /**
     * 加载Gif图片
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(iv);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).transform(new GlideCircleTransform(context)).into(iv);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).transform(new GlideRoundTransform(context, 10)).into(iv);
    }

    /**
     * 释放内存
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除所有缓存
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
