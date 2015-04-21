package com.xbd.erp.category.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.impl.BaseDaoImpl;
import com.xbd.erp.category.dao.CategoryDao;
import com.xbd.oa.vo.OaCategory;

@Repository("categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<OaCategory> implements CategoryDao {

}
