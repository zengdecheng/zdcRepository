package com.xbd.oa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EJB {
	String name()default "";
    Class beanInterface()default Object.class;
	String beanName() default "";
	String description()default "";
}
