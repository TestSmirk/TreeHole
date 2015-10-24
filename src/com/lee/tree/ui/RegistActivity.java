package com.lee.tree.ui;

import cn.bmob.v3.listener.SaveListener;

import com.lee.tree.entity.MyUser;
import com.lee.tree.util.FailUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.NetworkUtil;
import com.lee.tree.util.UiTools;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RegistActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etAffirmword;
	private Button btSubmit;
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		setView();
	}

	private void setView() {
		etUsername = (EditText) findViewById(R.id.et_regist_username);
		etPassword = (EditText) findViewById(R.id.et_regist_password);
		etAffirmword = (EditText) findViewById(R.id.et_regist_affirmword);
		btSubmit = (Button) findViewById(R.id.bt_regist_submit);
		rg = (RadioGroup) findViewById(R.id.regist_radioGroup);
		btSubmit.setOnClickListener(this);
		rg.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btSubmit) {
			regist();
		}
	}

	/**
	 * radioGroup
	 */
	String gender = null;

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rd = (RadioButton) findViewById(checkedId);
		gender = rd.getText() + "";
	}

	/**
	 * ע��
	 */
	private void regist() {

		String name = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		String affirmword = etAffirmword.getText().toString();
		// TODO����ƥ��
		// �ǳ�==�û���
		if (TextUtils.isEmpty(name)) {
			UiTools.showToast("空", this);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			UiTools.showToast("test", this);
			return;
		}
		if (!affirmword.equals(password)) {
			UiTools.showToast("test", this);
			return;
		}
		if (gender == null) {
			UiTools.showToast("test", this);
		}
		boolean isNetConnected = NetworkUtil.isNetworkAvailable(this);
		if (!isNetConnected) {
			UiTools.showToast("没有网络连接哦", RegistActivity.this);
			return;
		}
		UiTools.showProgressDialog(RegistActivity.this, "", false);
		MyUser myUser = new MyUser();
		myUser.setUsername(name);
		myUser.setPassword(password);
		LogUtil.i("dfdde", gender);
		myUser.setGender(gender);
		myUser.signUp(this, new SaveListener() {
			@Override
			public void onSuccess() {
				UiTools.closeProgressDialog();
				startActivity(WelcomeMainActivity.class);
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				UiTools.closeProgressDialog();
				UiTools.showToast(FailUtil.getFaultByCode(code), RegistActivity.this);
				Log.i("tag", msg);
			}
		});
	}

}
