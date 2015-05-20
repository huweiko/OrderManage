package com.order.manage.util;

import android.content.Context;
import android.os.Handler;

public class DatabaseSyncManager{
	private static DatabaseSyncManager instance = null;
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
	public boolean doSyncData(Context context,,Handler handler){
		return false;
	}
}