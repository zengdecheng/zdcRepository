package com.xbd.oa.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

public class CastorUtils {
	public static void marshal(Object bean, String fileName)
			throws MarshalException, ValidationException {
		FileWriter fWriter;
		try {
			fWriter = new FileWriter(fileName);
			// System.out.println("getEncoding:" + fWriter.getEncoding());
			Mapping map = new Mapping();
			map.loadMapping("config_maping.xml");
			Marshaller ms = new Marshaller(fWriter);
			ms.setMapping(map);
			ms.setEncoding("gb2312");
			ms.marshal(bean);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException me) {
			me.printStackTrace();
		}

	}
	public static String marshal(Object bean,URL cfg){
		try {
			Mapping map = new Mapping();
			map.loadMapping(cfg);//"config_maping.xml"
			StringWriter st = new StringWriter(); 
			Marshaller ms = new Marshaller(st);
			ms.setMapping(map);
			ms.marshal(bean);
			return st.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	public static String marshal(Object bean,Mapping map){
		return marshal( bean, map,null);
	}
	/**
	 * 
	 *@param bean
	 *@param map
	 *@return
	 *@author fuying
	 *@date 2009-4-7 下午03:16:53
	 *@comment 用于缓存Mapping 的方法主要用于大量解析bean文件到xml,缓存config.xml
	 */
	public static String marshal(Object bean,Mapping map,String encoding){
		if(encoding == null){
			encoding = "utf-8";
		}
		try {
			//Mapping map = new Mapping();
			//map.loadMapping(cfg);//"config_maping.xml"
			StringWriter st = new StringWriter(); 
			Marshaller ms = new Marshaller(st);
			ms.setMapping(map);
			ms.setEncoding(encoding);
			ms.marshal(bean);
			return st.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static Object unmarshal(InputStream input,Mapping map){
		return  unmarshal( input, map,null);
	}
	
	/**
	 * 
	 *@param input
	 *@param map
	 *@return
	 *@author fuying
	 *@date 2009-4-7 下午03:18:14
	 *@comment 用于缓存Mapping 的方法主要用于大量解析InputStream到bean,缓存config.xml
	 */
	public static Object unmarshal(InputStream input,Mapping map,String encoding) {
		if(encoding == null){
			encoding = "utf-8";
		}
		//Mapping map = new Mapping();
		//map.loadMapping(cfg);//"config_maping.xml"
		// FileReader in = new FileReader("src/response.xml");
		Object obj;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding));
			Unmarshaller un = new Unmarshaller(map);
			obj = un.unmarshal(reader);
			
			input.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(input != null) input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	public static Object unmarshalStr(String srcStr,Mapping map){
		return  unmarshalStr( srcStr, map,null);
	}
	public static Object unmarshalStr(String srcStr,Mapping map,String encoding) {
		StringReader sr = new StringReader(srcStr);
		Object obj;
		try {
			Unmarshaller un = new Unmarshaller(map);
		obj =  un.unmarshal(sr);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			sr.close();
		}
		return obj;
	}
	public static Object unmarshalStr(String srcStr,String cfg)throws MarshalException,
	ValidationException, IOException, MappingException {
		StringReader sr = new StringReader(srcStr);
		Mapping map = new Mapping();
		map.loadMapping(cfg);//"config_maping.xml"
		Unmarshaller un = new Unmarshaller(map);
		// FileReader in = new FileReader("src/response.xml");
		Object obj =  un.unmarshal(sr);
		sr.close();
		return obj;
	}
	public static Object unmarshal(String srcFile,String cfg) throws MarshalException,
			ValidationException, IOException, MappingException {
		FileInputStream input = new FileInputStream(srcFile);//"tktResult.xml"
		return unmarshal(input,cfg);
	}
	public static Object unmarshal(InputStream input,String cfg) throws MarshalException,
	ValidationException, IOException, MappingException {
		Mapping map = new Mapping();
		map.loadMapping(cfg);//"config_maping.xml"
		InputSource ins = new InputSource(input);
		Unmarshaller un = new Unmarshaller(map);
		// FileReader in = new FileReader("src/response.xml");
		Object obj =  un.unmarshal(ins);
		input.close();
		return obj;
	}
	public static Object unmarshal(InputStream input,URL cfg) {
		try {
			Mapping map = new Mapping();
			map.loadMapping(cfg);//"config_maping.xml"
			InputSource ins = new InputSource(input);
			Unmarshaller un = new Unmarshaller(map);
			// FileReader in = new FileReader("src/response.xml");
			Object obj =  un.unmarshal(ins);
			input.close();
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		try {
			//TktResult book = CastorUtils.mock();
			//CastorUtils.marshal(book, "tktResult222.xml");
			
			/*QueryBean obj = (QueryBean)CastorUtils.unmarshal("src\\NewFile.xml","src\\org\\use\\base\\daoquery\\bean\\config_mapping.xml");
			obj.initmapFunc();
			System.out.println(obj);*/
			Mapping map = new Mapping();
			map.loadMapping("config_mapping.xml");//"config_maping.xml"
			FileInputStream fs = new FileInputStream("bean.xml");
			ByteBean obj = (ByteBean)CastorUtils.unmarshal(fs,map);
			FileUtils.writeFile(obj.getSrc(), "D:/fuyin1.jpg");
			System.out.println(obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
