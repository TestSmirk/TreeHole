package com.lee.tree.ui;

import com.lee.tree.util.Config;
import com.lee.tree.util.ExceptionUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class WelcomeMainActivity extends BaseActivity {
	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;
	private int screenWidth;
	private  int screenHeight;
	
	private ImageView iv;
	private ScaleAnimation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//setBackgroundSize();
		//setAnim();
		initBmob();
		next();
//		  WindowManager windowManager = getWindowManager();
//		  Display display = windowManager.getDefaultDisplay();
//		  screenWidth = display.getWidth();
//		  screenHeight = display.getHeight();
	}
	
  

//private void setBackgroundSize() {
//	  try {
//		  WindowManager windowManager = getWindowManager();
//		  Display display = windowManager.getDefaultDisplay();
//		  screenWidth = display.getWidth();
//		  screenHeight = display.getHeight();
//		   iv = (ImageView)findViewById(R.id.ic_bg);
//		  if(screenWidth!=0 && screenHeight!=0){
//			  iv.measure(screenWidth, screenHeight);
//		  }
//	} catch (Exception e) {
//		ExceptionUtil.handleException(e);
//	}
//	
//	}

//private void setAnim() {
//	 iv = (ImageView)findViewById(R.id.ic_bg);
//	   animation =new ScaleAnimation(fromX, toX, fromY, toY, pivotX, pivotY)
//	  animation.setDuration(2000);
//	  iv.setAnimation(animation);
//	  animation.startNow();
//	}

/**
   * 初始化Bmob
   */
	private void initBmob() {
		//
		Bmob.initialize(this, Config.BMOB_SDK_KEY);
		
	}

	/**
	 * 判断用户的登陆状态 跳转到主页面或者登录界面
	 */
	private void next() {
		if (BmobUser.getCurrentUser(this) != null) {
		//如果不是第一次使用跳到主界面
			
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			//如果是第一次进入  调到登陆界面
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}
/**
 * 发消息 跳转页面
 */
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startActivity(MainActivity.class);
				finish();
				break;
			case GO_LOGIN:
				startActivity(LoginActivity.class);
				finish();
				break;
			}
		}
	};
}
