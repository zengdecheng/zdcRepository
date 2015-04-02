package com.xbd.oa.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.utils.HttpInvoker;
import com.xbd.oa.utils.SendWeChatMsg;
import com.xbd.oa.utils.Struts2Utils;

/**
 * 
 * @author 张华 定时任务执行
 *
 */
@WebServlet("/AutoBxServlet")
@Controller
@SuppressWarnings("all")
public class AutoBxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final Logger logger = Logger.getLogger(AutoBxServlet.class);

	private static BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Resource(name = "bxDaoImpl")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 
	 * @author 张华 构造方法
	 *
	 */
	public AutoBxServlet() {
		bxEveryDayTask();
	}

	/**
	 * 
	 * @Title: bxEveryDayTask
	 * @Description: TODO启动定时任务
	 *
	 * @author 张华
	 */
	public static void bxEveryDayTask() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				sendWechat();
			}
		}, day2Run(), ConstantUtil.NOTIFYSTAFF_TIMER);
	}

	/**
	 * 
	 * @Title: day2Run
	 * @Description: TODO设置每天的运行时间
	 *
	 * @author 张华
	 * @return
	 */
	public static long day2Run() {
		Calendar cal = Calendar.getInstance();
		long time = 0L;
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		long now = Calendar.getInstance().getTimeInMillis();// 当前时间
		long target = cal.getTimeInMillis();// 目标执行时间

		if (target - now < 0) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			time = (cal.getTimeInMillis() - now);
		} else {
			time = (target - now);
		}

		logger.warn("大货定时发送微信给客户，首次发送时间计算，得出时间为：" + time + "。。" + DateUtil.formatDate(new Date(), DateUtil.ALL_24H));
		return time;
	}

	/**
	 * 
	 * @Title: sendWechat
	 * @Description: TODO大货发货5天后，发送微信给客户
	 *
	 * @author 张华
	 */
	public static void sendWechat() {
		logger.warn("大货定时发送微信给客户，具体方法执行开始" + "。。" + DateUtil.formatDate(new Date(), DateUtil.ALL_24H));
		Date d = new Date();
		String date1 = "";
		String date2 = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		date1 = df.format(new Date(d.getTime() - 4L * 24 * 60 * 60 * 1000));
		date2 = df.format(new Date(d.getTime() - 3L * 24 * 60 * 60 * 1000));

		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_SEND_WECHAT_CUS_BY_SQL_STRING);
		fspBean.set("date1", date1);
		fspBean.set("date2", date2);
		fspBean.set("wf_step", "c_ppc_confirm_9");
		fspBean.set("type", "3");
		List<LazyDynaMap> beans = baseDao.getObjectsBySql(fspBean);
		logger.warn("大货定时发送微信给客户，具体客户数为：" + beans.size() + "。。" + DateUtil.formatDate(new Date(), DateUtil.ALL_24H));

		for (LazyDynaMap lazyDynaMap : beans) {
			String url = "http://14.23.89.228:9000/ext/getStyle?crmCustomer.id=" + lazyDynaMap.get("cus_id") + "&fsp.map.id=" + lazyDynaMap.get("sell_order_id");
			// url = getSimpleUrl(url);
			try {
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(url)) {
				// 发送微信消息
				String userId = (String) lazyDynaMap.get("cus_code");
				// String content = "亲，为了保证我们的大货以及服务质量还请您为订单" + lazyDynaMap.get("sell_order_code") + "「" + lazyDynaMap.get("style_desc") + "」进行打分，点击<a%20href='" + url +
				// "'>以下链接</a>进入评分页面。我们非常重视您的建议与意见。";
				String content = "亲爱的伙伴，您已经与宝贝「" + lazyDynaMap.get("style_desc") + "」相见，想必有喜有忧，辛巴达真诚的邀请您点击<a%20href='" + url + "'>下方链接</a>用30秒为她打分，我们很在意您的建议。";
				SendWeChatMsg.sendMsg(userId, content);
			}
		}
		logger.warn("大货定时发送微信给客户，具体方法执行结束" + "。。" + DateUtil.formatDate(new Date(), DateUtil.ALL_24H));
	}

	/**
	 * 
	 * @Title: getSimpleUrl
	 * @Description: TODO生成短链
	 *
	 * @author 张华
	 * @param url
	 */
	public static String getSimpleUrl(String url) {
		try {
			String dwzURL = "http://985.so/api.php?url=";
			String jsonStr = HttpInvoker.httpGet(dwzURL + URLEncoder.encode(url, "utf-8"));
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("error");
		}
		return null;
	}
}
