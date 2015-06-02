package com.order.manage.struct;


public class StructOrderHeader extends OrderHeader{
	private boolean OrderUnfoldSstatus = false;//¶©µ¥Õ¹¿ª×´Ì¬
	public boolean getOrderUnfoldSstatus() {
		return OrderUnfoldSstatus;
	}
	public void setOrderUnfoldSstatus(boolean orderUnfoldSstatus) {
		OrderUnfoldSstatus = orderUnfoldSstatus;
	}
}