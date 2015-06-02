package com.order.manage;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
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
    /**  
       * 字符串转换为java.util.Date<br>  
       * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>  
       * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>  
       * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>  
       * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>  
       * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>  
       * @param time String 字符串<br>  
       * @return Date 日期<br>  
       */  
    public static Date stringToDate(String time){   
        SimpleDateFormat formatter;   
        int tempPos=time.indexOf("AD") ;   
        time=time.trim() ;   
        formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");   
        if(tempPos>-1){   
          time=time.substring(0,tempPos)+   
               "公元"+time.substring(tempPos+"AD".length());//china   
          formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");   
        }   
        tempPos=time.indexOf("-");   
        if(tempPos<0&&(time.indexOf(" ")<0)){   
          formatter = new SimpleDateFormat ("yyyyMMddHHmmssZ");   
        }   
        else if((time.indexOf("/")>-1) &&(time.indexOf(" ")>-1)){   
          formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");   
        }   
        else if((time.indexOf("-")>-1) &&(time.indexOf(" ")>-1)){   
          formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
        }   
        else if((time.indexOf("/")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){   
          formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");   
        }   
        else if((time.indexOf("-")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){   
          formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");   
        }   
        else if((time.indexOf(".")>-1) && (time.indexOf("-")>-1) &&(time.indexOf(" ")>-1)){   
        	formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss.SSS");   
        }   
        else if((time.indexOf(".")>-1) && (time.indexOf("-")>-1) &&(time.indexOf("T")>-1)){ 
        	time = time.replace('T', ' ');
        	formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss.SSS");   
        }   
        ParsePosition pos = new ParsePosition(0);   
        java.util.Date ctime = formatter.parse(time, pos);   
      
        return ctime;   
    }   
      
      
    /**  
       * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>  
       * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>  
       * @param time Date 日期<br>  
       * @return String   字符串<br>  
       */  
         
      
    public static String dateToString(Date time){   
        SimpleDateFormat formatter;   
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");   
        String ctime = formatter.format(time);   
      
        return ctime;   
    }   
      


}