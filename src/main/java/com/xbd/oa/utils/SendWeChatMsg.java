/**
 * @author 张华
 *
 */
package com.xbd.oa.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张华
 *
 */
public class SendWeChatMsg {

	/**
	 * 
	 * @Title: sendMsg
	 * @Description: TODO调取微信接口发送消息
	 *
	 * @author 张华
	 * @param userId
	 * @param content
	 */
	public static void sendMsg(String userId, String content) {
		String url;
//		userId = "yangxiao";
		try {
			Map<String, String> postHeaders = new HashMap<String, String>();
			postHeaders.put("Accept-Charset", "utf-8");
			postHeaders.put("contentType", "utf-8");

			url = "http://14.23.89.228:8090/XBDwx?flag=erp&userId=" + userId + "&content=" + content;
			HttpInvoker.httpPost(url, postHeaders, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: main
	 * @Description: TODO
	 *
	 * @author 张华
	 * @param args
	 */
	public static void main(String[] args) {
		sendMsg("baizhantang", "lalala");
	}
}
