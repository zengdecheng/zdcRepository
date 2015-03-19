package com.xbd.oa.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.LazyDynaMap;

/**
 * 
 * @author zhanghu 
 * @since 2014-05-11
 * @param <T>
 */
public class CommonSort<T> {
	
	/**
	 * @param list  		
	 * @param propertyName 	需要排序的属性名
	 * @param order  		1为正序，0为倒叙
	 */
	public void ListPropertySort(List<T> list,String propertyName, Boolean orderBy) {
		PropertyComparator comparator = new PropertyComparator(propertyName,orderBy);
		Collections.sort(list, comparator);
	}
	
	public class PropertyComparator implements Comparator<Object> {
		private String propertyName;
		private Boolean orderBy;
		public PropertyComparator(String propertyName, Boolean orderBy) {
			super();
			this.propertyName = propertyName;
			this.orderBy = orderBy;
		}
		public int compare(Object o1, Object o2) {
			//1.取得o1,o2的class
			//2.根据传入属性，获得属性值 propertyField
			//3.获取属性的类型，依次判断 Sting，Integer，Double，Float，Date
			//4.获取propertyValue，进行比较
			Class<? extends Object> clazz = o1.getClass();
			Double result = null;
			
			if(o1 instanceof LazyDynaMap){
				Object property1 =((LazyDynaMap)o1).get(propertyName);
				Object property2 = ((LazyDynaMap)o2).get(propertyName);
				if(property1 instanceof String){
					result = (double) ((String)property1).compareTo((String)property2);
				}else if(property1 instanceof Integer){
					result = (double) ((Integer) property1-(Integer)property2);
				}else if(property1 instanceof Long){
					result = (double) ((Long)property1-(Long)property2);
				}else if(property1 instanceof Double){
					result = (Double) property1-(Double)property2;
				}else if(property1 instanceof Float){
					result = (double) ((Float) property1-(Float)property2);
				}else if(property1 instanceof java.sql.Timestamp){
					result = (double) (((java.sql.Timestamp)property1).getTime()-((java.sql.Timestamp)property2).getTime());
				}else if(property1 instanceof java.sql.Date){
					result = (double) (((java.sql.Date)property1).getTime()-((java.sql.Date)property2).getTime());
				}else if(property1 instanceof java.util.Date){
					result = (double) (((java.util.Date)property1).getTime()-((java.util.Date)property2).getTime());
				}else{
					System.out.println("目前只支持 String Integer Double Float Date类型的排序");
					result = -1.0;
				}
			}else{
				try {
					Field field = clazz.getDeclaredField(propertyName);
					String propertyType = field.getType().getSimpleName();
					String methodName = "get"+propertyName.substring(0, 1).toUpperCase()+propertyName.substring(1);
					Method method = clazz.getMethod(methodName, new Class[] {});
					if("String".equals(propertyType)){
						result = (double) ((String)method.invoke(o1,new Object[]{})).compareTo((String)method.invoke(o2,new Object[]{}));
					}else if("Integer".equals(propertyType)){
						result = (double) ((Integer) method.invoke(o1, new Object[]{})-(Integer)method.invoke(o2, new Object[]{}));
					}else if("Long".equals(propertyType)){
						result = (double) ((Long) method.invoke(o1, new Object[]{})-(Long)method.invoke(o2, new Object[]{}));
					}else if("Double".equals(propertyType)){
						result = (Double) method.invoke(o1, new Object[]{})-(Double)method.invoke(o2, new Object[]{});
					}else if("Float".equals(propertyType)){
						result = (double) ((Float) method.invoke(o1, new Object[]{})-(Float)method.invoke(o2, new Object[]{}));
					}else if("Date".equals(propertyType)){
						DateFormat df = new SimpleDateFormat(DateUtil.ALL_12H);
						Date date1 = df.parse((String) method.invoke(o1, new Object[]{}));
						Date date2 = df.parse((String) method.invoke(o2, new Object[]{}));
						result = (double) (date1.getTime()-date2.getTime());
					}else{
						System.out.println("目前只支持 String Integer Double Float Date类型的排序");
						result = -1.0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(true==orderBy){
				if(result>0){
					return 1;
				}else if(result==0){
					return 0;
				}else{
					return -1;
				}
			}else{
				if(result>0){
					return -1;
				}else if(result==0){
					return 0;
				}else{
					return 1;
				}
			}
		}
	}
	
	/**
	 * 根据属性名称生成新数组
	 * @param array			源数组
	 * @param propertyName	源属性名称
	 * @return				Object[] 新数组
	 */
	public Object[] getArray(T[] array,String propertyName){
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) array[0].getClass();
		Object [] arrayProperty = new Object[array.length];
		try {
			String methodName = "get"+propertyName.substring(0, 1).toUpperCase()+propertyName.substring(1);
			Method method = clazz.getMethod(methodName, new Class[] {});
			for (int i = 0; i < arrayProperty.length; i++) {
				arrayProperty[i] = method.invoke(array[i], new Object[]{});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayProperty;
	}
	
	/**
	 * 数组的快速排序
	 * @param srcArray		目标数组
	 * @param genArray		根据属性名称生成的数组
	 * @param fieldName		要排序的属性名称
	 * @param l				初始位置
	 * @param h				最大下标
	 * @param orderBy		排序类型
	 */
	public void arraySort(T[] srcArray,Object[] genArray,String fieldName,int l,int h,Boolean orderBy){
		if(l>=h){
			return;
		}
		Object genTemp = genArray[l];
		int compare = 0;
		int leftIdx = l;
		int rightIdx = h;
		
		int i=l+1;
		while(i<=rightIdx){
			if("String".equals(fieldName)){
				String a =(String) genTemp;
				String b =(String)genArray[i];
				compare = b.compareTo(a);
			}else if("Integer".equals(fieldName)){
				Integer a =(Integer) genTemp;
				Integer b =(Integer)genArray[i];
				compare = b-a;
			}else if("Long".equals(fieldName)){
				Integer a =(Integer) genTemp;
				Integer b =(Integer)genArray[i];
				compare = b-a;
			}else if("Double".equals(fieldName)){
				Double a =(Double) genTemp;
				Double b =(Double)genArray[i];
				compare = (int) (b-a);
			}else if("Float".equals(fieldName)){
				Float a =(Float) genTemp;
				Float b =(Float)genArray[i];
				compare = (int) (b-a);
			}else if("Date".equals(fieldName)){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date1 = null;
				Date date2 = null;
				try {
					date1 = df.parse((String) genTemp);
					date2 = df.parse((String) genArray[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				compare = (int) (date1.getTime()-date2.getTime());
			}else{
				System.out.println("目前只支持 String Integer Double Float Date类型的排序");
			}
			if(compare == 0){
				i++;
			}else if(compare <0 ==orderBy){
				exchange(srcArray, leftIdx, i);  
				exchange(genArray, leftIdx++, i++);  
			}else{
				exchange(srcArray, rightIdx, i);  
				exchange(genArray, rightIdx--, i);  
			}
		}
		arraySort(srcArray,genArray,fieldName ,l, leftIdx - 1, orderBy);  
		arraySort(srcArray,genArray,fieldName,rightIdx + 1, h, orderBy);
	}
	
	/**
	 * 得到属性的类型名称
	 * @param srcArray		目标数组
	 * @param propertyName	属性名称
	 * @return
	 */
	public String getFieldName(T[] srcArray,String propertyName){
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) srcArray[0].getClass();
		Field field = null;
		try {
			field = clazz.getDeclaredField(propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field.getType().getSimpleName();
	}
	
	/**
	 * 交换数组中的对象
	 * @param obj	对象数组
	 * @param i		下标1
	 * @param h		下标2
	 */
	public void exchange(Object[] obj,int i,int h){
		Object temp = obj[i];
		obj[i] =obj[h];
		obj[h] = temp;
	}
	
	/**
	 * 数组属性排序
	 * @param srcArray 目标数组
	 * @param propertyName 属性名称
	 * @param orderBy 排序类型，true正序，false逆序
	 */
	public void ArrayPropertySort(T[] srcArray,String propertyName,Boolean orderBy){
		if (null==srcArray) {
			return ;
		}
		Object[] genArray = getArray(srcArray, propertyName);
		String fieldName = this.getFieldName(srcArray, propertyName);
		arraySort(srcArray, genArray,fieldName,0,srcArray.length-1,orderBy);
	}
}