package com.order.manage.struct;

import java.util.ArrayList;
import java.util.List;


public class StructOrder{
	private OrderHeader mOrderHeader;

	private List<StructOrderDetail> ListOrderDetail = new ArrayList<StructOrderDetail>() ;

	public List<StructOrderDetail> getListOrderDetail() {
		return ListOrderDetail;
	}
	public void setListOrderDetail(List<StructOrderDetail> listOrderDetail) {
		ListOrderDetail = listOrderDetail;
	}
	public OrderHeader getmOrderHeader() {
		return mOrderHeader;
	}
	public void setmOrderHeader(OrderHeader mOrderHeader) {
		this.mOrderHeader = mOrderHeader;
	}
}
