package com.lee.tree.view;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Line  extends View{

	public Line(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	Paint paint = new Paint();
	@SuppressLint("ResourceAsColor")
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		paint.setColor(0xff45c01a);
	
		canvas.drawLine(0, getHeight()-12, getWidth(), getHeight()-12, paint);
		
	}

}
