package com.order.manage.ui;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.order.manage.AppContext;
import com.order.manage.Constant;
import com.order.manage.Constant.Preference;
import com.order.manage.R;
import com.order.manage.UIHealper;
import com.order.manage.bean.Response;
import com.order.manage.bean.Urls;
import com.order.manage.db.BDInventoryClassBrand;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.http.AjaxCallBack;
import com.order.manage.http.AjaxParams;
import com.order.manage.struct.StructDBInventoryMaster;
import com.order.manage.util.AssetUtils;
import com.order.manage.util.DatabaseSyncManager;
import com.order.manage.util.ToastHelper;

/**
 * 更多模块
 * */
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {
	private AppContext appcontext;
	List<StructDBInventoryMaster> mListStructDBInventoryMaster;
	private BDInventoryMaster mBDInventoryMaster;
	private BDInventoryClassBrand mBDInventoryClassBrand;
	@AfterViews
	void initView(){
		appcontext = (AppContext) getApplicationContext();
		mBDInventoryMaster = new BDInventoryMaster(appcontext);
		mBDInventoryMaster.createDBtable();
		mBDInventoryClassBrand = new BDInventoryClassBrand(appcontext);
		mBDInventoryClassBrand.createDBtable();
		
	}
	@Click
	void LinearLayoutMoreServiceAddress(){
		showEditRemarksDialog();
	}
	@Click
	void LinearLayoutMoreLoadNewDada(){
		/*String json = AssetUtils.getDataFromAssets(this, "ware_list_all.txt");
		DataSyncTask mDataSyncTask = new DataSyncTask();
		mDataSyncTask.execute(json);*/
		Cursor cur = mBDInventoryMaster.selectMaxTime();
		String endsavetime = "";
		if(cur.getCount() > 0){
			cur.moveToPosition(0);
			endsavetime = cur.getString(14);
		}
		if(endsavetime.equals("")){
			
		}else{
			String [] aa = endsavetime.split("T");
			endsavetime = aa[0];
		}
		if(cur != null){
			cur.close();
		}
		getWareList(endsavetime);
	}
	@Click
	void LinearLayoutLoadAll(){
		Dialog noticeDialog = null;
		AlertDialog.Builder builder = new Builder(MoreActivity.this);
		builder.setInverseBackgroundForced(true);
		builder.setTitle("全部下载");
		final LayoutInflater inflater = LayoutInflater.from(appcontext);
		View vv = inflater.inflate(R.layout.text_tip_dialog, null);
		builder.setView(vv);
		builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				mBDInventoryMaster.deleteAll();
				mBDInventoryClassBrand.deleteAll();
				getWareList("");
				isCloseShowDialog(dialog, true);
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
	@Click
	void LinearLayoutMoreAbaout(){
		Intent intent = new Intent(appcontext, AboutActivity_.class);
		startActivity(intent);
	}
	@Override
	protected void onDestroy() {
		mBDInventoryMaster.close();
		super.onDestroy();
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
		String ip = preferences.getString(Preference.SERVER_IP, Urls.getInstance().getSERVER_IP());
		mEditTextAddressName.setText(ip);
		builder.setView(vv);
		builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String externalIP = mEditTextAddressName.getText().toString();
				/*if(UIHealper.isIpv4(externalIP) == false){
					UIHealper.DisplayToast(appcontext, "输入的IP地址不合法！");
					isCloseShowDialog(dialog, false);
				}else{*/
					preferences.edit().putString(Preference.SERVER_IP, externalIP).commit();
					Urls.getInstance().setSERVER_IP(externalIP);
					isCloseShowDialog(dialog, true);
//				}

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
	private final static int MSG_SYNC_SUCCESS = 0x0001;
	private final static int MSG_SYNC_FAIL = 0x0002;
	Handler mHandle = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SYNC_SUCCESS:
				ToastHelper.ToastLg(R.string.db_sync_success, activity);
				break;
			case MSG_SYNC_FAIL:
				ToastHelper.ToastLg(R.string.db_sync_fail, activity);
				break;

			default:
				break;
			}
			
		};
	};
	private class DataSyncTask extends AsyncTask<String, Void, Boolean>{
		
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Boolean doInBackground(String... params) {
    		boolean retult = false;
			try {
				retult = DatabaseSyncManager.getInstance().doSyncData(getApplicationContext(), params[0], mHandler);
				if(retult){
					Intent intent = new Intent(CategoryActivity.INTERNAL_ACTION_UPDATE_CATEGORY);
					appcontext.sendBroadcast(intent);
					mHandle.sendEmptyMessage(MSG_SYNC_SUCCESS);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				mHandle.sendEmptyMessage(MSG_SYNC_FAIL);
				e.printStackTrace();
			}
			return retult;
    	}  
    	
    	protected void onPostExecute(Boolean result){
			if(result){
				Toast.makeText(activity, R.string.db_sync_success, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(activity, R.string.db_sync_fail, Toast.LENGTH_SHORT).show();

			}
    	}
	}
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DatabaseSyncManager.POST_PROGRESS_NOTIFY:
				int completePercent = msg.arg1;
				Toast.makeText(activity, R.string.db_sync_success, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	void getWareList(String endsavetime){
		AjaxParams params = new AjaxParams();
		params.put("tab","BD_InventoryMaster");
		params.put("condition"," and endsavetime > '"+endsavetime +"'");
		params.put("fldList","");
		showReqeustDialog(R.string.db_sync_doing);
//		final LoginCallBack callback = new LoginCallBack(isBackLogin, btnLoad, user, LoginActivity.this, isShowLoading);
		getFinalHttp().post(Urls.getInstance().getWare(), params, new AjaxCallBack<String>(){

			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
//				callback.parseData(t);
				parseData(t);
				cancelRequestDialog();
				
			}
			private void parseData(String t) {
					DataSyncTask mDataSyncTask = new DataSyncTask();
					mDataSyncTask.execute(t);
		}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				cancelRequestDialog();
				ToastHelper.ToastLg(strMsg, activity);
			}
		});
	}
	@Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        PgyFeedbackShakeManager.register(this, Constant.PgyerAPPID);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        PgyFeedbackShakeManager.unregister();
    }
}
