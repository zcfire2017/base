package com.base.extensions.java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.base.entity.key_value.KeyValue;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import manifold.ext.rt.api.ThisClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map扩展
 */
@Extension
public class MapExtend {
	//region 类方法扩展

	/**
	 * 初始化
	 *
	 * @param theClass Map对象
	 * @param key      键
	 * @param value    值
	 * @param <K>      键类型
	 * @param <V>      值类型
	 * @return HashMap对象
	 */
	public static <K, V> HashMap<K, V> init(@ThisClass Class<Map<K, V>> theClass, K key, V value) {
		var map = new HashMap<K, V>();
		map.put(key, value);
		return map;
	}

	/**
	 * 初始化
	 *
	 * @param theClass Map对象
	 * @param keyValue 值
	 * @param <K>      键类型
	 * @param <V>      值类型
	 * @return HashMap对象
	 */
	@SafeVarargs
	public static <K, V> HashMap<K, V> init(@ThisClass Class<Map<K, V>> theClass, KeyValue<K, V>... keyValue) {
		var map = new HashMap<K, V>();
		for (var item : keyValue) {
			map.put(item.getKey(), item.getValue());
		}
		return map;
	}

	//endregion

	//region 实例方法扩展

	//region 层级获取值

	/**
	 * 获取值（支持层级）
	 * eg: map.get("key1.key2.key3")
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @return 返回值
	 */
	private static Object getL(Map<String, Object> map, String key) {
		if (key.isNullOrEmpty())
			return null;

		//根据逗号分隔
		var keys = key.split("\\.");
		if (keys.length == 1)
			return map.get(key);

		//最后一层key
		var lastKey = keys.last();
		//层级map
		Map<String, Object> levelMap = new HashMap<>();
		//循环key
		for (var k : keys) {
			//最后一层获取值
			if (k.equals(lastKey)) {
				return levelMap.get(lastKey);
			}
			//其它层获取map
			levelMap = (Map<String, Object>) map.get(k);
			//如果键不对，则返回空
			if (levelMap == null)
				return null;
		}
		return null;
	}

	/**
	 * 获取值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 值
	 */
	private static <K, V> Object get(Map<K, V> map, K key) {
		//获取值
		if (key instanceof String k) {
			if (k.contains("."))
				return getL((Map<String, Object>) map, k);
		}
		return map.get(key);
	}

	//endregion

	//region 普通获取值

	/**
	 * 获取map的值并转换为指定类型数据
	 *
	 * @param map       键值对集合
	 * @param key       键
	 * @param backClass 返回类型
	 * @param <K>       键类型
	 * @param <V>       值类型
	 * @param <R>       返回值类型
	 * @return 返回值
	 */
	public static <K, V, R> R get(@This Map<K, V> map, K key, Class<R> backClass) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(backClass, back);
		}
		return null;
	}

	/**
	 * 获取map的值并转换为指定类型数据
	 *
	 * @param map       键值对集合
	 * @param key       键
	 * @param beanClass 返回类型
	 * @param <K>       键类型
	 * @param <V>       值类型
	 * @param <R>       返回值类型
	 * @return 返回值
	 */
	public static <K, V, R> R getBean(@This Map<K, V> map, K key, Class<R> beanClass) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return BeanUtil.toBean(back, beanClass);
		}
		return null;
	}

	/**
	 * 获取map的值并转换为指定类型数据
	 *
	 * @param map       键值对集合
	 * @param key       键
	 * @param beanClass 返回类型
	 * @param <K>       键类型
	 * @param <V>       值类型
	 * @param <R>       返回值类型
	 * @return 返回值
	 */
	public static <K, V, R> R getList(@This Map<K, V> map, K key, TypeReference<R> beanClass) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(beanClass, back);
		}
		return null;
	}

	/**
	 * 获取map的值并转换为制定类型数据
	 *
	 * @param map          键值对集合
	 * @param key          键
	 * @param defaultValue 默认值
	 * @param backClass    返回类型
	 * @param <K>          键类型
	 * @param <V>          值类型
	 * @param <R>          返回值类型
	 * @return 返回值
	 */
	public static <K, V, R> R get(@This Map<K, V> map, K key, R defaultValue, Class<R> backClass) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(backClass, back);
		}
		return defaultValue;
	}

	/**
	 * 获取map的值，没找到则返回默认值
	 *
	 * @param map          键值对集合
	 * @param key          键
	 * @param defaultValue 默认值
	 * @param <K>          键类型
	 * @param <V>          值类型
	 * @return 返回值
	 */
	public static <K, V> V get(@This Map<K, V> map, K key, V defaultValue) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return (V) back;
		}
		return defaultValue;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> String getString(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return back.toString();
		}
		return null;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Byte getByte(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Byte.class, back);
		}
		return 0;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Short getShort(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Short.class, back);
		}
		return 0;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Integer getInt(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Integer.class, back);
		}
		return 0;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Long getLong(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Long.class, back);
		}
		return 0L;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Boolean getBool(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Boolean.class, back);
		}
		return false;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Double getDouble(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(Double.class, back);
		}
		return 0d;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> BigDecimal getDecimal(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.convert(BigDecimal.class, back);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Map<String, Object> getMap(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return (Map<String, Object>) back;
		}
		return new HashMap<>();
	}

	/**
	 * 获取map的值
	 *
	 * @param map 键值对集合
	 * @param key 键
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> List<Map<String, Object>> getListMap(@This Map<K, V> map, K key) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return (List<Map<String, Object>>) back;
		}
		return new ArrayList<>();
	}

	//endregion

	//endregion
}