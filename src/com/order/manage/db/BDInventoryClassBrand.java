package com.order.manage.db;


import com.order.manage.struct.StructInventoryClassBrand;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDInventoryClassBrand extends DBHelper {
	private final static String TABLE_NAME="BD_InventoryClassBrand";//表名
	
	private final static String InvClassIdCode="InvClassIdCode";//内码
	private final static String ClassOrBrand="ClassOrBrand";//类别或者品牌
	private final static String InvClassCode="InvClassCode";//类别代码
	private final static String InvClassName="InvClassName";//类别名称
	private final static String ParentId="ParentId";//父id  
	private final static String EndSaveTime="EndSaveTime";//最后更新时间
	private final static String OrderId="OrderId";//排序

	
	public BDInventoryClassBrand(Context context)
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
            			InvClassIdCode+" TEXT,"+
            			ClassOrBrand+" INTEGER,"+
            			InvClassCode+" TEXT,"+
            			InvClassName+" TEXT,"+
            			ParentId+" TEXT,"+
            			EndSaveTime+" TEXT,"+
            			OrderId+" INTEGER,primary key(InvClassIdCode))";
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
	public Cursor selectByAttribute(String x_InvClassIdCode)
	{
		return super.selectByAttribute(TABLE_NAME,InvClassIdCode,x_InvClassIdCode);
	}
	
	
	public void delete(String x_InvClassIdCode)
	{
		String [] whereValue = {x_InvClassIdCode};
		String where = InvClassIdCode + "=?";
		super.delete(TABLE_NAME, where, whereValue);
	}
	public long insert(StructInventoryClassBrand mStructBDInventoryClassBrand)
	{
		ContentValues cv=new ContentValues(); 
		cv.put(InvClassIdCode, mStructBDInventoryClassBrand.getInvClassIdCode());
		cv.put(ClassOrBrand, mStructBDInventoryClassBrand.getClassOrBrand());
		cv.put(InvClassCode, mStructBDInventoryClassBrand.getInvClassCode());
		cv.put(InvClassName, mStructBDInventoryClassBrand.getInvClassName());
		cv.put(ParentId, mStructBDInventoryClassBrand.getParentId());
		return super.insert(TABLE_NAME, cv);
	}
	
	public void update(StructInventoryClassBrand mStructBDInventoryClassBrand,String x_InvClassIdCode)
	{
		String [] whereValue = {x_InvClassIdCode};
		String where = InvClassIdCode + "=?";
		ContentValues cv=new ContentValues(); 
		cv.put(InvClassIdCode, mStructBDInventoryClassBrand.getInvClassIdCode());
		cv.put(ClassOrBrand, mStructBDInventoryClassBrand.getClassOrBrand());
		cv.put(InvClassCode, mStructBDInventoryClassBrand.getInvClassCode());
		cv.put(InvClassName, mStructBDInventoryClassBrand.getInvClassName());
		cv.put(ParentId, mStructBDInventoryClassBrand.getParentId());
		cv.put(EndSaveTime, mStructBDInventoryClassBrand.getEndSaveTime());
		cv.put(OrderId, mStructBDInventoryClassBrand.getOrderId());

		super.update(TABLE_NAME, where, whereValue, cv);
	}
}
