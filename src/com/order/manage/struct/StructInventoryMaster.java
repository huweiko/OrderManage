package com.order.manage.struct;


public class StructInventoryMaster{
	private boolean OrderChooseStatus = true;
	
	private String InvIdCode;//��Ʒ����
	private String InvClassCode;//������
	private String InvClassName;//�������
	private String InvBrandCode;//Ʒ�ƴ���
	private String InvBrandName;//Ʒ������
	private String InvCode;//��Ʒ����
	private String SimpleCode;//ƴ����
	private String InvName;//��Ʒ����
	private String InvBarCode;//����
	private String InvSpec;//���
	private String Unit;//��λ
	private double InPrice;//����
	private double SalePrice;//���ۼ�
	private double MemPrice;//��Ա��
	private String EndSaveTime;//������ʱ��
	private int OrderId;//����
	public String getInvIdCode() {
		return InvIdCode;
	}
	public void setInvIdCode(String invIdCode) {
		InvIdCode = invIdCode;
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
	public String getInvBrandCode() {
		return InvBrandCode;
	}
	public void setInvBrandCode(String invBrandCode) {
		InvBrandCode = invBrandCode;
	}
	public String getInvBrandName() {
		return InvBrandName;
	}
	public void setInvBrandName(String invBrandName) {
		InvBrandName = invBrandName;
	}
	public String getInvCode() {
		return InvCode;
	}
	public void setInvCode(String invCode) {
		InvCode = invCode;
	}
	public String getSimpleCode() {
		return SimpleCode;
	}
	public void setSimpleCode(String simpleCode) {
		SimpleCode = simpleCode;
	}
	public String getInvName() {
		return InvName;
	}
	public void setInvName(String invName) {
		InvName = invName;
	}
	public String getInvBarCode() {
		return InvBarCode;
	}
	public void setInvBarCode(String invBarCode) {
		InvBarCode = invBarCode;
	}
	public String getInvSpec() {
		return InvSpec;
	}
	public void setInvSpec(String invSpec) {
		InvSpec = invSpec;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public double getInPrice() {
		return InPrice;
	}
	public void setInPrice(double inPrice) {
		InPrice = inPrice;
	}
	public double getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(double salePrice) {
		SalePrice = salePrice;
	}
	public double getMemPrice() {
		return MemPrice;
	}
	public void setMemPrice(double memPrice) {
		MemPrice = memPrice;
	}
	public String getEndSaveTime() {
		return EndSaveTime;
	}
	public void setEndSaveTime(String endSaveTime) {
		EndSaveTime = endSaveTime;
	}
	public int getOrderId() {
		return OrderId;
	}
	public void setOrderId(int orderId) {
		OrderId = orderId;
	}
	public boolean getOrderChooseStatus() {
		return OrderChooseStatus;
	}
	public void setOrderChooseStatus(boolean orderChooseStatus) {
		OrderChooseStatus = orderChooseStatus;
	}
}