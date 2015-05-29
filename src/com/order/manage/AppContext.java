package com.order.manage;

import com.order.manage.Constant.Preference;
import com.order.manage.bean.Urls;
import com.order.manage.http.FinalHttp;
import com.pgyersdk.crash.PgyCrashManager;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
public class AppContext extends Application{ 
	public String ApplicationName;
	public String AppVesion;
	private static AppContext application;
	private static FinalHttp finalHttp;
	private int a = 0;
	public static AppContext getInstance() {
		if(application == null){
			application = new AppContext();
		}
		return application;
	}

	public User getUser() {
		return null;
	}

	public FinalHttp getFinalHttp() {
		if(finalHttp == null){
			finalHttp = new FinalHttp();
		}
		return finalHttp;
	}
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
        PgyCrashManager.register(this,Constant.PgyerAPPID);// 集成蒲公英sdk应用的appId
        //初始化服务器IP
        String ip = Preference.getSharedPreferences(this).getString(Preference.SERVER_IP, Urls.getInstance().getSERVER_IP());
        Urls.getInstance().setSERVER_IP(ip);
    } 
    /*
     * 获取应用版本号
     * return 返回版本号
     * */
	public String getVersionName() throws Exception
	{
       // 获取packagemanager的实例
       PackageManager packageManager = getPackageManager();
       // getPackageName()是你当前类的包名，0代表是获取版本信息
       PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
       String version = packInfo.versionName;
       return version;
	}
}
