/*
 * �ļ�����LoginActivity.java
 * ���ܣ���½����
 * ���ߣ�huwei
 * ����ʱ�䣺2013-10-17
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
	
	private List<View> list = new ArrayList<View>();// �൱������Դ
	private View view = null;
	private View view1 = null;
	private View view2 = null;
	private View view3 = null;
	private PagerAdapter pagerAdapter = null;// ����Դ��viewpager֮�������
	
	//��Ŀ��
	private BDInventoryClassBrand mBDInventoryClassBrand;
	//��Ʒ��
	private BDInventoryMaster mBDInventoryMaster;
	
	public static final String[] LeiMuArray = {"���1","���2","���3","���4","���5","���6","���7","���8","���9","���10","���11","���12","���13","���14","���15","���16","���17","���18","���19"};
	public static final String[] WareArray = {"����1","����2","����3","����4","����5","����6","����7","����8","����9","����10","����11","����12","����13","����14","����15","����16","����17","����18","����19"};
//	public static final String[] LeiMuArray = {"���1","���2","���3","���4","���5","���6","���7","���8","���9","���10","���11","���12","���13","���14","���15","���16","���17","���18","���19"};
//	public static final String[] WareArray = {"����1","����2","����3","����4","����5","����6","����7","����8","����9","����10","����11","����12","����13","����14","����15","����16","����17","����18","����19"};
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
	//��ʼ������
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
//		PgyUpdateManager.register(this,Constant.PgyerAPPID);
//		insertDB();
		createView();
		// дһ���ڲ���pageradapter
		pagerAdapter = new PagerAdapter() {
			// �ж��ٴ���ӵ�view��֮ǰ��view �Ƿ���ͬһ��view
			// arg0����ӵ�view��arg1֮ǰ��
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			// ��������Դ����
			@Override
			public int getCount() {
				return list.size();
			}

			// ���ٱ������ߵ�view
			/**
			 * ViewGroup ���������ǵ�viewpager �൱��activitygroup���е�view������ ���֮ǰ���Ƴ���
			 * position �ڼ������� Object ���Ƴ���view
			 * */
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// �Ƴ�view
				container.removeView(list.get(position));
			}

			/**
			 * instantiateItem viewpagerҪ��ʵ��view ViewGroup viewpager position
			 * �ڼ�������
			 * */
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// ��ȡview��ӵ��������У�������
				View v = list.get(position);
				container.addView(v);
				return v;
			}
		};
		// �������ǵ�adapter
		mViewPager.setAdapter(pagerAdapter);
//		mViewPager.setOffscreenPageLimit(3);
		
		MyBtnOnclick mytouchlistener = new MyBtnOnclick();
		MyBottemSearchBtn.setOnClickListener(mytouchlistener);
		MyBottemTuanBtn.setOnClickListener(mytouchlistener);
		MyBottemCheckinBtn.setOnClickListener(mytouchlistener);
		MyBottemMoreBtn.setOnClickListener(mytouchlistener);
		
		// ����viewpager�����л�����,����viewpager�л��ڼ��������Լ�������
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// arg0 ��ȡ viewpager����Ľ����л����ڼ�����
			@Override
			public void onPageSelected(int arg0) {
				// �������ť��ʽ
				initBottemBtn();
				// ���ն�Ӧ��view��tag���жϵ����л����ĸ����档
				// ���Ķ�Ӧ��button״̬
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
			 * ����ҳ�滬���� arg0 ��ʾ��ǰ������view arg1 ��ʾ�����İٷֱ� arg2 ��ʾ�����ľ���
			 * */
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/**
			 * ��������״̬ arg0 ��ʾ���ǵĻ���״̬ 0:Ĭ��״̬ 1:����״̬ 2:����ֹͣ
			 * */
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		IntentFilter filter = new IntentFilter();
		appContext.registerReceiver(receiver, filter);

	}
	// ��viewpager����Ҫ��ʾ��viewʵ�������������Ұ���ص�view��ӵ�һ��list����
	private void createView() {
		view = this
				.getLocalActivityManager()
				.startActivity("search",
						new Intent(FrameActivity.this, CategoryActivity_.class))
				.getDecorView();
		// ������������button����ʽ�ı�־
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
	 * ��ʼ���ؼ�����ɫ
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
	 * ���ذ�ť�ļ���������ѯ���û��Ƿ��˳�����
	 * *//*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Builder builder = new Builder(FrameActivity.this);
				builder.setTitle("��ʾ");
				builder.setMessage("��ȷ��Ҫ�˳���");
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
				builder.setPositiveButton("ȡ��", dialog);
				builder.setNegativeButton("ȷ��", dialog);
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
//		�Ѵ����ݿ��л�ȡ�����ݷ��������б�
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
	 * ��linearlayout��Ϊ��ť�ļ����¼�
	 * */
	private class MyBtnOnclick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			int mBtnid = arg0.getId();
			switch (mBtnid) {
			case R.id.MyBottemSearchBtn:
				// //�������ǵ�viewpager��ת�Ǹ�����0������������ǵ�list���,�൱��list������±�
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
		 * ��ʼ���ؼ�����ɫ
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
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"�����˳���");
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses(getPackageName()); // API Level����Ϊ8����ʹ��
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, "�ٰ�һ���˳�����");
		}
		firstTime = System.currentTimeMillis();
	}

}