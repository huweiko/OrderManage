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
     * �ж��Ƿ�Ϊ�Ϸ�IP
     * ����ժ�ģ��Լ���֤�£���ô�ã��ҾͲ���˵�˰ɣ�
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
     * ��������ַ�Ƿ�Ϸ�
     * @param email
     * @return true�Ϸ� false���Ϸ�
     */
    public static boolean isEmail(String email){
          if (null==email || "".equals(email)) return false;  
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //��ƥ��
          Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//����ƥ��
          Matcher m = p.matcher(email);
          return m.matches();
    }
    /**	 * �ַ���ת����ʱ���ʽ	 
     ** @param dateStr ��Ҫת�����ַ���	 
     ** @param formatStr ��Ҫ��ʽ��Ŀ���ַ���  ���� yyyy-MM-dd	 
     ** @return Date ����ת�����ʱ��	 
     ** @throws ParseException ת���쳣	 
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