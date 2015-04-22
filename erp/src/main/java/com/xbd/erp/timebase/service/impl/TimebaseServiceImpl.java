package com.xbd.erp.timebase.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.LazyDynaMap;
import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.timebase.dao.TimebaseDao;
import com.xbd.erp.timebase.service.TimebaseService;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.WorkFlowUtil;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaTimebase;

@Service("timebaseService")
public class TimebaseServiceImpl extends BaseServiceImpl<OaTimebase> implements TimebaseService {

	private TimebaseDao timebaseDao; // timebaseDao的Dao

	public TimebaseDao getTimebaseDao() {
		return timebaseDao;
	}

	@Resource(name = "timebaseDao")
	public void setTimebaseDao(TimebaseDao timebaseDao) {
		super.setBaseHibernateDao(timebaseDao);
		this.timebaseDao = timebaseDao;
	}

	@Override
	public void saveOaTimebase(OaTimebase oaTimebase, OaCategory oaCategory) {
		// TODO Auto-generated method stub
		try {
			// 保存基准时间设置
			oaTimebase.setCategoryId(oaCategory.getId()); // 品类Id
			oaTimebase.setName(oaCategory.getName()); // 品类名称
			oaTimebase.setDefineKey(ConstantUtil.WORKFLOW_KEY_PROCESS3); // 大货工作流key

			// 获取工作流列表
			List<LazyDynaMap> process_list = WorkFlowUtil.getProcessDefinitionList();
			for (LazyDynaMap pl : process_list) {
				if (oaTimebase.getDefineKey().equals(pl.get("key"))) {
					oaTimebase.setDefineId((String) pl.get("id")); // 设置流程定义Id
				}
			}
			this.timebaseDao.saveOrUpdate(oaTimebase); // 保存基准时间设置
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public OaTimebase findTimebase(Integer categoryId) {
		// TODO Auto-generated method stub
		try {
			return this.timebaseDao.findUniqueByProperty("categoryId", categoryId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
