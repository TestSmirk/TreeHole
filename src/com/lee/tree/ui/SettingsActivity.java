package com.lee.tree.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lee.tree.entity.MyUser;
import com.lee.tree.util.CacheUtils;
import com.lee.tree.util.Consts;
import com.lee.tree.util.ExceptionUtil;
import com.lee.tree.util.FailUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.UiTools;
import com.lee.tree.view.NameEditView;
import com.lee.treehole.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity  implements OnClickListener {

	private ImageView iv_userLogoSet;
	private Button bt_quit;
	private Button bt_aboutMe;
	private ImageView iv_userLogo;
	private NameEditView NameEditView;
	private Button bt_submit;
	private EditText et_text;
	private ImageView iv_EditName;
	private MyUser currentUser ;
	private TextView tv_username;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setView();
		setListener();
		setUserInfo();
	}


	private void setView() {
		iv_userLogo=(ImageView)findViewById(R.id.settings_userLogo);
		   iv_userLogoSet= (ImageView)findViewById(R.id.settings_userLogo_set);
		 bt_quit= (Button)findViewById(R.id.settings_quit);
		 bt_aboutMe= (Button)findViewById(R.id.settings_aboutMe);
		 //自定义ViewGroup
		 NameEditView=(NameEditView)findViewById(R.id.settings_NameEditView);
		 bt_submit=(Button)findViewById(R.id.bt_EditName_submit);//修改后提交
		 et_text=(EditText)findViewById(R.id.et_EditName_text);//输入框
		 iv_EditName=(ImageView)findViewById(R.id.iv_EditName);//修改名字 按钮
		 tv_username=(TextView)findViewById(R.id.tv_settings_username);
		 
		 
		
	}
	//TODO  缓存  --> 网络
	/**
	 * 加载用户信息
	 */
	private void setUserInfo() {
		try {
			
			 if(currentUser==null){
				 currentUser= BmobUser.getCurrentUser(getApplicationContext(),
							MyUser.class);
				  
			 }
			 String username = currentUser.getUsername();	
			 tv_username.setText(username);
			 
			 BmobFile picFile = currentUser.getPic();
			 if(picFile!=null){
				 ImageLoader.getInstance().displayImage(picFile.getFileUrl(getApplicationContext()),//图片url
						 iv_userLogo, MyApplication.instance.getOptions(
									R.drawable.default_head));
				 LogUtil.i("URL", picFile.getFileUrl(getApplicationContext()));
			 }
			
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
			}
		
	}


	private void setListener() {
		iv_userLogoSet.setOnClickListener(this);
		bt_quit.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		iv_EditName.setOnClickListener(this);
		bt_aboutMe.setOnClickListener(this);
	}

	


	@Override
	public void onClick(View v) {
		try {
			//登出
			if(v==bt_quit){
				//退出当前登录用户
				BmobUser.logOut(this); 
				//退出成功 跳转到登录界面
				if( BmobUser.getCurrentUser(this)==null){
					MyApplication.instance.exit();
					startActivity(LoginActivity.class);
				}
			}
			//换头像
			if(v==iv_userLogoSet){
				//打开系统相册
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, Consts.OPEN_ALBUM_REQUESTCODE);
			}
			
			//关于
			if(v==bt_aboutMe){
				showDailog();
			}
			//ViewGroup  修改名字
			//显示输入
			if(v==iv_EditName){
				
				NameEditView.showChild(1);
			}
			//提交修改 显示名字
			if(v==bt_submit){
				updateName();
			
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
	
	}
	/**
	 * 改名字
	 */
	private void updateName() {
		final String newName = et_text.getText().toString();
		if(TextUtils.isEmpty(newName)){
			NameEditView.showChild(0);
		}else {
			try {
				currentUser.setUsername(newName);
				UiTools.showProgressDialog(SettingsActivity.this, "", true);
				currentUser.update(getApplicationContext(), new UpdateListener() {
					@Override
					public void onSuccess() {
						UiTools.closeProgressDialog();
						NameEditView.showChild(0);
						tv_username.setText(newName);
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						UiTools.closeProgressDialog();
					UiTools.showToast(FailUtil.getFaultByCode(arg0), SettingsActivity.this);
					}
				});
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
				UiTools.showToast("森林潮湿..", SettingsActivity.this);
			}
			
		}		
	}


	/**
	 * 关于
	 */
	private void showDailog() {
		AlertDialog.Builder dialog = new Builder(SettingsActivity.this);
		LayoutInflater inflater = LayoutInflater.from(this);  
        View view = inflater.inflate(R.layout.about_dailog, null);  
		dialog.setView(view);
		dialog.show();
	}
	String dateTime ;
	private String iconUrl;
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
	     //传过来的图片进行剪切
		case Consts.OPEN_ALBUM_REQUESTCODE:
			if (data == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
			//  剪切成功  缓存 上传图片
         case Consts.OPEN_PHOTO_ZOOM_REQUESTCODE:
			if(data!=null){
				Bundle extras = data.getExtras();
				if(extras!=null){
					dateTime=System.currentTimeMillis()+"";
					Bitmap bitmap = extras.getParcelable("data");
					iconUrl = saveToSdCard(bitmap);
					iv_userLogo.setImageBitmap(bitmap);
					updateIcon(iconUrl);
				}
			}
			
			break;
		}
		
	}
/**
 * 图片上传到服务器
 * @param iconUrl2
 */
private void updateIcon(String iconUrl) {
	try {
		if(iconUrl!=null){
			final BmobFile file = new BmobFile(new File(iconUrl));
			UiTools.showProgressDialog(SettingsActivity.this, "拼命上传..", false);
			file.upload(getApplicationContext(), new UploadFileListener() {
				
				

				//上传成功
				@Override
				public void onSuccess() {
					if(currentUser==null){
						 currentUser= BmobUser.getCurrentUser(getApplicationContext(),
									MyUser.class);
						    
					 }
					 
					 currentUser.setPic(file);
					 currentUser.update(getApplicationContext(), new UpdateListener() {
						
						@Override
						public void onSuccess() {
							UiTools.closeProgressDialog();
							UiTools.showToast("上传成功", SettingsActivity.this);							
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							UiTools.closeProgressDialog();

							UiTools.showToast("更新失败,请检查网络", SettingsActivity.this);							
						}
					});
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					UiTools.closeProgressDialog();

					UiTools.showToast("更新失败,请检查网络", SettingsActivity.this);							

				}
			});
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	}


/**
 * 打开剪切图片的ACTIVITY
 * @param data
 */
	private void startPhotoZoom(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 120);
		intent.putExtra("outputY", 120);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, Consts.OPEN_PHOTO_ZOOM_REQUESTCODE);
	}
	/**
	 * 缓存到SDCard
	 * @param bitmap
	 * @return
	 */
	public String saveToSdCard(Bitmap bitmap) {
		String files =CacheUtils.getCacheDirectory(getApplicationContext(), true, "icon")
				+ dateTime + "_12.jpg";
		File file = new File(files);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogUtil.i("tag", file.getAbsolutePath());
		
		return file.getAbsolutePath();
	}

}
