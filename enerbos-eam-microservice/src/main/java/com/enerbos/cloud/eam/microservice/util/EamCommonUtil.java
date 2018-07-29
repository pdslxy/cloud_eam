package com.enerbos.cloud.eam.microservice.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月8日
 * @Description eam工具类
 */
public class EamCommonUtil {
	/**
	 * reModelToMap: 反射将实体中的值都放到map中
	 * @param obj  实体
	 * @return Map<String,Object> map用来做查询条件
	 * @throws Exception Map<String,Object>
	 */
	public static Map<String, Object> reModelToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			// key == 字段名    value == 字段值  
			map.put(fields[j].getName(), fields[j].get(obj));
		}
		return map;
	}
}