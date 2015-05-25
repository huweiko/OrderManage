package com.order.manage.util;

import java.util.Iterator;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructOrder;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

public class DatabaseSyncManager{
	private static DatabaseSyncManager instance = null;
	
	private BDInventoryMaster mBDInventoryMaster;
	private Cursor myInventoryMasterCursor;
	public static final int POST_PROGRESS_NOTIFY = 101;
	private static final String TAG = DatabaseSyncManager.class.getName();
	private DatabaseSyncManager(){
		
	}
	/**
	 * Factory method
	 */
	public static synchronized DatabaseSyncManager getInstance(){
		if(instance == null){
			instance = new DatabaseSyncManager();
		}
		return instance;
	}
	//更新商品，同步到数据库
	@SuppressWarnings("rawtypes")
	public boolean doSyncData(Context context,String waredata,Handler handler) throws JSONException {
		JSONObject jsonObject1 = new JSONObject(waredata);
		String value = jsonObject1.getString("BD_InventoryMaster");
		JSONArray jsonArray = new JSONArray(value);
		for (int i = 0; i< jsonArray.length();i++){
			String json_c = jsonArray.getString(i);
			JSONObject jsonObject = new JSONObject(json_c);
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) { //先遍历整个 people 对象
			    String key = (String)iter.next();
			    System.out.println(key + "#" + jsonObject.getString(key));
			}	
		}
/*
		mBDInventoryMaster = new BDInventoryMaster(context);
		mBDInventoryMaster.createDBtable();
		for(int i = 0;i < waredata.size();i++){
			myInventoryMasterCursor = mBDInventoryMaster.selectByAttribute(waredata.get(i).getInvIdCode());
			//判断数据库是否已有改商品
			if(myInventoryMasterCursor.getCount()>0){
				myInventoryMasterCursor.moveToPosition(i);
				String DBEndUpdateString = myInventoryMasterCursor.getString(14);
				String NetEndUpdateTimeString = waredata.get(i).getEndSaveTime();
				Date DBEndUpdateTime = OtherHealper.stringToDate(DBEndUpdateString);
				Date NetEndUpdateTime = OtherHealper.stringToDate(NetEndUpdateTimeString);
				//判断商品是否需要更新
				if(DBEndUpdateTime == null&&NetEndUpdateTime == null){
					Log.e(TAG, "商品更新时间为空");
					return false;
				}else{
					if(NetEndUpdateTime.getTime()>DBEndUpdateTime.getTime()){
						mBDInventoryMaster.update(waredata.get(i), waredata.get(i).getInvIdCode());
					}
				}

			}else{//数据库里面没有的商品就插入到数据库
				mBDInventoryMaster.insert(waredata.get(i));
			}
		}
		*/
		return true;
	}
	public boolean doSubmitOrder(Context context,StructOrder order,Handler handler){

		return true;
	}
}