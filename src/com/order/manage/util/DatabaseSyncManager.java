package com.order.manage.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.order.manage.OtherHealper;
import com.order.manage.db.BDInventoryClassBrand;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructDBInventoryMaster;
import com.order.manage.struct.StructInventoryClassBrand;
import com.order.manage.struct.StructOrder;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

public class DatabaseSyncManager{
	private static DatabaseSyncManager instance = null;
	
	private BDInventoryMaster mBDInventoryMaster;
	private Cursor myInventoryMasterCursor;
	
	private BDInventoryClassBrand mBDInventoryClassBrand;
	private Cursor mBDInventoryClassBrandCursor;
	
	private List<StructDBInventoryMaster> mListStructDBInventoryMaster = new ArrayList<StructDBInventoryMaster>();
	private List<StructInventoryClassBrand> mListStructInventoryClassBrand= new ArrayList<StructInventoryClassBrand>();
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
		mListStructDBInventoryMaster.clear();
		mListStructInventoryClassBrand.clear();
		JSONObject jsonObject1 = new JSONObject(waredata);
		String value = jsonObject1.getString("BD_InventoryMaster");
		JSONArray jsonArray = new JSONArray(value);
		for (int i = 0; i< jsonArray.length();i++){
			String json_c = jsonArray.getString(i);
			JSONObject jsonObject = new JSONObject(json_c);
		    StructDBInventoryMaster l_StructDBInventoryMaster = new StructDBInventoryMaster();
		    StructInventoryClassBrand l_StructInventoryClassBrand = new StructInventoryClassBrand();
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) { //先遍历整个 people 对象
			    String key = (String)iter.next();

			    if(key.equals("InvIdCode")){
			    	l_StructDBInventoryMaster.setInvIdCode(jsonObject.getString(key));
			    }
			    else if(key.equals("InvClassCode")){
			    	l_StructDBInventoryMaster.setInvClassCode(jsonObject.getString(key));
			    	l_StructInventoryClassBrand.setInvClassCode(jsonObject.getString(key));
			    }
			    else if(key.equals("InvClassName")){
			    	l_StructDBInventoryMaster.setInvClassName(jsonObject.getString(key));
			    	l_StructInventoryClassBrand.setInvClassName(jsonObject.getString(key));
			    }
			    else if(key.equals("InvBrandCode")){
			    	l_StructDBInventoryMaster.setInvBrandCode(jsonObject.getString(key));
			    }
			    else if(key.equals("InvBrandName")){
			    	l_StructDBInventoryMaster.setInvBrandName(jsonObject.getString(key));
			    }
			    else if(key.equals("InvCode")){
			    	l_StructDBInventoryMaster.setInvCode(jsonObject.getString(key));
			    }
			    else if(key.equals("SimpleCode")){
			    	l_StructDBInventoryMaster.setSimpleCode(jsonObject.getString(key));
			    }
			    else if(key.equals("InvName")){
			    	l_StructDBInventoryMaster.setInvName(jsonObject.getString(key));
			    }
			    else if(key.equals("InvBarCode")){
			    	l_StructDBInventoryMaster.setInvBarCode(jsonObject.getString(key));
			    }
			    else if(key.equals("InvSpec")){
			    	l_StructDBInventoryMaster.setInvSpec(jsonObject.getString(key));
			    }
			    else if(key.equals("Unit")){
			    	l_StructDBInventoryMaster.setUnit(jsonObject.getString(key));
			    }
			    else if(key.equals("InPrice")){
			    	l_StructDBInventoryMaster.setInPrice(jsonObject.getDouble(key));
			    }
			    else if(key.equals("SalePrice")){
			    	l_StructDBInventoryMaster.setSalePrice(jsonObject.getDouble(key));
			    }
			    else if(key.equals("MemPrice")){
			    	l_StructDBInventoryMaster.setMemPrice(jsonObject.getDouble(key));
			    }
			    else if(key.equals("EndSaveTime")){
			    	l_StructDBInventoryMaster.setEndSaveTime(jsonObject.getString(key));
			    	l_StructInventoryClassBrand.setEndSaveTime(jsonObject.getString(key));
			    }
			    else if(key.equals("InvClassIdCode")){
			    	l_StructInventoryClassBrand.setInvClassIdCode(jsonObject.getString(key));
			    }
			    else if(key.equals("ClassOrBrand")){
			    	l_StructInventoryClassBrand.setClassOrBrand(jsonObject.getInt(key));
			    }
			    else if(key.equals("ParentId")){
			    	l_StructInventoryClassBrand.setParentId(jsonObject.getString(key));
			    }
			    System.out.println(key + "#" + jsonObject.getString(key));
			}	
		    mListStructDBInventoryMaster.add(l_StructDBInventoryMaster);
		    mListStructInventoryClassBrand.add(l_StructInventoryClassBrand);
		}
		filterWare(context);
		filterclass(context);
		return true;
	}
	public boolean doSubmitOrder(Context context,StructOrder order,Handler handler){

		return true;
	}
	private void filterWare(Context context){
		if(mListStructDBInventoryMaster.size()>0){
			mBDInventoryMaster = new BDInventoryMaster(context);
			mBDInventoryMaster.createDBtable();
			for(int i = 0;i < mListStructDBInventoryMaster.size();i++){
				myInventoryMasterCursor = mBDInventoryMaster.selectByAttribute(mListStructDBInventoryMaster.get(i).getInvIdCode());
				//判断数据库是否已有改商品
				if(myInventoryMasterCursor.getCount()>0){
					myInventoryMasterCursor.moveToPosition(0);
					myInventoryMasterCursor.getColumnCount();
					String DBEndUpdateString = myInventoryMasterCursor.getString(14);
					String NetEndUpdateTimeString = mListStructDBInventoryMaster.get(i).getEndSaveTime();
					if(DBEndUpdateString == null || NetEndUpdateTimeString == null){
						if(NetEndUpdateTimeString != null){
							mBDInventoryMaster.update(mListStructDBInventoryMaster.get(i), mListStructDBInventoryMaster.get(i).getInvIdCode());
						}
					}else{
						Date DBEndUpdateTime = OtherHealper.stringToDate(DBEndUpdateString);
						Date NetEndUpdateTime = OtherHealper.stringToDate(NetEndUpdateTimeString);
						//判断商品是否需要更新
						if(DBEndUpdateTime == null&&NetEndUpdateTime == null){
							Log.e(TAG, "商品更新时间为空");
						}else{
							if(NetEndUpdateTime.getTime()>DBEndUpdateTime.getTime()){
								mBDInventoryMaster.update(mListStructDBInventoryMaster.get(i), mListStructDBInventoryMaster.get(i).getInvIdCode());
							}
						}
					}


				}else{//数据库里面没有的商品就插入到数据库
					mBDInventoryMaster.insert(mListStructDBInventoryMaster.get(i));
				}
				if(myInventoryMasterCursor != null){
					myInventoryMasterCursor.close();
				}
			}
		}
	}
	private void filterclass(Context context){
		if(mListStructInventoryClassBrand.size()>0){
			mBDInventoryClassBrand = new BDInventoryClassBrand(context);
			mBDInventoryClassBrand.createDBtable();
			for(int i = 0;i < mListStructInventoryClassBrand.size();i++){
				mBDInventoryClassBrandCursor = mBDInventoryClassBrand.selectByAttribute(mListStructInventoryClassBrand.get(i).getInvClassIdCode());
				if(mBDInventoryClassBrandCursor.getCount()>0){
					mBDInventoryClassBrandCursor.moveToPosition(0);
					String DBEndUpdateString = mBDInventoryClassBrandCursor.getString(5);
					String NetEndUpdateTimeString = mListStructInventoryClassBrand.get(i).getEndSaveTime();
					if(DBEndUpdateString == null || NetEndUpdateTimeString == null){
						if(NetEndUpdateTimeString != null){
							mBDInventoryClassBrand.update(mListStructInventoryClassBrand.get(i), mListStructInventoryClassBrand.get(i).getInvClassIdCode());
						}
					}else{
						Date DBEndUpdateTime = OtherHealper.stringToDate(DBEndUpdateString);
						Date NetEndUpdateTime = OtherHealper.stringToDate(NetEndUpdateTimeString);
						//判断商品是否需要更新
						if(DBEndUpdateTime == null&&NetEndUpdateTime == null){
							Log.e(TAG, "类目更新时间为空");
						}else{
							if(NetEndUpdateTime.getTime()>DBEndUpdateTime.getTime()){
								Log.i(TAG, "类目更新:"+mListStructInventoryClassBrand.get(i).getInvClassName());
								mBDInventoryClassBrand.update(mListStructInventoryClassBrand.get(i), mListStructInventoryClassBrand.get(i).getInvClassIdCode());
							}
						}
					}

				}else{
					Log.i(TAG, "新类目插入:"+mListStructInventoryClassBrand.get(i).getInvClassName());
					mBDInventoryClassBrand.insert(mListStructInventoryClassBrand.get(i));
				}
				if(mBDInventoryClassBrandCursor != null){
					mBDInventoryClassBrandCursor.close();
				}
			}
			
		}
	}
}