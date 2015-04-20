package com.order.manage.ui;



import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.order.manage.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * ÍÅ¹ºÄ£¿é
 * */
@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {

	@AfterViews
	void initView() {}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	protected void onDestroy() {
		super.onDestroy();
	};

}
