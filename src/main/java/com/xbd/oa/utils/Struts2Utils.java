package com.xbd.oa.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Struts2工具类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 */
public final class Struts2Utils {

	//-- header 常量定义 --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;
	private static ObjectMapper mapper = new ObjectMapper();
	
	private Struts2Utils() {
		
	}
	//-- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	/**
	 * 取得HttpSession中Attribute的简化函数.
	 */
	public static Object getSessionAttribute(String name) {
		final HttpSession session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	
	//by fangwei
	public static void writeJson(Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, DateUtil.ALL_24H);
			ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//-- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		final HttpServletResponse response = initResponseHeader(contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(ServletUtils.TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(ServletUtils.HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(ServletUtils.XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		render(ServletUtils.JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON,使用Jackson转换Java对象.
	 * 
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object data, final String... headers) {
		final HttpServletResponse response = initResponseHeader(ServletUtils.JSON_TYPE, headers);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 分析并设置contentType与headers.
	 */
	private static HttpServletResponse initResponseHeader(final String contentType, final String... headers) {
		//分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			final String headerName = StringUtils.substringBefore(header, ":");
			final String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}
		}

		final HttpServletResponse response = ServletActionContext.getResponse();

		//设置headers参数
		final String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			ServletUtils.setDisableCacheHeader(response);
		}

		return response;
	}
	public static void main(String[] args) {
		String str ="{ 'w2_expires_in': 86400, 'taobao_user_id': '709343977', 'taobao_user_nick': 'enspook', 'w1_expires_in': 86400, 're_expires_in': 86400, 'r2_expires_in': 86400, 'expires_in': 86400, 'token_type': 'Bearer', 'refresh_token': '6201606356133egif1cf4883488eb396683c4b44caef243709343977', 'access_token': '620010625564begi4fcfafb86f7e19ca1719f4277d8a4ef709343977', 'r1_expires_in': 86400} ";
		JSONObject jsonObject = JSONObject.fromObject(str); 
		System.out.println(jsonObject.get("taobao_user_nick"));
//		try {
//			mapper.readTree(str);
//			System.out.println(mapper);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
