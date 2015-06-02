package com.order.manage.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.order.manage.AppContext;
import com.order.manage.AppManager;
import com.order.manage.R;
import com.order.manage.UIHealper;
import com.order.manage.adapter.CategoryMainAdapter;
import com.order.manage.adapter.WareMoreAdapter;
import com.order.manage.adapter.WareMoreAdapter.OnWareItemClickClass;
import com.order.manage.bean.Response;
import com.order.manage.bean.Urls;
import com.order.manage.db.BDInventoryClassBrand;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.http.AjaxCallBack;
import com.order.manage.http.AjaxParams;
import com.order.manage.struct.StructDBInventoryMaster;
import com.order.manage.struct.StructInventoryClassBrand;
import com.order.manage.struct.StructWare;
import com.order.manage.util.AssetUtils;
import com.order.manage.util.DatabaseSyncManager;
import com.order.manage.util.ToastHelper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
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
	
	private List<StructInventoryClassBrand> mStructBDInventoryClassBrand = new ArrayList<StructInventoryClassBrand>(); 
	private BDInventoryClassBrand mBDInventoryClassBrand;
	private BDInventoryMaster mBDInventoryMaster;
	private Cursor myInventoryClassBrandCursor;
	private Cursor myInventoryMasterCursor;
	
	private int mCurrentMainItem = 0;
	private int mCurrentSecondItem = 0;
	//���յ����¹㲥
	public static final String INTERNAL_ACTION_UPDATE_CATEGORY="broadcast.UPDATE_CATEGORY";
	@Click(R.id.LinearLayoutGategoryClick)
	void OnClickLinearLayoutGategoryClick(View v){
		
		Intent intent = new Intent();
		intent.setClass(appContext, SearchWareActivity_.class);
		startActivity(intent);
	}
	@Click(R.id.ImageButtonRefresh)
	void OnClickImageButtonRefresh(View v){
		updateCategory();
	}
	private void updateCategory() {
		initCategory();
		if(mStructBDInventoryClassBrand.size() > 0){
			mCategoryMainAdapter = new CategoryMainAdapter(appContext, mStructBDInventoryClassBrand);
			mShoplist_onelist2.setAdapter(mCategoryMainAdapter);
			mCategoryMainAdapter.setSelectItem(mCurrentMainItem);
			initAdapter2(mStructBDInventoryClassBrand.get(mCurrentMainItem).getmBDInventoryClassBrand());
		}else{
			mCurrentMainItem = 0;
			mShoplist_onelist2.removeAllViews();
			mShoplist_twolist2.removeAllViews();
		}
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
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTERNAL_ACTION_UPDATE_CATEGORY);
		appContext.registerReceiver(receiver, filter);
	}
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if(intent.getAction().equals(INTERNAL_ACTION_UPDATE_CATEGORY))
			{
				updateCategory();
			}
			
		}

	};
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
	private void initAdapter2(List<StructWare> array) {
		mWareMoreAdapter = new WareMoreAdapter(appContext, array,R.layout.ware_list_item);
		mShoplist_twolist2.setAdapter(mWareMoreAdapter);
		mWareMoreAdapter.SetOnWareItemClickClassListener(this);
		mWareMoreAdapter.notifyDataSetChanged();
	}
	private void initCategory() {
		mBDInventoryClassBrand = new BDInventoryClassBrand(appContext);
		mBDInventoryClassBrand.createDBtable();
		mBDInventoryMaster = new BDInventoryMaster(appContext);
		mBDInventoryMaster.createDBtable();
		
		myInventoryClassBrandCursor = mBDInventoryClassBrand.select();
		mStructBDInventoryClassBrand.clear();
		for(int i = 0;i < myInventoryClassBrandCursor.getCount();i++){
			myInventoryClassBrandCursor.moveToPosition(i);
			StructInventoryClassBrand l_StructBDInventoryClassBrand = new StructInventoryClassBrand();
			l_StructBDInventoryClassBrand.setInvClassIdCode(myInventoryClassBrandCursor.getString(0));
			l_StructBDInventoryClassBrand.setClassOrBrand(myInventoryClassBrandCursor.getInt(1));
			l_StructBDInventoryClassBrand.setInvClassCode(myInventoryClassBrandCursor.getString(2));
			l_StructBDInventoryClassBrand.setInvClassName(myInventoryClassBrandCursor.getString(3));
			l_StructBDInventoryClassBrand.setParentId(myInventoryClassBrandCursor.getString(4));
			myInventoryMasterCursor = mBDInventoryMaster.selectByInvClassCode(l_StructBDInventoryClassBrand.getInvClassCode());
			for(int j =0;j<myInventoryMasterCursor.getCount();j++){
				myInventoryMasterCursor.moveToPosition(j);
				StructWare l_StructInventoryMaster = new StructWare();
				
				l_StructInventoryMaster.setInvIdCode(myInventoryMasterCursor.getString(0));
				l_StructInventoryMaster.setInvName(myInventoryMasterCursor.getString(7));
				l_StructInventoryMaster.setSalePrice(myInventoryMasterCursor.getDouble(12));
				l_StructInventoryMaster.setInvCode(myInventoryMasterCursor.getString(5));
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
		appContext.unregisterReceiver(receiver);
		mBDInventoryClassBrand.close();
		mBDInventoryMaster.close();
	}
	@Override
	public void OnItemClick(View v, int Position) {
		boolean isHave = false;
		mCurrentSecondItem = Position;
		String WareId = mStructBDInventoryClassBrand.get(mCurrentMainItem).getmBDInventoryClassBrand().get(Position).getInvIdCode();
		StructWare l_StructWare = new StructWare();
		StructWareCopy(l_StructWare, mStructBDInventoryClassBrand.get(mCurrentMainItem).getmBDInventoryClassBrand().get(Position));
		for(int i = 0;i<OrderActivity.mOrderStructInventoryMaster.size();i++){
			if(OrderActivity.mOrderStructInventoryMaster.get(i).getInvIdCode().equals(WareId)){
				OrderActivity.mOrderStructInventoryMaster.get(i).setOrderNumber(OrderActivity.mOrderStructInventoryMaster.get(i).getOrderNumber()+1);
				isHave = true;
				break;
			}
		}
		if(!isHave){
			OrderActivity.mOrderStructInventoryMaster.add(l_StructWare);
		}

		Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
		appContext.sendBroadcast(intent);
	};
	private void StructWareCopy(StructWare a,StructWare b){
		a.setInvIdCode(b.getInvIdCode());
		a.setInvClassCode(b.getInvClassCode());
		a.setInvClassName(b.getInvClassName());
		a.setInvBrandCode(b.getInvBrandCode());
		a.setInvBrandName(b.getInvBrandName());
		a.setInvCode(b.getInvCode());
		a.setSimpleCode(b.getSimpleCode());
		a.setInvName(b.getInvName());
		a.setInvBarCode(b.getInvBarCode());
		a.setInvSpec(b.getInvSpec());
		a.setUnit(b.getUnit());
		a.setInPrice(b.getInPrice());
		a.setSalePrice(b.getSalePrice());
		a.setMemPrice(b.getMemPrice());
		a.setEndSaveTime(b.getEndSaveTime());
		a.setOrderId(b.getOrderId());
	}
	private static long firstTime;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"�����˳�");
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, "�ٰ�һ���˳�����");
		}
		firstTime = System.currentTimeMillis();
	}
}
