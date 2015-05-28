package com.order.manage.ui;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.order.manage.adapter.OrderListViewAdapter;
import com.order.manage.adapter.OrderListViewAdapter.OnOrderItemClickClass;
import com.order.manage.bean.Response;
import com.order.manage.bean.Urls;
import com.order.manage.db.BDOrderDetail;
import com.order.manage.db.BDOrderHeader;
import com.order.manage.http.AjaxCallBack;
import com.order.manage.http.AjaxParams;
import com.order.manage.struct.StructDBInventoryMaster;
import com.order.manage.struct.StructOrder;
import com.order.manage.struct.StructWare;
import com.order.manage.struct.StructOrderDetail;
import com.order.manage.struct.StructOrderHeader;
import com.order.manage.util.ToastHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 签到模块
 * </BR> </BR> By：苦涩 </BR> 联系作者：QQ 534429149
 * */
@SuppressLint("SimpleDateFormat") @EActivity(R.layout.activity_order)
public class OrderActivity extends BaseActivity implements OnOrderItemClickClass{
	private Context appContext;
	public static boolean OrderSubmitStatus= false;
	public static List<StructWare> mOrderStructInventoryMaster = new ArrayList<StructWare>();
	private OrderListViewAdapter mOrderListViewAdapter;
	//接收到订单广播
	public static final String INTERNAL_ACTION_UPDATEORDERACTIVITY="broadcast.UPDATEORDERACTIVITY";
	
	public final static int HANDLE_UPDATE = 10000;
	
	private static double mPriceTotal = 0.0;
	
	private boolean mOrderEditStratus = false;
	private final String mOrderHeader = "ODR_";
	private BDOrderHeader mBDOrderHeader;
	private BDOrderDetail mBDOrderDetail;
	private StructOrder mStructOrder;
	@ViewById
	ListView ListViewOrder;
	@ViewById
	CheckBox CheckBoxSelectAll;
	@ViewById
	TextView TextViewMoney;
	@ViewById
	Button ButtonOrderListEdit;
	
	
	@Click(R.id.ButtonSubmit)
	void OnclickButtonSubmit(){
		if(mOrderStructInventoryMaster.size()>0){
			showEditRemarksDialog();
		}else{
			UIHealper.DisplayToast(appContext, "没有订单！");
		}
		

	}
	//插入订单头信息到数据库
	private void InsertOrderHeaderToDB(String x_remarks){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date Time = new Date();
		String DateString = mSimpleDateFormat.format(Time);
		StructOrderHeader l_StructOrderHeader = new StructOrderHeader();
		System.currentTimeMillis();
		Long.toString(System.currentTimeMillis());
		l_StructOrderHeader.setBillId(mOrderHeader+Time.getTime());
		l_StructOrderHeader.setTotalMny(mPriceTotal);
		l_StructOrderHeader.setBizDate(DateString);
		l_StructOrderHeader.setBillDate(DateString);
		l_StructOrderHeader.setMemo(x_remarks);
		mBDOrderHeader.insert(l_StructOrderHeader);
		mStructOrder.setmStructOrderHeader(l_StructOrderHeader);
		InsertOrderDetailToDB(mOrderHeader+Time.getTime());
		
	}
	//订单明细
	private void InsertOrderDetailToDB(String x_BillId){
		Date Time = new Date();
		int ordertotalnum = 0;
		for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
			ordertotalnum += mOrderStructInventoryMaster.get(i).getOrderNumber();
		}
		for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
			StructOrderDetail l_StructOrderDetail = new StructOrderDetail();
			int ordernum = mOrderStructInventoryMaster.get(i).getOrderNumber();
			Double price = mOrderStructInventoryMaster.get(i).getSalePrice();
			l_StructOrderDetail.setBillId(x_BillId);
			l_StructOrderDetail.setItemId((int) Time.getTime()+i);
			l_StructOrderDetail.setNum(ordernum);
			l_StructOrderDetail.setPrice(price);
			l_StructOrderDetail.setOrderMny(ordernum*price);
			l_StructOrderDetail.setInvName(mOrderStructInventoryMaster.get(i).getInvName());
			l_StructOrderDetail.setInvIdCode(mOrderStructInventoryMaster.get(i).getInvIdCode());
			l_StructOrderDetail.setTotalNum(ordertotalnum);
			mStructOrder.getListOrderDetail().add(l_StructOrderDetail);
			mBDOrderDetail.insert(l_StructOrderDetail);
		}
	}
	void submitOrder(StructOrder order){
		String jsonOrder = new Gson().toJson(order);
		AjaxParams params = new AjaxParams();
		params.put("tab","co_order");
		params.put("condition","	");
		params.put("jsons",jsonOrder);
		showReqeustDialog(R.string.submit_order);
//		final LoginCallBack callback = new LoginCallBack(isBackLogin, btnLoad, user, LoginActivity.this, isShowLoading);
		getFinalHttp().post(Urls.submitOrder, params, new AjaxCallBack<String>(){

			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
//				callback.parseData(t);
				parseData(t);
				ToastHelper.ToastLg(R.string.submit_order_success, activity);
				cancelRequestDialog();
			}
			private void parseData(String t) {
/*				Response<StructDBInventoryMaster> response = new Gson().fromJson(t, 
						new TypeToken<Response<StructDBInventoryMaster>>(){}.getType());
				if(response.getResult()){
					StructDBInventoryMaster aa = response.getResponse();
					
				}else{
					ToastHelper.ToastLg(response.getMessage(), getActivity());
				}*/

			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				cancelRequestDialog();
			}
		});
	}
	void showEditRemarksDialog(){
		final EditText mEditTextAddressName;
		Dialog noticeDialog = null;
		AlertDialog.Builder builder = new Builder(appContext);
		builder.setInverseBackgroundForced(true);
	
		builder.setTitle("增加备注");
		final LayoutInflater inflater = LayoutInflater.from(appContext);
		View vv = inflater.inflate(R.layout.remarks_dialog, null);
		mEditTextAddressName = (EditText) vv.findViewById(R.id.EditTextRemarks);
		builder.setView(vv);
		builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String Remarks = mEditTextAddressName.getText().toString();
				if(!Remarks.equals("")){
					mStructOrder = new StructOrder();
					InsertOrderHeaderToDB(Remarks);
					submitOrder(mStructOrder);
					Intent intent = new Intent(HistoryActivity.INTERNAL_ACTION_SUBMITORDER);
					appContext.sendBroadcast(intent);
					mOrderStructInventoryMaster.clear();
					mOrderListViewAdapter.setListItems(mOrderStructInventoryMaster);
					mOrderListViewAdapter.notifyDataSetChanged();
					isCloseShowDialog(dialog, true);
				}else{
					UIHealper.DisplayToast(appContext, "备注不能为空");
					isCloseShowDialog(dialog, false);
				}

			}

		});
		builder.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				isCloseShowDialog(dialog, true);
			}
		});
		noticeDialog = builder.create();

		noticeDialog.show();
	}
	private void isCloseShowDialog(DialogInterface dialog,boolean status) {
		try
		{
		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
		    field.setAccessible(true);
		     //设置mShowing值，欺骗android系统
		    field.set(dialog, status);
		}catch(Exception e) {
		    e.printStackTrace();
		}
	}
	@Click(R.id.ButtonOrderListEdit)
	void OnclickButtonOrderListEdit(){
		if(mOrderEditStratus){
			mOrderEditStratus = false;
			ButtonOrderListEdit.setText("编辑");
			List<StructWare> l_OrderStructInventoryMaster = mOrderListViewAdapter.getListItems();
			for(int i = 0;i < l_OrderStructInventoryMaster.size();i++){
				l_OrderStructInventoryMaster.get(i).setOrderEditStatus(mOrderEditStratus);
			}
			mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
			mOrderListViewAdapter.notifyDataSetChanged();
			Message msg = new Message();
			msg.what = HANDLE_UPDATE;
			mHandler.sendMessage(msg);
		}else{
			if(mOrderStructInventoryMaster.size() > 0){
				mOrderEditStratus = true;
				ButtonOrderListEdit.setText("完成");
				for(int i = 0;i < mOrderStructInventoryMaster.size();i++){
					mOrderStructInventoryMaster.get(i).setOrderEditStatus(mOrderEditStratus);
				}
				
				List<StructWare> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
				mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
				mOrderListViewAdapter.notifyDataSetChanged();	
			}

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
		mBDOrderHeader = new BDOrderHeader(appContext);
		mBDOrderHeader.createDBtable();
		mBDOrderDetail = new BDOrderDetail(appContext);
		mBDOrderDetail.createDBtable();
		
		CheckBoxSelectAll.setChecked(true);
		CheckBoxSelectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				List<StructWare> l_Order = new ArrayList<StructWare>();
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
						List<StructWare> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
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
				List<StructWare> l_OrderStructInventoryMaster = mOrderListViewAdapter.getListItems();
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
						List<StructWare> l_OrderStructInventoryMaster = mOrderStructInventoryMaster;
						mOrderListViewAdapter.setListItems(l_OrderStructInventoryMaster);
						mOrderListViewAdapter.notifyDataSetChanged();
					}
					Message msg = new Message();
					msg.what = HANDLE_UPDATE;
					mHandler.sendMessage(msg);
				}

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
		mBDOrderHeader.close();
		mBDOrderDetail.close();
		super.onDestroy();
	}
	@Override
	public void UpdateUI(List<StructWare> data) {
		// TODO Auto-generated method stub
		mOrderListViewAdapter.setListItems(data);
		mOrderListViewAdapter.notifyDataSetChanged();
	}
	
}
