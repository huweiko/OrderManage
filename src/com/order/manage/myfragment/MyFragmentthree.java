package com.order.manage.myfragment;


import com.order.manage.R;
import com.order.manage.ui.FrameActivity_;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class MyFragmentthree extends Fragment {

	private Context ctx;

	public MyFragmentthree(Context ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		view = View.inflate(ctx, R.layout.fragment_my03, null);
		ImageView mBtn = (ImageView) view.findViewById(R.id.MyLoginBtn);
		mBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(ctx, FrameActivity_.class);
				ctx.startActivity(intent);
			}
		});
		return view;
	}

}
