package com.order.manage.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.order.manage.AppContext;
import com.order.manage.AppManager;
import com.order.manage.Constant.Preference;
import com.order.manage.R;
import com.order.manage.User;
import com.order.manage.http.FinalHttp;
import com.order.manage.view.RequestDialog;
import com.order.manage.view.SelfDefineActionBar;
import com.order.manage.view.SelfDefineActionBar.IProvideActionBar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends FragmentActivity implements IProvideActionBar{
	public static String tag = BaseActivity.class.getSimpleName();
	public User user;
	public SharedPreferences preferences;
	private FinalHttp http;
	protected SelfDefineActionBar actionBar;
	protected RequestDialog requestDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
		AppManager.getAppManager().addActivity(this);
		tag = getClass().getSimpleName();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		if (null == preferences) {
			preferences = Preference.getSharedPreferences(this);
		} 
		getFinalHttp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().removeActivity(this);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right); 
	}
	@Override
	public SelfDefineActionBar getSelfDefActionBar() {
		actionBar = (SelfDefineActionBar) findViewById(R.id.tkActionBar);
		return actionBar;
	}
	
	public Activity getActivity() {
		return this;
	}

	public void showReqeustDialog(int strId){
		if(requestDialog == null){
			requestDialog = new RequestDialog(this);
		}
		requestDialog.setCancelable(false);
		requestDialog.setMessage(getString(strId));
		
		if(!requestDialog.isShowing()){
			requestDialog.show();
		}
	}
	
	public void cancelRequestDialog(){
		if(requestDialog != null && requestDialog.isShowing()){
			requestDialog.cancel();
		}
	}
	
	@Override
	public void setTitle(CharSequence title, OnClickListener listener) {
		getSelfDefActionBar();
		if (actionBar != null) {
			actionBar.setTitle(title, listener);
		}
	}
	
	@Override
	public void setTitle(int strId) {
		getSelfDefActionBar();
		if (actionBar != null) {
			actionBar.setTitle(strId, null);
		}
	}
	@Override
	public void setTitle(CharSequence title) {
		getSelfDefActionBar();
		if (actionBar != null) {
			actionBar.setTitle(title, null);
		}
	}
 
	protected void addBackBtn(View.OnClickListener listener) {
		getSelfDefActionBar();
		if (actionBar != null) {
			if (listener == null) {
				listener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				};
			}
			actionBar.addBackText(R.string.back, listener);
		}
	}
	protected void addBackImage(int drawId, OnClickListener listener) {
		getSelfDefActionBar();
		if (actionBar != null) {
			if (listener == null) {
				listener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				};
			}
			actionBar.addBackImage(drawId, listener);
		}
	}
	
	protected void addRightBtn(int strId, OnClickListener listener) {
		getSelfDefActionBar();
		if (actionBar != null) {
			if (listener == null) {
				listener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				};
			}
			actionBar.addRightText(strId, listener);
		}
	}

	protected void addRightImage(int drawId, OnClickListener listener) {
		getSelfDefActionBar();
		if (actionBar != null) {
			if (listener == null) {
				listener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				};
			}
			actionBar.addRightImage(drawId, listener);
		}
	}

	@Override
	public void setupSelfDefineActionBar(int resId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitle(int strId, OnClickListener listener) {
		// TODO Auto-generated method stub
		
	}
	public FinalHttp getFinalHttp() {
		if (http == null) {
			http = AppContext.getInstance().getFinalHttp();
		}
		return http;
	}
	
	protected void getCurrentUser() {
		try {
			user = new Gson().fromJson(
					preferences.getString(Preference.LOGIN_USER, null),
					new TypeToken<User>() {
					}.getType());
		} catch (Exception e) {
			Log.e(tag, getString(R.string.login_userinfo_not_exist));
		}
	}

	protected void hideInput() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
