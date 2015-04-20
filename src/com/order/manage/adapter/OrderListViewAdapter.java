package com.order.manage.adapter;


import java.util.List;

import com.order.manage.R;
import com.order.manage.adapter.WareMoreAdapter.OnWareItemClickClass;
import com.order.manage.struct.StructInventoryMaster;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 查找中的更多的界面中右边listview的适配器
 * @author 苦涩
 *
 */

public class OrderListViewAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructInventoryMaster> listItems;
	private int position = 0;
	private int layout = R.layout.order_list_item;
	private OnOrderItemClickClass onItemClickClass;
	public OrderListViewAdapter(Context ctx, List<StructInventoryMaster> data) {
		this.ctx = ctx;
		this.listItems = data;
	}

	public OrderListViewAdapter(Context ctx, List<StructInventoryMaster> data, int layout) {
		this.ctx = ctx;
		this.listItems = data;
		this.layout = layout;
	}
	public void setListItems(List<StructInventoryMaster> data){
		this.listItems = data;
	}
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	public void SetOnOrderItemClickClassListener(OnOrderItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1
					.findViewById(R.id.TextViewOrderName);
			hold.price = (TextView) arg1
					.findViewById(R.id.TextViewOrderPrice);
			hold.options = (CheckBox) arg1
					.findViewById(R.id.CheckBoxSelectAll);
			hold.layout = (LinearLayout) arg1
					.findViewById(R.id.LinearLayoutOrderList);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		hold.txt.setText(listItems.get(arg0).getInvName());
		hold.price.setText(listItems.get(arg0).getSalePrice()+"元");
		hold.layout.setBackgroundResource(R.drawable.my_list_txt_background);
		
	
		hold.options.setOnCheckedChangeListener(new OnOptionsClick(arg0));
		hold.txt.setTextColor(Color.parseColor("#FF666666"));
		if (arg0 == position) {
			hold.layout
					.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			hold.txt.setTextColor(Color.parseColor("#FFFF8C00"));
		}
		return arg1;
	}

	public void setSelectItem(int i) {
		position = i;
	}

	private static class Holder {
		LinearLayout layout;
		LinearLayout layoutEdit;
		TextView txt;
		TextView price;
		CheckBox options;
	}
	public interface OnOrderItemClickClass{
		public void OnItemClick(int Position,boolean OnOptionsStatus);
	}
	class OnOptionsClick implements OnCheckedChangeListener{
		int position;
		
		public OnOptionsClick(int position) {
			this.position=position;
		}
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) { 
			if (onItemClickClass!=null ) {
				onItemClickClass.OnItemClick(position,isChecked);
			}
		}
	}
}
