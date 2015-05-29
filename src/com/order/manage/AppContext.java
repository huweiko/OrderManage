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
        PgyCrashManager.register(this,Constant.PgyerAPPID);// �����ѹ�ӢsdkӦ�õ�appId
        //��ʼ��������IP
        String ip = Preference.getSharedPreferences(this).getString(Preference.SERVER_IP, Urls.getInstance().getSERVER_IP());
        Urls.getInstance().setSERVER_IP(ip);
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
