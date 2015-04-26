package com.order.manage.ui;



import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.order.manage.R;
import com.order.manage.adapter.HistoryOrderAdapter;
import com.order.manage.adapter.HistoryOrderAdapter.OnWareItemClickClass;
import com.order.manage.db.BDOrderDetail;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.struct.StructOrderDetail;
import com.order.manage.struct.StructOrderHeader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * ÍÅ¹ºÄ£¿é
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
	
	@AfterViews
	void initView() {
		mContext =  HistoryActivity.this;
		mBDOrderHeader = new BDOrderHeader(mContext);
		mBDOrderHeader.createDBtable();
		mBDOrderDetail = new BDOrderDetail(mContext);
		mBDOrderDetail.createDBtable();
		InitOrderHeaderData();
		mHistoryOrderAdapter = new HistoryOrderAdapter(mContext, lp_StructOrderHeader,lp_StructOrderDetail);
		ListViewOrderHistory.setAdapter(mHistoryOrderAdapter);
		mHistoryOrderAdapter.SetOnHistoryOrderItemClickClassListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTERNAL_ACTION_SUBMITORDER);
		mContext.registerReceiver(receiver, filter);
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
		super.onDestroy();
	}
	@Override
	public void OnItemClick(View v, int Position) {
		// TODO Auto-generated method stub
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

		String l_BillId = lp_StructOrderHeader.get(Position).getBillId();
		loadOrderDetailData(l_BillId);
		mHistoryOrderAdapter.setListItem(lp_StructOrderHeader,lp_StructOrderDetail);
		mHistoryOrderAdapter.notifyDataSetChanged();
	};

}
