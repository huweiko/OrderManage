package com.order.manage.struct;

import java.util.ArrayList;
import java.util.List;

public class StructInventoryClassBrand{
	private String InvClassIdCode;//内码
	private int ClassOrBrand;//类别或者品牌
	private String InvClassCode;//类别代码
	private String InvClassName;//类别名称
	private String ParentId;//父id  
	private String EndSaveTime;//最后更新时间
	private int OrderId;//排序
	
	private List<StructWare> mBDInventoryClassBrand = new ArrayList<StructWare>();
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
	public List<StructWare> getmBDInventoryClassBrand() {
		return mBDInventoryClassBrand;
	}
	public void setmBDInventoryClassBrand(List<StructWare> mBDInventoryClassBrand) {
		this.mBDInventoryClassBrand = mBDInventoryClassBrand;
	}
	public void addmBDInventoryClassBrand(StructWare mBDInventoryClassBrand){
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