package com.base.tools.linq;

import cn.hutool.core.convert.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;

/**
 * 数字Linq集合操作
 *
 * @param <T> 数字类型
 */
public class ZNumberLinq<T extends Number & Comparable<? super T>> extends ZLinq<T> {
	/**
	 * 初始化
	 *
	 * @param collection 集合
	 */
	public ZNumberLinq(Collection<T> collection) {
		super(collection);
	}

	//region 聚合

	/**
	 * 最大值
	 *
	 * @return 最大值
	 */
	public T max() {
		return stream.max(Comparator.naturalOrder()).orElse(null);
	}

	/**
	 * 最小值
	 *
	 * @return 最小值
	 */
	public T min() {
		return stream.min(Comparator.naturalOrder()).orElse(null);
	}

	/**
	 * 平均值
	 *
	 * @return 最小值
	 */
	public BigDecimal avg() {
		return BigDecimal.valueOf(stream.mapToDouble(Number::doubleValue).average().orElse(0));
	}

	//endregion

	//region 求和

	/**
	 * 求和
	 *
	 * @param defaultValue 默认值
	 * @return 数字之和
	 */
	public T sum(T defaultValue) {
		//值类型为空或不是继承的Number类
		if (tClass == null || tClass.getGenericSuperclass() != Number.class) {
			return defaultValue;
		}
		//byte, short和int使用int求和
		if (tClass.equals(Byte.class) || tClass.equals(Short.class) || tClass.equals(Integer.class)) {
			//值
			var value = stream.mapToInt(Number::intValue).sum();
			return Convert.convert(tClass, value);
		}
		else if (tClass.equals(BigInteger.class)) {
			//值
			var value = stream.map(t -> BigInteger.valueOf(t.longValue())).reduce(BigInteger.ZERO, BigInteger::add);
			return Convert.convert(tClass, value);
		}
		else if (tClass.equals(Long.class)) {
			//值
			var value = stream.mapToLong(Number::longValue).sum();
			return Convert.convert(tClass, value);
		}
		//float, double,BigDecimal使用double求和
		var value = stream.mapToDouble(Number::doubleValue).sum();
		return Convert.convert(tClass, value);
	}

	/**
	 * 求和
	 *
	 * @return 数字之和
	 */
	public T sum() {
		//值类型为空或不是继承的Number类
		if (tClass == null || tClass.getGenericSuperclass() != Number.class) {
			return null;
		}
		//byte, short和int使用int求和
		if (tClass.equals(Byte.class) || tClass.equals(Short.class) || tClass.equals(Integer.class)) {
			//值
			var value = stream.mapToInt(Number::intValue).sum();
			return Convert.convert(tClass, value);
		}
		else if (tClass.equals(BigInteger.class)) {
			//值
			var value = stream.map(t -> BigInteger.valueOf(t.longValue())).reduce(BigInteger.ZERO, BigInteger::add);
			return Convert.convert(tClass, value);
		}
		else if (tClass.equals(Long.class)) {
			//值
			var value = stream.mapToLong(Number::longValue).sum();
			return Convert.convert(tClass, value);
		}
		//float, double,BigDecimal使用double求和
		var value = stream.mapToDouble(Number::doubleValue).sum();
		return Convert.convert(tClass, value);
	}

	/**
	 * 求和（自定义返回类型）
	 *
	 * @param rClass 返回结果类型
	 * @param <R>    结果类型
	 * @return 数字之和
	 */
	public <R extends Number> R sum(Class<R> rClass) {
		//值类型为空或不是继承的Number类
		if (tClass == null || tClass.getGenericSuperclass() != Number.class)
			return Convert.convert(rClass, 0);
		//byte, short和int使用int求和
		if (tClass.equals(Byte.class) || tClass.equals(Short.class) || tClass.equals(Integer.class)) {
			//值
			var value = stream.mapToInt(Number::intValue).sum();
			return Convert.convert(rClass, value);
		}
		else if (tClass.equals(BigInteger.class)) {
			//值
			var value = stream.map(t -> BigInteger.valueOf(t.longValue())).reduce(BigInteger.ZERO, BigInteger::add);
			return Convert.convert(rClass, value);
		}
		else if (tClass.equals(Long.class)) {
			//值
			var value = stream.mapToLong(Number::longValue).sum();
			return Convert.convert(rClass, value);
		}
		//float, double,BigDecimal使用double求和
		var value = stream.mapToDouble(Number::doubleValue).sum();
		return Convert.convert(rClass, value);
	}

	//endregion
}