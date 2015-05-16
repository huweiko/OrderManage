package com.order.manage.bean;

import org.json.JSONException;
import org.json.JSONObject;
 

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author 
 * 
 * @param <T>
 */
public class Response<T> {

	public Response(String code, String msg) {
		this.respCode = code;
		this.respMsg = msg;
	}

	String respCode;
	String respMsg;
	String valicode;
	T data;
	String token;


	public boolean getResult() {
		if ("E00".equals(respCode)) {
			return true;
		}
		return false;
	}

	public String getMessage() {
		return respMsg;
	}

	public T getResponse() {
		return data;
	}

	public static String setFailStr(String value) {
		JSONObject object = new JSONObject();
		try {
			object.put("msg", value);
			object.put("code", "0000");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	public String getValiCode() {
		return this.valicode;
	}

	public String getCode() {
		return this.respCode;
	}

	public String getToken() {
		return this.token;
	}
}
