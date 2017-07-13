package com.nextech.erp.factory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import com.nextech.erp.dto.AbstractDTO;

public class AbstractRequestResponse {
	
	public static <T extends AbstractDTO> T setAbstractData(T t, Serializable serializable) {
		try {
			Object object = serializable;
			Class<?> class1 = object.getClass(); 
			Method method = class1.getMethod("getId");
			t.setId((long) method.invoke(object));
			Method method1 = class1.getMethod("getCreatedBy");
			t.setCreatedBy((long) method1.invoke(object));
			Method method2 = class1.getMethod("getCreatedDate");
			t.setCreatedDate((Timestamp) method2.invoke(object));
			Method method3 = class1.getMethod("getUpdatedBy");
			t.setUpdatedBy((long) method3.invoke(object));
			Method method4 = class1.getMethod("getUpdatedDate");
			t.setUpdatedDate((Timestamp) method4.invoke(object));
			Method method5 = class1.getMethod("getIsactive");
			t.setActive((boolean) method5.invoke(object));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
	public static Serializable setAbstractDataToEntity(Object object, Serializable serializable) {
		try {
			Class<?> class1 = serializable.getClass();
			Class<?> class2 = object.getClass();
			Method method = class1.getMethod("setId",long.class);
			Method method0 = class2.getMethod("getId");
			method.invoke(serializable,method0.invoke(object));
			Method method1 = class1.getMethod("setCreatedBy",long.class);
			Method method11 = class2.getMethod("getCreatedBy");
			method1.invoke(serializable,method11.invoke(object));
			Method method2 = class1.getMethod("setCreatedDate",Timestamp.class);
			Method method22 = class2.getMethod("getCreatedDate");
			method2.invoke(serializable,method22.invoke(object));
			Method method3 = class1.getMethod("setUpdatedBy",long.class);
			Method method33 = class2.getMethod("getUpdatedBy");
			method3.invoke(serializable,method33.invoke(object));
			Method method4 = class1.getMethod("setUpdatedDate",Timestamp.class);
			Method method44 = class2.getMethod("getUpdatedDate");
			method4.invoke(serializable,method44.invoke(object));
			Method method5 = class1.getMethod("setIsactive",boolean.class);
			Method method55 = class2.getMethod("isActive");
			method5.invoke(serializable,method55.invoke(object));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serializable;
	}
}