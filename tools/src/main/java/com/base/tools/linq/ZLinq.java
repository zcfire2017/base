package com.base.tools.linq;

import cn.hutool.core.convert.Convert;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Linq 集合操作
 *
 * @param <T> 集合对象
 * @author zc
 * @since 2023-10-17
 */
public class ZLinq<T> {
	/**
	 * 集合操作对象
	 */
	protected Stream<T> stream;

	/**
	 * 泛型类型
	 */
	protected Class<T> tClass;

	//region 构造函数

	/**
	 * 构造函数
	 *
	 * @param collection 集合
	 */
	public ZLinq(Collection<T> collection) {
		this.stream = collection.stream();
		//取第一个值，获取值类型
		var itr = collection.iterator();
		if (itr.hasNext()) {
			var value = itr.next();
			this.tClass = (Class<T>) value.getClass();
		}
	}

	/**
	 * 创建对象
	 *
	 * @param collection 集合
	 * @param <T>        对象类型
	 * @return 操作对象
	 */
	public static <T> ZLinq<T> create(Collection<T> collection) {
		return new ZLinq<>(collection);
	}

	//endregion

	//region 条件筛选

	/**
	 * 条件筛选
	 *
	 * @param predicate 过滤条件
	 * @return 操作对象
	 */
	public ZLinq<T> where(Predicate<T> predicate) {
		stream = stream.filter(predicate);
		return this;
	}

	/**
	 * 条件匹配
	 *
	 * @param predicate 过滤条件
	 * @return 是否匹配
	 */
	public boolean match(Predicate<T> predicate) {
		return stream.anyMatch(predicate);
	}

	/**
	 * 条件匹配
	 *
	 * @param predicate 过滤条件
	 * @param isAll     是否全部匹配
	 * @return 是否匹配
	 */
	public boolean match(Predicate<T> predicate, boolean isAll) {
		return isAll ? stream.allMatch(predicate) : stream.anyMatch(predicate);
	}

	//endregion

	//region 迭代

	/**
	 * 循环
	 *
	 * @param comparator 比较器
	 */
	public void forEach(Consumer<T> comparator) {
		stream.forEach(comparator);
	}

	//endregion

	//region 返回对象

	/**
	 * 选择返回对象
	 *
	 * @param selector 返回的对象
	 * @param <R>      返回对象
	 * @return 操作对象
	 */
	public <R> ZLinq<R> select(Function<T, R> selector) {
		return new ZLinq<>(stream.map(selector).toList());
	}

	/**
	 * 选择返回对象(数字对象)
	 *
	 * @param selector 返回的对象
	 * @param <R>      返回数字对象
	 * @return 操作对象
	 */
	public <R extends Number & Comparable<? super R>> ZNumberLinq<R> selectNum(Function<T, R> selector) {
		return new ZNumberLinq<>(stream.map(selector).toList());
	}

	/**
	 * 跳过几个对象
	 *
	 * @param skip 跳过的条数
	 * @return 操作对象
	 */
	public ZLinq<T> skip(long skip) {
		stream = stream.skip(skip);
		return this;
	}

	/**
	 * 获取几条
	 *
	 * @param limit 获取的条数
	 * @return 操作对象
	 */
	public ZLinq<T> limit(long limit) {
		stream = stream.limit(limit);
		return this;
	}

	//endregion

	//region 排序

	/**
	 * 正序排序
	 *
	 * @return 操作对象
	 */
	public ZLinq<T> asc() {
		stream = stream.sorted();
		return this;
	}

	/**
	 * 正序排序
	 *
	 * @param order 排序字段
	 * @param <U>   排序对象
	 * @return 操作对象
	 */
	public <U extends Comparable<? super U>> ZLinq<T> asc(Function<T, U> order) {
		stream = stream.sorted(Comparator.comparing(order));
		return this;
	}

	/**
	 * 倒序排序
	 *
	 * @return 操作对象
	 */
	public ZLinq<T> desc() {
		stream = stream.sorted(Comparator.comparing(Object::toString).reversed());
		return this;
	}

	/**
	 * 倒序排序
	 *
	 * @param order 排序字段
	 * @param <U>   排序对象
	 * @return 操作对象
	 */
	public <U extends Comparable<? super U>> ZLinq<T> desc(Function<T, U> order) {
		stream = stream.sorted(Comparator.comparing(order).reversed());
		return this;
	}

	//endregion

	//region 返回

	//region 第一个值

	/**
	 * 查找第一个值并返回
	 * 如果没有就是null
	 *
	 * @return 第一个值
	 */
	public T first() {
		return stream.findFirst().orElse(null);
	}

	/**
	 * 查找第一个值并返回
	 *
	 * @param defaultValue 默认值
	 * @return 第一个值
	 */
	public T first(T defaultValue) {
		return stream.findFirst().orElse(defaultValue);
	}

	/**
	 * 查找第一个值并返回
	 *
	 * @param value 值对象
	 * @param <R>   值类型
	 * @return 第一个值
	 */
	public <R> R first(Function<T, R> value) {
		return stream.map(value).findFirst().orElse(null);
	}

	/**
	 * 查找第一个值并返回
	 *
	 * @param value        值对象
	 * @param <R>          值类型
	 * @param defaultValue 默认值
	 * @return 第一个值
	 */
	public <R> R first(Function<T, R> value, R defaultValue) {
		return stream.map(value).findFirst().orElse(defaultValue);
	}

	/**
	 * 查找第一个值并返回
	 * 如果没有就是null
	 *
	 * @param predicate 过滤条件
	 * @return 第一个值
	 */
	public T first(Predicate<T> predicate) {
		return stream.filter(predicate).findFirst().orElse(null);
	}

	/**
	 * 查找第一个值并返回
	 *
	 * @param predicate    过滤条件
	 * @param defaultValue 默认值
	 * @return 第一个值
	 */
	public T first(Predicate<T> predicate, T defaultValue) {
		return stream.filter(predicate).findFirst().orElse(defaultValue);
	}

	//endregion

	//region 集合

	/**
	 * 生成List集合
	 *
	 * @return List集合
	 */
	public List<T> toList() {
		return stream.collect(Collectors.toList());
	}

	/**
	 * 生成List集合
	 *
	 * @param predicate 过滤条件
	 * @return List集合
	 */
	public List<T> toList(Predicate<T> predicate) {
		return stream.filter(predicate).collect(Collectors.toList());
	}

	/**
	 * 生成Set集合
	 *
	 * @return Set集合
	 */
	public Set<T> toSet() {
		return stream.collect(Collectors.toSet());
	}

	/**
	 * 生成Set集合
	 *
	 * @param predicate 过滤条件
	 * @return Set集合
	 */
	public Set<T> toSet(Predicate<T> predicate) {
		return stream.filter(predicate).collect(Collectors.toSet());
	}

	/**
	 * 转换成Map
	 *
	 * @param keyMapper   键
	 * @param valueMapper 值
	 * @param <R>         键对象
	 * @return Map集合
	 */
	public <R, P> Map<R, P> toMap(Function<T, R> keyMapper, Function<T, P> valueMapper) {
		return stream.collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * 转换成Map
	 *
	 * @param predicate   过滤条件
	 * @param keyMapper   键
	 * @param valueMapper 值
	 * @param <R>         键对象
	 * @return Map集合
	 */
	public <R, P> Map<R, P> toMap(Predicate<T> predicate, Function<T, R> keyMapper, Function<T, P> valueMapper) {
		return stream.filter(predicate).collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * 转换成Map
	 *
	 * @param keyMapper 键
	 * @param <R>       键对象
	 * @return Map集合
	 */
	public <R> Map<R, T> toMap(Function<T, R> keyMapper) {
		return stream.collect(Collectors.toMap(keyMapper, Function.identity()));
	}

	//endregion

	//endregion

	//region 聚合

	/**
	 * 去重
	 *
	 * @return 去重
	 */
	public List<T> distinct() {
		return stream.distinct().toList();
	}

	/**
	 * 去重
	 *
	 * @return 去重
	 */
	public <R> List<T> distinct(Function<T, R> selector) {
		//先根据字段分组，然后取每组的第一条
		var group = stream.collect(Collectors.groupingBy(selector));
		return group.values().stream().map(list -> list.get(0)).collect(Collectors.toList());
	}

	/**
	 * 总条数
	 *
	 * @param predicate 统计条件
	 * @return 总条数
	 */
	public Long count(Predicate<T> predicate) {
		return stream.filter(predicate).count();
	}

	/**
	 * 总条数
	 *
	 * @param predicate 统计条件
	 * @param clazz     返回对象
	 * @param <R>       返回对象类型
	 * @return 总条数
	 */
	public <R extends Number> R count(Predicate<T> predicate, Class<R> clazz) {
		var count = stream.filter(predicate).count();
		return Convert.convert(clazz, count);
	}

	/**
	 * 总条数
	 *
	 * @return 总条数
	 */
	public Long count() {
		return stream.count();
	}

	/**
	 * 总条数
	 *
	 * @param clazz 返回对象
	 * @param <R>   返回对象类型
	 * @return 总条数
	 */
	public <R extends Number> R count(Class<R> clazz) {
		return Convert.convert(clazz, stream.count());
	}

	/**
	 * 最大值
	 *
	 * @param selector 返回的对象
	 * @param <U>      对象类型
	 * @return 最大值
	 */
	public <U extends Comparable<? super U>> U max(Function<T, U> selector) {
		return stream.map(selector).max(Comparator.naturalOrder()).orElse(null);
	}

	/**
	 * 最小值
	 *
	 * @param selector 返回的对象
	 * @param <U>      对象类型
	 * @return 最小值
	 */
	public <U extends Comparable<? super U>> U min(Function<T, U> selector) {
		return stream.map(selector).min(Comparator.naturalOrder()).orElse(null);
	}

	/**
	 * 平均值
	 *
	 * @param selector 返回的对象
	 * @param <U>      对象类型
	 * @return 最小值
	 */
	public <U extends Number> BigDecimal avg(Function<T, U> selector) {
		return BigDecimal.valueOf(stream.map(selector).mapToDouble(Number::doubleValue).average().orElse(0));
	}

	/**
	 * 分组
	 *
	 * @param selector 分组对象
	 * @param <U>      分组对象类型
	 * @return 分组map集合
	 */
	public <U> Map<U, List<T>> group(Function<T, U> selector) {
		return stream.collect(Collectors.groupingBy(selector));
	}

	/**
	 * 把集合用分隔符隔开
	 *
	 * @param split 分隔符
	 * @return 字符串
	 */
	public String join(String split) {
		return stream.map(Object::toString).collect(Collectors.joining(split));
	}

	//endregion

	//region 求和

	/**
	 * 求和
	 *
	 * @param selector 返回的对象
	 * @param <R>      返回类型
	 * @return 数字之和
	 */
	public <R extends Number & Comparable<? super R>> R sum(Function<T, R> selector) {
		return this.selectNum(selector).sum();
	}

	/**
	 * 求和
	 *
	 * @param selector     返回的对象
	 * @param <R>          返回类型
	 * @param defaultValue 默认值
	 * @return 数字之和
	 */
	public <R extends Number & Comparable<? super R>> R sum(Function<T, R> selector, R defaultValue) {
		return this.selectNum(selector).sum(defaultValue);
	}

	/**
	 * 求和
	 *
	 * @param selector 选择的对象
	 * @param <R>      选择对象的类型
	 * @param clazz    返回对象
	 * @param <B>      返回对象类型
	 * @return 数字之和
	 */
	public <R extends Number & Comparable<? super R>, B extends Number & Comparable<? super B>> B sum(Function<T, R> selector, Class<B> clazz) {
		return this.selectNum(selector).sum(clazz);
	}

	//endregion
}