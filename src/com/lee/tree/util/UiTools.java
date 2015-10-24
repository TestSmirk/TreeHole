package com.lee.tree.util;

import com.lee.tree.ui.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UiTools {

	private static ProgressDialog progressDialog;

	/**
	 * ������
	 * 
	 * @param activity
	 * @param msg
	 * @param isCancel
	 */
	public static void showProgressDialog(Activity activity, String msg, boolean isCancel) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(isCancel);
			progressDialog.show();
		}
	}

	/**
	 * �ر���ʾ��
	 */
	public static void closeProgressDialog() {

		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
		}
	}

	/**
	 * Toast
	 * 
	 * @param obj
	 * @param activity
	 */
	public static void showToast(Object obj, Activity activity) {
		if (activity != null) {
			if (!TextUtils.isEmpty("" + obj)) {

				Toast toast = new Toast(activity);
				toast.setDuration(toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				View view = View.inflate(activity, R.layout.toast_view, null);
				TextView text = (TextView) view.findViewById(R.id.toast_text);
				text.setText(obj + "");
				toast.setView(view);
				toast.show();

			}
		}

	}

}
