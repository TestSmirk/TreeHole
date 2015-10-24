package com.lee.tree.util;


import android.util.Log;

/**
 * 日志要统一处理。
 * 
 * @author tarena
 * 
 */
public class LogUtil {

	public static void i(String tag, Object msg) {
		if (1==2) {
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}

}
