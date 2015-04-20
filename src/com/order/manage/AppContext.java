package com.order.manage;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
public class AppContext extends Application{ 
	public String ApplicationName;
	public String AppVesion;
    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        ApplicationName = getString(R.string.app_name);
        try {
			AppVesion = getVersionName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    /*
     * ��ȡӦ�ð汾��
     * return ���ذ汾��
     * */
	public String getVersionName() throws Exception
	{
       // ��ȡpackagemanager��ʵ��
       PackageManager packageManager = getPackageManager();
       // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
       PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
       String version = packInfo.versionName;
       return version;
	}
}
