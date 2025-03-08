package com.base.extensions.java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.base.entity.key_value.KeyValue;
import com.base.tools.linq.ZLinq;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import manifold.ext.rt.api.ThisClass;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.base.tools.linq.ZLinq.create;

/**
 * Map扩展
 */
@Extension
public class MapExtend {
	//region 类方法扩展

	/**
	 * 初始化可变Map
	 *
	 * @param theClass Map对象
	 * @param key      键
	 * @param value    值
	 * @param <K>      键类型
	 * @param <V>      值类型
	 * @return HashMap对象
	 */
	public static <K, V> HashMap<K, V> on(@ThisClass Class<Map<K, V>> theClass, K key, V value) {
		var map = new HashMap<K, V>();
		map.put(key, value);
		return map;
	}

	/**
	 * 初始化可变Map
	 * <p>你可以这样使用</p>
	 * <pre> {@code
	 * import static com.base.extensions.java.util.Map.MapUnit.kv;
	 *
	 * var map1 = Map.on("key" kv "value", "key" kv "value");
	 * var map2 = Map.on(1 kv 2, 3 kv 4);
	 * var map23 = Map.on("key" kv 1, "key" kv 2);
	 * }</pre>
	 *
	 * @param theClass Map对象
	 * @param keyValue 值
	 * @param <K>      键类型
	 * @param <V>      值类型
	 * @return HashMap对象
	 */
	@SafeVarargs
	public static <K, V> HashMap<K, V> on(@ThisClass Class<Map<K, V>> theClass, KeyValue<K, V>... keyValue) {
		var map = new HashMap<K, V>();
		for (var item : keyValue) {
			map.put(item.getKey(), item.getValue());
		}
		return map;
	}

	//endregion

	//region 实例方法扩展

	/**
	 * map是否不为空集合
	 *
	 * @param map 键值对集合
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 是否不为空
	 */
	public static <K, V> boolean isNotEmpty(@This Map<K, V> map) {
		return map != null && !map.isEmpty();
	}

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
	 * @param map 键值对集合
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 返回值
	 */
	public static <K, V> Map.Entry<K, V> get(@This Map<K, V> map, int index) {
		return map.entrySet().first();
	}

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
	public static <K, V, R> List<R> getList(@This Map<K, V> map, K key, Class<R> beanClass) {
		//获取值
		var back = get(map, key);
		if (back != null) {
			//转换为指定类型
			return Convert.toList(beanClass, back);
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

	//region 集合操作

	/**
	 * 选择返回对象
	 *
	 * @param map       键值对对象
	 * @param predicate 条件选择
	 * @param <K>       键类型
	 * @param <V>       值类型
	 * @return 集合操作对象
	 */
	public static <K, V> ZLinq<Map.Entry<K, V>> where(@This Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
		return create(map.entrySet()).where(predicate);
	}

	/**
	 * 选择返回对象
	 *
	 * @param map    键值对对象
	 * @param mapper 选择对象
	 * @param <K>    键类型
	 * @param <V>    值类型
	 * @param <R>    返回值类型
	 * @return 集合操作对象
	 */
	public static <K, V, R> ZLinq<R> select(@This Map<K, V> map, Function<Map.Entry<K, V>, R> mapper) {
		return create(map.entrySet()).select(mapper);
	}

	/**
	 * 选择返回对象（泛型对象为集合）
	 * <p>因为要泛型擦除，所以要把类传进来，垃圾java</p>
	 *
	 * @param map      键值对对象
	 * @param theClass 集合对象
	 * @param mapper   选择对象
	 * @param <K>      键类型
	 * @param <V>      值类型
	 * @param <R>      返回值类型
	 * @return 集合操作对象
	 */
	public static <K, V extends Collection<T>, T, R> ZLinq<R> selectMany(@This Map<K, V> map, Class<T> theClass, Function<T, R> mapper) {
		//合并集合
		var list = new ArrayList<T>();
		for (var item : map.values()) {
			list.addAll(item);
		}
		return create(list).select(mapper);
	}

	/**
	 * 选择返回对象（泛型对象为集合）
	 *
	 * @param map 键值对对象
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 集合操作对象
	 */
	public static <K, V extends Collection<T>, T> List<T> selectMany(@This Map<K, V> map) {
		//合并集合
		var list = new ArrayList<T>();
		for (var item : map.values()) {
			list.addAll(item);
		}
		return list;
	}

	//endregion

	//endregion
}