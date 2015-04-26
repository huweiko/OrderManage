package com.order.manage.adapter;


import java.util.List;

import org.androidannotations.annotations.ViewById;

import com.order.manage.R;
import com.order.manage.struct.StructInventoryMaster;
import com.order.manage.struct.StructOrderDetail;
import com.order.manage.struct.StructOrderHeader;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * �����еĸ���Ľ������ұ�listview��������
 * @author ��ɬ
 *
 */

public class HistoryOrderAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructOrderHeader> listItemsOrderHeader;
	private List<StructOrderDetail> listItemOrderDetail;
	private int layout = R.layout.history_order_list_item;
	private OnWareItemClickClass onItemClickClass;
	public HistoryOrderAdapter(Context ctx, List<StructOrderHeader> data1,List<StructOrderDetail> data2) {
		this.ctx = ctx;
		this.listItemsOrderHeader = data1;
		this.listItemOrderDetail = data2;
	}

	public HistoryOrderAdapter(Context ctx, List<StructOrderHeader> data, int layout) {
		this.ctx = ctx;
		this.listItemsOrderHeader = data;
		this.layout = layout;
	}

	public int getCount() {
		return listItemsOrderHeader.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	public void setListItem(List<StructOrderHeader> data1,List<StructOrderDetail> data2){
		this.listItemsOrderHeader = data1;
		this.listItemOrderDetail = data2;
	}
	public void SetOnHistoryOrderItemClickClassListener(OnWareItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1
					.findViewById(R.id.TextViewRemarks);
			hold.price = (TextView) arg1
					.findViewById(R.id.TextViewTotalPrice);
			hold.layout = (LinearLayout) arg1
					.findViewById(R.id.LinearLayoutHistoryOrderListItem);
			hold.mImageViewArrow = (ImageView) arg1
					.findViewById(R.id.ImageViewArrow);
			
			hold.mLinearLayoutOrderDetail = (LinearLayout) arg1
					.findViewById(R.id.LinearLayoutOrderDetail);
			hold.mTextViewOrderId = (TextView) arg1
					.findViewById(R.id.TextViewOrderId);
			hold.mListViewWareListItem = (ListView) arg1
					.findViewById(R.id.ListViewWareListItem);
			hold.mTextViewPriceTotalNum = (TextView) arg1
					.findViewById(R.id.TextViewPriceTotalNum);
			hold.mTextViewOrderRemarks = (TextView) arg1
					.findViewById(R.id.TextViewOrderRemarks);
			hold.mTextViewOrderSubmitTime = (TextView) arg1
					.findViewById(R.id.TextViewOrderSubmitTime);
			
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		hold.layout.setOnClickListener(new OnOptionsClick(arg0));
		if(listItemsOrderHeader.get(arg0).getOrderUnfoldSstatus()){
			hold.mLinearLayoutOrderDetail.setVisibility(View.VISIBLE);
			hold.mImageViewArrow.setImageResource(R.drawable.arrow_down);
			
			hold.mTextViewOrderId.setText(listItemOrderDetail.get(0).getBillId());
			hold.mTextViewPriceTotalNum.setText(listItemsOrderHeader.get(arg0).getTotalMny()+"");
			hold.mTextViewOrderRemarks.setText(listItemsOrderHeader.get(arg0).getMemo());
			hold.mTextViewOrderSubmitTime.setText(listItemsOrderHeader.get(arg0).getBillDate());
			
			OrderDetailListViewAdapter l_OrderDetailListViewAdapter = new OrderDetailListViewAdapter(ctx, listItemOrderDetail);
			int WareTotalHeight = 0; 
			for(int i = 0;i < listItemOrderDetail.size();i++){
				View listItem = l_OrderDetailListViewAdapter.getView(i, null, hold.mListViewWareListItem);
				listItem.measure(0, 0);
				WareTotalHeight += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = hold.mListViewWareListItem.getLayoutParams();
			params.height = WareTotalHeight + (hold.mListViewWareListItem.getDividerHeight() * (l_OrderDetailListViewAdapter.getCount() - 1)); 
			hold.mListViewWareListItem.setLayoutParams(params);
			
			hold.mListViewWareListItem.setAdapter(l_OrderDetailListViewAdapter);
		}else{
			hold.mLinearLayoutOrderDetail.setVisibility(View.GONE);
			hold.mImageViewArrow.setImageResource(R.drawable.arrow);
		}
		hold.txt.setText(listItemsOrderHeader.get(arg0).getMemo());
		hold.price.setText(listItemsOrderHeader.get(arg0).getTotalMny()+"Ԫ");
		return arg1;
	}

	private static class Holder {
		LinearLayout layout;
		TextView txt;
		TextView price;
		ImageView mImageViewArrow;
		
		LinearLayout mLinearLayoutOrderDetail;
		TextView mTextViewOrderId;
		ListView mListViewWareListItem;
		TextView mTextViewPriceTotalNum;
		TextView mTextViewOrderRemarks;
		TextView mTextViewOrderSubmitTime;
	}
	public interface OnWareItemClickClass{
		public void OnItemClick(View v,int Position);
	}
	class OnOptionsClick implements OnClickListener{
		int position;
		
		public OnOptionsClick(int position) {
			this.position=position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (onItemClickClass!=null ) {
				onItemClickClass.OnItemClick(v, position);
			}
		}
	
	}
}
