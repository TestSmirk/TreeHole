package com.lee.tree.fragment;

import java.util.List;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnItemClickListener;
import zrc.widget.ZrcListView.OnStartListener;

import com.lee.tree.adapter.MyHoleAdapter;
import com.lee.tree.biz.GetContentBiz;
import com.lee.tree.entity.Word;
import com.lee.tree.ui.R;
import com.lee.tree.util.Consts;
import com.lee.tree.util.LogUtil;
import com.lee.tree.util.NetworkUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MyHoleFragment extends Fragment {

	private View myholeView;
	private  ZrcListView myhole_listView;
    private MyHoleAdapter myholeAdapter;
    private GetContentBiz gcb;
    //�������������ݳɹ�  ��Ϊˢ�³ɹ�
    private boolean RefreshSuccess=false;
	private List<Word> words;
	private Handler  handler = new Handler(){
   


		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			//������ݳɹ�
			case Consts.LOAD_MY_HOLE_CONTENT_SUCCESS:
				 words=(List<Word>)msg.obj;
				setAdapter(words);
				myholeAdapter.notifyDataSetChanged();

				RefreshSuccess = true;
				break;
				//ɾ���ɹ�
			case Consts.MY_HOLE_CONTENT_DELETE_SUCCESS:
				myholeAdapter.notifyDataSetChanged();
				 loadContent();
				 if(words.size()==1){
					 words.clear();
				 }
				 
				
				break;
			}
		};
	};
	private PopupWindow pop;
	

	public MyHoleFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myholeView= inflater.inflate(R.layout.fragment_my_hole, container, false);
		
		
		return myholeView;
	}

	
   @Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	setView();
	loadContent();
	
	
}
  


   /**
    * ����listView   ������ˢ��
    */
   @SuppressLint("ResourceAsColor")
  private void setView() {
	   //������listView  ����ˢ��
		 myhole_listView = (ZrcListView)myholeView.findViewById(R.id.myhole_listView);
//		 // ����Ĭ��ƫ��������Ҫ����ʵ��͸�����������ܡ�����ѡ��
//	        float density = getResources().getDisplayMetrics().density;
//	        myhole_listView.setFirstTopOffset((int) (50 * density));
		 // ��������ˢ�µ���ʽ����ѡ�������û��Header���޷�����ˢ�£�
	        SimpleHeader header = new SimpleHeader(getActivity());
	        header.setTextColor(0xff0066aa);
	        header.setCircleColor(0xff33bbee);
	        myhole_listView.setHeadable(header);
	        // ���ü��ظ������ʽ����ѡ��
	        SimpleFooter footer = new SimpleFooter(getActivity());
	        footer.setCircleColor(0xff33bbee);
	        myhole_listView.setFootable(footer);
           setListListener();
	}
   /**
    * listView �¼��ص�
    */
   private void setListListener() {
	   //����ˢ��
	   myhole_listView.setOnRefreshStartListener(new OnStartListener() {
		
		@Override
		public void onStart() {
			
			//���������  ��������������
			if(NetworkUtil.isNetworkAvailable(getActivity())){
				loadContent();
				if(RefreshSuccess==true){
					myhole_listView.setRefreshSuccess();
				}else {
					myhole_listView.setRefreshFail();
				}
				
			}else {
				myhole_listView.setRefreshFail("��鿴����");
				RefreshSuccess=false;
			}
		}
	});
	   //��������
	   myhole_listView.setOnLoadMoreStartListener(new OnStartListener() {
		
		@Override
		public void onStart() {
			//TODO  ��ҳ����
			LogUtil.i("OnLoad", getActivity());

		}
	});
	   //itme ����¼�
	   myhole_listView.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(ZrcListView parent, View view, int position, long id) {
			initMenu(position );
			if(pop.isShowing()) {  
                // ���ش��ڣ���������˵��������Сʱ������Ҫ�˷�ʽ����  
                pop.dismiss();  
            } else {  
                // ��ʾ����   ������Ļ
            	view.measure(0, 0);
				int viewWidth = view.getMeasuredWidth();
				int screenWidth = getResources().getDisplayMetrics().widthPixels;
				int xOffset = screenWidth - viewWidth;
                pop.showAsDropDown(view,xOffset,20);  
            } 
		}
	});
}
/**
 * ��ʼ�� popUpMenu
 */
     protected void initMenu(final int  position) {
    	 LayoutInflater inflater = LayoutInflater.from(getActivity());  
	        // ���봰�������ļ�  
	        View view = inflater.inflate(R.layout.myhole_option_dailog, null);  
	        // ����PopupWindow����  
	         pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);  
	         // ��Ҫ����һ�´˲����������߿���ʧ  
		        pop.setBackgroundDrawable(new BitmapDrawable());  
		        //���õ��������ߴ�����ʧ  
		        pop.setOutsideTouchable(true);  
		        // ���ô˲�����ý��㣬�����޷����  
		        pop.setFocusable(true);  
		        LinearLayout myhole_share = (LinearLayout)view.findViewById(R.id.myhole_share);
		        LinearLayout myhole_delete = (LinearLayout)view.findViewById(R.id.myhole_delete);
		        //����
		        myhole_share.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 Intent intent=new Intent(Intent.ACTION_SEND);    
				         intent.setType("image/*");    
				         intent.putExtra(Intent.EXTRA_SUBJECT, "Share");    
				         intent.putExtra(Intent.EXTRA_TEXT,words.get(position).getContent());    
				         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
				         startActivity(Intent.createChooser(intent, "ѡ�����"));    
				         pop.dismiss();
						
					}
				});
		        //ɾ��
		        myhole_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String objId = words.get(position).getObjectId();
						
						//LogUtil.i("objId", "λ��"+position);LogUtil.i("objId","�ܺ�"+ words.size());
						GetContentBiz  gcb  = new GetContentBiz();
						gcb.deleteContent(objId,getActivity(),handler);
						pop.dismiss();
					}
				});
}

/**
    * ��������
    */
   public void loadContent() {
	   if(gcb==null){
		   gcb = new GetContentBiz();
	   }
		gcb.getMyContentByCloud(getActivity(),handler);
	}
   /**
    * ���������� 
    */
   private void setAdapter(List<Word> words) {
	      myholeAdapter  = new MyHoleAdapter(getActivity(),words);
		myhole_listView.setAdapter(myholeAdapter);
	}

}
