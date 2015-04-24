package com.order.manage.adapter;


import java.util.List;

import com.order.manage.R;
import com.order.manage.struct.StructInventoryMaster;
import com.order.manage.struct.StructOrderHeader;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 查找中的更多的界面中右边listview的适配器
 * @author 苦涩
 *
 */

public class HistoryOrderAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructOrderHeader> listItems;
	private int layout = R.layout.history_order_list_item;
	private OnWareItemClickClass onItemClickClass;
	public HistoryOrderAdapter(Context ctx, List<StructOrderHeader> data) {
		this.ctx = ctx;
		this.listItems = data;
	}

	public HistoryOrderAdapter(Context ctx, List<StructOrderHeader> data, int layout) {
		this.ctx = ctx;
		this.listItems = data;
		this.layout = layout;
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
	public void setListItem(List<StructOrderHeader> data){
		this.listItems = data;
	}
	public void SetOnWareItemClickClassListener(OnWareItemClickClass Listener){
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
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		hold.txt.setText(listItems.get(arg0).getMemo());
		hold.price.setText(listItems.get(arg0).getTotalMny()+"元");
		return arg1;
	}

	private static class Holder {
		LinearLayout layout;
		TextView txt;
		TextView price;
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
