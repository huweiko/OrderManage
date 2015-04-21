package com.order.manage.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.order.manage.AppContext;
import com.order.manage.AppManager;
import com.order.manage.R;
import com.order.manage.UIHealper;
import com.order.manage.adapter.OrderListViewAdapter;
import com.order.manage.adapter.OrderListViewAdapter.OnOrderItemClickClass;
import com.order.manage.struct.StructInventoryMaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * ǩ��ģ��
 * </BR> </BR> By����ɬ </BR> ��ϵ���ߣ�QQ 534429149
 * */
@EActivity(R.layout.activity_order)
public class OrderActivity extends BaseActivity implements OnOrderItemClickClass{
	private Context appContext;
	public static boolean OrderSubmitStatus= false;
	public static List<StructInventoryMaster> mOrderStructInventoryMaster = new ArrayList<StructInventoryMaster>();
	private OrderListViewAdapter mOrderListViewAdapter;
	//���յ������㲥
	public static final String INTERNAL_ACTION_UPDATEORDERACTIVITY="broadcast.UPDATEORDERACTIVITY";
	
	public final static int HANDLE_UPDATE = 10000;
	
	private static double mPriceTotal = 0.0;
	
	private boolean mOrderEditStratus = false;
	@ViewById
	ListView ListViewOrder;
	@ViewById
	CheckBox CheckBoxSelectAll;
	@ViewById
	TextView TextViewMoney;
	@ViewById
	Button ButtonOrderListEdit;
	
	@Click(R.id.ButtonOrderListEdit)
	void OnclickButtonOrderListEdit(){
		if(mOrderEditStratus){
			mOrderEditStratus = false;
			ButtonOrderListEdit.setText("�༭");
			List<StructInventoryMaster> l_OrderStructInventoryMaster = mOrderListViewAdapter.getListItems();
			for(int i = 0;i < l_OrderStructInventoryMaster.size();i++){
				l_OrderStructInventoryMaster.get(i).setOrderEditStatus(mOrderEditStratus);
			}
			mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
			mOrderListViewAdapter.notifyDataSetChanged();
			Message msg = new Message();
			msg.what = HANDLE_UPDATE;
			mHandler.sendMessage(msg);
		}else{
			mOrderEditStratus = true;
			ButtonOrderListEdit.setText("���");
			for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
				mOrderStructInventoryMaster.get(i).setOrderEditStatus(mOrderEditStratus);
			}
			List<StructInventoryMaster> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
			mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
			mOrderListViewAdapter.notifyDataSetChanged();
		}


	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@AfterViews
	void initView() {
		appContext =  OrderActivity.this;
		CheckBoxSelectAll.setChecked(true);
		CheckBoxSelectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				List<StructInventoryMaster> l_Order = new ArrayList<StructInventoryMaster>();
				mOrderListViewAdapter.setListItems(l_Order);
				ListViewOrder.setAdapter(mOrderListViewAdapter);	
				// TODO Auto-generated method stub
				if(isChecked){
					for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
						mOrderStructInventoryMaster.get(i).setOrderChooseStatus(true);
//						mPriceTotal += mOrderStructInventoryMaster.get(i).getSalePrice();
					}
					
				}else{
					for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
						mOrderStructInventoryMaster.get(i).setOrderChooseStatus(false);
					}
				}
				if(mOrderStructInventoryMaster.size()>0){
					if(mOrderListViewAdapter == null){
						mOrderListViewAdapter = new OrderListViewAdapter(appContext, mOrderStructInventoryMaster);
						mOrderListViewAdapter.SetOnOrderItemClickClassListener(OrderActivity.this);
						ListViewOrder.setAdapter(mOrderListViewAdapter);	
					}else{
						List<StructInventoryMaster> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
						mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
						mOrderListViewAdapter.notifyDataSetChanged();
					}
					
				}
				Message msg = new Message();
				msg.what = HANDLE_UPDATE;
				mHandler.sendMessage(msg);
			} 
			
		}); 
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTERNAL_ACTION_UPDATEORDERACTIVITY);
		appContext.registerReceiver(receiver, filter);
	}
	
	@SuppressLint("HandlerLeak") 
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_UPDATE:
				List<StructInventoryMaster> l_OrderStructInventoryMaster = mOrderListViewAdapter.getListItems();
				mPriceTotal = 0.0;
				for(int i =0 ;i < l_OrderStructInventoryMaster.size();i++){
					if(l_OrderStructInventoryMaster.get(i).getOrderChooseStatus()){
						mPriceTotal += (l_OrderStructInventoryMaster.get(i).getSalePrice()*l_OrderStructInventoryMaster.get(i).getOrderNumber());
					}
				}
				TextViewMoney.setText(mPriceTotal+"");
			break;
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	};
	
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if(intent.getAction().equals(INTERNAL_ACTION_UPDATEORDERACTIVITY))
			{
				if(mOrderStructInventoryMaster.size()>0){
					if(mOrderListViewAdapter == null){
						mOrderListViewAdapter = new OrderListViewAdapter(appContext, mOrderStructInventoryMaster);
						mOrderListViewAdapter.SetOnOrderItemClickClassListener(OrderActivity.this);
						ListViewOrder.setAdapter(mOrderListViewAdapter);	
					}else{
						List<StructInventoryMaster> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
						mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
						mOrderListViewAdapter.notifyDataSetChanged();
					}
					
				}
				Message msg = new Message();
				msg.what = HANDLE_UPDATE;
				mHandler.sendMessage(msg);
			}
			
		}

	};
	@Override
	public void OnItemClick(int Position, boolean OnOptionsStatus) {
		// TODO Auto-generated method stub
/*		if(OnOptionsStatus){
			mOrderStructInventoryMaster.get(Position).setOrderChooseStatus(true);
			mPriceTotal = mPriceTotal + mOrderStructInventoryMaster.get(Position).getSalePrice();
		}else{
			mOrderStructInventoryMaster.get(Position).setOrderChooseStatus(false);
			if(mPriceTotal > 0.00000)
				mPriceTotal = mPriceTotal - mOrderStructInventoryMaster.get(Position).getSalePrice();
		}*/
		Message msg = new Message();
		msg.what = HANDLE_UPDATE;
		mHandler.sendMessage(msg);
			
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		appContext.unregisterReceiver(receiver);
		super.onDestroy();
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
	@Override
	public void UpdateUI(List<StructInventoryMaster> data) {
		// TODO Auto-generated method stub
		mOrderListViewAdapter.setListItems(data);
		mOrderListViewAdapter.notifyDataSetChanged();
	}
}