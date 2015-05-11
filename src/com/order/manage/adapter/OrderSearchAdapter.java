package com.order.manage.adapter;

import java.util.ArrayList;
import java.util.List;

import com.order.manage.PinyinHelper;
import com.order.manage.R;
import com.order.manage.struct.StructInventoryMaster;
import com.order.manage.struct.StructOrderHeader;
import com.order.manage.ui.OrderSearchActivity;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderSearchAdapter extends BaseAdapter implements Filterable{
	private Context ctx;
	private WareFilter filter;
	private List<StructOrderHeader> listItems;
	private List<StructOrderHeader> data;
	private int layout = R.layout.ware_list_item;
	PinyinHelper mPinyinHelper;
	private String SearchElement;
	private int position = 0;
	public OrderSearchAdapter(Context context,List<StructOrderHeader> data,int layout){
		this.ctx = context;
		this.listItems = data;
		this.layout = layout;
		mPinyinHelper = PinyinHelper.getInstance(context);
	}
	public void setWareFilter(List<StructOrderHeader> data){
		this.data = data;
	}
	public void setlistItems(List<StructOrderHeader> data){
		this.listItems = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	private OnSearchOrderItemClickClass onItemClickClass;
	public void SetOnWareItemClickClassListener(OnSearchOrderItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	
	public void setSelectItem(int i) {
		position = i;
	}

	public int getSelectItem() {
		return position;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1
					.findViewById(R.id.TextView_ware_name);
			hold.price = (TextView) arg1
					.findViewById(R.id.TextView_ware_price);
			hold.layout = (LinearLayout) arg1
					.findViewById(R.id.ware_list_lishi);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		hold.txt.setText(listItems.get(arg0).getMemo());
		hold.price.setText(listItems.get(arg0).getTotalMny()+"元");
		hold.txt.setTextColor(Color.parseColor("#FF666666"));
		hold.layout.setOnClickListener(new OnOptionsClick(arg0));
		if (arg0 == position) {
			hold.layout
					.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			hold.txt.setTextColor(Color.parseColor("#FFFF8C00"));
		}
		return arg1;
	}
	public void setSearchElement(String SearchElement){
		this.SearchElement = SearchElement;
	}
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if(filter == null){
			filter = new WareFilter(data);
		}
		return filter;
	}
	
	private class WareFilter extends Filter{
		private List<StructOrderHeader> filterDatas;


		public WareFilter(List<StructOrderHeader> datas) {
			this.filterDatas = datas;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			if (TextUtils.isEmpty(constraint)) { // 无过滤
				List<StructOrderHeader> lists = new ArrayList<StructOrderHeader>();
				results.values = lists;
				results.count = lists.size();
			} else {// 过滤
				List<StructOrderHeader> lists = new ArrayList<StructOrderHeader>();
				// lists.add(datas.get(0));// 固定当前城市
				for (StructOrderHeader data : filterDatas) {
					// 过滤规则: 名字或者名字拼音中包含关键字
					String keyWord = mPinyinHelper.getFullPinyin(constraint.toString());
					String l_SerarchElement = "";
					if(SearchElement.equals(OrderSearchActivity.ORDERLIST_TOPLIST[0])){
						l_SerarchElement = data.getMemo();
					}
					else if(SearchElement.equals(OrderSearchActivity.ORDERLIST_TOPLIST[1])){
						l_SerarchElement = data.getTotalMny()+"";
					}
					else if(SearchElement.equals(OrderSearchActivity.ORDERLIST_TOPLIST[2])){
						l_SerarchElement = data.getBillDate()+"";
					}
					String pinyin =  mPinyinHelper.getFullPinyin(l_SerarchElement);
					// boolean a = s.matches("^[a-zA-Z]*");
//					if (keyWord.matches("^[a-zA-Z]*")) {// 全英文
						boolean isContain = true;
						char[] b = keyWord.toCharArray();
						for (char c : b) {
							if (!pinyin.contains(String.valueOf(c))) {
								isContain = false;
							}
						}
						if (isContain) {
							lists.add(data);
						}
//					}
				}
				results.values = lists;
				results.count = lists.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			listItems = (List<StructOrderHeader>) results.values;
			notifyDataSetChanged();
		}
		
	}
	private static class Holder {
		LinearLayout layout;
		TextView txt;
		TextView price;
		ImageView mImageViewArrow;
	}
	public interface OnSearchOrderItemClickClass{
		public void OnItemClickSearchOrder(View v,int Position,String arg1);
	}
	private class OnOptionsClick implements OnClickListener{
		int on_position;
		
		public OnOptionsClick(int position) {
			this.on_position=position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (onItemClickClass!=null ) {
				onItemClickClass.OnItemClickSearchOrder(v, on_position,listItems.get(on_position).getBillId());
			}
		}
	
	}
}