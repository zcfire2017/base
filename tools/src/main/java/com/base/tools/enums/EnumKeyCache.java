package com.base.tools.enums;

import com.base.tools.enums.entity.IEnumKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双键枚举缓存
 */
public class EnumKeyCache {

	/**
	 * 值 - 枚举缓存
	 */
	private static final ConcurrentHashMap<Class<? extends IEnumKey<?, ?>>, Map<Object, IEnumKey<?, ?>>> valueCache = new ConcurrentHashMap<>();

	/**
	 * 枚举名称 - 枚举缓存
	 */
	private static final ConcurrentHashMap<Class<? extends IEnumKey<?, ?>>, Map<String, IEnumKey<?, ?>>> nameCache = new ConcurrentHashMap<>();

	/*
	  枚举键 - 枚举缓存
	 */
	private static final ConcurrentHashMap<Class<? extends IEnumKey<?, ?>>, Map<Object, IEnumKey<?, ?>>> keyCache = new ConcurrentHashMap<>();

	/**
	 * 锁对象
	 */
	private static final Object obj = new Object();

	/**
	 * 注册枚举
	 *
	 * @param type 枚举类型
	 */
	public static <T extends IEnumKey<?, ?>> void register(Class<T> type) {
		if (valueCache.containsKey(type))
			return;

		//锁同步
		synchronized (obj) {
			if (!valueCache.containsKey(type)) {
				//新增枚举键值对
				var valueMap = new ConcurrentHashMap<Object, IEnumKey<?, ?>>();
				var stringMap = new ConcurrentHashMap<String, IEnumKey<?, ?>>();
				var keyMap = new ConcurrentHashMap<Object, IEnumKey<?, ?>>();
				//循环枚举值，添加到集合
				for (var enumObj : type.getEnumConstants()) {
					valueMap.put(enumObj.getValue(), enumObj);
					stringMap.put(enumObj.getName(), enumObj);
					keyMap.put(enumObj.getKey(), enumObj);
				}
				valueCache.put(type, valueMap);
				nameCache.put(type, stringMap);
				keyCache.put(type, keyMap);
			}
		}
	}

	/**
	 * 获取枚举集合（值 - 枚举）
	 *
	 * @param type 枚举类
	 * @param <E>  枚举类型
	 * @return 枚举集合
	 */
	public static <E extends IEnumKey<?, ?>> Map<Object, IEnumKey<?, ?>> valueMap(Class<E> type) {
		//不存在则注册
		if (!valueCache.containsKey(type))
			register(type);

		return valueCache.get(type);
	}

	/**
	 * 根据值获取枚举
	 *
	 * @param type  枚举类型
	 * @param value 枚举值
	 * @param <T>   枚举值类型
	 * @return 枚举
	 */
	public static <T extends Number, E extends IEnumKey<?, ?>> E value(Class<E> type, T value) {
		var map = valueMap(type);
		if (map == null)
			return null;

		var result = map.get(value);
		return result == null ? null : (E) result;
	}

	/**
	 * 获取枚举集合（键 - 枚举）
	 *
	 * @param type 枚举类
	 * @param <E>  枚举类型
	 * @return 枚举集合
	 */
	public static <E extends IEnumKey<?, ?>> Map<Object, IEnumKey<?, ?>> keyMap(Class<E> type) {
		//不存在则注册
		if (!keyCache.containsKey(type))
			register(type);

		return keyCache.get(type);
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param type 枚举类型
	 * @param key  枚举键
	 * @param <T>  枚举值类型
	 * @return 枚举
	 */
	public static <T, E extends IEnumKey<?, ?>> E key(Class<E> type, T key) {
		var map = keyMap(type);
		if (map == null)
			return null;

		var result = map.get(key);
		return result == null ? null : (E) result;
	}

	/**
	 * 获取枚举集合（名称 - 枚举）
	 *
	 * @param type 枚举类
	 * @return 枚举集合
	 */
	public static <E extends IEnumKey<?, ?>> Map<String, IEnumKey<?, ?>> nameMap(Class<E> type) {
		//不存在则注册
		if (!nameCache.containsKey(type))
			register(type);

		return nameCache.get(type);
	}

	/**
	 * 根据枚举名称获取枚举
	 *
	 * @param type 枚举类型
	 * @param name 枚举名称
	 * @return 枚举
	 */
	public static <E extends IEnumKey<?, ?>> E name(Class<E> type, String name) {
		var map = nameMap(type);
		if (map == null)
			return null;

		var result = map.get(name);
		return result == null ? null : (E) result;
	}
}