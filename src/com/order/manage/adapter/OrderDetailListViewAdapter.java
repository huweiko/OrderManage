package com.order.manage.adapter;


import java.util.List;

import com.order.manage.R;
import com.order.manage.adapter.WareMoreAdapter.OnWareItemClickClass;
import com.order.manage.struct.StructInventoryMaster;
import com.order.manage.struct.StructOrderDetail;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 查找中的更多的界面中右边listview的适配器
 * @author 苦涩
 *
 */

public class OrderDetailListViewAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructOrderDetail> listItems;
	private int layout = R.layout.order_list_item;
	public OrderDetailListViewAdapter(Context ctx, List<StructOrderDetail> data) {
		this.ctx = ctx;
		this.listItems = data;
	}

	public OrderDetailListViewAdapter(Context ctx, List<StructOrderDetail> data, int layout) {
		this.ctx = ctx;
		this.listItems = data;
		this.layout = layout;
	}
	public void setListItems(List<StructOrderDetail> data){
		this.listItems = data;
	}
	public List<StructOrderDetail> getListItems(){
		return this.listItems;
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
			hold.mOrderNum = (TextView) arg1
					.findViewById(R.id.TextViewOrderNum);
			
			arg1.setTag(hold);
		}
			hold = (Holder) arg1.getTag();
			hold.layout.setVisibility(View.VISIBLE);
			hold.options.setVisibility(View.INVISIBLE);
			hold.txt.setText(listItems.get(arg0).getInvName());
			hold.price.setText(listItems.get(arg0).getPrice()+"元");
			hold.layout.setBackgroundResource(R.drawable.my_list_txt_background);
			hold.mOrderNum.setText(listItems.get(arg0).getNum()+"");
			hold.txt.setTextColor(Color.parseColor("#FF666666"));
		
		return arg1;
	}


	private static class Holder {
		LinearLayout layout;
		CheckBox options;
		TextView txt;
		TextView price;
		TextView mOrderNum;
		
	}
	
}
