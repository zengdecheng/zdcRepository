package com.xbd.oa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.xbd.oa.annotation.helper.AnnoConst;

@Retention(RetentionPolicy.RUNTIME)
public @interface FSP {
	boolean isPagination() default false;
	String queryBy() default AnnoConst.NONE;
	String parseFucName() default "";
	String hasBack() default AnnoConst.HAS_BACK_NO;
	int pageSize() default 10;
	String orderBy() default "";
}
