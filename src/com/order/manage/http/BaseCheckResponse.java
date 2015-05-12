package com.order.manage.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import android.text.TextUtils;
import android.util.Base64;

public class BaseCheckResponse implements CheckResponseInterface {

    @Override
    public boolean checkResponse(HttpUriRequest request, HttpResponse response) throws SeverRequestException {
        // 解析头信息�?
        Header befStatus = response.getFirstHeader(BEF_STATUS);
        Header befMsg = response.getFirstHeader(BEF_MESSAGE);
        String befStatusStr = "";
        String befMsgStr = "";
        if (befStatus != null) {
            befStatusStr = befStatus.getValue();
        }
        if (befMsg != null) {
            befMsgStr = msgDecode(befMsg.getValue());
        }
        if (!TextUtils.isEmpty(befStatusStr) && !"99".equals(befStatusStr)) {
            int statusInt = parseInt(befStatusStr, -1);
            // 不是99将抛出异常�?
            severRequestFailed(statusInt, befMsgStr);
        }

        return false;
    }

    protected void severRequestFailed(int status, String msg) throws SeverRequestException {
        throw new SeverRequestException(status, msg);
    }

    private int parseInt(String befStatusStr, int defValue) {
        try {
            return Integer.parseInt(befStatusStr);
        } catch (NumberFormatException ex) {
            return defValue;
        }
    }

    private String msgDecode(String befMsgStr) {
        if (befMsgStr == null) {
            return "";
        }

        byte[] varr = null;
        try {
            varr = Base64.decode(befMsgStr, Base64.DEFAULT);
            try {
                befMsgStr = new String(varr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            try {
                befMsgStr = new String(befMsgStr.getBytes("ISO8859-1"), "utf-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        return befMsgStr;
    }

	public void onConfirm(String msg) {
		
	}

	public void onCancel(String msg) {
		
	}
}
