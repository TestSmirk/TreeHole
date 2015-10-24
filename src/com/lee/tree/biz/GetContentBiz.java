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
	
		// ��õ�ǰ��¼�û���ID
		MyUser myUser = BmobUser.getCurrentUser(activity, MyUser.class);
		String id = myUser.getObjectId();
		// ��ѯ�Լ����������
		myWord.addWhereEqualTo("auther", id);
		myWord.order("-updatedAt");// ���޸�ʱ�併���ѯ
		
		// myWord.setLimit(newLimit); ��ѯ�������� ��ҳ����
        //������ƶ����� ���û���ʱ��Ϊһ�� ����Ĭ��Ϊ5��Сʱ
		if(NetworkUtil.isMobile(activity)){
			myWord.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));
		}
		// �ж��Ƿ��л���  
		boolean isCach = myWord.hasCachedResult(activity, Word.class);
		if (isCach) {
			// �ȴӻ���ȡ���ݣ����۽����ζ����ٴδ������ȡ����
			myWord.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		} else {
			// ���û�л���Ļ��������ò���ΪNETWORK_ELSE_CACHE
			myWord.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
		}
		// ��ѯ
		myWord.findObjects(activity, new FindListener<Word>() {
			@Override
			public void onSuccess(List<Word> arg0) {

				// String time = arg0.get(0).getUpdatedAt(); ��ʽ��������ʱ��
				// LogUtil.i("TIME",TimeUtil. ( TimeUtil.stringToLong(time,
				// "yyyy-MM-dd HH:mm:ss")) );
				// ��������õ����ݷ��� fragment ����listView
				if (arg0.size() != 0) {
					Message msg = new Message();
					msg.what = Consts.LOAD_MY_HOLE_CONTENT_SUCCESS;
					msg.obj = arg0;
					handler.handleMessage(msg);
				} else {
					// TODO ��ʾû������
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				UiTools.showToast(FailUtil.getFaultByCode(arg0), activity);
			}
		});
	}
	/**
	 * ɾ������
	 * @param objId
	 */
	public void deleteContent(String objId,final Activity context ,final Handler handler){
	
			Word word = new Word();
			word.setObjectId(objId);
			word.delete(context, new DeleteListener() {
				
				@Override
				public void onSuccess() {
					//ɾ���ɹ� ����ҳ��
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
