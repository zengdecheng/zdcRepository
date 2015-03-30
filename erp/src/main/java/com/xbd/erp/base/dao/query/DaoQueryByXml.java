package com.xbd.erp.base.dao.query;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.exolab.castor.mapping.Mapping;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.query.FunctionBean;
import com.xbd.erp.base.pojo.query.ParamBean;
import com.xbd.erp.base.pojo.query.ParserBean;
import com.xbd.erp.base.pojo.query.QueryBean;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.utils.CastorUtils;

public class DaoQueryByXml {

	private static PatternMatcher matcher = new Perl5Matcher();
	private static PatternCompiler compiler = new Perl5Compiler();
	private static String expressionStr = "\\$\\{case\\}(\\d+)";
	private static String replacePreStr = "\\$\\{case\\}";
	private final static String EQL_SP_SPECIAL = "sp";
	private static Mapping daoQueryByXmlCfgMapping;
	private boolean ifHibernate = false;

	public static HashMap<String, String> sqlStrMap = new HashMap<String, String>();
	public static HashMap<String, List<String>> sqlParamMap = new HashMap<String, List<String>>();
	public static HashMap<String, QueryBean> queryBeanMap = new HashMap<String, QueryBean>();

	static {
		try {
			daoQueryByXmlCfgMapping = new Mapping();
			URL daoQueryByXmlCfgUrl = Thread.currentThread().getContextClassLoader().getResource("com/xbd/erp/base/dao/query/config_mapping.xml");
			daoQueryByXmlCfgMapping.loadMapping(daoQueryByXmlCfgUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DaoQueryByXml() {
	}

	public DaoQueryByXml(boolean ifHibernate) {
		this.ifHibernate = ifHibernate;
	}

	@SuppressWarnings("all")
	private void parseFspByParams(FSPBean fsp, List params, List<String> sqlParamList, BaseDao<?> baseDao) {
		for (String param : sqlParamList) {
			if (param.endsWith("%")) {
				params.add(dealFspEqlLike(fsp, param.substring(0, param.length() - 1),baseDao));

			} else {
				params.add(dealFspEql(fsp, param,baseDao));
			}
		}
	}

	@SuppressWarnings("all")
	private String parseFspByXml(FSPBean fsp, List<ParamBean> ParserParams, List params, BaseDao<?> baseDao, FunctionBean functionBean, List sqlContainsParams) {
		boolean ifEql = true;
		if (functionBean.getType() != null && functionBean.getType().toLowerCase().equals("sql")) {
			ifEql = false;
		}
		StringBuffer buf = new StringBuffer();
		for (ParamBean paramBean : ParserParams) {
			String op = paramBean.getOp();
			String sqlname = paramBean.getSqlname();
			if (op == null || op.equals("")) {
				op = "=";
			}
			if (sqlname == null || sqlname.equals("")) {
				sqlname = paramBean.getFspname();
			}
			if (dealFspEql(fsp, paramBean.getFspname(),baseDao) != null) {
				if (op.equals(EQL_SP_SPECIAL)) {
					buf.append(sqlname);
					sqlContainsParams.add(paramBean.getFspname());
					params.add(dealFspEql(fsp, paramBean.getFspname(),baseDao));
				} else if (op.trim().toLowerCase().equals("like")) {
					buf.append(" and " + sqlname + " " + op + " " + "?");
					if ((!ifHibernate) && ifEql) {
						buf.append(params.size() + 1);
					}
					sqlContainsParams.add(paramBean.getFspname() + "%");
					params.add(dealFspEqlLike(fsp, paramBean.getFspname(),baseDao));
				} else if (op.trim().equalsIgnoreCase("in")) {
					boolean isSql = ((!ifHibernate) && ifEql);
					buf.append(" and ").append(sqlname).append(" in (");
					Object obj = fsp.getMap().get(paramBean.getFspname());
					if (obj.getClass().isArray()) {
						for (Object val : (Object[]) obj) {
							buf.append("?").append(isSql ? params.size() + 1 : "").append(",");
							params.add(val);
						}
					} else if (obj instanceof Collection) {
						for (Object val : (Collection<Object>) obj) {
							buf.append("?").append(isSql ? params.size() + 1 : "").append(",");
							params.add(val);
						}
					} else {
						buf.append("?").append(isSql ? params.size() + 1 : "").append(",");
						params.add(obj);
					}
					buf.setLength(buf.length() - 1);
					buf.append(")");
				} else {
					// 判断日期类型
					if (paramBean.getTotype() != null && paramBean.getTotype().equals("java.util.Date")) {
						buf.append(" and ").append(sqlname).append(" ").append(op).append(" to_date(?");
						if ((!ifHibernate) && ifEql) {
							buf.append(params.size() + 1);
						}
						buf.append(" , 'yyyy-MM-dd')");
					} else {
						buf.append(" and " + sqlname + " " + op + " " + "?");

						if ((!ifHibernate) && ifEql) {
							buf.append(params.size() + 1);
						}
					}
					sqlContainsParams.add(paramBean.getFspname());
					params.add(dealFspEql(fsp, paramBean.getFspname(),baseDao));
				}
			}
		}
		return buf.toString();
	}

	@SuppressWarnings("all")
	private String parseFsp(FSPBean fsp, ParserBean parser, List params, BaseDao<?> baseDao, FunctionBean functionBean, List sqlContainsParams) {
		String way = "xml";
		String eql = "";
		if (parser.getWay() != null && (parser.getWay().equals("code") || parser.getWay().equals("xml"))) {
			way = parser.getWay();
		}
		if (way.equals("xml")) {
			if (parser.getParams() != null && parser.getParams().size() > 0) {
				eql = parseFspByXml(fsp, parser.getParams(), params, baseDao, functionBean, sqlContainsParams);
			}
		} else {
			// 由于大规模并发的压力，如果xml里面的sql拼装复杂到要用到code组装，就不再适合queryByxml了
			// 所以不再提供parseFspByCode了
			// 如果遇到了需要程序才能拼装的eql的时候，就使用如下dao里面的方法
			// eql = parseFspByCode(fsp, params, parser.getIndex(), dao,sqlContainsParams);
		}
		return eql;
	}

	@SuppressWarnings("all")
	private String getKeyForFsp(FSPBean fsp, BaseDao baseDao) {
		StringBuffer buf = new StringBuffer();
		buf.append(baseDao.getClass().getName());
		buf.append("||").append(fsp.get(FSPBean.FSP_QUERY_BY_XML));
		for (Object key : fsp.getMap().keySet()) {
			if (!key.equals(FSPBean.FSP_QUERY_BY_XML)) {
				if (dealFspEql(fsp, (String) key,baseDao) != null) {
					buf.append("||").append(key);
				}
			}
		}
		return buf.toString();
	}

	private String paramRecursion(FunctionBean functionBean, FSPBean fsp, List<?> params, BaseDao<?> baseDao, List<?> sqlContainsParams) {
		String tmpOriginalStr = "";
		MatchResult result = null;
		org.apache.oro.text.regex.Pattern pattern2 = null;
		try {
			pattern2 = compiler.compile(expressionStr, Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (functionBean == null)
				throw new RuntimeException("Not exists this function when use " + baseDao.getClass() + " please check " + baseDao.getVoName() + "DaoImpl_query.xml");
			PatternMatcherInput input = new PatternMatcherInput(functionBean.getSql());
			tmpOriginalStr = functionBean.getSql();
			while (matcher.contains(input, pattern2)) {
				result = matcher.getMatch();
				for (ParserBean bean : functionBean.getParsers()) {
					if (bean.getIndex().equals(result.group(1))) {
						tmpOriginalStr = tmpOriginalStr.replaceAll(replacePreStr + result.group(1), parseFsp(fsp, bean, params, baseDao, functionBean, sqlContainsParams));
						continue;
					}
				}
			}
			return tmpOriginalStr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized String queryByXmlRecursion(FSPBean fsp, List<?> params, BaseDao<?> baseDao, List<?> sqlContainsParams) {
		QueryBean queryBean;
		FunctionBean functionBean;
		try {
			String keyStr = baseDao.getClass().getName() + "DaoImpl_query.xml";
			if (queryBeanMap.containsKey(keyStr)) {
				queryBean = queryBeanMap.get(keyStr);
			} else {
				InputStream in = baseDao.getClass().getResourceAsStream("/query/"+baseDao.getVoName() + "DaoImpl_query.xml");
				queryBean = (QueryBean) CastorUtils.unmarshal(in, daoQueryByXmlCfgMapping);
				queryBean.initmapFunc();
				queryBeanMap.put(keyStr, queryBean);
			}
			functionBean = queryBeanMap.get(keyStr).getMapFunc().get(fsp.get(FSPBean.FSP_QUERY_BY_XML));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return paramRecursion(functionBean, fsp, params, baseDao, sqlContainsParams);
	}

	@SuppressWarnings("all")
	public String queryByXml(FSPBean fsp, List<?> params, BaseDao baseDao) {
		String key = getKeyForFsp(fsp, baseDao);
		String returnSql = "";
		if (sqlStrMap.containsKey(key)) {
			parseFspByParams(fsp, params, sqlParamMap.get(key), baseDao);
			returnSql = sqlStrMap.get(key);
		} else {
			List<String> sqlContainsParams = new ArrayList<String>();
			String newSql = queryByXmlRecursion(fsp, params, baseDao, sqlContainsParams);
			sqlStrMap.put(key, newSql);
			sqlParamMap.put(key, sqlContainsParams);
			returnSql = newSql;

		}
		if (fsp.getStaticSqlPart() != null) {
			returnSql = returnSql.replace("$$", fsp.getStaticSqlPart());
		}
		return returnSql;
	}

	private Object dealFspEqlLike(FSPBean fsp, String name,BaseDao<?> baseDao) {
		Object object = dealFspEql(fsp, name,baseDao);
		if (object instanceof String) {
			String value = (String) object;
			return "%" + value + "%";
		}
		throw new RuntimeException("the value is not String type , is n't allowed use like %%");
	}

	private Object dealFspEql(FSPBean fsp, String name,BaseDao<?> baseDao) {
		Object object = fsp.getMap().get(name);
		Object valueObj = null;
		Field field = null;
		if (object == null) {
			return null;
		}
		if (object instanceof String[]) {
			String[] ss = (String[]) object;
			if (ss[0] != null) {
				valueObj = ss[0].trim(); // 加入了自动去空格
			}
		} else if (object instanceof String) {
			valueObj = ((String) object).trim();// 加入了自动去空格
		} else {
			valueObj = object;
		}
		try {
			if (valueObj == null || valueObj.equals("")) {
				return null;
			}
			if (baseDao.getVoClass() == null)
				return valueObj;
			// 判断vo里面是否有此属性
			boolean hasFiled = false;
			for (Field declaredField : baseDao.getVoClass().getDeclaredFields()) {
				if (declaredField.getName().equals(name)) {
					hasFiled = true;
					break;
				}
			}
			if (hasFiled) {
				field = baseDao.getVoClass().getDeclaredField(name);
			}
			if (field == null)
				return valueObj;
			if (valueObj instanceof String) {
				String value = (String) valueObj;
				value = value.trim();
				if (field.getType().equals(Integer.class)) {
					return Integer.valueOf(value);
				} else if (field.getType().equals(Float.class)) {
					return Float.valueOf(value);
				} else if (field.getType().equals(Long.class)) {
					return Long.valueOf(value);
				} else if (field.getType().equals(Double.class)) {
					return Double.valueOf(value);
				} else if (field.getType().equals(BigDecimal.class)) {
					return new BigDecimal(value);
				} else

				{
					return value;
				}
			} else {
				return valueObj;
			}
		} catch (Exception e) {
			throw new RuntimeException("not convert the value:" + object + " to type:" + field);
		}
	}
}
