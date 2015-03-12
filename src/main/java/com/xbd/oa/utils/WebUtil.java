package com.xbd.oa.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.xbd.oa.vo.OaOrg;
import com.xbd.oa.vo.OaStaff;

public final class WebUtil {
	/**
	 * session当前辛巴达员工
	 */
	public static final String SESSION_LOGIN_BX = "session_login_bx";
	
	public static final String SESSION_IS_MANAGER = "is_manager";
	/**
	 * 
	 * <B>设置当前登录的员工VO</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static void setCurrentLoginBx(OaStaff sysStaff) {
		Struts2Utils.getSession().setAttribute(SESSION_LOGIN_BX, sysStaff);
	}

	/**
	 * 
	 * <B>获取当前登录的员工VO</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static OaStaff getCurrentLoginBx() {
		OaStaff oaStaff = (OaStaff) Struts2Utils.getSession().getAttribute(
				SESSION_LOGIN_BX);
		return oaStaff;
	}

	/**
	 * 
	 * <B>获取当前登录的员工VO</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static boolean ifAdmin() {
		if(!isBxLogined()) return false;
		if(getCurrentLoginBx().getLoginName() == null)return false;
		if (getCurrentLoginBx().getLoginName().equals("admin")) {
			return true;
		}else if((getCurrentLoginBx() != null) && (getCurrentLoginBx().getOaRole() != null) && (getCurrentLoginBx().getOaRole()==9)){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回当前登录的员工部门
	 * 
	 * @return
	 */
	public static String getCurrentLoginBxGroup() {
		return XbdBuffer.getOrgNameById(getCurrentLoginBx().getOaOrg());
	}

	/**
	 * 
	 * <B>判断辛巴达员工是否登录</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static boolean isBxLogined() {
		return null != getCurrentLoginBx();
	}

	
	public static void setManager(OaOrg oo){
		Struts2Utils.getSession().setAttribute(SESSION_IS_MANAGER,oo);
	}
	
	/**
	 * <B>判断是否是部门主管</B><br>
	 * 
	 */
	public static boolean ifManager(){
		if( Struts2Utils.getSession().getAttribute(SESSION_IS_MANAGER)!= null ){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * <B>判断是否ajax请求</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @param request
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return (header != null && "XMLHttpRequest".equals(header));
	}

	/**
	 * 
	 * <B>获取不带“-”的UUID</B><br>
	 * 功能详细描述
	 * 
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	public static String price2String(double d) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(d);
	}

	public static String priceToString(Object obejct) {
		String result = null;
		if (obejct instanceof String) {
			result = (String) obejct;
		} else if (obejct instanceof Double || obejct instanceof Integer) {
			DecimalFormat df = new DecimalFormat("#0.00");
			result = df.format(obejct);
		}
		return result;
	}

	public static String minusTimeAndOffset(Timestamp timestamp, long offset) {
		StringBuffer buf = new StringBuffer();
		long diff = offset
				- BizUtil.getWorkTimeBetween(
						new Timestamp(System.currentTimeMillis()), timestamp);
		if (diff < 0) {
			buf.append("超时");
		} else {
			buf.append("剩余");
		}
		long l = Math.abs(diff);
		buf.append(getTimeDisPlayExcel(l));
		return buf.toString();
	}

    /**
     * 用户excel导出计算
     * @param timestamp
     * @param offset
     * @return
     * @author 范蠡
     */
    public static String minusTimeAndOffsetExcel(Timestamp timestamp, long offset) {
        StringBuffer buf = new StringBuffer();
        long diff = offset
                - BizUtil.getWorkTimeBetween(
                new Timestamp(System.currentTimeMillis()), timestamp);
        if (diff < 0) {
            buf.append("超时");
        } else {
            buf.append("剩余");
        }
        long l = Math.abs(diff);
        buf.append(getTimeDisPlayExcel(l));
        return buf.toString();
    }

	public static String minusTime(Timestamp timestamp) {
		StringBuffer buf = new StringBuffer();
		long diff = BizUtil.getWorkTimeBetween(
				new Timestamp(System.currentTimeMillis()), timestamp);
		if (diff > 0) {
			buf.append("超时");
		} else {
			buf.append("剩余");
		}
		long l = Math.abs(diff);
		buf.append(getTimeDisPlayExcel(l));
		return buf.toString();
	}

	public static String getTimeDisPlay(long l) {
		StringBuffer buf = new StringBuffer();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		if (day > 0) {
			buf.append(day + "天");
			buf.append(hour + "小时");
		} else if (hour > 0) {
			buf.append(hour + "小时");
			buf.append(min + "分");
		} else if (min > 0) {
			buf.append(min + "分");
			buf.append(s + "秒");
		} else if (s > 0) {
			buf.append("0分");
			buf.append(s + "秒");
		} else {
			buf.append("0分");
			buf.append("0秒");
		}
		return buf.toString();
	}

    public static String minusTimeExcel(Timestamp timestamp) {
        StringBuffer buf = new StringBuffer();
        long diff = BizUtil.getWorkTimeBetween(
                new Timestamp(System.currentTimeMillis()), timestamp);
        if (diff > 0) {
            buf.append("超时");
        } else {
            buf.append("剩余");
        }
        long l = Math.abs(diff);
        buf.append(getTimeDisPlayExcel(l));
        return buf.toString();
    }

    public static String getTimeDisPlayExcel(long l) {
        StringBuffer buf = new StringBuffer();
        //long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000));
        long min = ((l / (60 * 1000)) - hour * 60);
//        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

//        if (day > 0) {
//            buf.append(day + "天");
//            buf.append(hour + "小时");
//            buf.append(min + "分");
//        } else
        if (hour > 0) {
            //buf.append("0天");
            buf.append(hour + "小时");
            if(min < 10){
                buf.append("0"+min + "分");
            }else{
                buf.append(min + "分");
            }

        } else if (min > 0) {
           // buf.append("0天");
            buf.append("0小时");
            if(min < 10){
                buf.append("0"+min + "分");
            }else{
                buf.append(min + "分");
            }
        }
//        else if (s > 0) {
//            buf.append("0分");
//            buf.append(s + "秒");
//        } else {
//            buf.append("0分");
//            buf.append("0秒");
//        }
        return buf.toString();
    }
	
	public static String getTimeDisPlay4DH(long l) {
		StringBuffer buf = new StringBuffer();
		long hour = (l / (60 * 60 * 1000));
		long min = ((l / (60 * 1000)) - hour * 60);
		long s = (l / 1000 - hour * 60 * 60 - min * 60);

		if (hour > 0) {
			buf.append(hour + "小时");
			buf.append(min + "分");
		} else if (min > 0) {
			buf.append(min + "分");
			buf.append(s + "秒");
		} else if (s > 0) {
			buf.append("0分");
			buf.append(s + "秒");
		} else {
			buf.append("0分");
			buf.append("0秒");
		}
		return buf.toString();
	}

	/**
	 * 
	 * @Title: getOrderTypeStr
	 * @Description: TODO通过订单类型（1、2、3、4）转换为汉子返回
	 *
	 * @author 张华
	 * @param type
	 * @return
	 */
	public static String getOrderTypeStr(String type) {
		switch (type) {
		case "1":
			return "模拟报价";
		case "2":
			return "样衣打版";
		case "3":
			return "大货生产";
		case "4":
			return "售后服务";
		default:
			return "未知类型";
		}
	}
	public static String getWfIndx(String wfStep){
		String inx = "";
		if(wfStep != null ){
			inx = wfStep.substring(wfStep.lastIndexOf("_")+1);
		}
		return inx;
	}

	public static String getUrgentStr(String is_urgent) {
		if (is_urgent != null && is_urgent.equals("0")) {
			return "[加急]";
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(getWfIndx("b_dfdfd_1"));
	}
}
