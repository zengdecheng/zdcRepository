package com.xbd.oa.annotation.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.xbd.oa.annotation.EJB;
import com.xbd.oa.annotation.FSP;

public class AnnoHelper {
	
	public static Object getAnnoContent(Object obj, Class clazz) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(clazz)) {
				// parser.dealField(obj, field);
				field.setAccessible(true);
				try {
					return field.get(obj);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		}
		return null;
	}
	
	public static Field getAnnoField(Object obj, Class clazz) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(clazz)) {
				// parser.dealField(obj, field);
				field.setAccessible(true);
				try {
					return field;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		}
		return null;
	}
	
	public static FSP getFspIsPagination(Object obj,String methodName){
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName, null);
			 return method.getAnnotation(FSP.class);
		} catch (Exception e) {
			
		}
		return null;
		
	}
	
	public static void setFspBeanIsPagination(Object obj,String methodName){
		
	}

}
