package com.order.manage.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.order.manage.PinyinHelper;
import com.order.manage.R;
import com.order.manage.adapter.SearchMoreAdapter;
import com.order.manage.adapter.SearchWareAdapter;
import com.order.manage.adapter.SearchWareAdapter.OnSearchWareItemClickClass;
import com.order.manage.adapter.WareSearchItemAdapter;
import com.order.manage.adapter.WareSearchItemAdapter.OnSearchWareElementClickClass;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructInventoryMaster;

@EActivity(R.layout.activity_search)
public class SearchWareActivity extends BaseActivity implements OnSearchWareItemClickClass,OnSearchWareElementClickClass{
	private Context context;
	private SearchWareAdapter mSearchWareAdapter;
	private WareSearchItemAdapter mWareSearchItemAdapter;
	private List<StructInventoryMaster> data = new ArrayList<StructInventoryMaster>();
	private Cursor myInventoryMasterCursor;
	private boolean toplistview = false;
	public final static String[] WARELIST_TOPLIST = new String[] { "品名", "条码字段","扫描条码"};
	public static final int CAPTURE_RESULT = 1;
	//商品表
	private BDInventoryMaster mBDInventoryMaster;
	@ViewById
	Button ButtonSearchClear;
	@ViewById
	ListView ListViewSearch;
	@ViewById
	ListView ListViewWareSearchElement;
	@ViewById
	EditText EditTextSearchInput;
	@ViewById
	ImageView ImageViewSearchCategory;
	@ViewById
	TextView TextViewSearchCategory;
	@Click(R.id.ButtonSearchClear)
	void OnclickButtonSearchClear(View v){
		finish();
	}
	@Click
	void LinearLayoutCategory(){
		if (!toplistview) {
			ImageViewSearchCategory
					.setImageResource(R.drawable.title_arrow_up);
			ListViewWareSearchElement.setVisibility(View.VISIBLE);
			mWareSearchItemAdapter.notifyDataSetChanged();
			toplistview = true;
		} else {
			ImageViewSearchCategory.setImageResource(R.drawable.search_city);
			ListViewWareSearchElement.setVisibility(View.GONE);
			toplistview = false;
		}
	}
	@AfterViews
	void Init(){
		context = getApplicationContext();
		mSearchWareAdapter = new SearchWareAdapter(context, new ArrayList<StructInventoryMaster>(), R.layout.ware_list_item);
		mSearchWareAdapter.SetOnWareItemClickClassListener(this);
		ListViewSearch.setAdapter(mSearchWareAdapter);
		mWareSearchItemAdapter = new WareSearchItemAdapter(context, WARELIST_TOPLIST);
		ListViewWareSearchElement.setAdapter(mWareSearchItemAdapter);
		mWareSearchItemAdapter.setWareElementListenr(this);
		
		mBDInventoryMaster = new BDInventoryMaster(context);
		mBDInventoryMaster.createDBtable();
		myInventoryMasterCursor = mBDInventoryMaster.select();
		for(int j =0;j<myInventoryMasterCursor.getCount();j++){
			myInventoryMasterCursor.moveToPosition(j);
			StructInventoryMaster l_StructInventoryMaster = new StructInventoryMaster();
			
			l_StructInventoryMaster.setInvIdCode(myInventoryMasterCursor.getString(0));
			l_StructInventoryMaster.setInvName(myInventoryMasterCursor.getString(7));
			l_StructInventoryMaster.setSalePrice(myInventoryMasterCursor.getDouble(12));
			data.add(l_StructInventoryMaster);
		}
		mSearchWareAdapter.setWareFilter(data);
		
		
		EditTextSearchInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(mSearchWareAdapter != null && mSearchWareAdapter.getFilter() != null){
					mSearchWareAdapter.getFilter().filter(s);
				}
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mBDInventoryMaster.close();
	}
	@Override
	public void OnItemClick(View v, int Position) {
		// TODO Auto-generated method stub
		String WareId = data.get(Position).getInvIdCode();
		for(int i = 0;i<OrderActivity.mOrderStructInventoryMaster.size();i++){
			if(OrderActivity.mOrderStructInventoryMaster.get(i).getInvIdCode().equals(WareId)){
				OrderActivity.mOrderStructInventoryMaster.get(i).setOrderNumber(OrderActivity.mOrderStructInventoryMaster.get(i).getOrderNumber()+1);
				Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
				context.sendBroadcast(intent);
				return;
			}
		}
		OrderActivity.mOrderStructInventoryMaster.add(data.get(Position));
		Intent intent = new Intent(OrderActivity.INTERNAL_ACTION_UPDATEORDERACTIVITY);
		context.sendBroadcast(intent);
	}
	@Override
	public void OnItemClickWareElement(View v, int Position) {
		// TODO Auto-generated method stub

		mSearchWareAdapter.setSearchElement(WARELIST_TOPLIST[Position]);
		TextViewSearchCategory.setText(WARELIST_TOPLIST[Position]);
		ImageViewSearchCategory.setImageResource(R.drawable.search_city);
		ListViewWareSearchElement.setVisibility(View.GONE);
		toplistview = false;
		if(Position == 2){
			Intent intent = new Intent();
			intent.setClass(context, CaptureActivity_.class);
			startActivityForResult(intent, CAPTURE_RESULT);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode,  Intent data)  
	{   
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CAPTURE_RESULT)
		{
			if(resultCode == RESULT_OK)
			{
				if(data != null)
				{
					if(data.getStringExtra("barcode") != null){
						String barcode = data.getStringExtra("barcode");
						Message msg = new Message();
						msg.what = 1;
						msg.obj = barcode;
						mHandler.sendMessage(msg);
					}

				}
			}
		}
	}
	@SuppressLint("HandlerLeak")
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case 1:
				EditTextSearchInput.setText((String)msg.obj);
				break;
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
}