package com.order.manage.struct;

import java.util.ArrayList;
import java.util.List;


public class StructOrder{
	private StructOrderHeader mStructOrderHeader;
<<<<<<< HEAD
	private List<StructOrderDetail> ListOrderDetail = new ArrayList<StructOrderDetail>() ;
=======
	private List<StructOrderDetail> ListOrderDetail = new ArrayList<StructOrderDetail>();
>>>>>>> refs/remotes/origin/master
	public StructOrderHeader getmStructOrderHeader() {
		return mStructOrderHeader;
	}
	public void setmStructOrderHeader(StructOrderHeader mStructOrderHeader) {
		this.mStructOrderHeader = mStructOrderHeader;
	}
	public List<StructOrderDetail> getListOrderDetail() {
		return ListOrderDetail;
	}
	public void setListOrderDetail(List<StructOrderDetail> listOrderDetail) {
		ListOrderDetail = listOrderDetail;
	}
}
