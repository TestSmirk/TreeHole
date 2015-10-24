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
   * ��ʼ��Bmob
   */
	private void initBmob() {
		//
		Bmob.initialize(this, Config.BMOB_SDK_KEY);
		
	}

	/**
	 * �ж��û��ĵ�½״̬ ��ת����ҳ����ߵ�¼����
	 */
	private void next() {
		if (BmobUser.getCurrentUser(this) != null) {
		//������ǵ�һ��ʹ������������
			
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			//����ǵ�һ�ν���  ������½����
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}
/**
 * ����Ϣ ��תҳ��
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
