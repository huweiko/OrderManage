package com.order.manage.struct;

import java.util.List;


public class StructOrder{
	private String BillId;//单号
	private String OrganCode;//机构号
	private String BillType;//单据类型
	private Double TotalMny;//合计金额
	private String BizDate;//业务日期
	private String BillDate;//单据日期
	private String BillMan;//开单人
	private String Memo;//备注
	private int BillState;//审核状态
	private int Uploaded;//上传标志
	private String EndSaveTime;//最后更新时间
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