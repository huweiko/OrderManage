package com.order.manage.view;

import com.order.manage.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class RequestDialog extends Dialog{

	public RequestDialog(Context context) {
		super(context, R.style.dialog);
		setContentView(R.layout.remarks_dialog);
	}
	
	public void setMessage(String msg){
		TextView tvMsg = (TextView)findViewById(R.id.tvMsg);
		tvMsg.setText(msg);
	}

}
