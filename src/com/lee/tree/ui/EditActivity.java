package com.lee.tree.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.lee.tree.entity.MyUser;
import com.lee.tree.entity.Word;
import com.lee.tree.util.FailUtil;
import com.lee.tree.util.UiTools;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditActivity extends BaseActivity implements OnClickListener {

	private EditText et_edit;
	private TextView tv_textNumber;
	private Button bt_submit;
	private String content;
	private TextWatcher tw_edit;
	private Spinner sp_spinner;
	private ArrayAdapter<String> spAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		setView();
	}

	private void setView() {
		et_edit = (EditText) findViewById(R.id.et_edit_edit);
		tv_textNumber = (TextView) findViewById(R.id.tv_edit_textNumber);
		bt_submit = (Button) findViewById(R.id.bt_edit_submit);
		sp_spinner = (Spinner) findViewById(R.id.sp_edit_spinner);
		bt_submit.setOnClickListener(this);
		setWatcher();
		et_edit.addTextChangedListener(tw_edit);
		setspAdapter();
	}

	/**
	 */
	boolean isShare = false;

	private void setspAdapter() {
		List<String> list = new ArrayList<String>();
		list.add("One");
		list.add("Two");
		spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		// spAdapter.setDropDownViewResource(R.drawable.spinner_item);
		sp_spinner.setAdapter(spAdapter);

		sp_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					isShare = false;
				} else {
					isShare = true;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	/**
	 * �ı�����
	 */
	int textLength = 0;

	private void setWatcher() {
		tw_edit = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				textLength = s.length();
				tv_textNumber.setText(textLength + "/200");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				textLength = s.length();
				tv_textNumber.setText(textLength + "/200");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				textLength = s.length();
				tv_textNumber.setText(textLength + "/200");
				// if(textLength>200){
				// tv_textNumber.setTextColor(Color.RED);
				// }else{
				// tv_textNumber.setTextColor(Color.BLACK);
				// }
			}
		};
	}

	/**
	 * �ύ
	 */
	@Override
	public void onClick(View v) {

		content = et_edit.getText().toString();
		// TODO �ж�
		if (TextUtils.isEmpty(content)) {
			UiTools.showToast("Empty Text", this);
			return;
		}
		if (textLength > 200) {
			UiTools.showToast("太长啦", this);
			return;
		}
		sendWord();

	}

	/**
	 * ��������
	 */
	private void sendWord() {
		MyUser user = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
		Word word = new Word();
		word.setContent(content);
		word.setAuther(user);
		word.setIsShare(isShare);

		UiTools.showProgressDialog(this, "分享中", false);
		bt_submit.setEnabled(false);
		word.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				UiTools.showToast("成功", EditActivity.this);
				runOnUiThread(new Runnable() {
					public void run() {
						UiTools.closeProgressDialog();
						finish();
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				UiTools.closeProgressDialog();
				UiTools.showToast(FailUtil.getFaultByCode(arg0), EditActivity.this);

				bt_submit.setEnabled(true);
			}
		});
	}

}
