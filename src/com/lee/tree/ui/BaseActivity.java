package com.lee.tree.ui;


import com.lee.treehole.MyApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/**
 * 基类
 * @author Lee
 *
 */
public class BaseActivity extends FragmentActivity{
@Override
protected void onCreate(Bundle arg0) {
	// TODO Auto-generated method stub
	super.onCreate(arg0);
	MyApplication.activities.add(this);
}
/**
 * 
 * @param cla 目标activity
 */
public void startActivity(Class<?> cla) {
	this.startActivity(new Intent(this, cla));
}


}
