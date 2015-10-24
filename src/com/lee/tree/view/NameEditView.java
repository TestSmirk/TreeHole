package com.lee.tree.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class NameEditView extends ViewGroup {

	public NameEditView(Context context,AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	//������ʾһ���ؼ�
	public void showChild(int index) {
		if (index == 0) {
			this.getChildAt(0).setVisibility(View.VISIBLE);
			this.getChildAt(1).setVisibility(View.GONE);
		}
		if (index == 1) {
			this.getChildAt(1).setVisibility(View.VISIBLE);
			this.getChildAt(0).setVisibility(View.GONE);
		}
	}
	//����
    @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	int childCount = this.getChildCount();
	for (int i=0;i<childCount;i++)
	{
		View view=this.getChildAt(i);
		//�����ӿؼ�
		view.measure(widthMeasureSpec, heightMeasureSpec);
	}
 }
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//framlayout
//		View viewName=this.getChildAt(0);
//		viewName.layout(0, 0, viewName.getMeasuredWidth(), viewName.getMeasuredHeight());
//		
//		View viewEdit=this.getChildAt(1);
//		viewEdit.layout(0, 0, viewEdit.getMeasuredWidth(), viewEdit.getMeasuredHeight());
		int left = 0;
		for (int i = 0; i < this.getChildCount(); i++) {
			View view = this.getChildAt(i);
			if (view.getVisibility() == View.VISIBLE) {

				view.layout(left, 0, left + view.getMeasuredWidth(),
						view.getMeasuredHeight());
				left = left + view.getMeasuredWidth();
			}
		}
	}

}
