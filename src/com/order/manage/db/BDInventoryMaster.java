package com.order.manage.db;


import com.order.manage.struct.StructDBInventoryMaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDInventoryMaster extends DBHelper {
	private final static String TABLE_NAME="BD_InventoryMaster";//����
	
	private final static String InvIdCode="InvIdCode";//��Ʒ����
	private final static String InvClassCode="InvClassCode";//������
	private final static String InvClassName="InvClassName";//�������
	private final static String InvBrandCode="InvBrandCode";//Ʒ�ƴ���
	private final static String InvBrandName="InvBrandName";//Ʒ������
	private final static String InvCode="InvCode";//��Ʒ����
	private final static String SimpleCode="SimpleCode";//ƴ����
	private final static String InvName="InvName";//��Ʒ����
	private final static String InvBarCode="InvBarCode";//����
	private final static String InvSpec="InvSpec";//���
	private final static String Unit="Unit";//��λ
	private final static String InPrice="InPrice";//����
	private final static String SalePrice="SalePrice";//���ۼ�
	private final static String MemPrice1="MemPrice1";//��Ա��
	private final static String EndSaveTime="EndSaveTime";//������ʱ��
	private final static String OrderId="OrderId";//����
	
	public BDInventoryMaster(Context context)
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
            			InvIdCode+" TEXT,"+
            			InvClassCode+" TEXT,"+
            			InvClassName+" TEXT,"+
            			InvBrandCode+" TEXT,"+
            			InvBrandName+" TEXT,"+
            			InvCode+" TEXT,"+
            			SimpleCode+" TEXT,"+
            			InvName+" TEXT,"+
            			InvBarCode+" TEXT,"+
            			InvSpec+" TEXT,"+
            			Unit+" TEXT,"+
            			InPrice+" REAL,"+
            			SalePrice+" REAL,"+
            			MemPrice1+" REAL,"+
            			EndSaveTime+" TEXT,"+
            			OrderId+" INTEGER,primary key(InvIdCode))";
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
	public Cursor selectByAttribute(String x_invIdCode)
	{
		return super.selectByAttribute(TABLE_NAME,InvIdCode,x_invIdCode);
	}
	//�����������ѯ
	public Cursor selectByInvClassCode(String x_InvClassCode)
	{
		return super.selectByAttribute(TABLE_NAME,InvClassCode,x_InvClassCode);
	}
	//��ѯEndSaveTime����һ���ֶ�
	public Cursor selectMaxTime()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor= db.rawQuery("select * from "+TABLE_NAME+" order by "+EndSaveTime+" desc limit 1",null);
		return cursor;
	}
	
	
	public void delete(String x_invIdCode)
	{
		String [] whereValue = {x_invIdCode};
		String where = InvIdCode + "=?";
		super.delete(TABLE_NAME, where, whereValue);
	}
	public void deleteAll()
	{
		super.deleteAll(TABLE_NAME);
	}
	
	public long insert(StructDBInventoryMaster mStructInventoryMaster)
	{
		ContentValues cv=new ContentValues(); 
		cv.put(InvIdCode, mStructInventoryMaster.getInvIdCode());
		cv.put(InvClassCode, mStructInventoryMaster.getInvClassCode());
		cv.put(InvClassName, mStructInventoryMaster.getInvIdCode());
		cv.put(InvBrandCode, mStructInventoryMaster.getInvClassName());
		cv.put(InvBrandName, mStructInventoryMaster.getInvBrandName());
		cv.put(InvCode, mStructInventoryMaster.getInvCode());
		cv.put(SimpleCode, mStructInventoryMaster.getSimpleCode());
		cv.put(InvName, mStructInventoryMaster.getInvName());
		cv.put(InvBarCode, mStructInventoryMaster.getInvBarCode());
		cv.put(InvSpec, mStructInventoryMaster.getInvSpec());
		cv.put(Unit, mStructInventoryMaster.getUnit());
		cv.put(InPrice, mStructInventoryMaster.getInPrice());
		cv.put(SalePrice, mStructInventoryMaster.getSalePrice());
		cv.put(MemPrice1, mStructInventoryMaster.getMemPrice());
		cv.put(EndSaveTime, mStructInventoryMaster.getEndSaveTime());
		cv.put(OrderId, mStructInventoryMaster.getOrderId());
		return super.insert(TABLE_NAME, cv);
	}
	
	public void update(StructDBInventoryMaster mStructInventoryMaster,String x_invIdCode)
	{
		String [] whereValue = {x_invIdCode};
		String where = InvIdCode + "=?";
		ContentValues cv=new ContentValues(); 
		cv.put(InvIdCode, mStructInventoryMaster.getInvIdCode());
		cv.put(InvClassCode, mStructInventoryMaster.getInvClassCode());
		cv.put(InvClassName, mStructInventoryMaster.getInvIdCode());
		cv.put(InvBrandCode, mStructInventoryMaster.getInvClassName());
		cv.put(InvBrandName, mStructInventoryMaster.getInvBrandName());
		cv.put(InvCode, mStructInventoryMaster.getInvCode());
		cv.put(SimpleCode, mStructInventoryMaster.getSimpleCode());
		cv.put(InvName, mStructInventoryMaster.getInvName());
		cv.put(InvBarCode, mStructInventoryMaster.getInvBarCode());
		cv.put(InvSpec, mStructInventoryMaster.getInvSpec());
		cv.put(Unit, mStructInventoryMaster.getUnit());
		cv.put(InPrice, mStructInventoryMaster.getInPrice());
		cv.put(SalePrice, mStructInventoryMaster.getSalePrice());
		cv.put(MemPrice1, mStructInventoryMaster.getMemPrice());
		cv.put(EndSaveTime, mStructInventoryMaster.getEndSaveTime());
		cv.put(OrderId, mStructInventoryMaster.getOrderId());
		super.update(TABLE_NAME, where, whereValue, cv);
	}
	
}
