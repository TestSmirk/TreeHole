package com.lee.tree.util;

public class FailUtil {
	public static String getFaultByCode(int code) {
		String fault = null;
		if (code == 9001) {
			LogUtil.i("fault:", "Application IdΪd is wrong");
		}
		if (code == 9002) {
			LogUtil.i("fault:", "9002");

		}
		if (code == 9003) {
			LogUtil.i("fault:", "9003");
		}
		if (code == 9004) {
			LogUtil.i("fault:", "9004");

		}
		if (code == 9006) {
			LogUtil.i("fault:", "objectIdΪ");

		}
		if (code == 9007) {
			LogUtil.i("fault:", "9007 10M");
		}
		if (code == 9008) {
			LogUtil.i("fault:", "9008");
		}
		if (code == 9009) {
			LogUtil.i("fault:", "9009");
			return fault = "9009";
		}
		if (code == 9010) {
			LogUtil.i("fault:", "9009");
			return fault = "9010T_T";
		}
		if (code == 9011) {
			LogUtil.i("fault:", "BmobUser9011");
		}
		if (code == 9012) {
			LogUtil.i("fault:", "9012");
		}
		if (code == 9013) {
			LogUtil.i("fault:", "BmobObjec9013");
		}
		if (code == 9014) {
			LogUtil.i("fault:", "9014");
		}
		if (code == 9015) {
			LogUtil.i("fault:", "9014");
		}
		if (code == 9016) {
			LogUtil.i("fault:", "9016");
			return fault = "9016";
		}
		if (code == 9017) {
			LogUtil.i("fault:", "9017");
		}
		if (code == 9019) {
			LogUtil.i("fault:", "9019");
			return fault = "9019";
		}
		if (code == 202) {
			return fault = "202";
		}
		if (code == 101) {
			return fault = "101(>�n<)o";
		}
		fault = "101" + code;
		return fault;
	}
}
