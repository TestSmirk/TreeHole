package com.lee.tree.biz;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.UserDictionary.Words;

import com.lee.tree.entity.MyUser;
import com.lee.tree.entity.Word;
import com.lee.tree.util.Consts;
import com.lee.tree.util.FailUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.NetworkUtil;
import com.lee.tree.util.UiTools;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class GetContentBiz {
	private BmobQuery<Word> myWord = new BmobQuery<Word>();
	public void getMyContentByCloud(final Activity activity,
			final Handler handler) {
	
		// 获得当前登录用户的ID
		MyUser myUser = BmobUser.getCurrentUser(activity, MyUser.class);
		String id = myUser.getObjectId();
		// 查询自己发表的内容
		myWord.addWhereEqualTo("auther", id);
		myWord.order("-updatedAt");// 按修改时间降序查询
		
		// myWord.setLimit(newLimit); 查询数据条数 分页加载
        //如果是移动网络 设置缓存时间为一天 否则默认为5个小时
		if(NetworkUtil.isMobile(activity)){
			myWord.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));
		}
		// 判断是否有缓存  
		boolean isCach = myWord.hasCachedResult(activity, Word.class);
		if (isCach) {
			// 先从缓存取数据，无论结果如何都会再次从网络获取数据
			myWord.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		} else {
			// 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
			myWord.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
		}
		// 查询
		myWord.findObjects(activity, new FindListener<Word>() {
			@Override
			public void onSuccess(List<Word> arg0) {

				// String time = arg0.get(0).getUpdatedAt(); 格式成描述性时间
				// LogUtil.i("TIME",TimeUtil. ( TimeUtil.stringToLong(time,
				// "yyyy-MM-dd HH:mm:ss")) );
				// 服务器获得得数据发给 fragment 更新listView
				if (arg0.size() != 0) {
					Message msg = new Message();
					msg.what = Consts.LOAD_MY_HOLE_CONTENT_SUCCESS;
					msg.obj = arg0;
					handler.handleMessage(msg);
				} else {
					// TODO 提示没有内容
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				UiTools.showToast(FailUtil.getFaultByCode(arg0), activity);
			}
		});
	}
	/**
	 * 删除操作
	 * @param objId
	 */
	public void deleteContent(String objId,final Activity context ,final Handler handler){
	
			Word word = new Word();
			word.setObjectId(objId);
			word.delete(context, new DeleteListener() {
				
				@Override
				public void onSuccess() {
					//删除成功 更新页面
					Message msg = new Message();
					msg.what =Consts.MY_HOLE_CONTENT_DELETE_SUCCESS;
					handler.handleMessage(msg);
					
				UiTools.showToast("onSuccess",context );
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					UiTools.showToast(FailUtil.getFaultByCode(arg0),context );
				}
			});
			
		
	}
	
}
