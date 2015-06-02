/*
 * 文件名：LoginActivity.java
 * 功能：登陆界面
 * 作者：huwei
 * 创建时间：2013-10-17
 * 
 * 
 * 
 * */
package com.order.manage.ui;


import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.order.manage.AppContext;
import com.order.manage.AppManager;
import com.order.manage.Constant;
import com.order.manage.R;
import com.order.manage.UIHealper;
import com.order.manage.db.BDInventoryClassBrand;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructInventoryClassBrand;
import com.order.manage.struct.StructWare;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
@EActivity(R.layout.activity_frame)
public class FrameActivity extends ActivityGroup
{
	private AppContext appContext;
	
	@ViewById(R.id.FramePager)
	android.support.v4.view.ViewPager mViewPager;
	@ViewById
	LinearLayout Frame_BottemView;
	@ViewById
	LinearLayout MyBottemSearchBtn;
	@ViewById
	LinearLayout MyBottemTuanBtn;
	@ViewById
	LinearLayout MyBottemCheckinBtn;
	@ViewById
	LinearLayout MyBottemMoreBtn;
	@ViewById
	ImageView MyBottemSearchImg;
	@ViewById
	ImageView MyBottemTuanImg;
	@ViewById
	ImageView MyBottemCheckinImg;
	@ViewById
	ImageView MyBottemMoreImg;
	@ViewById
	TextView MyBottemSearchTxt;
	@ViewById
	TextView MyBottemTuanTxt;
	@ViewById
	TextView MyBottemCheckinTxt;
	@ViewById
	TextView MyBottemMoreTxt;
	
	private List<View> list = new ArrayList<View>();// 相当于数据源
	private View view = null;
	private View view1 = null;
	private View view2 = null;
	private View view3 = null;
	private PagerAdapter pagerAdapter = null;// 数据源和viewpager之间的桥梁
	
	//类目表
	private BDInventoryClassBrand mBDInventoryClassBrand;
	//商品表
	private BDInventoryMaster mBDInventoryMaster;
	
	public static final String[] LeiMuArray = {"类别1","类别2","类别3","类别4","类别5","类别6","类别7","类别8","类别9","类别10","类别11","类别12","类别13","类别14","类别15","类别16","类别17","类别18","类别19"};
	public static final String[] WareArray = {"材料1","材料2","材料3","材料4","材料5","材料6","材料7","材料8","材料9","材料10","材料11","材料12","材料13","材料14","材料15","材料16","材料17","材料18","材料19"};
//	public static final String[] LeiMuArray = {"类别1","类别2","类别3","类别4","类别5","类别6","类别7","类别8","类别9","类别10","类别11","类别12","类别13","类别14","类别15","类别16","类别17","类别18","类别19"};
//	public static final String[] WareArray = {"材料1","材料2","材料3","材料4","材料5","材料6","材料7","材料8","材料9","材料10","材料11","材料12","材料13","材料14","材料15","材料16","材料17","材料18","材料19"};
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	//初始化函数
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
//		PgyUpdateManager.register(this,Constant.PgyerAPPID);
//		insertDB();
		createView();
		// 写一个内部类pageradapter
		pagerAdapter = new PagerAdapter() {
			// 判断再次添加的view和之前的view 是否是同一个view
			// arg0新添加的view，arg1之前的
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			// 返回数据源长度
			@Override
			public int getCount() {
				return list.size();
			}

			// 销毁被滑动走的view
			/**
			 * ViewGroup 代表了我们的viewpager 相当于activitygroup当中的view容器， 添加之前先移除。
			 * position 第几条数据 Object 被移出的view
			 * */
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// 移除view
				container.removeView(list.get(position));
			}

			/**
			 * instantiateItem viewpager要现实的view ViewGroup viewpager position
			 * 第几条数据
			 * */
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// 获取view添加到容器当中，并返回
				View v = list.get(position);
				container.addView(v);
				return v;
			}
		};
		// 设置我们的adapter
		mViewPager.setAdapter(pagerAdapter);
//		mViewPager.setOffscreenPageLimit(3);
		
		MyBtnOnclick mytouchlistener = new MyBtnOnclick();
		MyBottemSearchBtn.setOnClickListener(mytouchlistener);
		MyBottemTuanBtn.setOnClickListener(mytouchlistener);
		MyBottemCheckinBtn.setOnClickListener(mytouchlistener);
		MyBottemMoreBtn.setOnClickListener(mytouchlistener);
		
		// 设置viewpager界面切换监听,监听viewpager切换第几个界面以及滑动的
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// arg0 获取 viewpager里面的界面切换到第几个的
			@Override
			public void onPageSelected(int arg0) {
				// 先清除按钮样式
				initBottemBtn();
				// 按照对应的view的tag来判断到底切换到哪个界面。
				// 更改对应的button状态
				int flag = (Integer) list.get((arg0)).getTag();
				if (flag == 0) {
					MyBottemSearchImg
							.setImageResource(R.drawable.main_index_search_pressed);
					MyBottemSearchTxt.setTextColor(Color.parseColor("#FF8C00"));
				} else if (flag == 1) {
					MyBottemTuanImg
							.setImageResource(R.drawable.main_index_tuan_pressed);
					MyBottemTuanTxt.setTextColor(Color.parseColor("#FF8C00"));
				} else if (flag == 2) {
					MyBottemCheckinImg
							.setImageResource(R.drawable.main_index_checkin_pressed);
					MyBottemCheckinTxt.setTextColor(Color
							.parseColor("#FF8C00"));
				}else if (flag == 3) {
					MyBottemMoreImg
							.setImageResource(R.drawable.main_index_more_pressed);
					MyBottemMoreTxt.setTextColor(Color
							.parseColor("#FF8C00"));
		}
			}

			/**
			 * 监听页面滑动的 arg0 表示当前滑动的view arg1 表示滑动的百分比 arg2 表示滑动的距离
			 * */
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/**
			 * 监听滑动状态 arg0 表示我们的滑动状态 0:默认状态 1:滑动状态 2:滑动停止
			 * */
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		IntentFilter filter = new IntentFilter();
		appContext.registerReceiver(receiver, filter);

	}
	// 把viewpager里面要显示的view实例化出来，并且把相关的view添加到一个list当中
	private void createView() {
		view = this
				.getLocalActivityManager()
				.startActivity("search",
						new Intent(FrameActivity.this, CategoryActivity_.class))
				.getDecorView();
		// 用来更改下面button的样式的标志
		view.setTag(0);
		list.add(view);
		view1 = FrameActivity.this
				.getLocalActivityManager()
				.startActivity("tuan",
						new Intent(FrameActivity.this, OrderActivity_.class))
				.getDecorView();
		view1.setTag(1);
		list.add(view1);
		view2 = FrameActivity.this
				.getLocalActivityManager()
				.startActivity("sign",
						new Intent(FrameActivity.this, HistoryActivity_.class))
				.getDecorView();
		view2.setTag(2);
		list.add(view2);
		view3 = FrameActivity.this
				.getLocalActivityManager()
				.startActivity("more",
						new Intent(FrameActivity.this, MoreActivity_.class))
						.getDecorView();
		view3.setTag(3);
		list.add(view3);
	}
	/**
	 * 初始化控件的颜色
	 * */
	private void initBottemBtn() {
		MyBottemSearchImg.setImageResource(R.drawable.search_bottem_search);
		MyBottemTuanImg.setImageResource(R.drawable.search_bottem_tuan);
		MyBottemCheckinImg.setImageResource(R.drawable.search_bottem_checkin);
		MyBottemMoreImg.setImageResource(R.drawable.search_bottem_more);
		MyBottemSearchTxt.setTextColor(getResources().getColor(
				R.color.search_bottem_textcolor));
		MyBottemTuanTxt.setTextColor(getResources().getColor(
				R.color.search_bottem_textcolor));
		MyBottemCheckinTxt.setTextColor(getResources().getColor(
				R.color.search_bottem_textcolor));
		MyBottemMoreTxt.setTextColor(getResources().getColor(
				R.color.search_bottem_textcolor));
	}
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {};
	};
/*	*//**
	 * 返回按钮的监听，用来询问用户是否退出程序
	 * *//*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Builder builder = new Builder(FrameActivity.this);
				builder.setTitle("提示");
				builder.setMessage("你确定要退出吗？");
				builder.setIcon(R.drawable.ic_launcher);

				DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (arg1 == DialogInterface.BUTTON_POSITIVE) {
							arg0.cancel();
						} else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
							FrameActivity.this.finish();
						}
					}
				};
				builder.setPositiveButton("取消", dialog);
				builder.setNegativeButton("确定", dialog);
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		}
		return false;
	}*/
	
	void insertDB(){
		mBDInventoryMaster = new BDInventoryMaster(appContext);
		mBDInventoryClassBrand = new BDInventoryClassBrand(appContext);
		mBDInventoryMaster.createDBtable();
		mBDInventoryClassBrand.createDBtable();
		if(mBDInventoryClassBrand.select().getCount()>0){
			mBDInventoryMaster.close();
			mBDInventoryClassBrand.close();
			return;
		}
//		把从数据库中获取的数据放入数组列表
		for(int i = 0;i < LeiMuArray.length;i++){

			StructInventoryClassBrand l_StructBDInventoryClassBrand = new StructInventoryClassBrand();
			l_StructBDInventoryClassBrand.setInvClassIdCode(""+i);
			l_StructBDInventoryClassBrand.setClassOrBrand(i);
			l_StructBDInventoryClassBrand.setInvClassCode(""+i);
			l_StructBDInventoryClassBrand.setInvClassName(LeiMuArray[i]);
			l_StructBDInventoryClassBrand.setParentId("sdfsdfsdf");

			for(int j = 0;j<WareArray.length;j++){
				
				StructWare l_StructInventoryMaster = new StructWare();
				l_StructInventoryMaster.setInvIdCode(i+"_"+j);
				l_StructInventoryMaster.setInvName(WareArray[i]);
				l_StructInventoryMaster.setInvClassCode(""+j);
				l_StructInventoryMaster.setSalePrice(i+j);
				mBDInventoryMaster.insert(l_StructInventoryMaster);
			}
			mBDInventoryClassBrand.insert(l_StructBDInventoryClassBrand);
			
		}
		mBDInventoryMaster.close();
		mBDInventoryClassBrand.close();
	}
	
	/**
	 * 用linearlayout作为按钮的监听事件
	 * */
	private class MyBtnOnclick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			int mBtnid = arg0.getId();
			switch (mBtnid) {
			case R.id.MyBottemSearchBtn:
				// //设置我们的viewpager跳转那个界面0这个参数和我们的list相关,相当于list里面的下标
				mViewPager.setCurrentItem(0);
				initBottemBtn();
				MyBottemSearchImg
						.setImageResource(R.drawable.main_index_search_pressed);
				MyBottemSearchTxt.setTextColor(Color.parseColor("#FF8C00"));
				break;
			case R.id.MyBottemTuanBtn:
				mViewPager.setCurrentItem(1);
				initBottemBtn();
				MyBottemTuanImg
						.setImageResource(R.drawable.main_index_tuan_pressed);
				MyBottemTuanTxt.setTextColor(Color.parseColor("#FF8C00"));
				break;
			case R.id.MyBottemCheckinBtn:
				mViewPager.setCurrentItem(2);
				initBottemBtn();
				MyBottemCheckinImg
						.setImageResource(R.drawable.main_index_checkin_pressed);
				MyBottemCheckinTxt.setTextColor(Color.parseColor("#FF8C00"));
				break;
			
			case R.id.MyBottemMoreBtn:
				mViewPager.setCurrentItem(3);
				initBottemBtn();
				MyBottemMoreImg
					.setImageResource(R.drawable.main_index_more_pressed);
				MyBottemMoreTxt.setTextColor(Color.parseColor("#FF8C00"));
				break;
			}
		}
		/**
		 * 初始化控件的颜色
		 * */
		private void initBottemBtn() {
			MyBottemSearchImg.setImageResource(R.drawable.search_bottem_search);
			MyBottemTuanImg.setImageResource(R.drawable.search_bottem_tuan);
			MyBottemCheckinImg.setImageResource(R.drawable.search_bottem_checkin);
			MyBottemMoreImg.setImageResource(R.drawable.search_bottem_more);
			MyBottemSearchTxt.setTextColor(getResources().getColor(
					R.color.search_bottem_textcolor));
		}
	}
	private static long firstTime;
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"程序退出！");
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses(getPackageName()); // API Level至少为8才能使用
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, "再按一次退出程序");
		}
		firstTime = System.currentTimeMillis();
	}

}