package com.base.tools.enums;

import com.base.tools.enums.entity.IEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举缓存
 */
public class EnumCache {

	/**
	 * 值 - 枚举缓存
	 */
	private static final ConcurrentHashMap<Class<? extends IEnum<?>>, Map<Object, IEnum<?>>> valueCache = new ConcurrentHashMap<>();

	/**
	 * 枚举名称 - 枚举缓存
	 */
	private static final ConcurrentHashMap<Class<? extends IEnum<?>>, Map<String, IEnum<?>>> nameCache = new ConcurrentHashMap<>();

	/*
	  枚举名 - 枚举缓存
	 */
	//private static final ConcurrentHashMap<String, Map<Object, String>> enumCache = new ConcurrentHashMap<>();

	/**
	 * 锁对象
	 */
	private static final Object obj = new Object();

	/**
	 * 注册枚举
	 *
	 * @param type 枚举类型
	 */
	public static <T extends IEnum<?>> void register(Class<T> type) {
		if (valueCache.containsKey(type))
			return;

		//锁同步
		synchronized (obj) {
			if (!valueCache.containsKey(type)) {
				//新增枚举键值对
				var valueMap = new ConcurrentHashMap<Object, IEnum<?>>();
				var stringMap = new ConcurrentHashMap<String, IEnum<?>>();
				//var enumMap = new ConcurrentHashMap<Object, String>();
				//循环枚举值，添加到集合
				for (var enumObj : type.getEnumConstants()) {
					valueMap.put(enumObj.getValue(), enumObj);
					stringMap.put(enumObj.getName(), enumObj);
					//enumMap.put(enumObj.getValue(), enumObj.getName());
				}
				valueCache.put(type, valueMap);
				nameCache.put(type, stringMap);
				//enumCache.put(type.getName(), enumMap);
			}
		}
	}

	/**
	 * 获取枚举集合（键 - 枚举）
	 *
	 * @param type 枚举类
	 * @param <E>  枚举类型
	 * @return 枚举集合
	 */
	public static <E extends IEnum<?>> Map<Object, IEnum<?>> valueMap(Class<E> type) {
		//不存在则注册
		if (!valueCache.containsKey(type))
			register(type);

		return valueCache.get(type);
	}

	/**
	 * 获取枚举集合（名称 - 枚举）
	 *
	 * @param type 枚举类
	 * @return 枚举集合
	 */
	public static <E extends IEnum<?>> Map<String, IEnum<?>> nameMap(Class<E> type) {
		//不存在则注册
		if (!nameCache.containsKey(type))
			register(type);

		return nameCache.get(type);
	}

	/**
	 * 根据值获取枚举
	 *
	 * @param type  枚举类型
	 * @param value 枚举值
	 * @param <T>   枚举值类型
	 * @return 枚举
	 */
	public static <T, E extends IEnum<T>> E value(Class<E> type, T value) {
		var map = valueMap(type);
		if (map == null)
			return null;

		var result = map.get(value);
		return result == null ? null : (E) result;
	}

	/**
	 * 根据枚举名称获取枚举
	 *
	 * @param type 枚举类型
	 * @param name 枚举名称
	 * @return 枚举
	 */
	public static <E extends IEnum<?>> E name(Class<E> type, String name) {
		var map = nameMap(type);
		if (map == null)
			return null;

		var result = map.get(name);
		return result == null ? null : (E) result;
	}

	/**
	 * 根据枚举名称获取枚举值
	 *
	 * @param type 枚举类型
	 * @param name 枚举名称
	 * @return 枚举值
	 */
	public static <T, E extends IEnum<T>> Object getValue(Class<E> type, String name) {
		var map = nameMap(type);
		if (map == null)
			return null;

		var result = map.get(name);
		return result == null ? null : (T) result.getValue();
	}
}