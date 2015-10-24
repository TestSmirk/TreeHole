package com.lee.tree.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;

import com.lee.tree.adapter.MyHoleAdapter.ViewHolder;
import com.lee.tree.entity.MyUser;
import com.lee.tree.entity.Word;
import com.lee.tree.ui.R;
import com.lee.tree.util.ExceptionUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.TimeUtil;
import com.lee.treehole.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ForestAdapter extends BaseAdapter {
private Context context;
private List<Word> words;
public ForestAdapter(Context context,List<Word> words){
	this.context=context;
	if(words==null){
		this.words=new ArrayList<Word>();
	}else{
		this.words=words;
	}
}
	@Override
	public int getCount() {
		return words==null?0:words.size();
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
			convertView=View.inflate(context, R.layout.forest_item, null);
			viewHolder= new ViewHolder();
			viewHolder.ivUserLogo=(ImageView)convertView.findViewById(R.id.forest_userLogo);
			viewHolder.tvUserName=(TextView)convertView.findViewById(R.id.forest_userName);
			viewHolder.tvTime=(TextView)convertView.findViewById(R.id.forest_Time);
			viewHolder.tvContent=(TextView)convertView.findViewById(R.id.forest_userContent);
		
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
			
		}
		
		try {
			
			Word word = words.get(position);
			   MyUser auterUser = word.getAuther();
				  LogUtil.i("wordObjectId", auterUser.getObjectId());
				  //转成描述性时间
				String miaoshuTime = TimeUtil.getDescriptionTimeFromTimestamp(TimeUtil.stringToLong(word.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"));
				viewHolder.tvContent.setText(word.getContent());//内容
				viewHolder.tvTime.setText(miaoshuTime);//时间
				viewHolder.tvUserName.setText(auterUser.getUsername());//作者
				 BmobFile picFile = auterUser.getPic();
				
				 if(picFile!=null){
					 ImageLoader.getInstance().displayImage(
				picFile.getFileUrl(context),
							 viewHolder.ivUserLogo, MyApplication.instance.getOptions(
										R.drawable.default_head));
					 LogUtil.i("URLp", picFile.getFileUrl(context));

				 }else {
					 viewHolder.ivUserLogo.setImageResource(R.drawable.default_head);
				 }
			
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
		return convertView;
	}
	class ViewHolder {
		ImageView ivUserLogo;
		TextView tvUserName;
		TextView tvTime;
		TextView tvContent;
	}
	
	
	public static Bitmap getLogoByUser(int auterUser){
		Bitmap bitmap = null;
		
		return bitmap;
	}
}
