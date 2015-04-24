package com.order.manage.ui;



import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.order.manage.R;
import com.order.manage.adapter.HistoryOrderAdapter;
import com.order.manage.db.BDOrderDetail;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.struct.StructOrderHeader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

/**
 * ÍÅ¹ºÄ£¿é
 * */
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {
	private Context mContext;
	
	private BDOrderHeader mBDOrderHeader;
	private BDOrderDetail mBDOrderDetail;
	
	private HistoryOrderAdapter mHistoryOrderAdapter;
	
	private List<StructOrderHeader> lp_StructOrderHeader = new ArrayList<StructOrderHeader>();
	
	public static final String INTERNAL_ACTION_SUBMITORDER="broadcast.SUBMITORDER";
	@ViewById
	ListView ListViewOrderHistory;
	
	@AfterViews
	void initView() {
		mContext =  HistoryActivity.this;
		mBDOrderHeader = new BDOrderHeader(mContext);
		mBDOrderHeader.createDBtable();
		mBDOrderDetail = new BDOrderDetail(mContext);
		mBDOrderDetail.createDBtable();
		InitOrderHeaderData();
		mHistoryOrderAdapter = new HistoryOrderAdapter(mContext, lp_StructOrderHeader);
		ListViewOrderHistory.setAdapter(mHistoryOrderAdapter);
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
	
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if(intent.getAction().equals(INTERNAL_ACTION_SUBMITORDER))
			{
				InitOrderHeaderData();
				mHistoryOrderAdapter.setListItem(lp_StructOrderHeader);
				mHistoryOrderAdapter.notifyDataSetChanged();
			}
			
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	protected void onDestroy() {
		mContext.unregisterReceiver(receiver);
		mBDOrderHeader.close();
		mBDOrderDetail.close();
		super.onDestroy();
	};

}
