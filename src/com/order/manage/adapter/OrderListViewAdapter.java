package com.order.manage.adapter;


import java.util.List;

import com.order.manage.R;
import com.order.manage.adapter.WareMoreAdapter.OnWareItemClickClass;
import com.order.manage.struct.StructInventoryMaster;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 查找中的更多的界面中右边listview的适配器
 * @author 苦涩
 *
 */

public class OrderListViewAdapter extends BaseAdapter {
	private Context ctx;
	private List<StructInventoryMaster> listItems;
	private int position = 0;
	private int layout = R.layout.order_list_item;
	private OnOrderItemClickClass onItemClickClass;
	public OrderListViewAdapter(Context ctx, List<StructInventoryMaster> data) {
		this.ctx = ctx;
		this.listItems = data;
	}

	public OrderListViewAdapter(Context ctx, List<StructInventoryMaster> data, int layout) {
		this.ctx = ctx;
		this.listItems = data;
		this.layout = layout;
	}
	public void setListItems(List<StructInventoryMaster> data){
		this.listItems = data;
	}
	public List<StructInventoryMaster> getListItems(){
		return this.listItems;
	}
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	public void SetOnOrderItemClickClassListener(OnOrderItemClickClass Listener){
		this.onItemClickClass = Listener;
	}
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, layout, null);
			hold.txt = (TextView) arg1
					.findViewById(R.id.TextViewOrderName);
			hold.price = (TextView) arg1
					.findViewById(R.id.TextViewOrderPrice);
			hold.options = (CheckBox) arg1
					.findViewById(R.id.CheckBoxSelectAll);
			hold.layout = (LinearLayout) arg1
					.findViewById(R.id.LinearLayoutOrderList);
			hold.mOrderNum = (TextView) arg1
					.findViewById(R.id.TextViewOrderNum);
			
			
			hold.mLinearLayoutOrderListEdit = (LinearLayout) arg1
					.findViewById(R.id.LinearLayoutOrderListEdit);
			hold.mImageButtonNumSubtract = (Button) arg1
					.findViewById(R.id.ImageButtonNumSubtract);
			hold.mImageButtonNumAdd = (Button) arg1
					.findViewById(R.id.ImageButtonNumAdd);
			hold.mTextViewOderNum = (TextView) arg1
					.findViewById(R.id.TextViewOderNum);
			hold.mTextViewOrderEditPrice = (TextView) arg1
					.findViewById(R.id.TextViewOrderEditPrice);
			hold.mButtonOrderDelete = (Button) arg1
					.findViewById(R.id.ButtonOrderDelete);
			hold.mTextViewEditOrderName = (TextView) arg1
					.findViewById(R.id.TextViewEditOrderName);
			
			
			hold.options.setChecked(listItems.get(arg0).getOrderChooseStatus());
			
			
			
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		//编辑状态
		if(listItems.get(arg0).getOrderEditStatus()){
			hold.layout.setVisibility(View.GONE);
			hold.mLinearLayoutOrderListEdit.setVisibility(View.VISIBLE);
			
			hold.mImageButtonNumSubtract.setOnClickListener(new OnButtonClick(arg0));
			hold.mImageButtonNumAdd.setOnClickListener(new OnButtonClick(arg0));
			hold.mTextViewOderNum.setText(listItems.get(arg0).getOrderNumber()+"");
			hold.mTextViewOrderEditPrice.setText(listItems.get(arg0).getSalePrice()+"");
			hold.mTextViewOrderEditPrice.setOnClickListener(new OnButtonClick(arg0));
			hold.mButtonOrderDelete.setOnClickListener(new OnButtonClick(arg0));
			hold.mTextViewEditOrderName.setText(listItems.get(arg0).getInvName());
		}//正常状态
		else{
			
			hold.mLinearLayoutOrderListEdit.setVisibility(View.GONE);
			hold.layout.setVisibility(View.VISIBLE);
			
			hold.txt.setText(listItems.get(arg0).getInvName());
			hold.price.setText(listItems.get(arg0).getSalePrice()+"元");
			hold.layout.setBackgroundResource(R.drawable.my_list_txt_background);
			hold.mOrderNum.setText(listItems.get(arg0).getOrderNumber()+"");
		
			hold.options.setOnCheckedChangeListener(new OnOptionsClick(arg0));
			hold.txt.setTextColor(Color.parseColor("#FF666666"));
		}
		
		return arg1;
	}

	public void setSelectItem(int i) {
		position = i;
	}

	private static class Holder {
		LinearLayout layout;
		CheckBox options;
		TextView txt;
		TextView price;
		TextView mOrderNum;
		
		LinearLayout mLinearLayoutOrderListEdit;
		Button mImageButtonNumSubtract;
		Button mImageButtonNumAdd;
		TextView mTextViewOderNum;
		TextView mTextViewOrderEditPrice;
		Button mButtonOrderDelete;
		TextView mTextViewEditOrderName;
		
	}
	public interface OnOrderItemClickClass{
		public void OnItemClick(int Position,boolean OnOptionsStatus);
		public void UpdateUI(List<StructInventoryMaster> data);
	}
	class OnButtonClick implements OnClickListener{
		int position;
		
		public OnButtonClick(int position) {
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.ImageButtonNumSubtract:
				if(listItems.get(position).getOrderNumber()>1){
					listItems.get(position).setOrderNumber(listItems.get(position).getOrderNumber()-1);
					onItemClickClass.UpdateUI(listItems);
				}
				break;
			case R.id.ImageButtonNumAdd:
				listItems.get(position).setOrderNumber(listItems.get(position).getOrderNumber()+1);
				onItemClickClass.UpdateUI(listItems);
				break;
			case R.id.ButtonOrderDelete:
				listItems.remove(position);
				onItemClickClass.UpdateUI(listItems);
				break;
			case R.id.TextViewOrderEditPrice:
				showEditPriceDialog(position);
				break;

			default:
				break;
			}
		}
		
	}
	
	
	class OnOptionsClick implements OnCheckedChangeListener{
		int position;
		
		public OnOptionsClick(int position) {
			this.position=position;
		}
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) { 
			if (onItemClickClass!=null ) {
				listItems.get(position).setOrderChooseStatus(isChecked);
				onItemClickClass.OnItemClick(position,isChecked);
			}
		}
	}
	
	void showEditPriceDialog(final int position){
		final EditText mEditTextAddressName;
		Dialog noticeDialog = null;
		AlertDialog.Builder builder = new Builder(ctx,R.style.dialog);
		builder.setInverseBackgroundForced(true);
	

		builder.setTitle("修改商品价格");
		final LayoutInflater inflater = LayoutInflater.from(ctx);
		View vv = inflater.inflate(R.layout.conserve_dialog, null);
		mEditTextAddressName = (EditText) vv.findViewById(R.id.EditTextAddressName);
		builder.setView(vv);
		builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String SalePrice = mEditTextAddressName.getText().toString();
				if(!SalePrice.equals("")){
					listItems.get(position).setSalePrice(Double.parseDouble(SalePrice));
					onItemClickClass.UpdateUI(listItems);
				}

			}

		});
		builder.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();

		noticeDialog.show();
	}
}
