package com.lee.tree.ui;

import com.lee.tree.util.FailUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.UiTools;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private BmobUser bu;
	private EditText etUsername;
	private EditText etPassword;
	private Button btSubmit;
	private TextView tvRegist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setView();

	}

	private void setView() {
		etUsername = (EditText) findViewById(R.id.et_login_username);
		etPassword = (EditText) findViewById(R.id.et_login_password);
		btSubmit = (Button) findViewById(R.id.bt_login_submit);
		tvRegist = (TextView) findViewById(R.id.tv_login_regist);
		btSubmit.setOnClickListener(this);
		tvRegist.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_login_submit) {
			login();
		}
		if (v.getId() == R.id.tv_login_regist) {
			startActivity(RegistActivity.class);

		}
	}

	/**
	 * ��¼
	 */
	private void login() {
		String name = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		if (TextUtils.isEmpty(name)) {
			UiTools.showToast("username is null", this);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			UiTools.showToast("password is null", this);
			return;
		}

		UiTools.showProgressDialog(LoginActivity.this, "", false);
		// Bmob ��¼
		bu = new BmobUser();
		bu.setUsername(name);
		bu.setPassword(password);
		bu.login(this, new SaveListener() {
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					public void run() {
						UiTools.closeProgressDialog();
						startActivity(MainActivity.class);
						finish();
					}
				});

			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO �жϴ������͸�����ʾ ������
				UiTools.closeProgressDialog();
				UiTools.showToast(FailUtil.getFaultByCode(code), LoginActivity.this);
				LogUtil.i("LOGIN..", code + msg);
			}
		});
		// ȡ��������
		// progress.dismiss();

	}

}
