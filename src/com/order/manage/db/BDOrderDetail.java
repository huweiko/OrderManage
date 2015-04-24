package com.order.manage.db;


import com.order.manage.struct.StructOrderDetail;
import com.order.manage.struct.StructOrderHeader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDOrderDetail extends DBHelper {
	private final static String TABLE_NAME="CO_OrderD";//����
	
	private final static String iId = "iId"; //id
	private final static String BillId = "BillId";//����
	private final static String ItemId = "ItemId";//��ϸid
	private final static String InvIdCode = "InvIdCode";//��Ʒ����
	private final static String	InvClassIdCode = "InvClassIdCode";//�������
	private final static String	InvBrandIdCode = "InvBrandIdCode";//Ʒ������
	private final static String	InvClassCode = "InvClassCode";//��Ʒ����
	private final static String	InvBrandCode = "InvBrandCode";//Ʒ�ƴ���
	private final static String	InvCode = "InvCode";//��Ʒ����
	private final static String InvBarCode = "InvBarCode";//����
	private final static String SimpleCode = "SimpleCode";//ƴ����
	private final static String InvName = "InvName";//��Ʒ����
	private final static String Unit = "Unit";//��λ
	private final static String Factor = "Factor";//������Ĭ��1
	private final static String Num = "Num";//����
	private final static String Price = "Price";//����
	private final static String OrderMny = "OrderMny";//С��
	private final static String SUnit = "SUnit";//��λ2
	private final static String TotalNum = "TotalNum";//�ϼ�����
	private final static String Memo = "Memo";//����¼
	private final static String UpLoad = "UpLoad";//ѡ��
	private final static String Uploaded = "Uploaded";//�ϴ���־
	private final static String EndSaveTime = "EndSaveTime";//������ʱ��
	
	public BDOrderDetail(Context context)
	{
		super(context);
	}
	public void createDBtable(){
		SQLiteDatabase db=this.getWritableDatabase(); 
		String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+TABLE_NAME+"';";
		Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            int count = cursor.getInt(0);
            if(count>0){

            }else{
            	String sql0="Create table "+TABLE_NAME+"("+
            			iId+" INTEGER,"+
            			BillId+" TEXT,"+
            			ItemId+" INTEGER,"+
            			InvIdCode+" TEXT,"+
            			InvClassIdCode+" TEXT,"+
            			InvBrandIdCode+" TEXT,"+
            			InvClassCode+" TEXT,"+
            			InvBrandCode+" TEXT,"+
            			InvCode+" TEXT,"+
            			InvBarCode+" TEXT,"+
            			SimpleCode+" TEXT,"+
            			InvName+" TEXT,"+
            			Unit+" TEXT,"+
            			Factor+" REAL,"+
            			Num+" INTEGER,"+
            			Price+" REAL,"+
            			OrderMny+" REAL,"+
            			SUnit+" TEXT,"+
            			TotalNum+" INTEGER,"+
            			Memo+" TEXT,"+
            			UpLoad+" INTEGER,"+
            			Uploaded+" INTEGER,"+
            			EndSaveTime+" TEXT,primary key(BillId,ItemId))";
        		super.createDBtable(sql0);
            }
         }
        if(cursor != null){
        	cursor.close();
        }

	}

	public Cursor select()
	{
		return super.select(TABLE_NAME);
	}
	public Cursor selectByAttribute(String x_BillId)
	{
		return super.selectByAttribute(TABLE_NAME,BillId,x_BillId);
	}
	
	public void delete(String x_BillId)
	{
		String [] whereValue = {x_BillId};
		String where = BillId + "=?";
		super.delete(TABLE_NAME, where, whereValue);
	}
	public long insert(StructOrderDetail mStructOrderDetail)
	{
		
		ContentValues cv=new ContentValues(); 
		cv.put(iId, mStructOrderDetail.getiId());
		cv.put(BillId, mStructOrderDetail.getBillId());
		cv.put(ItemId, mStructOrderDetail.getItemId());
		cv.put(InvIdCode, mStructOrderDetail.getInvIdCode());
		cv.put(InvClassIdCode, mStructOrderDetail.getInvClassIdCode());
		cv.put(InvBrandIdCode, mStructOrderDetail.getInvBrandIdCode());
		cv.put(InvClassCode, mStructOrderDetail.getInvClassCode());
		cv.put(InvBrandCode, mStructOrderDetail.getInvBrandCode());
		cv.put(InvCode, mStructOrderDetail.getInvCode());
		cv.put(InvBarCode, mStructOrderDetail.getInvBarCode());
		cv.put(SimpleCode, mStructOrderDetail.getSimpleCode());
		cv.put(InvName, mStructOrderDetail.getInvName());
		cv.put(Unit, mStructOrderDetail.getUnit());
		cv.put(Factor, mStructOrderDetail.getFactor());
		cv.put(Num, mStructOrderDetail.getNum());
		cv.put(Price, mStructOrderDetail.getPrice());
		cv.put(OrderMny, mStructOrderDetail.getOrderMny());
		cv.put(SUnit, mStructOrderDetail.getSUnit());
		cv.put(TotalNum, mStructOrderDetail.getTotalNum());
		cv.put(Memo, mStructOrderDetail.getMemo());
		cv.put(UpLoad, mStructOrderDetail.getUpLoad());
		cv.put(Uploaded, mStructOrderDetail.getUploaded());
		cv.put(EndSaveTime, mStructOrderDetail.getEndSaveTime());
		

		return super.insert(TABLE_NAME, cv);
	}
	
	public void update(StructOrderDetail mStructOrderDetail,String x_BillId)
	{
		String [] whereValue = {x_BillId};
		String where = BillId + "=?";
		ContentValues cv=new ContentValues(); 
		cv.put(iId, mStructOrderDetail.getiId());
		cv.put(BillId, mStructOrderDetail.getBillId());
		cv.put(ItemId, mStructOrderDetail.getItemId());
		cv.put(InvIdCode, mStructOrderDetail.getInvIdCode());
		cv.put(InvClassIdCode, mStructOrderDetail.getInvClassIdCode());
		cv.put(InvBrandIdCode, mStructOrderDetail.getInvBrandIdCode());
		cv.put(InvClassCode, mStructOrderDetail.getInvClassCode());
		cv.put(InvBrandCode, mStructOrderDetail.getInvBrandCode());
		cv.put(InvCode, mStructOrderDetail.getInvCode());
		cv.put(InvBarCode, mStructOrderDetail.getInvBarCode());
		cv.put(SimpleCode, mStructOrderDetail.getSimpleCode());
		cv.put(InvName, mStructOrderDetail.getInvName());
		cv.put(Unit, mStructOrderDetail.getUnit());
		cv.put(Factor, mStructOrderDetail.getFactor());
		cv.put(Num, mStructOrderDetail.getNum());
		cv.put(Price, mStructOrderDetail.getPrice());
		cv.put(OrderMny, mStructOrderDetail.getOrderMny());
		cv.put(SUnit, mStructOrderDetail.getSUnit());
		cv.put(TotalNum, mStructOrderDetail.getTotalNum());
		cv.put(Memo, mStructOrderDetail.getMemo());
		cv.put(UpLoad, mStructOrderDetail.getUpLoad());
		cv.put(Uploaded, mStructOrderDetail.getUploaded());
		cv.put(EndSaveTime, mStructOrderDetail.getEndSaveTime());
		super.update(TABLE_NAME, where, whereValue, cv);
	}
	
}
