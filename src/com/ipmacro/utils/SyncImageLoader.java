/**
 * 异步加载图片类
 */
package com.ipmacro.utils;

import java.io.File;


import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import android.widget.ImageView;

public class SyncImageLoader
{

	ImageLoader imageLoader;
	DisplayImageOptions options;
	ImageLoaderConfiguration config;
	
	public SyncImageLoader(Context context)
	{
		File cacheDir = StorageUtils.getCacheDirectory(context);
		config = new ImageLoaderConfiguration.Builder(context)
        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
        .discCacheExtraOptions(480, 800, CompressFormat.PNG, 80, null)
        .threadPoolSize(5) // default
        .threadPriority(Thread.NORM_PRIORITY - 1) // default
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
        .memoryCacheSize(10 * 1024 * 1024)
        .memoryCacheSizePercentage(13) // default
        .discCache(new TotalSizeLimitedDiscCache(cacheDir,20 * 1024 * 1024)) // default 
        //.discCacheSize(50 * 1024 * 1024)
        //.discCacheFileCount(5)
        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new BaseImageDownloader(context)) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .writeDebugLogs()
        .build();


		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true) 
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}
	

	public void displayImage(ImageView imgView, String url)
	{
		imageLoader.displayImage(url, imgView,options); 
	}

	
}
