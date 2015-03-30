package com.xbd.oa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DAOQUERY {
	String name()default "";
	String index() default "1";
	String description()default "";
}
