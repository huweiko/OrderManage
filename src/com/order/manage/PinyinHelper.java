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
		mulitVoiceMap.put("晟", "cheng");
		mulitVoiceMap.put("种", "chong");
		mulitVoiceMap.put("单", "shan");
		mulitVoiceMap.put("解", "xie");
		mulitVoiceMap.put("查", "zha");
		mulitVoiceMap.put("曾", "zeng");
		mulitVoiceMap.put("盖", "ge");
		mulitVoiceMap.put("缪", "miao");
		mulitVoiceMap.put("朴", "piao");
		mulitVoiceMap.put("区", "ou");
		mulitVoiceMap.put("繁", "po");
		mulitVoiceMap.put("仇", "qiu");
		mulitVoiceMap.put("么", "yao");
		mulitVoiceMap.put("召", "shao");
		mulitVoiceMap.put("乐", "yue");
		mulitVoiceMap.put("翟", "zhai");
		mulitVoiceMap.put("覃", "qin");
		mulitVoiceMap.put("宓", "fu");
		mulitVoiceMap.put("虎", "mao");
		mulitVoiceMap.put("仉", "zhang");
		mulitVoiceMap.put("祭", "zhai");
		mulitVoiceMap.put("褚", "chu");
		mulitVoiceMap.put("郇", "huan");
		mulitVoiceMap.put("洗", "xian");
		mulitVoiceMap.put("眭", "sui");
		mulitVoiceMap.put("阚", "kan");
		mulitVoiceMap.put("秘", "bi");
		mulitVoiceMap.put("弗", "fei");
		mulitVoiceMap.put("藉", "ji");
		mulitVoiceMap.put("适", "kuo");
		mulitVoiceMap.put("句", "gou");
		mulitVoiceMap.put("乜", "nie");
		mulitVoiceMap.put("员", "yun");
		mulitVoiceMap.put("厘", "xi");
		mulitVoiceMap.put("宿", "su");
		mulitVoiceMap.put("盛", "sheng");
		mulitVoiceMap.put("折", "she");
		mulitVoiceMap.put("尉迟", "yu 尉 chi 迟 ");
		mulitVoiceMap.put("万俟", "mo 万 qi 俟 ");
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
	 * 将resource.txt文件 以unicode为key pinyin为value 加到hashmap中
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
				// 提取文件流每行中的unicode码以及 pinyin字符串
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
	 * 获取汉字的全拼
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
					unicode = Integer.toHexString(arr[i]);// 得到汉字的unicode编码
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
	 * 将汉语转换为汉语拼音,字母为小写
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
				unicode = Integer.toHexString(arr[i]);// 得到汉字的unicode编码
				pinyin = hashMap.get(unicode);
				if (pinyin == null) {// 特殊符号
					pinyin = arr[i] + "";
				} else {
					pinyin = pinyin + "," + String.valueOf(arr[i]);
				}
				// pinyin = hashMap.get(unicode)+","+String.valueOf(arr[i]);;//
				// 从hashMap中找到汉子的拼音
				result[i] = "," + pinyin;
			} else {
				result[i] = ("," + arr[i]).toLowerCase();
			}
		}
		return result;
	}

	/**
	 * 将汉字或字母转换为在拨号键上对应的数字
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
				unicode = Integer.toHexString(arr[i]);// 得到汉字的unicode编码
				pinyin = hashMap.get(unicode);// 从hashMap中找到汉字的拼音
				if (pinyin == null) {// 没有找到拼音，该字符为特殊符号
					pinyin = "," + arr[i];
				} else {
					pinyin = "," + pinyin;
				}
			} else {
				pinyin = ("," + arr[i]).toLowerCase();
			}
			result[i] = getTotalDigits(pinyin); // 将字母转换成T9数字
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
