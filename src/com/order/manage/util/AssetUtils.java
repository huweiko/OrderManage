package com.order.manage.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class AssetUtils {

	
	public static String getDataFromAssets(Context context, String string) {
		String ENCODING = "UTF-8";
		String result = "";
		try {
			InputStream is = context.getAssets().open(string);
			int reads = is.available();
			byte[] buffer = new byte[reads];
			is.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
			result = result.replace("\r\n", "");
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public static String getDataFromAssetsNoChangeLine(Context context, String string) {
		String ENCODING = "UTF-8";
		String result = "";
		try {
			InputStream is = context.getAssets().open(string);
			int reads = is.available();
			byte[] buffer = new byte[reads];
			is.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
//			result = result.replace("\r", "");
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
