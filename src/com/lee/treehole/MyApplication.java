package com.lee.treehole;

import java.io.File;
import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {
	public static ArrayList<Activity> activities = new ArrayList<Activity>();
	public static MyApplication instance = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		initImageLoader();
	}

	/**
	 * ��ʼ��imageLoader
	 */
	public void initImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024)).memoryCacheSize(10 * 1024 * 1024)
				.discCache(new UnlimitedDiscCache(cacheDir)).discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.build();
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getOptions(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId).showImageForEmptyUri(drawableId)
				.showImageOnFail(drawableId).resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	/**
	 * ��ȫ�˳�
	 */
	public void exit() {
		for (Activity activity : activities) {
			try {
				activity.finish();

			} catch (Exception e) {
			}

		}
	}
}
