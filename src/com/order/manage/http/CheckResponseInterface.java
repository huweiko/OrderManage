package com.order.manage.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface CheckResponseInterface {
    /**
     * 状态值，99 正常，非99 异常
     */
    static final String BEF_STATUS = "bef_status";
    /**
     * 服务器返回信息
     */
    static final String BEF_MESSAGE = "bef_message";
    /**
     * 返回数据格式 protobuf/json
     */
    static final String BEF_FORMAT = "bef_format";
    /**
     * 客户端硬版本序号
     */
    static final String BEF_CLIENT_BUILD = "bef_client_build";
    /**
     * 后台动态配置文件序号
     */
    static final String BEF_VERSION_BUILD = "bef_version_build";
    /**
     * 平台
     */
    static final String BEF_PLATFORM = "bef_platform";

    boolean checkResponse(HttpUriRequest request, HttpResponse response) throws SeverRequestException;

	void onCancel(String msg);

}
