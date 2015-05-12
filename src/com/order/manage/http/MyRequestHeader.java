package com.order.manage.http;

import org.apache.http.HttpRequest;

import android.content.Context;
import android.text.TextUtils;
import com.order.manage.AppContext;



public class MyRequestHeader extends BaseReqeustHeader{ 

    public MyRequestHeader(Context context)
    {
        super(context); 
    }

    @Override
    public String getUid() {
		return null;
    }

    public static String getToken() {
    	if(AppContext.getInstance().getUser() != null){
    		return AppContext.getInstance().getUser().getToken();
    	}
    	return null;
    }

    @Override
    public String getLongitude() {
		return null;
    }

    @Override
    public String getLatitude() {
		return null;
    }

    @Override
    public String getPushToken() {
		return null;
    }

    @Override
    public String getSex() {
		return null;
    }

    @Override
    public String getLang() {
		return null;
    }
    
	public static String getUserName() {
		if(AppContext.getInstance().getUser() != null){
			return AppContext.getInstance().getUser().getUserName();
		}
		return null;
	}
    
    public static void addHeaders(HttpRequest request, AbsReqeustHeader headers) {
		if (!TextUtils.isEmpty(getUserName())) {
			request.addHeader("p", getUserName());
		}
		if (!TextUtils.isEmpty(getToken())) {
			request.addHeader("token", getToken());
		}
    }

}
