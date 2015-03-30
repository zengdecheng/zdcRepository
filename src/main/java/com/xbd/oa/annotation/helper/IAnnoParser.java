package com.xbd.oa.annotation.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IAnnoParser {
public void dealField (Object obj,Field field);
public void dealMethod(Object obj,Method method);
public void dealClass(Object obj);
public Class getAnnoClass();
}
