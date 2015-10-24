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
	case Consts.LOAD_MY_HOLE_CONTENT_SUCCESS://刷新成功
		 words=(List<Word>)msg.obj;
		//适配器
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
		//打开页面从第一页开始查询
	getForestContentByCloud(0,STATE_REFRESH);
	}
	

/**
 * 设置View  
 */
	private void setView() {
		forest_listView= (ZrcListView)forestView.findViewById(R.id.forest_listView);
		 // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        forest_listView.setHeadable(header);
        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xff33bbee);
        forest_listView.setFootable(footer);
       setListListener();
	}
	
			// 当前页的编号，从0开始
	private void setListListener() {
		//下拉刷新
		forest_listView.setOnRefreshStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				try {
					//检查网络
					if(!NetworkUtil.isNetworkAvailable(getActivity())){
						UiTools.showToast("请检查网络", getActivity());
						return ;
					}
					handler.postDelayed( new Runnable() {
						
						@Override
						public void run() {
							// 下拉刷新(从第一页开始装载数据)
							
							getForestContentByCloud(0, STATE_REFRESH);
						}
					}, 2000);
					
				} catch (Exception e) {
					UiTools.showToast("请检查网络", getActivity());
				}
			
				
			}
		});
		//上拉加载
		forest_listView.setOnLoadMoreStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				try {
					//检查网络
					if(!NetworkUtil.isNetworkAvailable(getActivity())){
						UiTools.showToast("请检查网络", getActivity());
						return ;
					}
					
				    handler.postDelayed( new Runnable() {
						
						@Override
						public void run() {
							forest_listView.startLoadMore();//开启下拉加载  每次下拉必须得设置  坑死了.....
							// 上拉加载更多(加载下一页数据)
							getForestContentByCloud(curPage, STATE_MORE);
						}
					}, 2000);
					
				} catch (Exception e) {
					
				}
				
           
				
			}
		});
	}
	public static final int STATE_REFRESH = 0;// 下拉刷新
	public static final int STATE_MORE = 1;// 加载更多
	
	private int limit = 10;		  // 每页的数据是10条
	private int curPage = 0;
	private ForestAdapter forestAdapter;
	/**
	 * 
	 * @param page  第几页
	 * @param actionType  查询类型  刷新或者加载
	 */
	public synchronized void    getForestContentByCloud (
			final int page, final int actionType){
		BmobQuery<Word> query = new BmobQuery<Word>();
		// 查询自己发表的内容
		query.addWhereEqualTo("isShare", true);//查询条件
		query.order("-updatedAt");// 按修改时间降序查询
		query.setLimit(limit);    // 设置每页多少条数据
		query.setSkip(page*limit);// 从第几条数据开始，
		query.include("auther");//查询出作者信息
		
		query.findObjects(getActivity(), new FindListener<Word>() {

			@Override
			public void onSuccess(List<Word> arg0) {
				

				if(arg0.size()>0){
					if(actionType == STATE_REFRESH){
						// 当是下拉刷新操作时，将当前页的编号重置为
						curPage = 0;
						forest_listView.setRefreshSuccess("加载成功");
						Message msg = new Message();
						msg.what = Consts.LOAD_MY_HOLE_CONTENT_SUCCESS;
						msg.obj= arg0;
						handler.handleMessage(msg);
						
						forest_listView.startLoadMore();//开启下拉加载
					}
					if(actionType ==  STATE_MORE){
						words.addAll(arg0);//查出的数据加入集合
						forestAdapter.notifyDataSetChanged();
						LogUtil.i("words", "下拉");
					}
					curPage++;
					LogUtil.i("words+curPage"+curPage, arg0.size()+"+现在数量"+words.size());
				
					
				}else{
					UiTools.showToast("哎呀到底了,去写点东西吧~", getActivity());
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
