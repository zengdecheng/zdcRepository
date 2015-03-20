package org.use.base.daoquery;

import java.io.InputStream;
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
import org.use.base.FSPBean;
import org.use.base.dao.impl.DaoEjb;
import org.use.base.daoquery.bean.FunctionBean;
import org.use.base.daoquery.bean.ParamBean;
import org.use.base.daoquery.bean.ParserBean;
import org.use.base.daoquery.bean.QueryBean;
import org.use.base.utils.base.CastorUtils;

public class DaoQueryByXml {
	private static PatternMatcher matcher = new Perl5Matcher();

	private static PatternCompiler compiler = new Perl5Compiler();

	private static String expressionStr = "\\$\\{case\\}(\\d+)";

	private static String replacePreStr = "\\$\\{case\\}";

	private final static String EQL_SP_SPECIAL = "sp";
	
	//private URL urlCfg =  this.getClass().getResource("config_mapping.xml");
	private static Mapping daoQueryByXmlCfgMapping;
	
	private boolean ifHibernate = false;
	
	
	public static HashMap<String,String> sqlStrMap = new HashMap<String, String>();
	public static HashMap<String,List<String>> sqlParamMap = new HashMap<String,List<String>>();
	public static HashMap<String,QueryBean> queryBeanMap = new HashMap<String, QueryBean>();

	
	static{
		try {
			daoQueryByXmlCfgMapping = new Mapping();
			URL daoQueryByXmlCfgUrl = Thread.currentThread().getContextClassLoader().getResource("org/use/base/daoquery/config_mapping.xml");
			daoQueryByXmlCfgMapping.loadMapping(daoQueryByXmlCfgUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public DaoQueryByXml(){
		
	}
	/**
	 * 
	 * @param ifHibernate 是否为hiberante 解析
	 */
	public DaoQueryByXml(boolean ifHibernate){
		this.ifHibernate = ifHibernate;
	}
	/**
	 * 把fsp里面的 *DaoImpl_query.xml||[paramName1]||[paramName1]......解析出来的sql String
	 * 缓存到sqlMap里面，对于大并发进行支持
	 * 同时要对params进行处理，把fsp值赋进去
	 * @param fsp
	 * @param params
	 * @param dao
	 * @return
	 */
	public String queryByXml(FSPBean fsp, List params, DaoEjb dao) {
		String key = getKeyForFsp(fsp,dao);
		String returnSql = "";
		if(sqlStrMap.containsKey(key)){
			parseFspByParams(fsp,params,sqlParamMap.get(key), dao);
			 returnSql = sqlStrMap.get(key);
		}else{
			List<String> sqlContainsParams = new ArrayList<String>();
			String newSql = queryByXmlRecursion(fsp,params,dao,sqlContainsParams);
			sqlStrMap.put(key, newSql);
			sqlParamMap.put(key, sqlContainsParams);
			returnSql =  newSql;
		
		}
		//加入$1处理模式，直接替换原语句
		if(fsp.getStaticSqlPart() != null){
			returnSql = returnSql.replace("$$", fsp.getStaticSqlPart());
		}
		return returnSql;
		//return sqlMap.get(key);
	}
	
	private void parseFspByParams(FSPBean fsp,List params,List<String> sqlParamList,DaoEjb dao){
		for (String param : sqlParamList) {
			if(param.endsWith("%")){
				params.add(dao.dealFspEqlLike(fsp, param.substring(0,param.length()-1)));
				
			}else{
				params.add(dao.dealFspEql(fsp, param));
			}
		}
	}
	
	private String getKeyForFsp(FSPBean fsp,DaoEjb dao){
		StringBuffer buf = new StringBuffer();
		buf.append(dao.getClass().getName());
		buf.append("||").append(fsp.get(FSPBean.FSP_QUERY_BY_XML));
		for (Object key : fsp.getMap().keySet()) {
			if(!key.equals(FSPBean.FSP_QUERY_BY_XML)){
				if(dao.dealFspEql(fsp, (String)key) != null){
					buf.append("||").append(key);
				}
			}
		}
		return buf.toString();
		
	}
	/**
	 * 如果sqlMap找不到，就要重新解析，这个是同步方法会比较慢，大规模并发下用到这个方法的情况比较少
	 * @param fsp
	 * @param params
	 * @param dao
	 * @return
	 */
	public synchronized String queryByXmlRecursion(FSPBean fsp, List params, DaoEjb dao,List sqlContainsParams){
		QueryBean queryBean;
		FunctionBean functionBean;
		try {
			String keyStr = dao.getClass().getName()+"DaoImpl_query.xml";
			if(queryBeanMap.containsKey(keyStr)){
				queryBean = queryBeanMap.get(keyStr);
			}else{
			InputStream in =dao.getClass().getResourceAsStream(dao.getVoName()+"DaoImpl_query.xml");
			queryBean = (QueryBean) CastorUtils.unmarshal(in, daoQueryByXmlCfgMapping);
			queryBean.initmapFunc();
			queryBeanMap.put(keyStr, queryBean);
			}
			functionBean = queryBeanMap.get(keyStr).getMapFunc().get(fsp.get(FSPBean.FSP_QUERY_BY_XML));
		    
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return paramRecursion(functionBean, fsp, params, dao,sqlContainsParams);
	}

	private String parseFsp(FSPBean fsp, ParserBean parser, List params,
			DaoEjb dao,FunctionBean functionBean,List sqlContainsParams) {
		String way = "xml";
		String eql = "";
		if (parser.getWay() != null
				&& (parser.getWay().equals("code") || parser.getWay().equals(
						"xml"))) {
			way = parser.getWay();
		}
		if (way.equals("xml")) {
			if (parser.getParams() != null && parser.getParams().size() > 0) {			
					eql = parseFspByXml(fsp, parser.getParams(), params, dao ,functionBean,sqlContainsParams);
			}

		} else {
		     //由于大规模并发的压力，如果xml里面的sql拼装复杂到要用到code组装，就不再适合queryByxml了
			//所以不再提供parseFspByCode了
			// 如果遇到了需要程序才能拼装的eql的时候，就使用如下dao里面的方法
			//	eql = parseFspByCode(fsp, params, parser.getIndex(), dao,sqlContainsParams);
		}
		return eql;
	}

	/*private String parseFspByCode(FSPBean fsp, List params, String index,
			DaoEjb dao,List sqlContainsParams) {
		String eql = "";
		Method[] methods = dao.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(DAOQUERY.class)) {
				// parser.dealField(obj, field);
				DAOQUERY query = method.getAnnotation(DAOQUERY.class);
				if (query.name().equals(fsp.get(FSPBean.FSP_QUERY_BY_XML))
						&& query.index().equals(index)) {
					try {
						method.setAccessible(true);
						Object arglist[] = new Object[2];
						arglist[0] = fsp;
						arglist[1] = params;
						Object retobj = method.invoke(dao, arglist);
						if (retobj != null) {
							eql = (String) retobj;
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return eql;

	}*/

	private String parseFspByXml(FSPBean fsp, List<ParamBean> ParserParams,
			List params, DaoEjb dao,FunctionBean functionBean,List sqlContainsParams) {
        boolean ifEql = true;
		if(functionBean.getType()!= null && functionBean.getType().toLowerCase().equals("sql")){
			ifEql = false;
		}
		StringBuffer buf = new StringBuffer();
        for (ParamBean paramBean : ParserParams) {
			String op = paramBean.getOp();
			String sqlname = paramBean.getSqlname();
			if (op == null || op.equals("")) {
				op = "=";
			}
			if(sqlname == null || sqlname.equals("")){
				sqlname = paramBean.getFspname();
			}
            if (dao.dealFspEql(fsp, paramBean.getFspname()) != null) {
				if (op.equals(EQL_SP_SPECIAL)) {
					buf.append(sqlname);
					sqlContainsParams.add(paramBean.getFspname());
					params.add(dao.dealFspEql(fsp, paramBean.getFspname()));
				} else if (op.trim().toLowerCase().equals("like")) {
					buf.append(
							" and " + sqlname + " " + op + " "
									+ "?");
					if((!ifHibernate)&&ifEql) {buf.append(params.size() + 1);}
					sqlContainsParams.add(paramBean.getFspname()+"%");
					params.add(dao.dealFspEqlLike(fsp, paramBean.getFspname()));
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
					if (paramBean.getTotype() != null
							&& paramBean.getTotype().equals("java.util.Date")) {
						buf.append(" and ").append(sqlname)
								.append(" ").append(op).append(" to_date(?");
						if((!ifHibernate)&&ifEql) {buf.append(params.size() + 1);}
						buf.append(" , 'yyyy-MM-dd')");
					} else {
						buf.append(
								" and " + sqlname + " " + op
										+ " " + "?");
										
						if((!ifHibernate)&&ifEql) {buf.append(params.size() + 1);}
					}
					sqlContainsParams.add(paramBean.getFspname());
					params.add(dao.dealFspEql(fsp, paramBean.getFspname()));
				}
			}
		}
        return buf.toString();
	}

	public void changeBean(Object obj) {
		//System.out.println(obj.getClass().getResource("").getPath());
	}

	// modify by hongliang on 2010-1-13 上午11:49:23 comments:添加了synchronized关键字。
	public  String paramRecursion(FunctionBean functionBean, FSPBean fsp,
			 List params, DaoEjb dao,List sqlContainsParams) {
		String tmpOriginalStr = "";
		MatchResult result = null;
		org.apache.oro.text.regex.Pattern pattern2 = null;
		try {
			pattern2 = compiler.compile(expressionStr,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			if(functionBean == null) throw new RuntimeException("Not exists this function when use "+dao.getClass()+" please check " + dao.getVoName()+"DaoImpl_query.xml");
			PatternMatcherInput input = new PatternMatcherInput(functionBean.getSql());
			tmpOriginalStr = functionBean.getSql();
			while (matcher.contains(input, pattern2)) {
				result = matcher.getMatch();
				// String tmpStr = EjbResource.getString(result.group(1));
				//System.out.println(result.group(1));
				for (ParserBean bean : functionBean.getParsers()) {
					if (bean.getIndex().equals(result.group(1))) {
						tmpOriginalStr = tmpOriginalStr.replaceAll(
								replacePreStr + result.group(1), parseFsp(fsp,
										bean, params, dao,functionBean,sqlContainsParams));
						continue;
					}
				}
			}
			return tmpOriginalStr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		FunctionBean bean = new FunctionBean();
		DaoQueryByXml daoXml = new DaoQueryByXml();
		FSPBean fsp = new FSPBean();
		List params = new ArrayList();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, "billCount");
		// String eql = daoXml.queryByXml(fsp, params, new BillAct);
		// System.out.println(eql);
		// xm.changeBean(bean);
		// xm.paramRecursion("select * from where ${case1} in ${case2}");
	}
}
