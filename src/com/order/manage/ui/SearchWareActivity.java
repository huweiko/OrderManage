package com.order.manage.ui;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.order.manage.R;
import com.order.manage.adapter.SearchWareAdapter;
import com.order.manage.struct.StructInventoryMaster;

@EActivity(R.layout.activity_search)
public class SearchWareActivity extends BaseActivity{
	private Context context;
	private SearchWareAdapter mSearchWareAdapter;
	private List<StructInventoryMaster> data;
	
	@ViewById
	ListView ListViewSearch;
	@ViewById
	EditText EditTextSearchInput;
	@AfterViews
	void Init(){
		context = getApplicationContext();
		data = new ArrayList<StructInventoryMaster>();
		mSearchWareAdapter = new SearchWareAdapter(context, data, R.layout.ware_list_item);
		ListViewSearch.setAdapter(mSearchWareAdapter);
		
		EditTextSearchInput.addTextChangedListener(new TextWatcher() {
			
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
				if(mSearchWareAdapter != null && mSearchWareAdapter.getFilter() != null){
					mSearchWareAdapter.getFilter().filter(s);
				}
			}
		});
	}
}