package com.base.tools.key_value;


import com.base.entity.key_value.KeyValueBO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 键值对缓存对象
 */
public class KeyValueCache {
	/**
	 * 常量集合
	 */
	private static final Map<String, KeyValueBO<String>> CONST_MAP = new HashMap<>();

	/**
	 * 初始化缓存
	 *
	 * @param clazz 缓存对象
	 */
	public static void init(Class<?> clazz) {
		//键值对常量类型集合
		var types = clazz.getClasses();
		//循环类型
		for (var type : types) {
			//获取类型下常量字段
			var fields = type.getDeclaredFields();
			for (var field : fields) {
				try {
					var value = (KeyValueBO<String>) field.get(null);
					CONST_MAP.put(value.getType() + "." + value.getKeyName(), value);
				} catch (IllegalAccessException e) {
					break;
				}
			}
		}
	}

	/**
	 * 获取可选参数
	 *
	 * @param name 键名
	 * @return 参数对象
	 */
	public static KeyValueBO<String> getOptional(String name) {
		return CONST_MAP.get(name);
	}

	/**
	 * 获取可选参数集合
	 *
	 * @param names 键名集合
	 * @return 参数对象集合
	 */
	public static List<KeyValueBO<String>> getOptional(List<String> names) {
		return names.select(KeyValueCache::getOptional).toList();
	}

	/**
	 * 获取可选参数集合
	 *
	 * @param names 键名集合
	 * @return 参数对象集合
	 */
	public static List<KeyValueBO<String>> getOptional(String... names) {
		return getOptional(Arrays.stream(names).toList());
	}
}