package com.base.extensions.java.util.Collection;

import cn.hutool.core.convert.Convert;
import com.base.tools.linq.ZLinq;
import com.base.tools.linq.ZNumberLinq;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.base.tools.linq.ZLinq.create;

/**
 * 集合扩展
 */
@Extension
public class CollectionExtend {
	//region 常用方法

	/**
	 * 是否不为空
	 *
	 * @param collection 集合
	 * @param <E>        集合对象类型
	 * @return 是否不为空
	 */
	public static <E> boolean isNotEmpty(@This Collection<E> collection) {
		return collection != null && !collection.isEmpty();
	}

	//endregion

	//region 流操作

	//region 条件

	/**
	 * 筛选条件
	 *
	 * @param collection 集合对象
	 * @param predicate  过滤条件
	 * @param <E>        实体对象
	 * @return 集合操作对象
	 */
	public static <E> ZLinq<E> where(@This Collection<E> collection, Predicate<E> predicate) {
		return create(collection).where(predicate);
	}

	/**
	 * 匹配
	 *
	 * @param collection 集合对象
	 * @param predicate  过滤条件
	 * @param <E>        实体对象
	 * @return 集合操作对象
	 */
	public static <E> boolean match(@This Collection<E> collection, Predicate<E> predicate) {
		return create(collection).match(predicate);
	}

	/**
	 * 匹配
	 *
	 * @param collection 集合对象
	 * @param predicate  过滤条件
	 * @param isAll      是否全部匹配
	 * @param <E>        实体对象
	 * @return 集合操作对象
	 */
	public static <E> boolean match(@This Collection<E> collection, Predicate<E> predicate, boolean isAll) {
		return create(collection).match(predicate, isAll);
	}


	//endregion

	//region 选择返回

	/**
	 * 选择返回对象
	 *
	 * @param collection 集合对象
	 * @param mapper     生成返回对象
	 * @param <E>        集合实体对象
	 * @param <R>        返回实体对象
	 * @return 集合操作对象
	 */
	public static <E, R> ZLinq<R> select(@This Collection<E> collection, Function<E, R> mapper) {
		return create(collection).select(mapper);
	}

	/**
	 * 选择返回对象（泛型对象为集合）
	 * <p>因为要泛型擦除，所以要把类传进来，垃圾java</p>
	 *
	 * @param collection 集合对象
	 * @param mapper     生成返回对象
	 * @param <E>        集合实体对象
	 * @param <R>        返回实体对象
	 * @return 集合操作对象
	 */
	public static <E extends Collection<T>, T, R> ZLinq<R> selectMany(@This Collection<E> collection, Class<T> clazz, Function<T, R> mapper) {
		//合并集合
		var list = new ArrayList<T>();
		for (var item : collection) {
			list.addAll(item);
		}
		return create(list).select(mapper);
	}

	/**
	 * 选择返回对象（泛型对象为集合）
	 * <p>因为要泛型擦除，所以这里只能使用 class::field 来获取</p>
	 *
	 * @param collection 集合对象
	 * @param mapper     生成返回对象
	 * @param <E>        集合实体对象
	 * @param <R>        返回实体对象
	 * @return 集合操作对象
	 */
	public static <E, R> ZLinq<R> selectMany(@This Collection<Collection<E>> collection, Function<E, R> mapper) {
		//合并集合
		var list = new ArrayList<E>();
		for (var item : collection) {
			list.addAll(item);
		}
		return create(list).select(mapper);
	}

	/**
	 * 选择返回对象(数字对象)
	 *
	 * @param collection 集合对象
	 * @param mapper     生成返回对象
	 * @param <E>        集合实体对象
	 * @param <R>        返回实体对象
	 * @return 集合操作对象
	 */
	public static <E, R extends Number & Comparable<? super R>> ZNumberLinq<R> selectNum(@This Collection<E> collection, Function<E, R> mapper) {
		return create(collection).selectNum(mapper);
	}

	/**
	 * 跳过几个对象
	 *
	 * @param skip       跳过的条数
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 第一个对象
	 */
	public static <E> ZLinq<E> skip(@This Collection<E> collection, long skip) {
		return create(collection).skip(skip);
	}

	/**
	 * 获取的条数
	 *
	 * @param limit      获取的条数
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 第一个对象
	 */
	public static <E> ZLinq<E> limit(@This Collection<E> collection, long limit) {
		return create(collection).limit(limit);
	}

	//endregion

	//region 返回

	//region 返回第一个对象

	/**
	 * 返回第一个对象
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 第一个对象
	 */
	public static <E> E first(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return null;
		}

		return create(collection).first();
	}

	/**
	 * 返回第一个对象
	 *
	 * @param collection   集合对象
	 * @param <E>          集合实体对象
	 * @param defaultValue 默认值
	 * @return 第一个对象
	 */
	public static <E> E first(@This Collection<E> collection, E defaultValue) {
		if (collection.isEmpty()) {
			return null;
		}

		return create(collection).first(defaultValue);
	}

	/**
	 * 返回第一个对象
	 *
	 * @param collection   集合对象
	 * @param <E>          集合实体对象
	 * @param predicate    过滤条件
	 * @param defaultValue 默认值
	 * @return 第一个对象
	 */
	public static <E> E first(@This Collection<E> collection, Predicate<E> predicate, E defaultValue) {
		if (collection.isEmpty()) {
			return null;
		}

		return create(collection).first(predicate, defaultValue);
	}

	/**
	 * 返回第一个对象
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @param predicate  过滤条件
	 * @return 第一个对象
	 */
	public static <E> E first(@This Collection<E> collection, Predicate<E> predicate) {
		if (collection.isEmpty()) {
			return null;
		}

		return create(collection).first(predicate);
	}

	//endregion

	//region 返回集合

	/**
	 * 返回List集合
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return List集合
	 */
	public static <E> List<E> toList(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return new ArrayList<>();
		}
		return create(collection).toList();
	}

	/**
	 * 返回List集合
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @param predicate  筛选条件
	 * @return List集合
	 */
	public static <E> List<E> toList(@This Collection<E> collection, Predicate<E> predicate) {
		if (collection.isEmpty()) {
			return new ArrayList<>();
		}
		return create(collection).toList(predicate);
	}

	/**
	 * 返回Set集合
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @param predicate  筛选条件
	 * @return Set集合
	 */
	public static <E> Set<E> toSet(@This Collection<E> collection, Predicate<E> predicate) {
		if (collection.isEmpty()) {
			return new HashSet<>();
		}
		return create(collection).toSet(predicate);
	}

	/**
	 * 返回Set集合
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return Set集合
	 */
	public static <E> Set<E> toSet(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return new HashSet<>();
		}
		return new HashSet<>(collection);
	}

	/**
	 * 返回map集合
	 *
	 * @param collection  集合对象
	 * @param predicate   筛选条件
	 * @param keyMapper   键对象
	 * @param valueMapper 值对象
	 * @param <E>         集合实体对象
	 * @param <R>         键类型
	 * @param <P>         值类型
	 * @return map集合
	 */
	public static <E, R, P> Map<R, P> toMap(@This Collection<E> collection, Predicate<E> predicate, Function<E, R> keyMapper, Function<E, P> valueMapper) {
		if (collection.isEmpty()) {
			return new HashMap<>();
		}
		return create(collection).toMap(predicate, keyMapper, valueMapper);
	}

	/**
	 * 返回map集合
	 *
	 * @param collection  集合对象
	 * @param keyMapper   键对象
	 * @param valueMapper 值对象
	 * @param <E>         集合实体对象
	 * @param <R>         键类型
	 * @param <P>         值类型
	 * @return map集合
	 */
	public static <E, R, P> Map<R, P> toMap(@This Collection<E> collection, Function<E, R> keyMapper, Function<E, P> valueMapper) {
		if (collection.isEmpty()) {
			return new HashMap<>();
		}
		return create(collection).toMap(keyMapper, valueMapper);
	}

	/**
	 * 返回map集合
	 *
	 * @param collection 集合对象
	 * @param keyMapper  键对象
	 * @param <E>        集合实体对象
	 * @param <R>        键类型
	 * @return map集合
	 */
	public static <E, R> Map<R, E> toMap(@This Collection<E> collection, Function<E, R> keyMapper) {
		if (collection.isEmpty()) {
			return new HashMap<>();
		}
		return create(collection).toMap(keyMapper);
	}

	//endregion

	//endregion

	//region 排序

	/**
	 * 正序排序
	 *
	 * @param collection 集合对象
	 * @param order      排序字段
	 * @param <U>        排序对象
	 * @param <E>        集合实体对象
	 * @return 操作对象
	 */
	public static <E, U extends Comparable<? super U>> ZLinq<E> asc(@This Collection<E> collection, Function<E, U> order) {
		return create(collection).asc(order);
	}

	/**
	 * 正序排序
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 操作对象
	 */
	public static <E extends Comparable<? super E>> ZLinq<E> asc(@This Collection<E> collection) {
		return create(collection).asc(o -> o);
	}

	/**
	 * 倒序排序
	 *
	 * @param collection 集合对象
	 * @param order      排序字段
	 * @param <U>        排序对象
	 * @param <E>        集合实体对象
	 * @return 操作对象
	 */
	public static <E, U extends Comparable<? super U>> ZLinq<E> desc(@This Collection<E> collection, Function<E, U> order) {
		return create(collection).desc(order);
	}

	/**
	 * 倒序排序
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 操作对象
	 */
	public static <E extends Comparable<? super E>> ZLinq<E> desc(@This Collection<E> collection) {
		return create(collection).desc(o -> o);
	}

	//endregion

	//region 聚合

	/**
	 * 去重
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @return set集合
	 */
	public static <E> Set<E> distinct(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return new HashSet<>();
		}
		return new HashSet<>(collection);
	}

	/**
	 * 去重
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @return set集合
	 */
	public static <E, U extends Comparable<? super U>> List<E> distinct(@This Collection<E> collection, Function<E, U> selector) {
		if (collection.isEmpty()) {
			return new ArrayList<>();
		}
		return create(collection).distinct(selector).asc(selector).toList();
	}

	/**
	 * 总条数
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @return 总条数
	 */
	public static <E> int count(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return 0;
		}
		return collection.size();
	}

	/**
	 * 总条数
	 *
	 * @param collection 集合对象
	 * @param predicate  过滤条件
	 * @param <E>        集合对象类型
	 * @param clazz      返回对象
	 * @param <R>        返回对象类型
	 * @return 总条数
	 */
	public static <E, R extends Number> R count(@This Collection<E> collection, Predicate<E> predicate, Class<R> clazz) {
		if (collection.isEmpty()) {
			return Convert.convert(clazz, 0);
		}
		return create(collection).count(predicate, clazz);
	}

	/**
	 * 总条数
	 *
	 * @param collection 集合对象
	 * @param predicate  过滤条件
	 * @param <E>        集合对象类型
	 * @return 总条数
	 */
	public static <E> Long count(@This Collection<E> collection, Predicate<E> predicate) {
		if (collection.isEmpty()) {
			return 0L;
		}
		return create(collection).count(predicate);
	}

	/**
	 * 总条数
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @param clazz      返回对象
	 * @param <R>        返回对象类型
	 * @return 总条数
	 */
	public static <E, R extends Number> R count(@This Collection<E> collection, Class<R> clazz) {
		if (collection.isEmpty()) {
			return clazz.cast(0);
		}
		return Convert.convert(clazz, collection.size());
	}

	/**
	 * 获取最大值
	 *
	 * @param collection 集合对象
	 * @param selector   返回的对象
	 * @param <U>        对象类型
	 * @param <E>        集合实体对象
	 * @return 最大值
	 */
	public static <E extends Class<? extends E>, U extends Comparable<? super U>> U max(@This Collection<E> collection, Function<E, U> selector) {
		if (collection.isEmpty()) {
			return null;
		}
		return create(collection).max(selector);
	}

	/**
	 * 获取最大值
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 最大值
	 */
	public static <E extends Comparable<? super E>> E max(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return null;
		}
		return create(collection).max(s -> s);
	}

	/**
	 * 获取最小值
	 *
	 * @param collection 集合对象
	 * @param selector   返回的对象
	 * @param <U>        对象类型
	 * @param <E>        集合实体对象
	 * @return 最小值
	 */
	public static <E, U extends Comparable<? super U>> U min(@This Collection<E> collection, Function<E, U> selector) {
		if (collection.isEmpty()) {
			return null;
		}
		return create(collection).min(selector);
	}

	/**
	 * 获取最小值
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @return 最小值
	 */
	public static <E extends Comparable<? super E>> E min(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return null;
		}
		return create(collection).min(s -> s);
	}

	/**
	 * 分组
	 *
	 * @param collection 集合对象
	 * @param selector   分组对象
	 * @param <E>        集合对象类型
	 * @param <U>        分组对象类型
	 * @return 分组map集合
	 */
	public static <E, U> Map<U, List<E>> group(@This Collection<E> collection, Function<E, U> selector) {
		if (collection == null || collection.isEmpty()) {
			return new HashMap<>();
		}
		return create(collection).group(selector);
	}

	/**
	 * 把集合用分隔符隔开
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @param split      分隔符
	 * @return 最大值
	 */
	public static <E> String join(@This Collection<E> collection, String split) {
		if (collection.isEmpty()) {
			return null;
		}
		return String.join(split, create(collection).select(Object::toString).toList());
	}

	//endregion

	//region 求和

	/**
	 * 求和
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @return 数字之和
	 */
	public static <E extends Number & Comparable<? super E>> E sum(@This Collection<E> collection) {
		if (collection.isEmpty()) {
			return null;
		}
		return new ZNumberLinq<>(collection).sum();
	}

	/**
	 * 求和
	 *
	 * @param collection   集合对象
	 * @param <E>          集合对象类型
	 * @param defaultValue 默认值
	 * @return 数字之和
	 */
	public static <E extends Number & Comparable<? super E>> E sum(@This Collection<E> collection, E defaultValue) {
		if (collection.isEmpty()) {
			return defaultValue;
		}
		return new ZNumberLinq<>(collection).sum(defaultValue);
	}

	/**
	 * 求和
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @param selector   选择的对象
	 * @param <R>        选择对象的类型
	 * @return 数字之和
	 */
	public static <E, R extends Number & Comparable<? super R>> R sum(@This Collection<E> collection, Function<E, R> selector) {
		if (collection.isEmpty()) {
			return null;
		}
		return new ZLinq<>(collection).selectNum(selector).sum();
	}

	/**
	 * 求和
	 *
	 * @param collection   集合对象
	 * @param <E>          集合对象类型
	 * @param selector     选择的对象
	 * @param <R>          选择对象的类型
	 * @param defaultValue 默认值
	 * @return 数字之和
	 */
	public static <E, R extends Number & Comparable<? super R>> R sum(@This Collection<E> collection, Function<E, R> selector, R defaultValue) {
		if (collection.isEmpty()) {
			return defaultValue;
		}
		return new ZLinq<>(collection).selectNum(selector).sum(defaultValue);
	}

	/**
	 * 求和
	 *
	 * @param collection 集合对象
	 * @param <E>        集合对象类型
	 * @param clazz      计算后的对象
	 * @param <R>        计算后的类型
	 * @return 数字之和
	 */
	public static <E extends Number & Comparable<? super E>, R extends Number> R sum(@This Collection<E> collection, Class<R> clazz) {
		if (collection.isEmpty()) {
			return Convert.convert(clazz, 0);
		}
		return new ZNumberLinq<>(collection).sum(clazz);
	}

	/**
	 * 求和
	 *
	 * @param collection 集合对象
	 * @param <E>        集合实体对象
	 * @param selector   选择的对象
	 * @param <R>        选择对象的类型
	 * @param clazz      计算后的对象
	 * @param <T>        计算对象的类型
	 * @return 数字之和
	 */
	public static <E, R extends Number & Comparable<? super R>, T extends Number & Comparable<? super T>> T sum(@This Collection<E> collection, Function<E, R> selector, Class<T> clazz) {
		if (collection.isEmpty()) {
			return Convert.convert(clazz, 0);
		}
		return new ZLinq<>(collection).selectNum(selector).sum(clazz);
	}

	//endregion

	//endregion

	//region 交，并，差集合操作

	/**
	 * 交集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 交集
	 */
	public static <E extends Number> List<E> intersectDistinct(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() || other.isEmpty()) {
			return new ArrayList<>();
		}
		var left = new HashSet<>(collection);
		var right = new HashSet<>(other);
		left.retainAll(right);
		return new ArrayList<>(left);
	}

	/**
	 * 交集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 交集
	 */
	public static <E extends Number> List<E> intersect(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() || other.isEmpty()) {
			return new ArrayList<>();
		}
		var left = new ArrayList<>(collection);
		var right = new ArrayList<>(other);
		left.retainAll(right);
		return new ArrayList<>(left);
	}

	/**
	 * 并集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 并集
	 */
	public static <E extends Number> List<E> unionDistinct(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() && other.isEmpty()) {
			return new ArrayList<>();
		}
		if (collection.isEmpty()) {
			return new HashSet<>(other).toList();
		}
		if (other.isEmpty()) {
			return new HashSet<>(collection).toList();
		}
		var left = new HashSet<>(collection);
		left.addAll(other);
		return new ArrayList<>(left);
	}

	/**
	 * 并集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 并集
	 */
	public static <E extends Number> List<E> union(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() && other.isEmpty()) {
			return new ArrayList<>();
		}
		if (collection.isEmpty()) {
			return (List<E>) other;
		}
		if (other.isEmpty()) {
			return (List<E>) collection;
		}
		var left = new ArrayList<>(collection);
		left.addAll(other);
		return new ArrayList<>(left);
	}

	/**
	 * 差集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 差集
	 */
	public static <E extends Number> List<E> exceptDistinct(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() && other.isEmpty()) {
			return new ArrayList<>();
		}
		if (collection.isEmpty()) {
			return new HashSet<>(other).toList();
		}
		if (other.isEmpty()) {
			return new HashSet<>(collection).toList();
		}
		var left = new HashSet<>(collection);
		left.removeAll(other);
		return new ArrayList<>(left);
	}

	/**
	 * 差集
	 *
	 * @param collection 集合对象
	 * @param other      集合对象
	 * @param <E>        集合实体对象
	 * @return 差集
	 */
	public static <E extends Number> List<E> except(@This Collection<E> collection, Collection<E> other) {
		if (collection.isEmpty() && other.isEmpty()) {
			return new ArrayList<>();
		}
		if (collection.isEmpty()) {
			return (List<E>) other;
		}
		if (other.isEmpty()) {
			return (List<E>) collection;
		}
		var left = new ArrayList<>(collection);
		left.removeAll(other);
		return new ArrayList<>(left);
	}

	//endregion
}