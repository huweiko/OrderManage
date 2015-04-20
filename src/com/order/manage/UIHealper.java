package com.order.manage;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class UIHealper extends Activity{
	public static void DisplayToast(Context context, CharSequence charSequence)
	{
		Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
	}

}
