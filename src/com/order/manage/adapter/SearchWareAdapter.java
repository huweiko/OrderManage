package com.order.manage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class SearchWareAdapter extends BaseAdapter implements Filterable{
	private WareFilter filter;
	private List<String> data = new ArrayList<String>();
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
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

		public WareFilter(List<String> data) {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			
		}
		
	}
}