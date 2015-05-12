package com.order.manage.http;

public interface AbsReqeustHeader {

    /**
     * 运营商的类型
     * 
     * @return
     */
    String getTelCom();

    /**
     * source 用户的内内部来源 appentry.php获取
     * 
     * @return
     */
    String getSource();

    /**
     * dkey 设备的唯一识别码 appentry.php获取
     * 
     * @return
     */
    String getDKey();

    /**
     * push 的 Token
     * 
     * @return
     */
    String getPushToken();

    /**
     * 当前登录用户的id
     * 
     * @return
     */
    String getUid();

    /**
     * 防重参数，也为了请求到缓存数据。
     * 
     * @return
     */
    String getUnique();

    /**
     * 内容提供商分成ID 比如用于和视频内容提供方在广告植入上进行分成
     * 
     * @return
     */
    String getFrom();

    /**
     * 当前登录用户的年龄
     * 
     * @return
     */
    String getAge();

    /**
     * 当前登录用户的性别
     * 
     * @return
     */
    String getSex();

    /**
     * 网络类型
     * 
     * @return
     */
    String getNetType();

    /**
     * 屏幕 分辨率换算比
     * 
     * @return
     */
    String getScreenScale();

    /**
     * 屏幕方向 、是否横屏
     * 
     * @return
     */
    String getScreenRotate();

    /**
     * 屏幕高度
     * 
     * @return
     */
    String getScreenHeight();

    /**
     * 屏幕宽度
     * 
     * @return
     */
    String getScreenWidth();

    /**
     * SDK版本
     * 
     * @return
     */
    String getSDKVer();

    /**
     * 设备是否ROOT
     * 
     * @return 是 = 1 、 否 = 0
     */
    String getCrack();

    /**
     * 设备型号
     * 
     * @return
     */
    String getModel();

    /**
     * 设备操作系统版本
     * 
     * @return
     */
    String getDeviceVer();

    /**
     * 设备类型
     * 
     * @return
     */
    String getDevice();

    /**
     * 应用渠道号(来源市场） 市场名称需要在广告系统中先登记
     * 
     * @return
     */
    String getMarket();

    /**
     * 应用版本号
     * 
     * @return
     */
    String getAppVer();

    /**
     * 应用识别码
     * 
     * @return
     */
    String getAppKey();

    /**
     * 语种
     * 
     * @return
     */
    String getLang();

    /**
     * 国家
     * 
     * @return
     */
    String getCountry();

    /**
     * 企业ID
     * 
     * @return
     */
    String getCorpId();

    /**
     * 设备电话号码
     * 
     * @return
     */
    String getTel();

    /**
     * MAC地址
     * 
     * @return
     */
    String getMAC();

    /**
     * 设备唯一标示码（IMEI号）
     * 
     * @return
     */
    String getIMEI();

    /**
     * SIM卡唯一标识码（IMSI）
     * 
     * @return
     */
    String getIMSI();

    // ////////////////////////非必须////////////////////////////////
    /**
     * 当前地理坐标(经度)
     * 
     * @return
     */
    String getLongitude();

    /**
     * 当前地理坐标 (纬度)
     * 
     * @return
     */
    String getLatitude();

    /**
     * 
     * @return
     */
    String getADSID();

    /**
     * 
     * @return
     */
    String getADID();

    /**
     * 请求应用模块名称 与广告位配置中的模块限制对应
     * 
     * @return
     */
    String getModule();

    /**
     * 用户登录的token
     * 
     * @return
     */
    String getUseragent();

    String getCmpId();

}
