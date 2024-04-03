package com.base.extensions.java.lang.Class;

import manifold.ext.rt.api.ComparableUsing;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * 类信息 扩展
 */
@Extension
public class ClassExtend {
	public static Class<?> getSuperClass(@This Class<?> clazz) {
		return clazz.getSuperclass();
	}

	/**
	 * 逻辑运算（Byte）
	 *
	 * @param left  BigDecimal
	 * @param right Byte
	 * @return boolean
	 */
	public static boolean compareToUsing(@This Class<?> left, Class<?> right, ComparableUsing.Operator op) {
		return left.equals(right);
	}
}