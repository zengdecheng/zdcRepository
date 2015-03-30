package com.xbd.oa.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import net.sf.json.JSONObject;

//TODO 应用通过审核后使用新的appid和appSercret
public class SMSUtils {
	private static String appId = "164166570000037517";//appid *
	private static String appSecret = "73c4c0eeff7ecdccb3fec527e5cf684a"; //app密钥 *
	private static String accessToken = "";//模板短信token *
	private static long expiresIn = 0L;//token 有效期 单位秒
	private static long startTime = 0L;//token 更新时间
	private static String params = "";//请求参数 * 参数必须使用utf8转义否则 无法传递中文
	private static String postEntity;//
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String sign = "";//可选参数	
	
	public  SMSUtils(){
		System.out.println("开始");
		accessToken = getAccessToken();
	}
	
	
	/**
	 * <B>模版短信接口</B>
	 * @param user 用户名
	 * @param phone 用户手机号
	 * @param email 用户邮箱
	 * @param templetId 模板id
	 * @param params 模板参数 （每个模板的参数需对应）
	 * @return
	 */
	public static boolean sendTempletMessage(String phone,String templetId,String params){
		phone = phone.trim();
		if(phone.length() != 11 ){
			return false;//手机号不等于11位直接返回
		}
		if( System.currentTimeMillis() - startTime > expiresIn*1000 ){
			//令牌超时，更新令牌
			updateAccessToken();
		}
		
		boolean flag = false;
		String time = sdf.format(new Date());
		String postUrl = "http://api.189.cn/v2/emp/templateSms/sendSms";//模版短信调用接口地址
		
		try {
			postEntity = "app_id=" + appId
					          + "&acceptor_tel=" + phone
					          + "&access_token=" + accessToken
					          + "&template_id=" + templetId
					          + "&template_param=" + params
					          + "&timestamp=" + URLEncoder.encode(time,"UTF-8");
			String result;
//			result = HttpInvoker.httpPost(postUrl,null,postEntity);
			result = "{\"res_code\":\"0\"}";
			JSONObject jsonObject = JSONObject.fromObject(result);
			System.out.println(result);
			if( "0".equals( jsonObject.getString("res_code") )){
				flag = true;
			}else{
				//若有错误发送邮件通知
				String content = "天翼接口返回代码：" + result + "<b>传输参数：" +  params + "<b>请求参数:" + postEntity ; 
				MailUtil.sendBySmtp("changyuchun@singbada.cn", "", "", "OA系统提示", "OA流程提醒短信发送失败报告", content, "bigbuyer@singbada.cn","smtp.qq.com", "xbd123456");
				flag = false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 获取应用token值 token30天更新一次
	 * @return
	 */
	public static String getAccessToken() {
		try {
			String url = "https://oauth.api.189.cn/emp/oauth2/v3/access_token?app_id="
					+ appId
					+ "&app_secret=" + appSecret
					+ "&grant_type=client_credentials";
			String resJsonAT = HttpInvoker.httpPost(url, null, null);
			System.out.println(resJsonAT);
			JSONObject json = JSONObject.fromObject(resJsonAT);
			if( json.get("res_code") != null && "0".equals( json.get("res_code").toString() )){
				expiresIn = json.getLong("expires_in");
				startTime = System.currentTimeMillis();
				return json.getString("access_token");
			}
			return resJsonAT;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 查询模板短信状态
	 * @param 
	 */
	public static void queryTempletMessageState(String identifier){
		try{
			String time = sdf.format(new Date());
			
			//获取sign
			TreeMap<String,String> params = new TreeMap<String,String>();
			params.put("app_id", appId);
			params.put("access_token", accessToken);
			params.put("identifier", identifier);
			params.put("timestamp", time);
			
			sign = ParamsSign.value(params, appSecret);
			System.out.println(sign);
			
			//查询短信状态
			String postUrl = "http://api.189.cn/v2/EMP/nsagSms/appnotify/querysmsstatus";//状态查询接口地址
			postEntity = "app_id=" + appId 
					+"&access_token=" + accessToken
					+"&identifier=" + identifier
					+"&timestamp=" + URLEncoder.encode(time,"UTF-8")
					+"&sign=" + sign;
			 System.out.println(postEntity);
			String result = HttpInvoker.httpPost(postUrl, null, postEntity);
			System.out.println(result);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新accessToken
	 */
	public static void updateAccessToken(){
		accessToken = getAccessToken();
	}
	
	public static long getExpiresIn() {
		return expiresIn;
	}

	public static long getStartTime() {
		return startTime;
	}

	public static void main(String[] args){
//		getAccessToken();
		sendTempletMessage("18510243340", "151548787", "adsf");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(2014, 9, 22);
		cal2.set(2014,10,15);
		if(cal1.before(cal2)){
			System.out.println("cal1 < cal2");
		}else{
			System.out.println("cal2 > cal1");
		}
		
//		refreshToken();
//		sendTempletMessage("18510243340", "91002451", "{\"style_code\":\"SINGBD\"}");
//		sendTempletMessage("常遇春","18510243340","380189291@qq.com");
//		queryTempletMessageState("90610827154803562466");
//		saveMessageInfo("15810868082","hahahaha","90610827154803562466","0","success");
		
//		String str ="{ 'w2_expires_in': 86400, 'taobao_user_id': '709343977', 'taobao_user_nick': 'enspook', 'w1_expires_in': 86400, 're_expires_in': 86400, 'r2_expires_in': 86400, 'expires_in': 86400, 'token_type': 'Bearer', 'refresh_token': '6201606356133egif1cf4883488eb396683c4b44caef243709343977', 'access_token': '620010625564begi4fcfafb86f7e19ca1719f4277d8a4ef709343977', 'r1_expires_in': 86400} ";
		//		JSONObject jsonObject = JSONObject.fromObject(str); 
//		System.out.println(jsonObject.get("taobao_user_nick"));fa7848c8082efead9fa0bafaf16cd15c1409024979642
	}
}
