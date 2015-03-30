package com.xbd.oa.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>
 * Title: StringUtils
 * </p>
 * 
 * <p>
 * Description: String Utilities
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author ShiYongxin
 * @version 1.0
 */

public abstract class StringUtils {
	private static final Log logger = LogFactory.getLog(StringUtils.class);

	/**
	 * Check if an Object is empty
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		else if (obj instanceof String)
			return ((String) obj).length() == 0;
		else
			return false;
	}

	/**
	 * Check if a String is empty
	 * 
	 * @param aString
	 *            String
	 * @return boolean
	 */
	public static boolean isEmpty(String aString) {
		return !hasLength(aString);
	}

	/**
	 * Check if a String is empty
	 * 
	 * @param aString
	 *            String
	 * @return boolean
	 */
	public static boolean isInteger(String aString) {
		try {
			new Integer(aString).intValue();
			return true;
		} catch (Exception exp) {
			return false;
		}
	}

	/**
	 * Check if a String has length.
	 * <p>
	 * 
	 * <pre>
	 *   StringUtils.hasLength(null) = false
	 *   StringUtils.hasLength(&quot;&quot;) = false
	 *   StringUtils.hasLength(&quot; &quot;) = true
	 *   StringUtils.hasLength(&quot;Hello&quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check if a String has text. More specifically, returns <code>true</code>
	 * if the string not <code>null<code>, it's <code>length is > 0</code>, and
	 * it has at least one non-whitespace character.
	 * <p><pre>
	 *   StringUtils.hasText(null) = false
	 *   StringUtils.hasText(&quot;&quot;) = false
	 *   StringUtils.hasText(&quot; &quot;) = false
	 *   StringUtils.hasText(&quot;12345&quot;) = true
	 *   StringUtils.hasText(&quot; 12345 &quot;) = true
	 * </pre>
	 * @param str the String to check, may be null
	 * @return <code>true</code> if the String is not null, length > 0,
	 *         and not whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0
				&& Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @return boolean
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * from ISO-8859-1 to GBK
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String ISO2GBK(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * from GBK ot ISO-8859-1
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String GBK2ISO(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * from GBK ot UTF-8
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String GBK2UTF(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * from ISO-8859-1 to UTF-8
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String ISO2UTF(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * from UTF-8 to ISO-8859-1
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String UTF2ISO(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * from UTF-8 to GBK
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String UTF2GBK(String input) {
		if (input == null) {
			return null;
		}

		try {
			return new String(input.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unsupported encoding", ex);
			return null;
		}
	}

	/**
	 * get method name according to property name
	 * 
	 * @param prefix
	 *            String
	 * @param propertyName
	 *            String
	 * @return String
	 */
	public static String getMethodName(String prefix, String propertyName) {
		if (!hasText(propertyName)) {
			return null;
		} else {
			return prefix + propertyName.substring(0, 1).toUpperCase()
					+ propertyName.substring(1);
		}
	}

	public static String getMyDate(String strdate) {
		String ret = "";
		if (strdate != null && !strdate.equals("") && strdate.length() > 0
				&& !strdate.startsWith("1900-01-01"))
			ret = strdate.substring(0, 10);
		return ret;
	}

	// for example:yy-mm-dd hh:mi
	public static String getMyDate2(String strdate) {
		String ret = "";
		if (strdate != null && !strdate.equals("") && strdate.length() > 0
				&& !strdate.startsWith("1900-01-01"))
			ret = strdate.substring(0, 16);
		return ret;
	}

	/**
	 * getMoneyFormat
	 * 
	 * @param amount
	 *            Object
	 * @return String
	 */
	public static String getMoneyFormat(Object amount) {
		NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
		if (Double.parseDouble(amount.toString()) < 0) {
			String formatTemp = usFormat.format(amount);
			return "-" + formatTemp.substring(2, formatTemp.length() - 1);
		}
		return usFormat.format(amount).substring(1);
	}

	public static String strPadLeft(String strInput, int len, String strAdd) {
		if (strInput == null) {
			strInput = "";
		}
		if (strInput.length() == 0) {
			return strInput;
		}

		int addCount = len - strInput.length();
		for (int i = 0; i < addCount; i++) {
			strInput = strAdd + strInput;
			if (strInput.length() == len) {
				break;
			}
		}
		return strInput;
	}

	/**
	 * 
	 * @param strSource
	 * @return
	 */
	public static String filterForOracleInsert(String strSource) {
		String strResult = null;
		strResult = strSource.replaceAll("'", "‘");

		return strResult;
	}

	public static String getStringISO8859_1(String strOld) {
		if (strOld == null)
			return null;

		String strNew = strOld;
		try {
			if (strOld == null || strOld.equals(""))
				strNew = "";
			else
				strNew = new String(strOld.getBytes("ISO8859_1"));
		} catch (Exception e) {
			System.out.println("Error in getISOStr():");
			System.out.println(e.getMessage());
		}

		return strNew;
	}

	/**
	 * 去掉字符串中的逗号
	 */
	public static String getNOCommaString(String input) {
		return input.replaceAll(",", "");
	}

	/**
	 * 得到拆分的字符串数组 拆分标志是/
	 */
	public static String[] splitString(String str) {
		String[] s = str.split("/");
		return s;
	}

	/**
	 * 输入String 转换为 InputStream
	 * 
	 * @param str
	 * @return
	 * @author fuying
	 * @date 2008-5-30 下午03:22:02
	 * @comment
	 */
	public static InputStream string2InputStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 输入inputStream 转换为 String
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 * @author fuying
	 * @date 2008-5-30 下午03:22:36
	 * @comment
	 */
	public static String inputStream2String(InputStream in) {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		try {
			while ((ptr = in.read()) != -1)
				buffer.append((char) ptr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return buffer.toString().trim();
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @author fuying
	 * @date 2009-3-19 下午12:11:34
	 * @comment 提高并发性能的inputStream 转化为String
	 */
	public static String inputStream2StringBuf(InputStream in) {
		return inputStream2StringBuf(in,"utf-8");
	}
	/**
	 * 
	 * @param in
	 * @return
	 * @author fuying
	 * @date 2009-3-19 下午12:11:34
	 * @comment 提高并发性能的inputStream 转化为String
	 */
	public static String inputStream2StringBuf(InputStream in,String encode) {
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {

			reader = new BufferedReader(new InputStreamReader(in, encode));
			String tmpStr = "";

			tmpStr = reader.readLine();
			while (tmpStr != null) {
				buffer.append(tmpStr);
				tmpStr = reader.readLine();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(reader != null)reader.close();
				if(in != null)in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buffer.toString().trim();
	}

	public static int getCharByHexNumStr(String Num0x) {
		int rs = 0;
		if (Num0x.length() == 2) {
			char a = Num0x.substring(0, 1).toCharArray()[0];
			char b = Num0x.substring(1, 2).toCharArray()[0];
			if (a <= '9' && a >= '0') {
				rs += 16 * Integer.valueOf(String.valueOf(a)).intValue();
			} else if (a >= 'A' && a <= 'F') {
				rs += 16 * (a - 'A' + 10);
			}
			if (b <= '9' && b >= '0') {
				rs += Integer.valueOf(String.valueOf(b)).intValue();
			} else if (b >= 'A' && b <= 'F') {
				rs += (b - 'A' + 10);
			}
		} else {
			System.out.println("参数Num0x 必须是2位16进制字符 如:D5 ");
		}
		return rs;
	}

	public static String getGBKStrByPXNM(String pxnm) {
		// pxnm = "00D5C5C6BCZ0";
		String name_str = null;

		if (pxnm.startsWith("00") && pxnm.indexOf("Z0") > 0)
			pxnm = pxnm.substring(0, pxnm.indexOf("Z0") + 2);
		else
			return pxnm;

		if (pxnm.startsWith("00") && pxnm.endsWith("Z0")) {
			name_str = pxnm.substring(2, pxnm.length() - 2);
			char[] a = new char[name_str.length() / 2];
			for (int i = 0; i * 2 < name_str.length() - 1; i++) {
				int j = getCharByHexNumStr(name_str.substring(i * 2, i * 2 + 2));
				// System.out.println("j="+j);
				a[i] = (char) j;
			}
			return StringUtils.ISO2GBK(String.valueOf(a));
		} else {
			return pxnm;
		}

	}

	public static String getOsCharset() {
		Properties p = System.getProperties();
		return (String) p.get("file.encoding");
	}

	/**
	 * find the specially strs in the input str.
	 * 
	 * @param sInput
	 * @param sRegEx
	 * @return
	 * @author zhuyawei
	 * @date 2008-5-18 下午07:22:11
	 * @comment
	 */
	public static boolean find(String sSrc, String sInput) {
		return (sSrc.toUpperCase().indexOf(sInput.toUpperCase()) > -1);
	}

	public static void main(String[] args) {
		// System.out.println(getGBKStrByPXNM("00D0ECC7E5CFCDZ0"));

		// String sInput = "D:" + File.separator + "ttcwork" + File.separator +
		// "file" + File.separator + "1211189307125" + File.separator + "打倒房贷" +
		// File.separator + "preview" + File.separator + "240x320.gif";
		// String sRegEx = "preview" + File.separator + "240X320";
		// System.out.println(-1<sInput.indexOf(sRegEx));
		// boolean b = find(sInput, sRegEx);
		// System.out.println(b);

		/*
		 * String str = ""; System.out.println(isEmpty(str)); BASE64Decoder
		 * base64Decoder = new BASE64Decoder(); try { System.out.println(new
		 * String(base64Decoder.decodeBuffer("MDEwMjg3DQoNCg=="))); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// String str =
		// "�������п�ҵ�㼪£��������ϣ���Դ���Ĺ���֮�⡣�����ڵ��̻���ҵ�Ŀ�ҵ���ʣ��Ա������߶����Ѽ���ҵ��֧����ף����"
		// +"���ⴺǰ�ݣ���Դ���Ȫ�������к�ҵ��¡����¡��ף��ҵ�㼪£���Դ���ϲ���ơ����ʻ���������";
		String str = "商品";
		// "康乃馨，大部分代表了爱，魅力和尊敬之情，多表现为比较清淡和温馨的情感，适于亲情之爱。有一个爱，一生一世不求回报──母爱"
		// +"您常说，子女的幸福快乐就是最好的的礼物，今天我把所有的幸福和快乐用彩信包裹送给您，祝身体健康，笑口常开!踏歌鲜花店荣誉出品"
		//String str = "ä½ çä»è¹ ç±ä½ çç±³å¼æåºç½";
		try {
		str = URLEncoder.encode(str,"utf-8");
		System.out.println(GBK2UTF(str));
		System.out.println(GBK2ISO(str));
		System.out.println(UTF2GBK(str));
		System.out.println(UTF2ISO(str));
		System.out.println(ISO2GBK(str));
		System.out.println(ISO2UTF(str));

		
			System.out.println( new String("付莹".getBytes(), "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { //System.out.println(new String(str.getBytes("UTF-8"))); }
		 * catch (UnsupportedEncodingException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
	}
}
