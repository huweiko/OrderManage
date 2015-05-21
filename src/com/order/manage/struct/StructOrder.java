package com.order.manage.struct;

import java.util.List;


public class StructOrder{
	private String BillId;//����
	private String OrganCode;//������
	private String BillType;//��������
	private Double TotalMny;//�ϼƽ��
	private String BizDate;//ҵ������
	private String BillDate;//��������
	private String BillMan;//������
	private String Memo;//��ע
	private int BillState;//���״̬
	private int Uploaded;//�ϴ���־
	private String EndSaveTime;//������ʱ��
	private List<StructOrderDetail> ListOrderDetail;
	
	public String getBillId() {
		return BillId;
	}
	public void setBillId(String billId) {
		BillId = billId;
	}
	public String getOrganCode() {
		return OrganCode;
	}
	public void setOrganCode(String organCode) {
		OrganCode = organCode;
	}
	public String getBillType() {
		return BillType;
	}
	public void setBillType(String billType) {
		BillType = billType;
	}
	public Double getTotalMny() {
		return TotalMny;
	}
	public void setTotalMny(Double totalMny) {
		TotalMny = totalMny;
	}
	public String getBizDate() {
		return BizDate;
	}
	public void setBizDate(String bizDate) {
		BizDate = bizDate;
	}
	public String getBillDate() {
		return BillDate;
	}
	public void setBillDate(String billDate) {
		BillDate = billDate;
	}
	public String getBillMan() {
		return BillMan;
	}
	public void setBillMan(String billMan) {
		BillMan = billMan;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public int getBillState() {
		return BillState;
	}
	public void setBillState(int billState) {
		BillState = billState;
	}
	public int getUploaded() {
		return Uploaded;
	}
	public void setUploaded(int uploaded) {
		Uploaded = uploaded;
	}
	public String getEndSaveTime() {
		return EndSaveTime;
	}
	public void setEndSaveTime(String endSaveTime) {
		EndSaveTime = endSaveTime;
	}
	public List<StructOrderDetail> getListOrderDetail() {
		return ListOrderDetail;
	}
	public void setListOrderDetail(List<StructOrderDetail> listOrderDetail) {
		ListOrderDetail = listOrderDetail;
	}
}