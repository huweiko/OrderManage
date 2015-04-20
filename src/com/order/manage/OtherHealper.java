package com.order.manage;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class OtherHealper{
	/**
     * 判断是否为合法IP
     * 网上摘的，自己验证下，怎么用，我就不用说了吧？
     * @return true or false
     */
    public static boolean isIpv4(String ipAddress) {
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
          if (null==email || "".equals(email)) return false;  
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
          Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
          Matcher m = p.matcher(email);
          return m.matches();
    }
    /**	 * 字符串转换到时间格式	 
     ** @param dateStr 需要转换的字符串	 
     ** @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd	 
     ** @return Date 返回转换后的时间	 
     ** @throws ParseException 转换异常	 
     **/	
    @SuppressLint("SimpleDateFormat") 
    public static Date StringToDate(String dateStr,String formatStr){
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    	Date date= null;	
    	try {			
    		date = sdf.parse(dateStr);		
    	} catch (ParseException e) {
    		e.printStackTrace();		
    	}		
    	return date;	
    }

}