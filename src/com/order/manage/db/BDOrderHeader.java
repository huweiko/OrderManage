package com.order.manage.db;


import com.order.manage.struct.StructOrderHeader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDOrderHeader extends DBHelper {
	private final static String TABLE_NAME="CO_Order";//表名
	
	private final static String BillId="BillId";//单号
	private final static String OrganCode="OrganCode";//机构号
	private final static String BillType="BillType";//单据类型
	private final static String TotalMny="TotalMny";//合计金额
	private final static String BizDate="BizDate";//业务日期
	private final static String BillDate="BillDate";//单据日期
	private final static String BillMan="BillMan";//开单人
	private final static String Memo="Memo";//备注
	private final static String BillState="BillState";//审核状态
	private final static String Uploaded="Uploaded";//上传标志
	private final static String EndSaveTime="EndSaveTime";//最后更新时间
	
	public BDOrderHeader(Context context)
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
            			BillId+" TEXT,"+
            			OrganCode+" TEXT,"+
            			BillType+" TEXT,"+
            			TotalMny+" REAL,"+
            			BizDate+" TEXT,"+
            			BillDate+" TEXT,"+
            			BillMan+" TEXT,"+
            			Memo+" TEXT,"+
            			BillState+" INTEGER,"+
            			Uploaded+" INTEGER,"+
            			EndSaveTime+" TEXT,primary key(BillId))";
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
	public long insert(StructOrderHeader mStructOrderHeader)
	{
		ContentValues cv=new ContentValues(); 
		cv.put(BillId, mStructOrderHeader.getBillId());
		cv.put(OrganCode, mStructOrderHeader.getOrganCode());
		cv.put(BillType, mStructOrderHeader.getBillType());
		cv.put(TotalMny, mStructOrderHeader.getTotalMny());
		cv.put(BizDate, mStructOrderHeader.getBizDate());
		cv.put(BillDate, mStructOrderHeader.getBillDate());
		cv.put(BillMan, mStructOrderHeader.getBillMan());
		cv.put(Memo, mStructOrderHeader.getMemo());
		cv.put(BillState, mStructOrderHeader.getBillState());
		cv.put(Uploaded, mStructOrderHeader.getUploaded());
		cv.put(EndSaveTime, mStructOrderHeader.getEndSaveTime());
		return super.insert(TABLE_NAME, cv);
	}
	
	public void update(StructOrderHeader mStructOrderHeader,String x_BillId)
	{
		String [] whereValue = {x_BillId};
		String where = BillId + "=?";
		ContentValues cv=new ContentValues(); 
		cv.put(BillId, mStructOrderHeader.getBillId());
		cv.put(OrganCode, mStructOrderHeader.getOrganCode());
		cv.put(BillType, mStructOrderHeader.getBillType());
		cv.put(TotalMny, mStructOrderHeader.getTotalMny());
		cv.put(BizDate, mStructOrderHeader.getBizDate());
		cv.put(BillDate, mStructOrderHeader.getBillDate());
		cv.put(BillMan, mStructOrderHeader.getBillMan());
		cv.put(Memo, mStructOrderHeader.getMemo());
		cv.put(BillState, mStructOrderHeader.getBillState());
		cv.put(Uploaded, mStructOrderHeader.getUploaded());
		cv.put(EndSaveTime, mStructOrderHeader.getEndSaveTime());
		super.update(TABLE_NAME, where, whereValue, cv);
	}
	
}
