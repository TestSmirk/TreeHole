package com.lee.tree.ui;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.lee.tree.adapter.MainPagerAdapter;
import com.lee.tree.fragment.ForestFragment;
import com.lee.tree.fragment.MyHoleFragment;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Color;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	// private boolean isopen= false;
	private DisplayMetrics dm;
	private PagerSlidingTabStrip tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		initTabs();
		initFAB();
	}

	/**
	 * ��ʼ��floatActionBttton
	 */
	private void initFAB() {
		final ImageView fabIconNew = new ImageView(this);
		fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
		final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this).setContentView(fabIconNew)
				.build();
		rightLowerButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				startActivity(EditActivity.class);
				// if(isopen){
				// fabIconNew.setRotation(45);
				// PropertyValuesHolder pvhR =
				// PropertyValuesHolder.ofFloat(View.ROTATION, 0);
				// ObjectAnimator animation =
				// ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				// animation.start();
				//
				//
				// isopen=false;
				//
				//
				// }else{
				// fabIconNew.setRotation(0);
				// PropertyValuesHolder pvhR =
				// PropertyValuesHolder.ofFloat(View.ROTATION, 45);
				// ObjectAnimator animation =
				// ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				// animation.start();
				// isopen=true;
				// startActivity(EditActivity.class);
				// }

			}
		});
	}

	/**
	 * ��ʼ��Tabs
	 */
	private void initTabs() {

		dm = getResources().getDisplayMetrics();
		tabs = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);

		ViewPager pager = (ViewPager) findViewById(R.id.main_pager);
		MainPagerAdapter mAdapter = new MainPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(mAdapter);
		tabs.setViewPager(pager);
		setTabsValue();

		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new ForestFragment());
		list.add(new MyHoleFragment());
		mAdapter.setFragment(list);

	}

	/** 
	*/
	private void setTabsValue() {
		tabs.setShouldExpand(true);
		tabs.setDividerColor(Color.GRAY);
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, dm));
		tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		tabs.setTabBackground(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(SettingsActivity.class);
			break;
		case R.id.about:
			Toast.makeText(getApplicationContext(), "this is a about", 0).show();

			break;

		}
		return super.onOptionsItemSelected(item);
	}

}
