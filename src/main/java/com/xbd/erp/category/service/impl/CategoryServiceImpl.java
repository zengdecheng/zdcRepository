package com.xbd.erp.category.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.LazyDynaMap;
import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.category.dao.CategoryDao;
import com.xbd.erp.category.service.CategoryService;
import com.xbd.erp.timebase.service.TimebaseService;
import com.xbd.erp.timebaseentry.service.TimebaseEntryService;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.WorkFlowUtil;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaTimebase;
import com.xbd.oa.vo.OaTimebaseEntry;

@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<OaCategory> implements CategoryService {

	private CategoryDao categoryDao; // category的Dao
	private TimebaseService timebaseService; // Timebase的Service
	private TimebaseEntryService timebaseEntryService; // TimebaseEntry的Service

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	@Resource(name = "categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		super.setBaseHibernateDao(categoryDao);
	}

	@Override
	public boolean edit(OaCategory oaCategory, OaTimebase dhTimebase, List<OaTimebaseEntry> dhTimebaseEntries) {
		// TODO Auto-generated method stub
		try {
			if (null != oaCategory) {
				// 保存品类对象
				saveOaCategory(oaCategory);

				// 保存基准时间设置
				dhTimebase.setCategoryId(oaCategory.getId()); // 品类Id
				dhTimebase.setName(oaCategory.getName()); // 品类名称
				dhTimebase.setDefineKey(ConstantUtil.WORKFLOW_KEY_PROCESS3); // 大货工作流key

				List<LazyDynaMap> process_list = WorkFlowUtil.getProcessDefinitionList(); // 获取工作流列表
				for (LazyDynaMap pl : process_list) {
					if (dhTimebase.getDefineKey().equals(pl.get("key"))) {
						dhTimebase.setDefineId((String) pl.get("id")); // 设置流程定义Id
					}
				}
				timebaseService.saveOaTimebase(dhTimebase); // 保存基准时间设置

				// 保存基准时间设置细节
				for (OaTimebaseEntry dhTimebaseEntry : dhTimebaseEntries) {
					dhTimebaseEntry.setOaTimebase(dhTimebase.getId()); // 基准时间设置Id
				}
				timebaseEntryService.saveOaTimebaseEntries(dhTimebaseEntries);

				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @Title: saveOaCategory
	 * @Description: TODO保存品类对象
	 *
	 * @author 张华
	 * @param oaCategory
	 * @throws Exception
	 */
	private void saveOaCategory(OaCategory oaCategory) throws Exception {
		categoryDao.saveOrUpdate(oaCategory);
	}

}
