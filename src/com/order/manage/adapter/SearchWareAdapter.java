package com.order.manage.adapter;

import java.util.ArrayList;
import java.util.List;

import com.order.manage.PinyinHelper;
import com.order.manage.R;
import com.order.manage.struct.StructInventoryMaster;
import com.order.manage.ui.OrderSearchActivity;
import com.order.manage.ui.SearchWareActivity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchWareAdapter extends BaseAdapter implements Filterable{
	private Context ctx;
	private WareFilter filter;
	private List<StructInventoryMaster> listItems;
	private List<StructInventoryMaster> data;
	private int layout = R.layout.ware_list_item;
	PinyinHelper mPinyinHelper;
	private String SearchElement;
	public SearchWareAdapter(Context context,List<StructInventoryMaster> data,int layout){
		this.ctx = context;
		this.listItems = data;
		this.layout = layout;
		mPinyinHelper = PinyinHelper.getInstance(context);
	}
	public void setWareFilter(List<StructInventoryMaster> data){
		this.data = data;
	}
	public void setlistItems(List<StructInventoryMaster> data){
		this.listItems = data;
	}
	public void setSearchElement(String SearchElement){
		this.SearchElement = SearchElement;
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
	private OnSearchWareItemClickClass onItemClickClass;
	public void SetOnWareItemClickClassListener(OnSearchWareItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	@Override
	public View getView(int position, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
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
		hold.txt.setText(listItems.get(position).getInvName());
		hold.price.setText(listItems.get(position).getSalePrice()+"元");
		hold.add.setOnClickListener(new OnOptionsClick(position));
/*		hold.txt.setTextColor(Color.parseColor("#FF666666"));
		if (arg0 == position) {
			hold.layout
					.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			hold.txt.setTextColor(Color.parseColor("#FFFF8C00"));
		}*/
		return arg1;
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
		private List<StructInventoryMaster> filterDatas;

		public WareFilter(List<StructInventoryMaster> datas) {
			this.filterDatas = datas;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			if (TextUtils.isEmpty(constraint)) { // 无过滤
				List<StructInventoryMaster> lists = new ArrayList<StructInventoryMaster>();
				results.values = lists;
				results.count = lists.size();
			} else {// 过滤
				List<StructInventoryMaster> lists = new ArrayList<StructInventoryMaster>();
				// lists.add(datas.get(0));// 固定当前城市
				for (StructInventoryMaster data : filterDatas) {
					// 过滤规则: 名字或者名字拼音中包含关键字
					String keyWord = mPinyinHelper.getFullPinyin(constraint.toString());
					String l_SerarchElement = "";
					if(SearchElement.equals(SearchWareActivity.WARELIST_TOPLIST[0])){
						l_SerarchElement = data.getInvName();
					}
					else if(SearchElement.equals(SearchWareActivity.WARELIST_TOPLIST[1])){
						l_SerarchElement = data.getInvBarCode();
					}
					else if(SearchElement.equals(SearchWareActivity.WARELIST_TOPLIST[2])){
						l_SerarchElement = data.getInvBarCode();
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
			listItems = (List<StructInventoryMaster>) results.values;
			notifyDataSetChanged();
		}
		
	}
	private static class Holder {
		LinearLayout layout;
		TextView txt;
		TextView price;
		ImageButton add;
	}
	public interface OnSearchWareItemClickClass{
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