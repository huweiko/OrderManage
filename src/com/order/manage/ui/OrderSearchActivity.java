package com.order.manage.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mirko.android.datetimepicker.date.DatePickerDialog;
import mirko.android.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.order.manage.R;
import com.order.manage.adapter.OrderSearchAdapter;
import com.order.manage.adapter.OrderSearchAdapter.OnSearchOrderItemClickClass;
import com.order.manage.adapter.SearchMoreAdapter;
import com.order.manage.adapter.SearchMoreAdapter.OnSearchOrderElementClickClass;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.struct.StructOrderHeader;
import com.order.manage.util.ToastHelper;
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
	
	private final Calendar mCalendar = Calendar.getInstance();

	private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);

	private int minute = mCalendar.get(Calendar.MINUTE);

	private int day = mCalendar.get(Calendar.DAY_OF_MONTH);

	private int month = mCalendar.get(Calendar.MONTH);

	private int year = mCalendar.get(Calendar.YEAR);
	
	private boolean mSearchButtonStatus = true;
	private int mSearchItemOption = 0;
	
	private final static int msg_searchedit_hasFocus = 0x0001;
	private final static int msg_searchedit_noFocus = 0x0002;
	@ViewById
	TextView EditTextStartDate;
	@ViewById
	TextView EditTextStopDate;
	@ViewById
	LinearLayout LinearLayoutDateSwitch;
	@ViewById
	LinearLayout LinearLayoutOrderSearchContent;
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
	@ViewById
	Button ButtonOrderSearchClear;
	
	@Click(R.id.ButtonOrderSearchClear)
	void OnclickButtonOrderSearchClear(){
		if(mSearchButtonStatus){
			if(mSearchItemOption ==2){
				String starttime = EditTextStartDate.getText().toString();
				String stoptime = EditTextStopDate.getText().toString();
				if(starttime.equals("") || stoptime.equals("")){
					ToastHelper.ToastLg("请选择时间范围", activity);
					return ;
				}else{
					starttime = starttime+" 00:00:00";
					stoptime = stoptime+" 23:59:59";
					Cursor cursor = mBDOrderHeader.selectByDateRange(starttime, stoptime);
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
					mOrderSearchAdapter.setlistItems(lp_StructOrderHeader);
					mOrderSearchAdapter.notifyDataSetChanged();
					if(cursor != null){
						cursor.close();
					}
				}
			}
			
		}else{
			finish();
		}
		
	}
	
	@Click(R.id.EditTextStartDate)
	void OnclickEditTextStartDate(){
		String tag = null;
		datestartPickerDialog.show(getFragmentManager(), tag);
	}
	
	@Click(R.id.EditTextStopDate)
	void OnclickEditTextStopDate(){
		String tag = null;
		datestopPickerDialog.show(getFragmentManager(), tag);
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
	
	Handler mHandle = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case msg_searchedit_hasFocus:
				ButtonOrderSearchClear.setText("取消");
				break;
			case msg_searchedit_noFocus:
				ButtonOrderSearchClear.setText("搜索");
				break;
			default:
				break;
			}
		};
		
	};
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
		EditTextOrderSearchInput.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					mSearchButtonStatus = false;
					Message msg = new Message();
					msg.what = msg_searchedit_hasFocus;
					mHandle.sendMessage(msg);
					
				}else{
					mSearchButtonStatus = true;
					Message msg = new Message();
					msg.what = msg_searchedit_noFocus;
					mHandle.sendMessage(msg);
				}
			}
		});
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
	final DatePickerDialog datestartPickerDialog = DatePickerDialog.newInstance(new OnDateSetListener() {

		public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

			EditTextStartDate.setText(
					new StringBuilder().append(pad(year))
					.append("-").append(pad(month+1)).append("-").append(pad(day)));
			EditTextStartDate.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
		}

	}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
	final DatePickerDialog datestopPickerDialog = DatePickerDialog.newInstance(new OnDateSetListener() {

		public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

			EditTextStopDate.setText(
					new StringBuilder().append(pad(year))
					.append("-").append(pad(month+1)).append("-").append(pad(day)));
			EditTextStopDate.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
		}

	}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
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
		mSearchItemOption = Position;
		mOrderSearchAdapter.setSearchElement(ORDERLIST_TOPLIST[Position]);
		TextViewOrderSearchElement.setText(ORDERLIST_TOPLIST[Position]);
		ImageViewOrderSearchElement.setImageResource(R.drawable.search_city);
		ListViewOrderSearchElement.setVisibility(View.GONE);
		toplistview = false;
		if(Position == 2){
			LinearLayoutDateSwitch.setVisibility(View.VISIBLE);
			LinearLayoutOrderSearchContent.setVisibility(View.GONE);
		}else{
			LinearLayoutDateSwitch.setVisibility(View.GONE);
			LinearLayoutOrderSearchContent.setVisibility(View.VISIBLE);
		}
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