package com.lee.tree.fragment;

import java.util.List;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.lee.tree.adapter.ForestAdapter;
import com.lee.tree.biz.GetContentBiz;
import com.lee.tree.entity.Word;
import com.lee.tree.fragment.BaseFragment;
import com.lee.tree.ui.R;
import com.lee.tree.util.Consts;
import com.lee.tree.util.FailUtil;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.NetworkUtil;
import com.lee.tree.util.UiTools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ForestFragment extends BaseFragment {

	private View forestView;
	private ZrcListView forest_listView;
	private GetContentBiz gcb;
List<Word> words ;

Handler  handler = new Handler(){
	public void handleMessage(android.os.Message msg) {
	  switch (msg.what) {
	case Consts.LOAD_MY_HOLE_CONTENT_SUCCESS://ˢ�³ɹ�
		 words=(List<Word>)msg.obj;
		//������
		setAdapter(words);
		break;

	default:
		break;
	}
	};
	};
	
	public ForestFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		forestView = inflater.inflate(R.layout.fragment_forest, container,
				false);
		
		return forestView;
	}

	@Override
	public void onResume() {
		super.onResume();
		setView();
		//��ҳ��ӵ�һҳ��ʼ��ѯ
	getForestContentByCloud(0,STATE_REFRESH);
	}
	

/**
 * ����View  
 */
	private void setView() {
		forest_listView= (ZrcListView)forestView.findViewById(R.id.forest_listView);
		 // ��������ˢ�µ���ʽ����ѡ�������û��Header���޷�����ˢ�£�
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        forest_listView.setHeadable(header);
        // ���ü��ظ������ʽ����ѡ��
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xff33bbee);
        forest_listView.setFootable(footer);
       setListListener();
	}
	
			// ��ǰҳ�ı�ţ���0��ʼ
	private void setListListener() {
		//����ˢ��
		forest_listView.setOnRefreshStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				try {
					//�������
					if(!NetworkUtil.isNetworkAvailable(getActivity())){
						UiTools.showToast("��������", getActivity());
						return ;
					}
					handler.postDelayed( new Runnable() {
						
						@Override
						public void run() {
							// ����ˢ��(�ӵ�һҳ��ʼװ������)
							
							getForestContentByCloud(0, STATE_REFRESH);
						}
					}, 2000);
					
				} catch (Exception e) {
					UiTools.showToast("��������", getActivity());
				}
			
				
			}
		});
		//��������
		forest_listView.setOnLoadMoreStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				try {
					//�������
					if(!NetworkUtil.isNetworkAvailable(getActivity())){
						UiTools.showToast("��������", getActivity());
						return ;
					}
					
				    handler.postDelayed( new Runnable() {
						
						@Override
						public void run() {
							forest_listView.startLoadMore();//������������  ÿ���������������  ������.....
							// �������ظ���(������һҳ����)
							getForestContentByCloud(curPage, STATE_MORE);
						}
					}, 2000);
					
				} catch (Exception e) {
					
				}
				
           
				
			}
		});
	}
	public static final int STATE_REFRESH = 0;// ����ˢ��
	public static final int STATE_MORE = 1;// ���ظ���
	
	private int limit = 10;		  // ÿҳ��������10��
	private int curPage = 0;
	private ForestAdapter forestAdapter;
	/**
	 * 
	 * @param page  �ڼ�ҳ
	 * @param actionType  ��ѯ����  ˢ�»��߼���
	 */
	public synchronized void    getForestContentByCloud (
			final int page, final int actionType){
		BmobQuery<Word> query = new BmobQuery<Word>();
		// ��ѯ�Լ����������
		query.addWhereEqualTo("isShare", true);//��ѯ����
		query.order("-updatedAt");// ���޸�ʱ�併���ѯ
		query.setLimit(limit);    // ����ÿҳ����������
		query.setSkip(page*limit);// �ӵڼ������ݿ�ʼ��
		query.include("auther");//��ѯ��������Ϣ
		
		query.findObjects(getActivity(), new FindListener<Word>() {

			@Override
			public void onSuccess(List<Word> arg0) {
				

				if(arg0.size()>0){
					if(actionType == STATE_REFRESH){
						// ��������ˢ�²���ʱ������ǰҳ�ı������Ϊ
						curPage = 0;
						forest_listView.setRefreshSuccess("���سɹ�");
						Message msg = new Message();
						msg.what = Consts.LOAD_MY_HOLE_CONTENT_SUCCESS;
						msg.obj= arg0;
						handler.handleMessage(msg);
						
						forest_listView.startLoadMore();//������������
					}
					if(actionType ==  STATE_MORE){
						words.addAll(arg0);//��������ݼ��뼯��
						forestAdapter.notifyDataSetChanged();
						LogUtil.i("words", "����");
					}
					curPage++;
					LogUtil.i("words+curPage"+curPage, arg0.size()+"+��������"+words.size());
				
					
				}else{
					UiTools.showToast("��ѽ������,ȥд�㶫����~", getActivity());
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				UiTools.showToast(FailUtil.getFaultByCode(arg0), getActivity());
			}
		});
		
	}
	private void setAdapter(List<Word> words) {
		 forestAdapter = new ForestAdapter(getActivity(),words);
		LogUtil.i("word", words.toString());

		forest_listView.setAdapter(forestAdapter);
	}
}
