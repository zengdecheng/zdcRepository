package com.xbd.oa.annotation.helper;

import java.lang.reflect.Field;

public class RefHelper {

	@SuppressWarnings("unchecked")
	public static void dealField(Object obj, IAnnoParser parser) {
		Field [] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(parser.getAnnoClass())) {
            	parser.dealField(obj, field);
            }
        }
//      目前只考虑一级父类如果有anno的话，做处理，在上层次则不考虑，为了效率
        Field [] supFields = obj.getClass().getSuperclass().getDeclaredFields();
        for (Field field : supFields) {
            if (field.isAnnotationPresent(parser.getAnnoClass())) {
            	parser.dealField(obj, field);
            }
        }
        
	}
	
	
	

}
