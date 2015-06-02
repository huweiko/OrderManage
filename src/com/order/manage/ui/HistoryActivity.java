package com.order.manage.ui;



import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.order.manage.R;
import com.order.manage.adapter.HistoryOrderAdapter;
import com.order.manage.adapter.HistoryOrderAdapter.OnWareItemClickClass;
import com.order.manage.bean.Urls;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.db.BDOrderDetail;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.http.AjaxCallBack;
import com.order.manage.http.AjaxParams;
import com.order.manage.struct.StructOrder;
import com.order.manage.struct.StructWare;
import com.order.manage.struct.StructOrderDetail;
import com.order.manage.struct.StructOrderHeader;
import com.order.manage.util.ToastHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 团购模块
 * */
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity implements OnWareItemClickClass {
	private Context mContext;
	
	private BDOrderHeader mBDOrderHeader;
	private BDOrderDetail mBDOrderDetail;
	
	private HistoryOrderAdapter mHistoryOrderAdapter;
	
	private List<StructOrderHeader> lp_StructOrderHeader = new ArrayList<StructOrderHeader>();
	private List<StructOrderDetail> lp_StructOrderDetail = new ArrayList<StructOrderDetail>();
	
	public static final String INTERNAL_ACTION_SUBMITORDER="broadcast.SUBMITORDER";
	
	public final int HISTORY_DISTRICT = 1;
	
	private BDInventoryMaster mBDInventoryMaster;
	
	private StructOrder mStructOrder;
	@ViewById
	ListView ListViewOrderHistory;
	@ViewById
	ImageView ImageViewArrow;
	@ViewById
	LinearLayout LinearLayoutOrderDetail;
	@ViewById
	TextView TextViewOrderId;
	@ViewById
	ListView ListViewWareListItem;
	@ViewById
	TextView TextViewPriceTotalNum;
	@ViewById
	TextView TextViewOrderRemarks;
	@ViewById
	TextView TextViewOrderSubmitTime;
	

	
	@Click(R.id.LinearLayoutHistoryOrderInputClick)
	void OnclickHistoryOrderInputClick(){
		Intent intent = new Intent();
		intent.setClass(mContext, OrderSearchActivity_.class);
		startActivityForResult(intent,HISTORY_DISTRICT);
	}
	@AfterViews
	void initView() {
		mContext =  HistoryActivity.this;
		mBDOrderHeader = new BDOrderHeader(mContext);
		mBDOrderHeader.createDBtable();
		mBDOrderDetail = new BDOrderDetail(mContext);
		mBDOrderDetail.createDBtable();
		mBDInventoryMaster = new BDInventoryMaster(mContext);
		mBDInventoryMaster.createDBtable();
		InitOrderHeaderData();
		mHistoryOrderAdapter = new HistoryOrderAdapter(mContext, lp_StructOrderHeader,lp_StructOrderDetail);
		ListViewOrderHistory.setAdapter(mHistoryOrderAdapter);
		mHistoryOrderAdapter.SetOnHistoryOrderItemClickClassListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTERNAL_ACTION_SUBMITORDER);
		mContext.registerReceiver(receiver, filter);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode,  Intent data)  
	{   
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == HISTORY_DISTRICT)
		{
			if(resultCode == RESULT_OK)
			{
				if(data != null)
				{
					if(data.getStringExtra("orderitem") != null){
						int item = getListItem(data.getStringExtra("orderitem"));
						
						ListViewOrderHistory.setSelection(item);
					}

				}
			}
		}
	}
	int getListItem(String billID){
		int result = 0;
		for(int i = 0;i < lp_StructOrderHeader.size();i++){
			if(lp_StructOrderHeader.get(i).getBillId().equals(billID)){
				result = i;
				break;
			}
		}
		return result;
	}
	void InitOrderHeaderData(){
		Cursor cursor = mBDOrderHeader.select();
		lp_StructOrderHeader.clear();
		for(int i = 0;i < cursor.getCount();i++){
			cursor.moveToPosition(i);
			StructOrderHeader l_StructOrderHeader = new StructOrderHeader();
			l_StructOrderHeader.setBillId(cursor.getString(0));
			l_StructOrderHeader.setTotalMny(cursor.getDouble(3));
			l_StructOrderHeader.setBizDate(cursor.getString(4));
			l_StructOrderHeader.setBillDate(cursor.getString(5));
			l_StructOrderHeader.setMemo(cursor.getString(7));
			l_StructOrderHeader.setUploaded(cursor.getInt(9));
			lp_StructOrderHeader.add(l_StructOrderHeader);
		}
		if(cursor != null){
			cursor.close();
		}
	}
	void loadOrderDetailData(String x_BillId){
		Cursor cursor = mBDOrderDetail.selectByAttribute(x_BillId);
		lp_StructOrderDetail.clear();
		for(int i = 0;i < cursor.getCount();i++){
			cursor.moveToPosition(i);
			StructOrderDetail l_StructOrderDetail = new StructOrderDetail();
			l_StructOrderDetail.setBillId(cursor.getString(1));
			l_StructOrderDetail.setItemId(cursor.getInt(2));
			
			l_StructOrderDetail.setNum(cursor.getInt(14));
			l_StructOrderDetail.setPrice(cursor.getDouble(15));
			l_StructOrderDetail.setOrderMny(cursor.getDouble(16));
			l_StructOrderDetail.setInvName(cursor.getString(11));
			l_StructOrderDetail.setInvCode(cursor.getString(8));
			l_StructOrderDetail.setInvIdCode(cursor.getString(3));
			l_StructOrderDetail.setTotalNum(cursor.getInt(18));
			lp_StructOrderDetail.add(l_StructOrderDetail);
		}
		if(cursor != null){
			cursor.close();
		}
	}
	
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if(intent.getAction().equals(INTERNAL_ACTION_SUBMITORDER))
			{
				InitOrderHeaderData();
				mHistoryOrderAdapter.setListItem(lp_StructOrderHeader,lp_StructOrderDetail);
				mHistoryOrderAdapter.notifyDataSetChanged();
			}
			
		}

	};
	private class OnclickWareListView implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(lp_StructOrderHeader.get(arg2).getOrderUnfoldSstatus()){
				lp_StructOrderHeader.get(arg2).setOrderUnfoldSstatus(false);
			}else{
				lp_StructOrderHeader.get(arg2).setOrderUnfoldSstatus(true);
			}
			String l_BillId = lp_StructOrderHeader.get(arg2).getBillId();
			loadOrderDetailData(l_BillId);
			mHistoryOrderAdapter.setListItem(lp_StructOrderHeader,lp_StructOrderDetail);
			mHistoryOrderAdapter.notifyDataSetChanged();
			
		}
	}
	
	protected void onDestroy() {
		mContext.unregisterReceiver(receiver);
		mBDOrderHeader.close();
		mBDOrderDetail.close();
		mBDInventoryMaster.close();
		super.onDestroy();
	}
	private void FromDBToInventory(Cursor cursor,StructWare l_StructInventoryMaster){
		for(int j =0;j<cursor.getCount();j++){
			cursor.moveToPosition(j);
			l_StructInventoryMaster.setInvIdCode(cursor.getString(0));
			l_StructInventoryMaster.setInvName(cursor.getString(7));
			l_StructInventoryMaster.setSalePrice(cursor.getDouble(12));
		}
	}
	void submitOrder(final StructOrder order){
		String jsonOrder = new Gson().toJson(order);
		AjaxParams params = new AjaxParams();
		params.put("tab","co_order");
		params.put("condition","	");
		params.put("jsons",jsonOrder);
		showReqeustDialog(R.string.submit_order);
//		final LoginCallBack callback = new LoginCallBack(isBackLogin, btnLoad, user, LoginActivity.this, isShowLoading);
		getFinalHttp().post(Urls.getInstance().getSubmitOrder(), params, new AjaxCallBack<String>(){

			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
//				callback.parseData(t);
				parseData(t);
				cancelRequestDialog();
			}
			private void parseData(String t) {
				JSONObject jsonObject1;
				try {
					jsonObject1 = new JSONObject(t);
					boolean value = jsonObject1.getBoolean("success");
					if(value){
						order.getmOrderHeader().setUploaded(1);
						mBDOrderHeader.update(order.getmOrderHeader(), order.getmOrderHeader().getBillId());
						ToastHelper.ToastLg(R.string.submit_order_success, activity);
					}else{
						String msg = jsonObject1.getString("msg");
						ToastHelper.ToastLg(msg, activity);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastHelper.ToastLg("返回错误格式", activity);
				}
				

			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				cancelRequestDialog();
			}
		});
	}
	@Override
	public void OnItemClick(View v, int Position) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
			case R.id.ButtonHistoryOrderUpdate:{
				Log.i("huwei", "Update Position = "+Position);
				if(OrderActivity.mOrderStructInventoryMaster.size()>0){
					OrderActivity.mOrderStructInventoryMaster.clear();
				}
				String BillId = lp_StructOrderHeader.get(Position).getBillId();
				Cursor OrderDetailCursor = mBDOrderDetail.selectByAttribute(BillId);
				for(int i = 0;i < OrderDetailCursor.getCount();i++){
					OrderDetailCursor.moveToPosition(i);
					String InvIdCode = OrderDetailCursor.getString(3);
					Cursor cursor = mBDInventoryMaster.selectByAttribute(InvIdCode);
					StructWare l_StructInventoryMaster = new StructWare();
					FromDBToInventory(cursor, l_StructInventoryMaster);
					if(cursor != null){
						cursor.close();
					}
					l_StructInventoryMaster.setOrderNumber(lp_StructOrderDetail.get(i).getNum());
					OrderActivity.mOrderStructInventoryMaster.add(l_StructInventoryMaster);
				}
				if(OrderDetailCursor != null){
					OrderDetailCursor.close();
				}
				
				Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
				mContext.sendBroadcast(intent);
			}
				
				break;
			case R.id.ButtonHistoryOrderCopy:{
				Log.i("huwei", "Copy Position = "+Position);
				String BillId = lp_StructOrderHeader.get(Position).getBillId();
				Cursor OrderDetailCursor = mBDOrderDetail.selectByAttribute(BillId);
				for(int i = 0;i < OrderDetailCursor.getCount();i++){
					OrderDetailCursor.moveToPosition(i);
					boolean isHave = false;
					String InvIdCode = OrderDetailCursor.getString(3);
					for(int j = 0;j<OrderActivity.mOrderStructInventoryMaster.size();j++){
						if(OrderActivity.mOrderStructInventoryMaster.get(j).getInvIdCode().equals(InvIdCode)){
							int OrderNumber = OrderActivity.mOrderStructInventoryMaster.get(j).getOrderNumber();
							OrderActivity.mOrderStructInventoryMaster.get(j).setOrderNumber(lp_StructOrderDetail.get(i).getNum()+OrderNumber);
							isHave = true;
							break;
						}
					}
					if(!isHave){
						Cursor cursor = mBDInventoryMaster.selectByAttribute(InvIdCode);
						StructWare l_StructInventoryMaster = new StructWare();
						FromDBToInventory(cursor, l_StructInventoryMaster);
						if(cursor != null){
							cursor.close();
						}
						l_StructInventoryMaster.setOrderNumber(lp_StructOrderDetail.get(i).getNum());
						OrderActivity.mOrderStructInventoryMaster.add(l_StructInventoryMaster);
					}
				}
				if(OrderDetailCursor != null){
					OrderDetailCursor.close();
				}
				
				Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
				mContext.sendBroadcast(intent);
				
			}
				break;
			case R.id.ButtonHistoryOrderDelete:{
				Log.i("huwei", "Delete Position = "+Position);
				String BillId = lp_StructOrderHeader.get(Position).getBillId();
				lp_StructOrderHeader.remove(Position);
				mBDOrderHeader.delete(BillId);
				mBDOrderDetail.delete(BillId);
				mHistoryOrderAdapter.setListItem(lp_StructOrderHeader,lp_StructOrderDetail);
				mHistoryOrderAdapter.notifyDataSetChanged();
			}
				break;
			case R.id.ButtonHistoryOrderSubmit:
				mStructOrder = new StructOrder();
				mStructOrder.setmOrderHeader(lp_StructOrderHeader.get(Position));
				mStructOrder.setListOrderDetail(lp_StructOrderDetail);
				submitOrder(mStructOrder);
				Log.i("huwei", "Submit Position = "+Position);
				break;
			case R.id.LinearLayoutHistoryOrderListItem:{
				for(int i = 0;i< lp_StructOrderHeader.size();i++){
					if(i == Position){
						if(lp_StructOrderHeader.get(Position).getOrderUnfoldSstatus()){
							lp_StructOrderHeader.get(Position).setOrderUnfoldSstatus(false);
						}else{
							lp_StructOrderHeader.get(Position).setOrderUnfoldSstatus(true);
						}
					}else{
						lp_StructOrderHeader.get(i).setOrderUnfoldSstatus(false);
					}

				}
				mHistoryOrderAdapter.setSelectItem(Position);
				String l_BillId = lp_StructOrderHeader.get(Position).getBillId();
				loadOrderDetailData(l_BillId);
				mHistoryOrderAdapter.setListItem(lp_StructOrderHeader,lp_StructOrderDetail);
				mHistoryOrderAdapter.notifyDataSetChanged();
			}
				break;
				default:break;
		}

	};

}
