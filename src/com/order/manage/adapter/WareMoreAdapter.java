package com.order.manage.adapter;


import java.util.List;

import com.order.manage.PinyinHelper;
import com.order.manage.R;
import com.order.manage.struct.StructWare;

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

public class WareMoreAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructWare> listItems;
	private int position = 0;
	private int layout = R.layout.ware_list_item;
	private OnWareItemClickClass onItemClickClass;
	
	public WareMoreAdapter(Context ctx, List<StructWare> data) {
		this.ctx = ctx;
		this.listItems = data;
	}

	public WareMoreAdapter(Context ctx, List<StructWare> data, int layout) {
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
	public void SetOnWareItemClickClassListener(OnWareItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1
					.findViewById(R.id.TextView_ware_name);
			hold.price = (TextView) arg1
					.findViewById(R.id.TextView_ware_price);
			hold.add = (ImageButton) arg1
					.findViewById(R.id.ImageButton_ware_add);
			hold.layout = (LinearLayout) arg1
					.findViewById(R.id.ware_list_lishi);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		hold.txt.setText(listItems.get(arg0).getInvName());
		hold.price.setText(listItems.get(arg0).getSalePrice()+"元");
		hold.add.setOnClickListener(new OnOptionsClick(arg0));
/*		hold.txt.setTextColor(Color.parseColor("#FF666666"));
		if (arg0 == position) {
			hold.layout
					.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			hold.txt.setTextColor(Color.parseColor("#FFFF8C00"));
		}*/
		return arg1;
	}

	public void setSelectItem(int i) {
		position = i;
	}

	private static class Holder {
		LinearLayout layout;
		TextView txt;
		TextView price;
		ImageButton add;
	}
	public interface OnWareItemClickClass{
		public void OnItemClick(View v,int Position);
	}
	private class OnOptionsClick implements OnClickListener{
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
