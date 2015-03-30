package com.xbd.oa.utils;

import org.apache.commons.beanutils.LazyDynaMap;


public class LOMSDynaBean extends LazyDynaMap {

	private static final long serialVersionUID = 1L;
	boolean isConvert = true;

	public boolean isConvert() {
		return isConvert;
	}

	public void setConvert(boolean isConvert) {
		this.isConvert = isConvert;
	}

	@SuppressWarnings("unchecked")
	public void set(String name, Object value) {
		if (isRestricted() && !values.containsKey(name)) {
			throw new IllegalArgumentException("Invalid property name '" + name
					+ "' (DynaClass is restricted)");
		}
		//for database to application charset change : iso-8859-1 to gbk
		/*if (isConvert() && value instanceof String)
			values.put(name, StringUtils.ISO2GBK((String) value));
		else
			values.put(name, value);*/
		values.put(name, value);

	}

	/*public static void main(String[] args) {
		String str = null;
		System.out.println(str instanceof Object);
		System.out.println(str instanceof String);

	}*/

}
