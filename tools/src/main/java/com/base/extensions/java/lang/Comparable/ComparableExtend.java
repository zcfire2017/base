package com.base.extensions.java.lang.Comparable;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * 数字类型 扩展
 */
@Extension
public class ComparableExtend {
	/**
	 * 是否包含在集合中
	 *
	 * @param the    值
	 * @param others 集合
	 * @param <T>    数值类型
	 * @return 是否包含
	 */
	@SafeVarargs
	public static <T> boolean in(@This Comparable<T> the, T... others) {
		return others.stream().anyMatch(n -> n.equals(the));
	}

	/**
	 * 是否不包含在集合中
	 *
	 * @param the    值
	 * @param others 集合
	 * @param <T>    值类型
	 * @return 是否不包含
	 */
	@SafeVarargs
	public static <T> boolean notIn(@This Comparable<T> the, T... others) {
		return others.stream().noneMatch(n -> n.equals(the));
	}
}