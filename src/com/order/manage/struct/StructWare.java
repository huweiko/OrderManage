package com.order.manage.struct;


public class StructWare extends StructDBInventoryMaster{
	//������ѡ״̬
	private boolean OrderChooseStatus = true;
	//��������
	private int OrderNumber = 1;
	private boolean OrderEditStatus = false; 
	public boolean getOrderChooseStatus() {
		return OrderChooseStatus;
	}
	public void setOrderChooseStatus(boolean orderChooseStatus) {
		OrderChooseStatus = orderChooseStatus;
	}
	public int getOrderNumber() {
		return OrderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		OrderNumber = orderNumber;
	}
	public boolean getOrderEditStatus() {
		return OrderEditStatus;
	}
	public void setOrderEditStatus(boolean orderEditStatus) {
		OrderEditStatus = orderEditStatus;
	}
}