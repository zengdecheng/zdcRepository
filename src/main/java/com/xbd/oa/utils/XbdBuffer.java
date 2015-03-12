package com.xbd.oa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.lang.StringUtils;
import org.use.base.FSPBean;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaOrg;
import com.xbd.oa.vo.OaStaff;

public class XbdBuffer {
	private static List<OaOrg> oaOrgList;
	private static List<OaStaff> oaStaffList;
	private static List<LazyDynaMap> oaDtList;
	private static Map<String, Map<String, List<LazyDynaMap>>> timeLineMap;// map的key
																			// 组成格式,processDefinitionId+"||"+clothClass+"||"+taskDefinitionKey
																			// lazyDynaMap<"step_duration","calculate_duration">

	private static BaseManager bxMgr;

	/**
	 * 传入数据字典类型得到数据的枚举值
	 * 
	 * @param type
	 * @return
	 */
	public static List<Map<String, String>> getOaDtList(String type) {
		if (oaDtList == null || oaDtList.size() == 0) {
			refreshDt();
		}
		// String key =
		// processDefinitionId+"||"+clothClass+"||"+taskDefinitionKey;
		List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
		for (LazyDynaMap map : oaDtList) {
			if (map.get("type").equals(type)) {
				Map<String, String> keyValueMap = new HashMap<String, String>();
				keyValueMap.put("code", (String) map.get("code"));
				keyValueMap.put("value", (String) map.get("value"));
				newList.add(keyValueMap);
			}
		}
		return newList;

	}

	/**
	 * 得到该节点的计划持续时长
	 * 
	 * @param processDefinitionKey
	 *            流程定义Id
	 * @param taskDefinitionKey
	 *            任务节点Key
	 * @param clothClass
	 *            服装品类
	 * @return
	 */
	public static LazyDynaMap getOaTimebaseEntry(String processDefinitionKey,
			String taskDefinitionKey, String clothClass) {
		if (timeLineMap == null || timeLineMap.size() == 0) {
			refreshTimeLineMap();
		}
		if (timeLineMap.containsKey(processDefinitionKey)) {
			Map<String, List<LazyDynaMap>> timeClothListMap = timeLineMap
					.get(processDefinitionKey);
			// 如果配置了此服装品类的时间基线则走此时间基线，如果没有没有配置则走基础类目，基础类目一定要配置不然就报错。
			if (timeClothListMap.containsKey(clothClass)
					&& (timeClothListMap.get(clothClass).size() > 0)) {
				List<LazyDynaMap> timeList = timeClothListMap.get(clothClass);
				for (LazyDynaMap lazyDynaMap : timeList) {
					if (lazyDynaMap.get("step").equals(taskDefinitionKey)) {
						return lazyDynaMap;
					}
				}
			} else {
				List<LazyDynaMap> timeList = timeClothListMap
						.get(ConstantUtil.DT_TYPE_CLOTH_CLASS_BASE);
				for (LazyDynaMap lazyDynaMap : timeList) {
					if (lazyDynaMap.get("step").equals(taskDefinitionKey)) {
						return lazyDynaMap;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据组名找到管理员
	 * 
	 * @param groupName
	 * @return
	 */
	public static String getAdminByGroupName(String groupName) {
		if (oaOrgList == null || oaOrgList.size() == 0) {
			refreshOaOrgList();
		}
		for (OaOrg oaOrg : oaOrgList) {
			if (oaOrg.getName().equals(groupName)) {
				return oaOrg.getAdmin();
			}
		}
		return null;
	}

	/**
	 * 根据组织id,返回组织名称
	 * 
	 * @param id
	 * @return
	 */
	public static String getOrgNameById(Integer id) {
		if (oaOrgList == null || oaOrgList.size() == 0) {
			refreshOaOrgList();
		}
		for (OaOrg oaOrg : oaOrgList) {
			if (oaOrg.getId().equals(id)) {
				return oaOrg.getName();
			}
		}
		return null;
	}

	/**
	 * 根据组名找到该组所有成员
	 * 
	 * @param id
	 * @return
	 */
	public static List<LazyDynaMap> getStaffsByGroupName(String groupName) {
		if (oaOrgList == null || oaOrgList.size() == 0) {
			refreshOaOrgList();
		}
		if (oaStaffList == null || oaStaffList.size() == 0) {
			refreshOaStaffList();
		}

		Integer oaOrgId = -1;
		List<LazyDynaMap> al = new ArrayList<LazyDynaMap>();
		for (OaOrg oaOrg : oaOrgList) {
			if (oaOrg.getName().equals(groupName)) {
				oaOrgId = oaOrg.getId();
				break;
			}
		}
		try {
			for (OaStaff oaStaff : oaStaffList) {
				if (oaStaff.getOaOrg().equals(oaOrgId)) {
					LazyDynaMap lazyDynaMap = new LazyDynaMap();
					lazyDynaMap.set("id", oaStaff.getId());
					lazyDynaMap.set("oa_org", oaStaff.getOaOrg());
					lazyDynaMap.set("login_name", oaStaff.getLoginName());
					al.add(lazyDynaMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	public static String getStaffsByGroupNameToJson(String groupName) {
		List<LazyDynaMap> al = getStaffsByGroupName(groupName);
		JSONArray ja = JSONArray.fromObject(al);
		String jaText = ja.toString();
		return jaText;
	}

	/**
	 * 读取数据库部分
	 * 
	 */

	/**
	 * 刷新组织机构表
	 */
	public synchronized static void refreshOaOrgList() {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.List_OA_ORG_BY_EQL);
		oaOrgList = getBxMgr().getObjectsByEql(fsp);
	}

	/**
	 * 刷新员工列表
	 */
	public synchronized static void refreshOaStaffList() {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_STAFF_BY_EQL);
		oaStaffList = getBxMgr().getObjectsByEql(fsp);
	}

	/**
	 * 刷新时间基准
	 */
	public synchronized static void refreshTimeLineMap() {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML,
				BxDaoImpl.LIST_OA_TIMEBASE_ENTRY_BY_SQL);
		List<LazyDynaMap> list = getBxMgr().getObjectsBySql(fsp);
		timeLineMap = new HashMap<String, Map<String, List<LazyDynaMap>>>();

		List<LazyDynaMap> wfList = WorkFlowUtil.getProcessDefinitionList();
		List<Map<String, String>> clothList = getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);

		for (LazyDynaMap wfBean : wfList) {
			Map<String, List<LazyDynaMap>> clothTimeListMap = new HashMap<String, List<LazyDynaMap>>();
			for (Map<String, String> clothMap : clothList) {
				List<LazyDynaMap> clothTimeList = new ArrayList<LazyDynaMap>();
				for (LazyDynaMap timeItem : list) {
					if (wfBean.get("key").equals(timeItem.get("define_key"))
							&& clothMap.get("code").equals(
									timeItem.get("cloth_class"))) {
						clothTimeList.add(timeItem);
					}
				}
				clothTimeListMap.put(clothMap.get("code"), clothTimeList);
			}
			timeLineMap.put((String) wfBean.get("key"), clothTimeListMap);
		}
	}

	/**
	 * 刷新数据字典表
	 */
	public synchronized static void refreshDt() {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_DT_BY_SQL);
		oaDtList = getBxMgr().getObjectsBySql(fsp);
	}

	public static BaseManager getBxMgr() {
		if (bxMgr == null) {
			bxMgr = new com.xbd.oa.business.impl.BxManagerImpl();
		}
		return bxMgr;
	}

	/**
	 * 汉字转拼音 （已过滤数字和字母）
	 * 
	 * @param str
	 * @return
	 */

	public static String hanyu2Pinyin(String str) {
		char[] nameChar = str.toCharArray();
		String pinyinName = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

			}
		}
		return pinyinName;
	}

	/**
	 * 
	 * @Title: getClothValueByCode
	 * @Description: TODO根据品类Code获取到品类名称
	 *
	 * @author 张华
	 * @param clothCode
	 * @return
	 */
	public static String getClothValueByCode(String clothCode) {
		if (StringUtils.isNotEmpty(clothCode)) {
			List<Map<String, String>> cloth_class_list = XbdBuffer
					.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
			for (Map<String, String> map : cloth_class_list) {
				if (map.get("code").equals(clothCode)) {
					return map.get("value");
				}
			}
		}
		return "基础类";
	}
	/**
	 * 根据款式分类得到对应的默认的部位名称或者公差
	 */
	public static String getClothesSizesByPosition(String position){
		ResourceUtilArgs.load("xiaZhuang");
		return ResourceUtilArgs.getString(position);
	}
}
