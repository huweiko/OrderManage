package com.order.manage.ui;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.order.manage.AppContext;
import com.order.manage.AppManager;
import com.order.manage.R;
import com.order.manage.UIHealper;
import com.order.manage.adapter.CategoryMainAdapter;
import com.order.manage.adapter.WareMoreAdapter;
import com.order.manage.adapter.WareMoreAdapter.OnWareItemClickClass;
import com.order.manage.db.BDInventoryClassBrand;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructBDInventoryClassBrand;
import com.order.manage.struct.StructInventoryMaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@EActivity(R.layout.activity_category)
public class CategoryActivity extends BaseActivity implements OnWareItemClickClass{
	private AppContext appContext;
	
	@ViewById(R.id.Shoplist_mainlist2)
	LinearLayout mShoplist_mainlist2;
	
	@ViewById(R.id.Shoplist_onelist2)
	ListView mShoplist_onelist2;
	
	@ViewById(R.id.Shoplist_twolist2)
	ListView mShoplist_twolist2;
	
	private CategoryMainAdapter mCategoryMainAdapter;
	private WareMoreAdapter mWareMoreAdapter;
	
	private List<StructBDInventoryClassBrand> mStructBDInventoryClassBrand = new ArrayList<StructBDInventoryClassBrand>(); 
	//类目表
	private BDInventoryClassBrand mBDInventoryClassBrand;
	//商品表
	private BDInventoryMaster mBDInventoryMaster;
	private Cursor myInventoryClassBrandCursor;
	private Cursor myInventoryMasterCursor;
	
	private int mCurrentMainItem = 0;
	private int mCurrentSecondItem = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@AfterViews
	void initView() {
		appContext = (AppContext) getApplication();
		initCategory();

		if(mStructBDInventoryClassBrand.size() > 0){
			mCategoryMainAdapter = new CategoryMainAdapter(appContext, mStructBDInventoryClassBrand);
			mCategoryMainAdapter.setSelectItem(0);
			mShoplist_onelist2.setAdapter(mCategoryMainAdapter);
			if(mStructBDInventoryClassBrand.get(0).getmBDInventoryClassBrand()!=null){
				mWareMoreAdapter = new WareMoreAdapter(appContext, mStructBDInventoryClassBrand.get(0).getmBDInventoryClassBrand());
				mWareMoreAdapter.SetOnWareItemClickClassListener(this);
				mShoplist_twolist2.setAdapter(mWareMoreAdapter);
			}

		}
		
		mShoplist_onelist2.setOnItemClickListener(new Onelistclick2());
		mShoplist_twolist2.setOnItemClickListener(new Onelistclick1());
	}
	private class Onelistclick1 implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}
	}
	private class Onelistclick2 implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(mStructBDInventoryClassBrand.get(arg2).getmBDInventoryClassBrand()!=null){
				initAdapter2(mStructBDInventoryClassBrand.get(arg2).getmBDInventoryClassBrand());
				mCurrentMainItem = arg2;
			}
			
			mCategoryMainAdapter.setSelectItem(arg2);
			mCategoryMainAdapter.notifyDataSetChanged();
		}
	}
	private void initAdapter2(List<StructInventoryMaster> array) {
		mWareMoreAdapter = new WareMoreAdapter(appContext, array,R.layout.ware_list_item);
		mShoplist_twolist2.setAdapter(mWareMoreAdapter);
		mWareMoreAdapter.SetOnWareItemClickClassListener(this);
		mWareMoreAdapter.notifyDataSetChanged();
	}
	/*
	 * 初始化类目，从数据库获取数据
	 * 
	 * */
	private void initCategory() {
		mBDInventoryClassBrand = new BDInventoryClassBrand(appContext);
		mBDInventoryClassBrand.createDBtable();
		mBDInventoryMaster = new BDInventoryMaster(appContext);
		mBDInventoryMaster.createDBtable();
		
		myInventoryClassBrandCursor = mBDInventoryClassBrand.select();

//		把从数据库中获取的数据放入数组列表
		for(int i = 0;i < myInventoryClassBrandCursor.getCount();i++){
			myInventoryClassBrandCursor.moveToPosition(i);
			StructBDInventoryClassBrand l_StructBDInventoryClassBrand = new StructBDInventoryClassBrand();
			l_StructBDInventoryClassBrand.setInvClassIdCode(myInventoryClassBrandCursor.getString(0));
			l_StructBDInventoryClassBrand.setClassOrBrand(myInventoryClassBrandCursor.getInt(1));
			l_StructBDInventoryClassBrand.setInvClassCode(myInventoryClassBrandCursor.getString(2));
			l_StructBDInventoryClassBrand.setInvClassName(myInventoryClassBrandCursor.getString(3));
			l_StructBDInventoryClassBrand.setParentId(myInventoryClassBrandCursor.getString(4));
			myInventoryMasterCursor = mBDInventoryMaster.selectByInvClassCode(l_StructBDInventoryClassBrand.getInvClassCode());
			for(int j =0;j<myInventoryMasterCursor.getCount();j++){
				myInventoryMasterCursor.moveToPosition(j);
				StructInventoryMaster l_StructInventoryMaster = new StructInventoryMaster();
				
				l_StructInventoryMaster.setInvIdCode(myInventoryMasterCursor.getString(0));
				l_StructInventoryMaster.setInvName(myInventoryMasterCursor.getString(7));
				l_StructInventoryMaster.setSalePrice(myInventoryMasterCursor.getDouble(12));
				l_StructBDInventoryClassBrand.addmBDInventoryClassBrand(l_StructInventoryMaster);
			}
			
			mStructBDInventoryClassBrand.add(l_StructBDInventoryClassBrand);
		}

		if(myInventoryClassBrandCursor != null){
			myInventoryClassBrandCursor.close();
		}
		if(myInventoryMasterCursor != null){
			myInventoryMasterCursor.close();
		}
	}
	
	@SuppressLint("HandlerLeak") Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
		};
	};
	protected void onDestroy() {
		super.onDestroy();
		mBDInventoryClassBrand.close();
		mBDInventoryMaster.close();
	}
	@Override
	public void OnItemClick(View v, int Position) {
		// TODO Auto-generated method stub
		mCurrentSecondItem = Position;
		String WareId = mStructBDInventoryClassBrand.get(mCurrentMainItem).getmBDInventoryClassBrand().get(Position).getInvIdCode();
		for(int i = 0;i<OrderActivity.mOrderStructInventoryMaster.size();i++){
			if(OrderActivity.mOrderStructInventoryMaster.get(i).getInvIdCode().equals(WareId)){
				OrderActivity.mOrderStructInventoryMaster.get(i).setOrderNumber(OrderActivity.mOrderStructInventoryMaster.get(i).getOrderNumber()+1);
				Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
				appContext.sendBroadcast(intent);
				return;
			}
		}
		OrderActivity.mOrderStructInventoryMaster.add(mStructBDInventoryClassBrand.get(mCurrentMainItem).getmBDInventoryClassBrand().get(Position));
		Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
		appContext.sendBroadcast(intent);
	};
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
