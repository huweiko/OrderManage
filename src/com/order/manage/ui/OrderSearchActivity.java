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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.order.manage.R;
import com.order.manage.adapter.OrderSearchAdapter;
import com.order.manage.adapter.OrderSearchAdapter.OnSearchOrderItemClickClass;
import com.order.manage.adapter.SearchMoreAdapter;
import com.order.manage.adapter.SearchMoreAdapter.OnSearchOrderElementClickClass;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.struct.StructOrderHeader;
//订单搜索页面
@EActivity(R.layout.activity_ordersearch)
public class OrderSearchActivity extends BaseActivity implements OnSearchOrderElementClickClass, OnSearchOrderItemClickClass{
	private Context context;
	private List<StructOrderHeader> lp_StructOrderHeader = new ArrayList<StructOrderHeader>();
	private OrderSearchAdapter mOrderSearchAdapter;
	private BDOrderHeader mBDOrderHeader;
	private boolean toplistview = false;
	public final static String[] ORDERLIST_TOPLIST = new String[] { "备注", "总金额","日期范围"};
	private SearchMoreAdapter mSearchMoreAdapter;
	@ViewById
	EditText EditTextOrderSearchInput;
	@ViewById
	TextView TextViewOrderSearchElement;
	@ViewById
	ImageView ImageViewOrderSearchElement;
	@ViewById
	ListView ListViewOrderSearchElement;
	@ViewById
	ListView ListViewOrderSearch;
	
	
	@Click(R.id.ButtonOrderSearchClear)
	void OnclickButtonOrderSearchClear(){
		finish();
	}
	
	@Click(R.id.LinearLayoutOrderSearchElement)
	void OnclickLinearLayoutOrderSearchElement(){
		if (!toplistview) {
			ImageViewOrderSearchElement
					.setImageResource(R.drawable.title_arrow_up);
			ListViewOrderSearchElement.setVisibility(View.VISIBLE);
			mSearchMoreAdapter.notifyDataSetChanged();
			toplistview = true;
		} else {
			ImageViewOrderSearchElement.setImageResource(R.drawable.search_city);
			ListViewOrderSearchElement.setVisibility(View.GONE);
			toplistview = false;
		}
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
	
	@AfterViews
	void Init(){
		context = getApplicationContext();
		mBDOrderHeader = new BDOrderHeader(context);
		mBDOrderHeader.createDBtable();
		InitOrderHeaderData();
		mOrderSearchAdapter = new OrderSearchAdapter(context,new ArrayList<StructOrderHeader>(), R.layout.ware_list_item);
		mOrderSearchAdapter.setSearchElement(ORDERLIST_TOPLIST[0]);
		mOrderSearchAdapter.setWareFilter(lp_StructOrderHeader);
		mOrderSearchAdapter.SetOnWareItemClickClassListener( this);
		ListViewOrderSearch.setAdapter(mOrderSearchAdapter);
		mSearchMoreAdapter = new SearchMoreAdapter(context, ORDERLIST_TOPLIST);
		ListViewOrderSearchElement.setAdapter(mSearchMoreAdapter);
		mSearchMoreAdapter.setOrderElementListenr(this);
		
		EditTextOrderSearchInput.addTextChangedListener(new TextWatcher() {
			
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
				if(mOrderSearchAdapter != null && mOrderSearchAdapter.getFilter() != null){
					mOrderSearchAdapter.getFilter().filter(s);
				}
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mBDOrderHeader.close();
	}
	//选择搜索条件
	@Override
	public void OnItemClickOrderElement(View v, int Position) {
		// TODO Auto-generated method stub
		
		mOrderSearchAdapter.setSearchElement(ORDERLIST_TOPLIST[Position]);
		TextViewOrderSearchElement.setText(ORDERLIST_TOPLIST[Position]);
		ImageViewOrderSearchElement.setImageResource(R.drawable.search_city);
		ListViewOrderSearchElement.setVisibility(View.GONE);
		toplistview = false;
	}

	@Override
	public void OnItemClickSearchOrder(View v, int Position,String arg1) {
		// TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.putExtra("orderitem", arg1);
        this.setResult(RESULT_OK, intent);
        this.finish();
	}
}