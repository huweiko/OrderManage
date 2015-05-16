package com.order.manage.util;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

	public static void ToastLg(String string, Context activity) {
		Toast.makeText(activity, string, Toast.LENGTH_LONG).show();
	}
	
	public static void ToastSht(String string, Context activity) {
		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
	}
	
	public static void ToastLg(int strId, Context activity) {
		Toast.makeText(activity, strId, Toast.LENGTH_LONG).show();
	}
	
	public static void ToastSht(int strId, Context activity) {
		Toast.makeText(activity, strId, Toast.LENGTH_SHORT).show();
	}

}
