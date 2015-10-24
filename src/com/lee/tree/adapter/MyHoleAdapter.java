package com.lee.tree.adapter;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lee.tree.entity.Word;
import com.lee.tree.ui.R;
import com.lee.tree.ui.SettingsActivity;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.UiTools;
/**
 * myhole  ≈‰∆˜
 * @author Lee
 *
 */
public class MyHoleAdapter extends BaseAdapter {
	private Context context;
	private List<Word> words;
public MyHoleAdapter (Context context , List<Word> words){
	this.context=context;
	if(words==null){
		this.words=new ArrayList<Word>();
	}else{
		this.words=words;
	}
}
	@Override
	public int getCount() {
		return words.size();
	}

	@Override
	public Object getItem(int position) {
		
		return words.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.myhole_item, null);
			viewHolder= new ViewHolder();
			viewHolder.myhole_word_content=(TextView)convertView.findViewById(R.id.tv_myhole_content);
			viewHolder.myhole_word_time=(TextView)convertView.findViewById(R.id.tv_myhole_time);
		
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		      Word word = words.get(position);
		      viewHolder.myhole_word_content.setText(word.getContent());
		      viewHolder.myhole_word_time.setText(word.getUpdatedAt());
//		      //ÃÌº”
//		      viewHolder.myhole_word_add.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					v.setRotation(45);
//	              PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
//	              ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(v, pvhR);
//	              animation.start();
//	              showOptionDailog();
//                  }
//
//				
//			} );
		      
		return convertView;
		
	}
//	private void showOptionDailog() {
//		AlertDialog.Builder dialog = new Builder(context);
//
//        View view = View.inflate(context, R.layout.myhole_option_dailog, null);  
//		dialog.setView(view);
//		dialog.show();
//		
//	}
class ViewHolder {
	TextView myhole_word_time;
	TextView myhole_word_content;
	ImageView myhole_word_add;
	
}
}
