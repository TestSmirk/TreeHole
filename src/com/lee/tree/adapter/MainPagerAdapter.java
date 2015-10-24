package com.lee.tree.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter  extends FragmentPagerAdapter {
  private List<Fragment> list;
 public void setFragment(List<Fragment> list){
	 this.list=list;
 }
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	private final String[] titles = { "Title1", "Title2"};
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

}
