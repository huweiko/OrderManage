package com.order.manage.ui;

import java.lang.reflect.Field;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.order.manage.AppContext;
import com.order.manage.Constant;
import com.order.manage.R;
import com.order.manage.UIHealper;

@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {
	private AppContext appcontext;
	@AfterViews
	void initView(){
		appcontext = (AppContext) getApplicationContext();
		setTitle("关于我们");
		addBackBtn(null);
		try {
			String version = appcontext.getVersionName();
			TextViewVersion.setText(version);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ViewById
	TextView TextViewVersion;
}
