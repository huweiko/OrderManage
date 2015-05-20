package com.order.manage.util;

import java.util.Date;
import java.util.List;

import com.order.manage.OtherHealper;
import com.order.manage.db.BDInventoryMaster;
import com.order.manage.struct.StructDBInventoryMaster;

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
	//������Ʒ��ͬ�������ݿ�
	public boolean doSyncData(Context context,List<StructDBInventoryMaster> waredata,Handler handler){
		mBDInventoryMaster = new BDInventoryMaster(context);
		mBDInventoryMaster.createDBtable();
		for(int i = 0;i < waredata.size();i++){
			myInventoryMasterCursor = mBDInventoryMaster.selectByAttribute(waredata.get(i).getInvIdCode());
			//�ж����ݿ��Ƿ����и���Ʒ
			if(myInventoryMasterCursor.getCount()>0){
				myInventoryMasterCursor.moveToPosition(i);
				String DBEndUpdateString = myInventoryMasterCursor.getString(14);
				String NetEndUpdateTimeString = waredata.get(i).getEndSaveTime();
				Date DBEndUpdateTime = OtherHealper.stringToDate(DBEndUpdateString);
				Date NetEndUpdateTime = OtherHealper.stringToDate(NetEndUpdateTimeString);
				//�ж���Ʒ�Ƿ���Ҫ����
				if(DBEndUpdateTime == null&&NetEndUpdateTime == null){
					Log.e(TAG, "��Ʒ����ʱ��Ϊ��");
					return false;
				}else{
					if(NetEndUpdateTime.getTime()>DBEndUpdateTime.getTime()){
						mBDInventoryMaster.update(waredata.get(i), waredata.get(i).getInvIdCode());
					}
				}

			}else{//���ݿ�����û�е���Ʒ�Ͳ��뵽���ݿ�
				mBDInventoryMaster.insert(waredata.get(i));
			}
		}
		
		return true;
	}
}