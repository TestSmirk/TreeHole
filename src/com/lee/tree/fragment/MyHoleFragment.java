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
    //服务器请求数据成功  视为刷新成功
    private boolean RefreshSuccess=false;
	private List<Word> words;
	private Handler  handler = new Handler(){
   


		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			//获得数据成功
			case Consts.LOAD_MY_HOLE_CONTENT_SUCCESS:
				 words=(List<Word>)msg.obj;
				setAdapter(words);
				myholeAdapter.notifyDataSetChanged();

				RefreshSuccess = true;
				break;
				//删除成功
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
    * 加载listView   设置下刷新
    */
   @SuppressLint("ResourceAsColor")
  private void setView() {
	   //第三方listView  下拉刷新
		 myhole_listView = (ZrcListView)myholeView.findViewById(R.id.myhole_listView);
//		 // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
//	        float density = getResources().getDisplayMetrics().density;
//	        myhole_listView.setFirstTopOffset((int) (50 * density));
		 // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
	        SimpleHeader header = new SimpleHeader(getActivity());
	        header.setTextColor(0xff0066aa);
	        header.setCircleColor(0xff33bbee);
	        myhole_listView.setHeadable(header);
	        // 设置加载更多的样式（可选）
	        SimpleFooter footer = new SimpleFooter(getActivity());
	        footer.setCircleColor(0xff33bbee);
	        myhole_listView.setFootable(footer);
           setListListener();
	}
   /**
    * listView 事件回调
    */
   private void setListListener() {
	   //下拉刷新
	   myhole_listView.setOnRefreshStartListener(new OnStartListener() {
		
		@Override
		public void onStart() {
			
			//如果有网络  服务器请求数据
			if(NetworkUtil.isNetworkAvailable(getActivity())){
				loadContent();
				if(RefreshSuccess==true){
					myhole_listView.setRefreshSuccess();
				}else {
					myhole_listView.setRefreshFail();
				}
				
			}else {
				myhole_listView.setRefreshFail("请查看网络");
				RefreshSuccess=false;
			}
		}
	});
	   //上拉加载
	   myhole_listView.setOnLoadMoreStartListener(new OnStartListener() {
		
		@Override
		public void onStart() {
			//TODO  分页加载
			LogUtil.i("OnLoad", getActivity());

		}
	});
	   //itme 点击事件
	   myhole_listView.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(ZrcListView parent, View view, int position, long id) {
			initMenu(position );
			if(pop.isShowing()) {  
                // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏  
                pop.dismiss();  
            } else {  
                // 显示窗口   测量屏幕
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
 * 初始化 popUpMenu
 */
     protected void initMenu(final int  position) {
    	 LayoutInflater inflater = LayoutInflater.from(getActivity());  
	        // 引入窗口配置文件  
	        View view = inflater.inflate(R.layout.myhole_option_dailog, null);  
	        // 创建PopupWindow对象  
	         pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);  
	         // 需要设置一下此参数，点击外边可消失  
		        pop.setBackgroundDrawable(new BitmapDrawable());  
		        //设置点击窗口外边窗口消失  
		        pop.setOutsideTouchable(true);  
		        // 设置此参数获得焦点，否则无法点击  
		        pop.setFocusable(true);  
		        LinearLayout myhole_share = (LinearLayout)view.findViewById(R.id.myhole_share);
		        LinearLayout myhole_delete = (LinearLayout)view.findViewById(R.id.myhole_delete);
		        //分享
		        myhole_share.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 Intent intent=new Intent(Intent.ACTION_SEND);    
				         intent.setType("image/*");    
				         intent.putExtra(Intent.EXTRA_SUBJECT, "Share");    
				         intent.putExtra(Intent.EXTRA_TEXT,words.get(position).getContent());    
				         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
				         startActivity(Intent.createChooser(intent, "选择分享"));    
				         pop.dismiss();
						
					}
				});
		        //删除
		        myhole_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String objId = words.get(position).getObjectId();
						
						//LogUtil.i("objId", "位置"+position);LogUtil.i("objId","总和"+ words.size());
						GetContentBiz  gcb  = new GetContentBiz();
						gcb.deleteContent(objId,getActivity(),handler);
						pop.dismiss();
					}
				});
}

/**
    * 加载数据
    */
   public void loadContent() {
	   if(gcb==null){
		   gcb = new GetContentBiz();
	   }
		gcb.getMyContentByCloud(getActivity(),handler);
	}
   /**
    * 设置适配器 
    */
   private void setAdapter(List<Word> words) {
	      myholeAdapter  = new MyHoleAdapter(getActivity(),words);
		myhole_listView.setAdapter(myholeAdapter);
	}

}
