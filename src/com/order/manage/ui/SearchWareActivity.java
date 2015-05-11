package com.order.manage.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.order.manage.PinyinHelper;
import com.order.manage.R;
import com.order.manage.adapter.SearchWareAdapter;
import com.order.manage.adapter.SearchWareAdapter.OnSearchWareItemClickClass;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructInventoryMaster;

@EActivity(R.layout.activity_search)
public class SearchWareActivity extends BaseActivity implements OnSearchWareItemClickClass{
	private Context context;
	private SearchWareAdapter mSearchWareAdapter;
	private List<StructInventoryMaster> data = new ArrayList<StructInventoryMaster>();
	private Cursor myInventoryMasterCursor;
	//…Ã∆∑±Ì
	private BDInventoryMaster mBDInventoryMaster;
	@ViewById
	Button ButtonSearchClear;
	@ViewById
	ListView ListViewSearch;
	@ViewById
	EditText EditTextSearchInput;
	@Click(R.id.ButtonSearchClear)
	void OnclickButtonSearchClear(View v){
		finish();
	}
	
	@AfterViews
	void Init(){
		context = getApplicationContext();
		mSearchWareAdapter = new SearchWareAdapter(context, new ArrayList<StructInventoryMaster>(), R.layout.ware_list_item);
		mSearchWareAdapter.SetOnWareItemClickClassListener(this);
		ListViewSearch.setAdapter(mSearchWareAdapter);
		
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
}