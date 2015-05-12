package com.order.manage.ui;

import java.lang.reflect.Field;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.order.manage.AppContext;
import com.order.manage.Constant;
import com.order.manage.R;
import com.order.manage.UIHealper;

/**
 * 更多模块
 * */
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {
	private AppContext appcontext;
	@AfterViews
	void initView(){
		appcontext = (AppContext) getApplicationContext();
	}
	@Click
	void LinearLayoutMoreServiceAddress(){
		showEditRemarksDialog();
	}
	@Click
	void LinearLayoutMoreLoadNewDada(){
		
	}
	@Click
	void LinearLayoutMoreAbaout(){
		Intent intent = new Intent(appcontext, AboutActivity_.class);
		startActivity(intent);
	}
	void showEditRemarksDialog(){
		final EditText mEditTextAddressName;
		Dialog noticeDialog = null;
		AlertDialog.Builder builder = new Builder(MoreActivity.this);
		builder.setInverseBackgroundForced(true);
	
		builder.setTitle("设置服务器IP地址");
		final LayoutInflater inflater = LayoutInflater.from(appcontext);
		View vv = inflater.inflate(R.layout.remarks_dialog, null);
		mEditTextAddressName = (EditText) vv.findViewById(R.id.EditTextRemarks);
		String ip = preferences.getString(Constant.SERVER_IP, "");
		mEditTextAddressName.setText(ip);
		builder.setView(vv);
		builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String externalIP = mEditTextAddressName.getText().toString();
				if(UIHealper.isIpv4(externalIP) == false){
					UIHealper.DisplayToast(appcontext, "输入的IP地址不合法！");
					isCloseShowDialog(dialog, false);
				}else{
					preferences.edit().putString(Constant.SERVER_IP, externalIP).commit();
					isCloseShowDialog(dialog, true);
				}

			}

		});
		builder.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				isCloseShowDialog(dialog, true);
			}
		});
		noticeDialog = builder.create();

		noticeDialog.show();
	}
	private void isCloseShowDialog(DialogInterface dialog,boolean status) {
		try
		{
		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
		    field.setAccessible(true);
		     //设置mShowing值，欺骗android系统
		    field.set(dialog, status);
		}catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
