package com.xbd.oa.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.TreeMap;

public class ResourceUtil {
	// 资源配置文件的文件名
	private static final String APP_RESOURCE_FILENAME = "Resources";

	// 存放全局应用属性配置信息的名、值对
	private static Map<String, String> cacheKeyValue = new TreeMap<String, String>();
	static {
		String name = "";
		String value = "";
		PropertyResourceBundle ldapConfigBundle = (PropertyResourceBundle) PropertyResourceBundle
				.getBundle(APP_RESOURCE_FILENAME);
		Enumeration<String> keysLDAP = ldapConfigBundle.getKeys();
		while (keysLDAP.hasMoreElements()) {
			name = keysLDAP.nextElement().toString();
			value = ldapConfigBundle.getString(name);
			cacheKeyValue.put(name, value);
		}
	}

	public static String getString(String key) {
		if (cacheKeyValue.containsKey(key)) {
			return cacheKeyValue.get(key);
		} else {
			PropertyResourceBundle ldapConfigBundle = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(APP_RESOURCE_FILENAME);
			return ldapConfigBundle.getString(key);
		}
	}
}
