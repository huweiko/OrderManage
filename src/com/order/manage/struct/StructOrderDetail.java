package com.order.manage.struct;


public class StructOrderDetail{
	private int iId; //id
	private String BillId;//单号
	private int ItemId;//明细id
	private String InvIdCode;//商品内码
	private String	InvClassIdCode;//类别内码
	private String	InvBrandIdCode;//品牌内码
	private String	InvClassCode;//商品代码
	private String	InvBrandCode;//品牌代码
	private String	InvCode;//商品代码
	private String InvBarCode;//条码
	private String SimpleCode;//拼音码
	private String InvName;//商品名称
	private String Unit;//单位
	private Double Factor;//换算率默认1
	private int Num;//数量
	private Double Price;//单价
	private Double OrderMny;//小计
	private String SUnit;//单位2
	private int TotalNum;//合计数量
	private String Memo;//备忘录
	private int UpLoad;//选中
	private int Uploaded;//上传标志
	private String EndSaveTime;//最后更新时间
	public int getiId() {
		return iId;
	}
	public void setiId(int iId) {
		this.iId = iId;
	}
	public String getBillId() {
		return BillId;
	}
	public void setBillId(String billId) {
		BillId = billId;
	}
	public int getItemId() {
		return ItemId;
	}
	public void setItemId(int itemId) {
		ItemId = itemId;
	}
	public String getInvIdCode() {
		return InvIdCode;
	}
	public void setInvIdCode(String invIdCode) {
		InvIdCode = invIdCode;
	}
	public String getInvClassIdCode() {
		return InvClassIdCode;
	}
	public void setInvClassIdCode(String invClassIdCode) {
		InvClassIdCode = invClassIdCode;
	}
	public String getInvBrandIdCode() {
		return InvBrandIdCode;
	}
	public void setInvBrandIdCode(String invBrandIdCode) {
		InvBrandIdCode = invBrandIdCode;
	}
	public String getInvClassCode() {
		return InvClassCode;
	}
	public void setInvClassCode(String invClassCode) {
		InvClassCode = invClassCode;
	}
	public String getInvBrandCode() {
		return InvBrandCode;
	}
	public void setInvBrandCode(String invBrandCode) {
		InvBrandCode = invBrandCode;
	}
	public String getInvCode() {
		return InvCode;
	}
	public void setInvCode(String invCode) {
		InvCode = invCode;
	}
	public String getInvBarCode() {
		return InvBarCode;
	}
	public void setInvBarCode(String invBarCode) {
		InvBarCode = invBarCode;
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
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public Double getFactor() {
		return Factor;
	}
	public void setFactor(Double factor) {
		Factor = factor;
	}
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	public Double getPrice() {
		return Price;
	}
	public void setPrice(Double price) {
		Price = price;
	}
	public Double getOrderMny() {
		return OrderMny;
	}
	public void setOrderMny(Double orderMny) {
		OrderMny = orderMny;
	}
	public String getSUnit() {
		return SUnit;
	}
	public void setSUnit(String sUnit) {
		SUnit = sUnit;
	}
	public int getTotalNum() {
		return TotalNum;
	}
	public void setTotalNum(int totalNum) {
		TotalNum = totalNum;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public int getUpLoad() {
		return UpLoad;
	}
	public void setUpLoad(int upLoad) {
		UpLoad = upLoad;
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
}