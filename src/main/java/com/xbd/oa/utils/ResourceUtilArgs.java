package com.xbd.oa.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;


public class ResourceUtilArgs {
	// 资源配置文件的文件名
	private static String RESOURCE_FILENAME;
	private static Map<String, String> cacheKeyValue = new TreeMap<String, String>();
	// 存放全局应用属性配置信息的名、值对
	
	public static void load(String filename){
		if(!StringUtils.isBlank(filename)){
			RESOURCE_FILENAME=filename;
			String name = "";
			String value = "";
			PropertyResourceBundle ldapConfigBundle = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(RESOURCE_FILENAME);
			Enumeration<String> keysLDAP = ldapConfigBundle.getKeys();
			while (keysLDAP.hasMoreElements()) {
				name = keysLDAP.nextElement().toString();
				value = ldapConfigBundle.getString(name);
				cacheKeyValue.put(name, value);
			}
		}
	}
	public static String getString(String key) {
		String result="";
		if (cacheKeyValue.containsKey(key)) {
			try {
				result=new String(cacheKeyValue.get(key).getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		} else {
			PropertyResourceBundle ldapConfigBundle = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(RESOURCE_FILENAME);
			return ldapConfigBundle.getString(key);
		}
	}

}
