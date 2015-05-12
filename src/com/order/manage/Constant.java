package com.order.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

public class Constant {

	public static final long MIN_LOGINTIME = 0;
	public static class Preference {

		public static final String LOGIN_USER = "LOGIN_USER";
		public static final String UNAME = "UNAME";
		public static final String PWD = "PWD";

		public static SharedPreferences getSharedPreferences(Context context) {
			return context.getSharedPreferences("OrderManageSharePref", Context.MODE_PRIVATE);
		}
	}
	public static String SERVER_IP = "serverip";
	

}
