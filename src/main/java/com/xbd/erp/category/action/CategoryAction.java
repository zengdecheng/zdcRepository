package com.xbd.erp.category.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xbd.erp.base.action.Action;
import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.erp.category.service.CategoryService;
import com.xbd.erp.timebase.service.TimebaseService;
import com.xbd.erp.timebaseentry.service.TimebaseEntryService;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderDetail;
import com.xbd.oa.vo.OaTimebase;
import com.xbd.oa.vo.OaTimebaseEntry;

@Results({ @Result(name = "page4list", type = "redirect", location = "category/list") })
public class CategoryAction extends Action {

	private static final long serialVersionUID = 1302383805516363265L;
	public static final Logger logger = Logger.getLogger(CategoryAction.class);

	private OaCategory oaCategory; // 品类对象
	private OaTimebase dhTimebase; // 基准时间设置品类对应流程记录
	private List<OaTimebaseEntry> dhTimebaseEntries; // 基准时间设置具体节点时长定义
	private BaseDao<?> baseDao;
	private CategoryService categoryService; // cagetory的service
	private TimebaseService timebaseService; // Timebase的Service
	private TimebaseEntryService timebaseEntryService; // TimebaseEntry的Service

	public CategoryService getCategoryService() {
		return categoryService;
	}

	@Resource(name = "categoryService")
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Resource(name = "timebaseService")
	public void setTimebaseService(TimebaseService timebaseService) {
		this.timebaseService = timebaseService;
	}

	@Resource(name = "timebaseEntryService")
	public void setTimebaseEntryService(TimebaseEntryService timebaseEntryService) {
		this.timebaseEntryService = timebaseEntryService;
	}

	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	@Resource(name = "baseDaoImpl")
	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 
	 * @Title: list
	 * @Description: TODO查询品类列表
	 *
	 * @author 张华
	 * @return
	 */
	// page4list
	public String list() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_BY_SQL);
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		this.beans = baseDao.getObjectsBySql(fsp);
		fsp.setRecordCount(getObjectsCountSql(fsp));
		return "category/page4list";
	}

	/**
	 * 
	 * @Title: page4add
	 * @Description: TODO添加品类表单
	 *
	 * @author lanyu
	 * @return
	 */
	// page4add
	public String page4add() {
		return "category/page4add";
	}

	/**
	 * 
	 * @Title: add
	 * @Description: TODO添加品类
	 *
	 * @author 张华
	 * @return
	 */
	public String add() {
		this.saveCategoryDetail();
		return "page4list";
	}

	/**
	 * 
	 * @Title: page4detail
	 * @Description: TODO品类详情获取
	 *
	 * @author 张华
	 * @return
	 */
	public String page4detail() {
		this.getCategoryDetail();
		return "category/page4detail";
	}

	/**
	 * 
	 * @Title: page4edit
	 * @Description: TODO编辑品类跳转
	 *
	 * @author 张华
	 * @return
	 */
	public String page4edit() {
		this.getCategoryDetail();
		return "category/page4edit";
	}

	/**
	 * 
	 * @Title: edit
	 * @Description: TODO编辑品类保存
	 *
	 * @author 张华
	 * @return
	 */
	public String edit() {
		this.saveCategoryDetail();
		return "page4list";
	}

	/**
	 * 
	 * @Title: getCategoryDetail
	 * @Description: TODO获取品类详情数据，包括品类、基准时间设置、基准时间设置细节
	 *
	 * @author 张华
	 */
	private void getCategoryDetail() {
		// 查询品类数据
		this.oaCategory = this.categoryService.findOaCategory(this.oaCategory.getId());

		// 查询基准时间设置
		if (null != this.oaCategory) {
			this.dhTimebase = this.timebaseService.findTimebase(this.oaCategory.getId());
		}

		// 查询基准时间设置细节
		if (null != this.dhTimebase) {
			this.dhTimebaseEntries = this.timebaseEntryService.listTimebaseEntries(dhTimebase.getId());
		}
	}

	/**
	 * 
	 * @Title: saveCategoryDetail
	 * @Description: TODO保存品类详情数据，包括品类、基准时间设置、基准时间设置细节
	 *
	 * @author 张华
	 */
	private void saveCategoryDetail() {
		// 保存品类数据
		this.categoryService.save(this.oaCategory);
		// 保存基准时间设置
		this.timebaseService.saveOaTimebase(dhTimebase, this.oaCategory);
		// 保存基准时间设置细节
		this.timebaseEntryService.saveOaTimebaseEntries(this.dhTimebaseEntries, dhTimebase.getId());
	}

	/**
	 * 
	 * @Title: hour2ms
	 * @Description: TODO把品类中涉及时间的字段以小时转换为毫秒，用于存储到数据库
	 *
	 * @author 张华
	 * @param oaCategory
	 * @return
	 */
	private OaCategory hour2ms(OaCategory oaCategory) {
		try {
			if (null != oaCategory.getDahuoCyc() && oaCategory.getDahuoCyc() > 0) {
				oaCategory.setDahuoCyc(oaCategory.getDahuoCyc() * 60 * 60 * 1000);
			}
			if (null != oaCategory.getDabanCyc() && oaCategory.getDabanCyc() > 0) {
				oaCategory.setDabanCyc(oaCategory.getDabanCyc() * 60 * 60 * 1000);
			}

			if (null != oaCategory.getEmbroidery() && oaCategory.getEmbroidery() > 0) {
				oaCategory.setEmbroidery(oaCategory.getEmbroidery() * 60 * 60 * 1000);
			} else {
				oaCategory.setEmbroidery(0l);
			}
			if (null != oaCategory.getWashwaterTime() && oaCategory.getWashwaterTime() > 0) {
				oaCategory.setWashwaterTime(oaCategory.getWashwaterTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setWashwaterTime(0l);
			}
			if (null != oaCategory.getPrintingTime() && oaCategory.getPrintingTime() > 0) {
				oaCategory.setPrintingTime(oaCategory.getPrintingTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setPrintingTime(0l);
			}

			if (null != oaCategory.getFoldingTime() && oaCategory.getFoldingTime() > 0) {
				oaCategory.setFoldingTime(oaCategory.getFoldingTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setFoldingTime(0l);
			}
			if (null != oaCategory.getDalanTime() && oaCategory.getDalanTime() > 0) {
				oaCategory.setDalanTime(oaCategory.getDalanTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setDalanTime(0l);
			}
			if (null != oaCategory.getBeadsTime() && oaCategory.getBeadsTime() > 0) {
				oaCategory.setBeadsTime(oaCategory.getBeadsTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setBeadsTime(0l);
			}

			if (null != oaCategory.getSellWait() && oaCategory.getSellWait() > 0) {
				oaCategory.setSellWait(oaCategory.getSellWait() * 60 * 60 * 1000);
			} else {
				oaCategory.setSellWait(0l);
			}
			if (null != oaCategory.getPaymentWait() && oaCategory.getPaymentWait() > 0) {
				oaCategory.setPaymentWait(oaCategory.getPaymentWait() * 60 * 60 * 1000);
			} else {
				oaCategory.setPaymentWait(0l);
			}
			if (null != oaCategory.getOtherTime() && oaCategory.getOtherTime() > 0) {
				oaCategory.setOtherTime(oaCategory.getOtherTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setOtherTime(0l);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return oaCategory;
	}

	/**
	 * 
	 * @Title: ms2hour
	 * @Description: TODO把品类中涉及时间的字段以毫秒转换为小时，用于显示到前台
	 *
	 * @author 张华
	 * @param oaCategory
	 * @return
	 */
	private OaCategory ms2hour(OaCategory oaCategory) {
		try {
			if (null != oaCategory.getDahuoCyc() && oaCategory.getDahuoCyc() > 0) {
				oaCategory.setDahuoCyc(oaCategory.getDahuoCyc() / 60 / 60 / 1000);
			}
			if (null != oaCategory.getDabanCyc() && oaCategory.getDabanCyc() > 0) {
				oaCategory.setDabanCyc(oaCategory.getDabanCyc() / 60 / 60 / 1000);
			}

			if (null != oaCategory.getEmbroidery() && oaCategory.getEmbroidery() > 0) {
				oaCategory.setEmbroidery(oaCategory.getEmbroidery() / 60 / 60 / 1000);
			} else {
				oaCategory.setEmbroidery(0l);
			}
			if (null != oaCategory.getWashwaterTime() && oaCategory.getWashwaterTime() > 0) {
				oaCategory.setWashwaterTime(oaCategory.getWashwaterTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setWashwaterTime(0l);
			}
			if (null != oaCategory.getPrintingTime() && oaCategory.getPrintingTime() > 0) {
				oaCategory.setPrintingTime(oaCategory.getPrintingTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setPrintingTime(0l);
			}

			if (null != oaCategory.getFoldingTime() && oaCategory.getFoldingTime() > 0) {
				oaCategory.setFoldingTime(oaCategory.getFoldingTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setFoldingTime(0l);
			}
			if (null != oaCategory.getDalanTime() && oaCategory.getDalanTime() > 0) {
				oaCategory.setDalanTime(oaCategory.getDalanTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setDalanTime(0l);
			}
			if (null != oaCategory.getBeadsTime() && oaCategory.getBeadsTime() > 0) {
				oaCategory.setBeadsTime(oaCategory.getBeadsTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setBeadsTime(0l);
			}

			if (null != oaCategory.getSellWait() && oaCategory.getSellWait() > 0) {
				oaCategory.setSellWait(oaCategory.getSellWait() / 60 / 60 / 1000);
			} else {
				oaCategory.setSellWait(0l);
			}
			if (null != oaCategory.getPaymentWait() && oaCategory.getPaymentWait() > 0) {
				oaCategory.setPaymentWait(oaCategory.getPaymentWait() / 60 / 60 / 1000);
			} else {
				oaCategory.setPaymentWait(0l);
			}
			if (null != oaCategory.getOtherTime() && oaCategory.getOtherTime() > 0) {
				oaCategory.setOtherTime(oaCategory.getOtherTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setOtherTime(0l);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return oaCategory;
	}

	public String updateHisDate() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_BY_EQL);
		List<OaOrder> beans = baseDao.getObjectsByEql(fsp);
		for (int i = 0; i < beans.size(); i++) {
			OaOrder oaOrder = beans.get(i);
			if (oaOrder.getStyleClass() != null && !"".equals(oaOrder.getStyleClass())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_ALL_BY_SQL);
				fsp.set("style_class", oaOrder.getStyleClass());
				superList = baseDao.getObjectsBySql(fsp);
				if (superList.size() > 0) {
					oaOrder.setSellReadyTime(Long.parseLong(superList.get(0).get("sell_wait").toString())); // 销售准备时间
					if (oaOrder.getType().equals("2")) { // 标准缓冲时间
						oaOrder.setStandardTime(Long.parseLong(superList.get(0).get("daban_cyc").toString()));
					} else {
						oaOrder.setStandardTime(Long.parseLong(superList.get(0).get("dahuo_cyc").toString()));
					}
					// 特殊工艺时间
					Long craftTime = (superList.get(0).get("embroidery") != null ? Long.parseLong(superList.get(0).get("embroidery").toString()) : 0)
							+ (superList.get(0).get("washwater_time") != null ? Long.parseLong(superList.get(0).get("washwater_time").toString()) : 0)
							+ (superList.get(0).get("printing_time") != null ? Long.parseLong(superList.get(0).get("printing_time").toString()) : 0)
							+ (superList.get(0).get("folding_time") != null ? Long.parseLong(superList.get(0).get("folding_time").toString()) : 0)
							+ (superList.get(0).get("dalan_time") != null ? Long.parseLong(superList.get(0).get("dalan_time").toString()) : 0)
							+ (superList.get(0).get("beads_time") != null ? Long.parseLong(superList.get(0).get("beads_time").toString()) : 0)
							+ (superList.get(0).get("other_time") != null ? Long.parseLong(superList.get(0).get("other_time").toString()) : 0);
					oaOrder.setCraftTime(craftTime);
				}

				// 货期
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 18:00:00");
				if (oaOrder.getPreVersionDate() != null && null != oaOrder.getPreproductDays()) {
					Date preVersion = (Date) oaOrder.getPreVersionDate();
					Calendar c = Calendar.getInstance();
					c.setTime(preVersion);
					c.add(c.DAY_OF_YEAR, oaOrder.getPreproductDays());
					Date temp_date = c.getTime();

					try {
						String string = df.format(temp_date);
						df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						oaOrder.setGoodsTime(new Timestamp(df.parse(string).getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						String string = df.format(oaOrder.getExceptFinish());
						df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						oaOrder.setGoodsTime(new Timestamp(df.parse(string).getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// 产前版日期
				if (null != oaOrder.getPreVersionDate()) {
					df = new SimpleDateFormat("yyyy-MM-dd 18:00:00");
					try {
						String string = df.format(oaOrder.getPreVersionDate());
						df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						oaOrder.setPreVersionDate(new Timestamp(df.parse(string).getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			baseDao.saveObject(oaOrder);
		}

		return null;
	}

	public String updateHisDate2() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_BY_EQL);
		List<OaOrder> beans = baseDao.getObjectsByEql(fsp);

		for (int i = 0; i < beans.size(); i++) {
			Map<OaOrderDetail, String> stepMap_01 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_02 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_03 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_04 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_05 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_06 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_07 = new HashMap<OaOrderDetail, String>();
			Map<OaOrderDetail, String> stepMap_08 = new HashMap<OaOrderDetail, String>();

			OaOrder oaOrder = beans.get(i);
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ORDER_BY_EQL);
			fsp.set("oa_order", oaOrder.getId());
			List<OaOrderDetail> superList = baseDao.getObjectsByEql(fsp);
			for (int j = 0; j < superList.size(); j++) {
				// OaOrderDetail oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, Integer.parseInt(superList.get(j).get("id").toString()));
				if (superList.get(j).getWfStep().equals("b_mr_improve_2")) {
					stepMap_01.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
				if (superList.get(j).getWfStep().equals("c_mr_improve_2")) {
					stepMap_01.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("b_ppc_confirm_3")) {
					stepMap_02.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
				if (superList.get(j).getWfStep().equals("c_ppc_assign_3")) {
					stepMap_02.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("b_pur_confirm_4")) {
					stepMap_03.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
				if (superList.get(j).getWfStep().equals("c_fi_pay_4")) {
					stepMap_03.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("b_ppc_confirm_5")) {
					stepMap_04.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
				if (superList.get(j).getWfStep().equals("c_ppc_factoryMsg_5")) {
					stepMap_04.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("b_qc_confirm_6")) {
					stepMap_05.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
				if (superList.get(j).getWfStep().equals("c_qc_cutting_6")) {
					stepMap_05.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("c_ppc_confirm_7")) {
					stepMap_06.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("c_qc_printing_8")) {
					stepMap_07.put(superList.get(j), superList.get(j).getWfStep().toString());
				}

				if (superList.get(j).getWfStep().equals("c_ppc_confirm_9")) {
					stepMap_08.put(superList.get(j), superList.get(j).getWfStep().toString());
				}
			}

			if (stepMap_01.size() > 1) {
				updateOaderDetail(stepMap_01);
			}

			if (stepMap_02.size() > 1) {
				updateOaderDetail(stepMap_02);
			}

			if (stepMap_03.size() > 1) {
				updateOaderDetail(stepMap_03);
			}

			if (stepMap_04.size() > 1) {
				updateOaderDetail(stepMap_04);
			}

			if (stepMap_05.size() > 1) {
				updateOaderDetail(stepMap_05);
			}

			if (stepMap_06.size() > 1) {
				updateOaderDetail(stepMap_06);
			}

			if (stepMap_07.size() > 1) {
				updateOaderDetail(stepMap_07);
			}

			if (stepMap_08.size() > 1) {
				updateOaderDetail(stepMap_08);
			}
		}

		return null;
	}

	public void updateOaderDetail(Map<OaOrderDetail, String> stepMap) {
		int[] k = new int[stepMap.size()];
		int t = 0;
		for (OaOrderDetail key : stepMap.keySet()) {
			k[t] = key.getId();
			t++;
		}
		Arrays.sort(k);
		t = 0;
		for (OaOrderDetail key : stepMap.keySet()) {
			k[t] = key.getId();
			t++;
			if (key.getId() != k[k.length - 1]) {
				key.setBackFlag("1");
				baseDao.saveObject(key);
			}
		}
	}

	public String updateHisDate3() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_BY_EQL_LS);
		List<OaOrder> beans = baseDao.getObjectsByEql(fsp);
		for (int i = 0; i < beans.size(); i++) {
			OaOrder oaOrder = beans.get(i);
			if (oaOrder.getStyleClass() != null && !"".equals(oaOrder.getStyleClass())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_ALL_BY_SQL);
				fsp.set("style_class", oaOrder.getStyleClass());
				superList = baseDao.getObjectsBySql(fsp);
				if (superList.size() > 0) {
					String craft = oaOrder.getStyleCraft();
					if (craft != null && !"".equals(craft)) {
						Long craftTime = 0l;

						String str[] = craft.split(",");
						for (String s : str) {
							switch (s) {
							case "绣花":
								craftTime = craftTime + (superList.get(0).get("embroidery") != null ? Long.parseLong(superList.get(0).get("embroidery").toString()) : 0);
								break;
							case "洗水":
								craftTime = craftTime + (superList.get(0).get("washwater_time") != null ? Long.parseLong(superList.get(0).get("washwater_time").toString()) : 0);
								break;
							case "印花":
								craftTime = craftTime + (superList.get(0).get("printing_time") != null ? Long.parseLong(superList.get(0).get("printing_time").toString()) : 0);
								break;
							case "缩折/打条":
								craftTime = craftTime + (superList.get(0).get("folding_time") != null ? Long.parseLong(superList.get(0).get("folding_time").toString()) : 0);
								break;
							case "打揽":
								craftTime = craftTime + (superList.get(0).get("dalan_time") != null ? Long.parseLong(superList.get(0).get("dalan_time").toString()) : 0);
								break;
							case "订珠":
								craftTime = craftTime + (superList.get(0).get("beads_time") != null ? Long.parseLong(superList.get(0).get("beads_time").toString()) : 0);
								break;
							case "其他":
								craftTime = craftTime + (superList.get(0).get("other_time") != null ? Long.parseLong(superList.get(0).get("other_time").toString()) : 0);
								break;
							}
						}
						oaOrder.setCraftTime(craftTime);
					} else {
						oaOrder.setCraftTime(0l);
					}

				}
			}
			baseDao.saveObject(oaOrder);
		}

		return null;
	}

	public OaCategory getOaCategory() {
		return oaCategory;
	}

	public void setOaCategory(OaCategory oaCategory) {
		this.oaCategory = oaCategory;
	}

	public OaTimebase getDhTimebase() {
		return dhTimebase;
	}

	public void setDhTimebase(OaTimebase dhTimebase) {
		this.dhTimebase = dhTimebase;
	}

	public List<OaTimebaseEntry> getDhTimebaseEntries() {
		return dhTimebaseEntries;
	}

	public void setDhTimebaseEntries(List<OaTimebaseEntry> dhTimebaseEntries) {
		this.dhTimebaseEntries = dhTimebaseEntries;
	}
}
