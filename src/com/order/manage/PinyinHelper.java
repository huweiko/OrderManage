package com.order.manage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.order.manage.AppContext;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;

public class PinyinHelper {
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	private boolean isInitied = false;
	public  boolean isIniting = false;
	static Context context = null;
	private static PinyinHelper pinyinHelper = null;

	private PinyinHelper() {
	}

	public static Map<String, String> mulitVoiceMap = new HashMap<String, String>();
	static {
		mulitVoiceMap.put("��", "cheng");
		mulitVoiceMap.put("��", "chong");
		mulitVoiceMap.put("��", "shan");
		mulitVoiceMap.put("��", "xie");
		mulitVoiceMap.put("��", "zha");
		mulitVoiceMap.put("��", "zeng");
		mulitVoiceMap.put("��", "ge");
		mulitVoiceMap.put("��", "miao");
		mulitVoiceMap.put("��", "piao");
		mulitVoiceMap.put("��", "ou");
		mulitVoiceMap.put("��", "po");
		mulitVoiceMap.put("��", "qiu");
		mulitVoiceMap.put("ô", "yao");
		mulitVoiceMap.put("��", "shao");
		mulitVoiceMap.put("��", "yue");
		mulitVoiceMap.put("��", "zhai");
		mulitVoiceMap.put("��", "qin");
		mulitVoiceMap.put("�", "fu");
		mulitVoiceMap.put("��", "mao");
		mulitVoiceMap.put("��", "zhang");
		mulitVoiceMap.put("��", "zhai");
		mulitVoiceMap.put("��", "chu");
		mulitVoiceMap.put("ۨ", "huan");
		mulitVoiceMap.put("ϴ", "xian");
		mulitVoiceMap.put("��", "sui");
		mulitVoiceMap.put("��", "kan");
		mulitVoiceMap.put("��", "bi");
		mulitVoiceMap.put("��", "fei");
		mulitVoiceMap.put("��", "ji");
		mulitVoiceMap.put("��", "kuo");
		mulitVoiceMap.put("��", "gou");
		mulitVoiceMap.put("ؿ", "nie");
		mulitVoiceMap.put("Ա", "yun");
		mulitVoiceMap.put("��", "xi");
		mulitVoiceMap.put("��", "su");
		mulitVoiceMap.put("ʢ", "sheng");
		mulitVoiceMap.put("��", "she");
		mulitVoiceMap.put("ξ��", "yu ξ chi �� ");
		mulitVoiceMap.put("��ٹ", "mo �� qi ٹ ");
	}

	public static PinyinHelper getInstance(Context ctx) {
		if (pinyinHelper == null) {
			pinyinHelper = new PinyinHelper();
			context = ctx;
		}
		return pinyinHelper;
	}
	public InputStream getAssetsStream(String fileName) throws IOException {
		return context.getAssets().open(fileName);
	}
	/**
	 * ��resource.txt�ļ� ��unicodeΪkey pinyinΪvalue �ӵ�hashmap��
	 */
	private void myReader() {
		isIniting = true;
		String s;
		String unicode;
		String pinyin;
		try {
			InputStream inStream = getAssetsStream("resource.txt");
			BufferedReader breader = new BufferedReader(new InputStreamReader(inStream));
//			BufferedReader breader = new BufferedReader(new InputStreamReader(this
//					.getClass().getClassLoader()
//					.getResourceAsStream("resource.txt")));
			while ((s = breader.readLine()) != null) {
				// ��ȡ�ļ���ÿ���е�unicode���Լ� pinyin�ַ���
				unicode = s.substring(0, 4);
				pinyin = s.substring(5, s.length() - 1);
				if (!hashMap.containsKey(unicode)) {
					hashMap.put(unicode, pinyin);
				}
			}
			breader.close();
			if (!hashMap.isEmpty())
				isInitied = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			isIniting = false;
		}
	}

	/**
	 * ��ȡ���ֵ�ȫƴ
	 * 
	 * @param value
	 * @return
	 */
	public String getFullPinyin(String value) {
		if (!isInitied) {
			myReader();
		}
		char[] arr = value.toCharArray();
		StringBuilder result = new StringBuilder();
		String unicode = "";
		String pinyin = null;
		for (int i = 0; i < value.length(); i++) {
			pinyin = null;
			if (arr[i] > 128) {
				String temp = null;
				if (i == 0) {
					String firstName = arr[0] + "";
					if (mulitVoiceMap.containsKey(firstName)) {
						pinyin = mulitVoiceMap.get(firstName) + " " + firstName
								+ " ";
					} else if (value.length() > 1) {
						firstName = arr[0] + "" + arr[1];
						if (mulitVoiceMap.containsKey(firstName)) {
							pinyin = mulitVoiceMap.get(firstName);
							i++;
						}
					}
				}
				if (TextUtils.isEmpty(pinyin)) {
					unicode = Integer.toHexString(arr[i]);// �õ����ֵ�unicode����
					temp = hashMap.get(unicode);
					if (temp == null) {
						pinyin = arr[i] + "";
					} else {
						pinyin = hashMap.get(unicode).split(",")[0] + " "
								+ arr[i] + " ";
					}
				}
			} else {
				pinyin = arr[i] + "";
			}
			result.append(pinyin);
		}
		return result.toString();
	}

	/**
	 * ������ת��Ϊ����ƴ��,��ĸΪСд
	 * 
	 * @param value
	 * @return pinyin[]
	 */
	public String[] toPinYin(String value) {
		if (!isInitied) {
			myReader();
		}
		char[] arr = value.toCharArray();
		String[] result = new String[value.length()];
		String unicode;
		String pinyin;
		for (int i = 0; i < value.length(); i++) {
			if (arr[i] > 128) {
				unicode = Integer.toHexString(arr[i]);// �õ����ֵ�unicode����
				pinyin = hashMap.get(unicode);
				if (pinyin == null) {// �������
					pinyin = arr[i] + "";
				} else {
					pinyin = pinyin + "," + String.valueOf(arr[i]);
				}
				// pinyin = hashMap.get(unicode)+","+String.valueOf(arr[i]);;//
				// ��hashMap���ҵ����ӵ�ƴ��
				result[i] = "," + pinyin;
			} else {
				result[i] = ("," + arr[i]).toLowerCase();
			}
		}
		return result;
	}

	/**
	 * �����ֻ���ĸת��Ϊ�ڲ��ż��϶�Ӧ������
	 * 
	 * @param value
	 * @return
	 */
	public String[] toPinYinDigits(String value) {
		if (!isInitied) {
			myReader();
		}
		char[] arr = value.toCharArray();
		String[] result = new String[value.length()];
		String unicode;
		String pinyin;
		for (int i = 0; i < value.length(); i++) {
			if (arr[i] > 128) {
				unicode = Integer.toHexString(arr[i]);// �õ����ֵ�unicode����
				pinyin = hashMap.get(unicode);// ��hashMap���ҵ����ֵ�ƴ��
				if (pinyin == null) {// û���ҵ�ƴ�������ַ�Ϊ�������
					pinyin = "," + arr[i];
				} else {
					pinyin = "," + pinyin;
				}
			} else {
				pinyin = ("," + arr[i]).toLowerCase();
			}
			result[i] = getTotalDigits(pinyin); // ����ĸת����T9����
		}
		return result;
	}

	public String getTotalDigits(String pinyin) {
		if (pinyin == null)
			return null;
		if ("".equals(pinyin))
			return "";
		T9Map t9Map = T9Map.DEFAULT;
		int len = pinyin.length();
		StringBuilder str = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			str.append(t9Map.letterToDigits(pinyin.charAt(i)));
		}
		return str.toString();
	}

	static class T9Map {
		public static T9Map DEFAULT = new T9Map();
		SparseIntArray _keyMap;
		StringBuilder sb;

		private T9Map() {
			this._keyMap = new SparseIntArray();
			initKeyMap();
			sb = new StringBuilder();
		}

		private void initKeyMap() {
			_keyMap.put('a', '2');
			_keyMap.put('b', '2');
			_keyMap.put('c', '2');
			_keyMap.put('d', '3');
			_keyMap.put('e', '3');
			_keyMap.put('f', '3');
			_keyMap.put('g', '4');
			_keyMap.put('h', '4');
			_keyMap.put('i', '4');
			_keyMap.put('j', '5');
			_keyMap.put('k', '5');
			_keyMap.put('l', '5');
			_keyMap.put('m', '6');
			_keyMap.put('n', '6');
			_keyMap.put('o', '6');
			_keyMap.put('p', '7');
			_keyMap.put('q', '7');
			_keyMap.put('r', '7');
			_keyMap.put('s', '7');
			_keyMap.put('t', '8');
			_keyMap.put('u', '8');
			_keyMap.put('v', '8');
			_keyMap.put('w', '9');
			_keyMap.put('x', '9');
			_keyMap.put('y', '9');
			_keyMap.put('z', '9');
		}

		public char letterToDigits(char paramChar) {
			return (char) this._keyMap.get(paramChar, paramChar);
		}
	}
}
