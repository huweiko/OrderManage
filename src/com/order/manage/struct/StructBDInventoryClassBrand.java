package com.order.manage.struct;

import java.util.ArrayList;
import java.util.List;

public class StructBDInventoryClassBrand{
	private String InvClassIdCode;//����
	private int ClassOrBrand;//������Ʒ��
	private String InvClassCode;//������
	private String InvClassName;//�������
	private String ParentId;//��id  
	private String EndSaveTime;//������ʱ��
	private int OrderId;//����
	
	private List<StructInventoryMaster> mBDInventoryClassBrand = new ArrayList<StructInventoryMaster>();
	public String getInvClassIdCode() {
		return InvClassIdCode;
	}
	public void setInvClassIdCode(String invClassIdCode) {
		InvClassIdCode = invClassIdCode;
	}
	public String getInvClassCode() {
		return InvClassCode;
	}
	public void setInvClassCode(String invClassCode) {
		InvClassCode = invClassCode;
	}
	public String getInvClassName() {
		return InvClassName;
	}
	public void setInvClassName(String invClassName) {
		InvClassName = invClassName;
	}
	public String getParentId() {
		return ParentId;
	}
	public void setParentId(String parentId) {
		ParentId = parentId;
	}
	public String getEndSaveTime() {
		return EndSaveTime;
	}
	public void setEndSaveTime(String endSaveTime) {
		EndSaveTime = endSaveTime;
	}
	public List<StructInventoryMaster> getmBDInventoryClassBrand() {
		return mBDInventoryClassBrand;
	}
	public void setmBDInventoryClassBrand(List<StructInventoryMaster> mBDInventoryClassBrand) {
		this.mBDInventoryClassBrand = mBDInventoryClassBrand;
	}
	public void addmBDInventoryClassBrand(StructInventoryMaster mBDInventoryClassBrand){
		this.mBDInventoryClassBrand.add(mBDInventoryClassBrand);
	}
	public int getClassOrBrand() {
		return ClassOrBrand;
	}
	public void setClassOrBrand(int classOrBrand) {
		ClassOrBrand = classOrBrand;
	}
	public int getOrderId() {
		return OrderId;
	}
	public void setOrderId(int orderId) {
		OrderId = orderId;
	}
}