package com.order.manage.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpRequest;

import com.order.manage.AppContext;
import com.order.manage.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.TextUtils;
 

public class BaseReqeustHeader implements AbsReqeustHeader {

    // �����ַ���
    private static String market = "�г�";
    private static String device = "Android";

    // ϵͳ����
    private static String udid; // imsi
    private static String openudid; // imei
    private static String mac; // mac��ַ
    private static String deviceVer; // �豸����ϵͳ�汾
    private static String model; // �豸����
    private static String screenwidth; // ��Ļ��
    private static String screenheight; // ��Ļ��
    private static String screenDensity; // ��Ļ�ܶ�
    private static String appVer; // app�汾
    private static String crack; // �Ƿ�root
    private static String tel; // �ֻ�����
    private static String telcom; // ��Ӫ������

    // ϵͳ���� ���ü���
    private static String country; // ����
    private static String lang; // ����
    private static String screenOrientation; // ��Ļ����
    private static String net; // ��������

    // �������������� appentry.php��ȡ
    private static String dkey; // �豸��Ψһʶ����
    private static String source; // �û������ڲ���Դ
    private Context context;
    SharedPreferences prefs;

    public BaseReqeustHeader(Context context)
    {
        this.context = context;
        prefs = Constant.Preference.getSharedPreferences(context);
        model = android.os.Build.MODEL;

//        IntentFilter filter = new IntentFilter();

    }

    public void unRegisterchangedReceive() {
        context.unregisterReceiver(changedReceiver);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getCmpId() {
        return "";
    }

    @Override
    public String getTelCom() {
        return telcom;
    }

    @Override
    public String getNetType() {
        return net;
    }

    @Override
    public String getScreenScale() {
        return screenDensity;
    }

    @Override
    public String getScreenRotate() {
        return screenOrientation;
    }

    @Override
    public String getScreenHeight() {
        return screenheight;
    }

    @Override
    public String getScreenWidth() {
        return screenwidth;
    }

    @Override
    public String getCrack() {
        return crack;
    }

    @Override
    public String getDeviceVer() {
        return deviceVer;
    }

    @Override
    public String getDevice() {
        return device;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getAppVer() {
        return appVer;
    }

    @Override
    public String getMarket() {
        return market;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getTel() {
        return AppContext.getInstance().getUser().getUserName();
    }

    @Override
    public String getMAC() {
        return mac;
    }

    @Override
    public String getIMEI() {
        return openudid;
    }

    public String getIMSI() {
        return udid;
    }

    @Override
    public String getPushToken() {
        // TODO ��ȡpushToken
        return "";
    }

    @Override
    public String getSource() {
        // TODO ��ȡsource
        return source;
    }

    @Override
    public String getDKey() {
        // TODO ��ȡDKey
        return dkey;
    }

    @Override
    public String getUnique() {
        // TODO ��ȡunique
        return "";
    }

    @Override
    public String getFrom() {
        // TODO ��ȡ�����ṩ�̷ֳ�ID �������ں���Ƶ�����ṩ���ڹ��ֲ���Ͻ��зֳ�
        return "";
    }

    @Override
    public String getModule() {
        // TODO ����Ӧ��ģ������ ����λ�����е�ģ�����ƶ�Ӧ
        return "";
    }

    @Override
    public String getAge() {
        // TODO ��ȡ age
        return "";
    }

    @Override
    public String getSDKVer() {
        // TODO SDK �汾��
        return "";
    }

    @Override
    public String getAppKey() {
        // TODO app ʶ����
        return "";
    }

    @Override
    public String getCorpId() {
        // TODO ��ҵid
        return "";
    }

    private BroadcastReceiver changedReceiver = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().compareTo(Intent.ACTION_LOCALE_CHANGED) == 0) {
                // ���� ���Һ�����
//                lang = DeviceConfiger.getLanguage();
//                country = DeviceConfiger.getCountry();
            } else if (intent.getAction().compareTo(Intent.ACTION_CONFIGURATION_CHANGED) == 0) {
                // ������Ļ����
//                screenOrientation = String.valueOf(DeviceConfiger.getScreenOrientation());
            } else if (intent.getAction().compareTo("android.net.conn.CONNECTIVITY_CHANGE") == 0) {
                // ��������״̬
//                net = DeviceConfiger.getNetTypeInChina();
            }
        }
    };

    public String parmaURLEncode(String params) {
        try {
        	if(!TextUtils.isEmpty(params)){
        		return URLEncoder.encode(params, "utf-8");
        	}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public String getUid() {
        return "";
    }


    @Override
    public String getSex() {
        return "";
    }

    /**
     * ��ǰ��������(����)
     * 
     * @return
     */
    public String getLongitude() {
        return "";
    };

    /**
     * ��ǰ�������� (γ��)
     * 
     * @return
     */
    public String getLatitude() {
        return "";
    };

    /**
     * 
     * @return
     */
    public String getADSID() {
        return "";
    }

    /**
     * 
     * @return
     */
    public String getADID() {
        return "";
    }

    /**
     * 
     * @return
     */
    public String getUseragent() {
        return "";
    };

//    public static void addHeaders(HttpRequest request, AbsReqeustHeader headers) {
    	 
        //

//        if (!TextUtils.isEmpty(headers.getIMSI())) {
//            request.addHeader("bef-udid", headers.getIMSI());
//        }
//        if (!TextUtils.isEmpty(headers.getIMEI())) {
//            request.addHeader("bef-openudid", headers.getIMEI());
//        }
//        if (!TextUtils.isEmpty(headers.getADID())) {
//            request.addHeader("bef-dadid", headers.getADID());
//        }
//        if (!TextUtils.isEmpty(headers.getADSID())) {
//            request.addHeader("bef-dasid", headers.getADSID());
//        }
//        if (!TextUtils.isEmpty(headers.getMAC())) {
//            request.addHeader("bef-mac", headers.getMAC());
//        }
//        if (!TextUtils.isEmpty(headers.getTel())) {
//            request.addHeader("bef-tel", headers.getTel());
//        }
//        if (!TextUtils.isEmpty(headers.getCorpId())) {
//            request.addHeader("bef-corpid", headers.getCorpId());
//        }
//        if (!TextUtils.isEmpty(headers.getCountry())) {
//            request.addHeader("bef-country", headers.getCountry());
//        }
//        if (!TextUtils.isEmpty(headers.getLang())) {
//            request.addHeader("bef-lang", headers.getLang());
//        }
//        if (!TextUtils.isEmpty(headers.getAppKey())) {
//            request.addHeader("bef-appkey", headers.getAppKey());
//        }
//        if (!TextUtils.isEmpty(headers.getAppVer())) {
//            request.addHeader("bef-appver", headers.getAppVer());
//        }
//        if (!TextUtils.isEmpty(headers.getMarket())) {
//            request.addHeader("bef-market", headers.getMarket());
//        }
//        if (!TextUtils.isEmpty(headers.getDevice())) {
//            request.addHeader("bef-device", headers.getDevice());
//        }
//        if (!TextUtils.isEmpty(headers.getDeviceVer())) {
//            request.addHeader("bef-devicever", headers.getDeviceVer());
//        }
//        if (!TextUtils.isEmpty(headers.getModel())) {
//            request.addHeader("bef-model", headers.getModel());
//        }
//        if (!TextUtils.isEmpty(headers.getCrack())) {
//            request.addHeader("bef-crack", headers.getCrack());
//        }
//        if (!TextUtils.isEmpty(headers.getSDKVer())) {
//            request.addHeader("bef-sdkver", headers.getSDKVer());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenWidth())) {
//            request.addHeader("bef-screenwidth", headers.getScreenWidth());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenHeight())) {
//            request.addHeader("bef-screenheight", headers.getScreenHeight());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenRotate())) {
//            request.addHeader("bef-screenrotate", headers.getScreenRotate());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenScale())) {
//            request.addHeader("bef-screenscale", headers.getScreenScale());
//        }
//        if (!TextUtils.isEmpty(headers.getNetType())) {
//            request.addHeader("bef-net", headers.getNetType());
//        }
//        if (!TextUtils.isEmpty(headers.getSex())) {
//            request.addHeader("bef-sex", headers.getSex());
//        }
//        if (!TextUtils.isEmpty(headers.getAge())) {
//            request.addHeader("bef-age", headers.getAge());
//        }
//        if (!TextUtils.isEmpty(headers.getModule())) {
//            request.addHeader("bef-module", headers.getModule());
//        }
//        if (!TextUtils.isEmpty(headers.getFrom())) {
//            request.addHeader("bef-from", headers.getFrom());
//        }
//        if (!TextUtils.isEmpty(headers.getUseragent())) {
//            request.addHeader("bef-useragent", headers.getUseragent());
//        }
//        if (!TextUtils.isEmpty(headers.getUnique())) {
//            request.addHeader("bef-unique", headers.getUnique());
//        }
//        if (!TextUtils.isEmpty(headers.getUid())) {
//            request.addHeader("bef-uid", headers.getUid());
//        }
//        if (!TextUtils.isEmpty(headers.getUserToken())) {
//            request.addHeader("bef-utoken", headers.getUserToken());
//        }
//        if (!TextUtils.isEmpty(headers.getUid())) {
//            request.addHeader("bef-cmpid", headers.getCmpId());
//        }
//        if (!TextUtils.isEmpty(headers.getPushToken())) {
//            request.addHeader("bef-token", headers.getPushToken());
//        }
//        if (!TextUtils.isEmpty(headers.getDKey())) {
//            request.addHeader("bef-dkey", headers.getDKey());
//        }
//        if (!TextUtils.isEmpty(headers.getSource())) {
//            request.addHeader("bef-source", headers.getSource());
//        }
//        if (!TextUtils.isEmpty(headers.getTelCom())) {
//            request.addHeader("bef-telcom", headers.getTelCom());
//        }
//        if (!TextUtils.isEmpty(headers.getLatitude())) {
//            request.addHeader("bef-gpsx", headers.getLatitude());
//        }
//        if (!TextUtils.isEmpty(headers.getLongitude())) {
//            request.addHeader("bef-gpxy", headers.getLongitude());
//        }
//    }

    public static Map<String, String> getHttpHeadersMaps(Map<String, String> map, AbsReqeustHeader headers) {
//        if (map == null) {
//            map = new HashMap<String, String>();
//        }
//        if (!TextUtils.isEmpty(headers.getIMSI())) {
//            map.put("bef-udid", headers.getIMSI());
//        }
//        if (!TextUtils.isEmpty(headers.getIMEI())) {
//            map.put("bef-openudid", headers.getIMEI());
//        }
//        if (!TextUtils.isEmpty(headers.getADID())) {
//            map.put("bef-dadid", headers.getADID());
//        }
//        if (!TextUtils.isEmpty(headers.getADSID())) {
//            map.put("bef-dasid", headers.getADSID());
//        }
//        if (!TextUtils.isEmpty(headers.getMAC())) {
//            map.put("bef-mac", headers.getMAC());
//        }
//        if (!TextUtils.isEmpty(headers.getTel())) {
//            map.put("bef-tel", headers.getTel());
//        }
//        if (!TextUtils.isEmpty(headers.getCorpId())) {
//            map.put("bef-corpid", headers.getCorpId());
//        }
//        if (!TextUtils.isEmpty(headers.getCountry())) {
//            map.put("bef-country", headers.getCountry());
//        }
//        if (!TextUtils.isEmpty(headers.getLang())) {
//            map.put("bef-lang", headers.getLang());
//        }
//        if (!TextUtils.isEmpty(headers.getAppKey())) {
//            map.put("bef-appkey", headers.getAppKey());
//        }
//        if (!TextUtils.isEmpty(headers.getAppVer())) {
//            map.put("bef-appver", headers.getAppVer());
//        }
//        if (!TextUtils.isEmpty(headers.getMarket())) {
//            map.put("bef-market", headers.getMarket());
//        }
//        if (!TextUtils.isEmpty(headers.getDevice())) {
//            map.put("bef-device", headers.getDevice());
//        }
//        if (!TextUtils.isEmpty(headers.getDeviceVer())) {
//            map.put("bef-devicever", headers.getDeviceVer());
//        }
//        if (!TextUtils.isEmpty(headers.getModel())) {
//            map.put("bef-model", headers.getModel());
//        }
//        if (!TextUtils.isEmpty(headers.getCrack())) {
//            map.put("bef-crack", headers.getCrack());
//        }
//        if (!TextUtils.isEmpty(headers.getSDKVer())) {
//            map.put("bef-sdkver", headers.getSDKVer());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenWidth())) {
//            map.put("bef-screenwidth", headers.getScreenWidth());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenHeight())) {
//            map.put("bef-screenheight", headers.getScreenHeight());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenRotate())) {
//            map.put("bef-screenrotate", headers.getScreenRotate());
//        }
//        if (!TextUtils.isEmpty(headers.getScreenScale())) {
//            map.put("bef-screenscale", headers.getScreenScale());
//        }
//        if (!TextUtils.isEmpty(headers.getNetType())) {
//            map.put("bef-net", headers.getNetType());
//        }
//        if (!TextUtils.isEmpty(headers.getSex())) {
//            map.put("bef-sex", headers.getSex());
//        }
//        if (!TextUtils.isEmpty(headers.getAge())) {
//            map.put("bef-age", headers.getAge());
//        }
//        if (!TextUtils.isEmpty(headers.getModule())) {
//            map.put("bef-module", headers.getModule());
//        }
//        if (!TextUtils.isEmpty(headers.getFrom())) {
//            map.put("bef-from", headers.getFrom());
//        }
//        if (!TextUtils.isEmpty(headers.getUseragent())) {
//            map.put("bef-useragent", headers.getUseragent());
//        }
//        if (!TextUtils.isEmpty(headers.getUnique())) {
//            map.put("bef-unique", headers.getUnique());
//        }
//        if (!TextUtils.isEmpty(headers.getUid())) {
//            map.put("bef-uid", headers.getUid());
//        }
//        if (!TextUtils.isEmpty(headers.getToken())) {
//            map.put("bef-utoken", headers.getToken());
//        }
//        if (!TextUtils.isEmpty(headers.getUid())) {
//            map.put("bef-cmpid", headers.getCmpId());
//        }
//        if (!TextUtils.isEmpty(headers.getPushToken())) {
//            map.put("bef-token", headers.getPushToken());
//        }
//        if (!TextUtils.isEmpty(headers.getDKey())) {
//            map.put("bef-dkey", headers.getDKey());
//        }
//        if (!TextUtils.isEmpty(headers.getSource())) {
//            map.put("bef-source", headers.getSource());
//        }
//        if (!TextUtils.isEmpty(headers.getTelCom())) {
//            map.put("bef-telcom", headers.getTelCom());
//        }
//        if (!TextUtils.isEmpty(headers.getLatitude())) {
//            map.put("bef-gpsx", headers.getLatitude());
//        }
//        if (!TextUtils.isEmpty(headers.getLongitude())) {
//            map.put("bef-gpxy", headers.getLongitude());
//        }
        return map;
    }

	 
}
