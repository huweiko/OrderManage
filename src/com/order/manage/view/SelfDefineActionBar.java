package com.order.manage.view;

import com.order.manage.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 
 * @author tom �?��界面用到的actionbar，顶部可配置
 * 
 */
public class SelfDefineActionBar extends RelativeLayout {

	Context context;
	View parentView;
	private ViewGroup mFlNaviLeft;
	private ViewGroup mFlNaviRight;
	private ViewGroup mFlNaviMid;

	public SelfDefineActionBar(Context context) {
		super(context, null);
		this.context = context;
	}

	public SelfDefineActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public static interface IProvideActionBar {
		SelfDefineActionBar getSelfDefActionBar();

		void setupSelfDefineActionBar(int resId);

		void setTitle(CharSequence title, OnClickListener listener);

		void setTitle(int strId, OnClickListener listener);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mFlNaviLeft = (ViewGroup) findViewById(R.id.flLeft);
		mFlNaviRight = (ViewGroup) findViewById(R.id.flRight);
		mFlNaviMid = (ViewGroup) findViewById(R.id.flMiddle);
	}

	/**
	 * 设置中间视图
	 * 
	 * @param view
	 * @param listener
	 */
	public void setMiddleView(View view, OnClickListener listener) {
		mFlNaviMid.removeAllViews();
		if (listener != null) {
			mFlNaviMid.setOnClickListener(listener);
		}
		mFlNaviMid.addView(view);
	}

	public View getMiddleView() {
		return (mFlNaviMid.getChildCount() > 0) ? mFlNaviMid.getChildAt(0)
				: null;
	}

	/**
	 * 设置左侧视图
	 * 
	 * @param view
	 */
	public void setLeftView(View view, final OnClickListener listener) {
		for (int i = 0; i < mFlNaviLeft.getChildCount(); i++) {
			View childView = mFlNaviLeft.getChildAt(i);
			childView.clearAnimation();
			mFlNaviLeft.removeViewAt(i);
		}
		if (listener != null) {
			mFlNaviLeft.setOnClickListener(listener);
		}
		mFlNaviLeft.addView(view);
	}

	// /**
	// * 设置左侧视图
	// *
	// * @param view
	// */
	// public void setLeftView() {
	//
	// TextView backBtn = (TextView) View.inflate(context,
	// R.layout.action_bar_text, null);
	// backBtn.setText(R.string.back);
	// for (int i = 0; i < mFlNaviLeft.getChildCount(); i++) {
	// View childView = mFlNaviLeft.getChildAt(i);
	// childView.clearAnimation();
	// mFlNaviLeft.removeViewAt(i);
	// }
	// mFlNaviLeft.addView(backBtn);
	// }

	public View getLeftView() {
		return (mFlNaviLeft.getChildCount() > 0) ? mFlNaviLeft.getChildAt(0)
				: null;
	}

	/**
	 * 设置右侧视图
	 * 
	 * @param view
	 * @param listener
	 */
	public void setRightView(View view, OnClickListener listener) {
		for (int i = 0; i < mFlNaviRight.getChildCount(); i++) {
			View childView = mFlNaviRight.getChildAt(i);
			childView.clearAnimation();
			mFlNaviRight.removeViewAt(i);
		}
		if (listener != null) {
			mFlNaviRight.setOnClickListener(listener);
		}
		if (view != null) {
			mFlNaviRight.addView(view);
		}
	}

	public View getRightView() {
		return (mFlNaviRight.getChildCount() > 0) ? mFlNaviRight.getChildAt(0)
				: null;
	}

	/**
	 * 添加返回按钮，返回按钮纯文字，没有箭头等图标
	 * 
	 * @param text
	 *            返回按钮的文�?
	 * @param listener
	 *            点击返回按钮监听事件
	 * @param activity
	 *            当前activity
	 */
	public void addBackText(String text, final OnClickListener listener,
			final Activity activity) {
		TextView backBtn = (TextView) View.inflate(context,
				R.layout.action_bar_text, null);
		backBtn.setText(text);
		setLeftView(backBtn, listener);
	}

	/**
	 * 添加返回按钮，返回按钮纯文字，没有箭头等图标
	 * 
	 * @param textId
	 *            返回按钮的文字资源id
	 * @param listener
	 *            点击返回按钮监听事件
	 * @param activity
	 *            当前activity
	 */
	public void addBackText(int textId, final OnClickListener listener) {
		TextView backBtn = (TextView) View.inflate(context,
				R.layout.action_bar_text, null);
		backBtn.setText(textId);
		setLeftView(backBtn, listener);
	}
 
	
	/**
	 * 添加actionbar右边按钮，返回按钮纯文字，没有箭头等图标
	 * 
	 * @param text
	 *            返回按钮的文�?
	 * @param listener
	 *            点击返回按钮监听事件
	 * @param activity
	 *            当前activity
	 */
	public void addRightText(String text, final OnClickListener listener) {
		TextView backBtn = (TextView) View.inflate(context,
				R.layout.action_bar_text, null);
		backBtn.setText(text);
		setRightView(backBtn, listener);
	}

	/**
	 * 添加返回按钮，返回按钮纯文字，没有箭头等图标
	 * 
	 * @param textId
	 *            返回按钮的文字资源id
	 * @param listener
	 *            点击返回按钮监听事件
	 * @param activity
	 *            当前activity
	 */
	public void addRightText(int textId, final OnClickListener listener) {
		TextView backBtn = (TextView) View.inflate(context,
				R.layout.action_bar_text, null);
		backBtn.setText(textId);
		setRightView(backBtn, listener);
	}

	/**
	 * 给当前actionbar设置标题
	 * 
	 * @param title
	 *            标题文字
	 * @param listener
	 */
	public void setTitle(CharSequence title, OnClickListener listener) {
		TextView tvTitle = (TextView) View.inflate(context,
				R.layout.action_bar_mid_text, null);
		tvTitle.setText(title);
		setMiddleView(tvTitle, listener);
	}

	/**
	 * 给当前actionbar设置标题
	 * 
	 * @param title
	 *            标题文字资源id
	 * @param listener
	 *            标题点击事件
	 */
	public void setTitle(int textId, OnClickListener listener) {
		TextView tvTitle = (TextView) View.inflate(context,
				R.layout.action_bar_text, null);
		tvTitle.setText(textId);
		setMiddleView(tvTitle, listener);
	}

	public void addBackImage(int draId, final OnClickListener listener) {
		ImageView backBtn = (ImageView) View.inflate(context,
				R.layout.action_bar_image, null);
		backBtn.setImageResource(draId);
		backBtn.setOnClickListener(listener);
		setLeftView(backBtn, null);
	}

	public void addRightImage(int draId, final OnClickListener listener) {
		ImageView backBtn = (ImageView) View.inflate(context,
				R.layout.action_bar_image, null);
		backBtn.setImageResource(draId);
		backBtn.setOnClickListener(listener);
		setRightView(backBtn, null);
	}

}
