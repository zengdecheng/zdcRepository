package com.xbd.erp.category.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.category.dao.CategoryDao;
import com.xbd.erp.category.service.CategoryService;
import com.xbd.oa.vo.OaCategory;

@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<OaCategory> implements CategoryService {

	private CategoryDao categoryDao; // category的Dao

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	@Resource(name = "categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		super.setBaseHibernateDao(categoryDao);
		this.categoryDao = categoryDao;
	}

	@Override
	public void save(OaCategory oaCategory) {
		// TODO Auto-generated method stub
		try {
			if (null != oaCategory) {
				// 保存品类对象
				this.categoryDao.saveOrUpdate(oaCategory);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public OaCategory findOaCategory(Integer id) {
		// TODO Auto-generated method stub
		if (null != id && id > 0) {
			return this.categoryDao.get(id);
		}
		return null;
	}

}
