package com.base.extensions.java.lang.Short;

import manifold.ext.rt.api.ComparableUsing;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.math.BigDecimal;

/**
 * Short 逻辑运算符扩展
 */
@Extension
public abstract class ShortOperatorExtends implements ComparableUsing<Short> {

	/**
	 * 逻辑运算符重载
	 *
	 * @param the Short 对象
	 * @param com Integer 对象
	 * @param op  逻辑运算符
	 * @return 逻辑运算结果
	 */
	public static boolean compareToUsing(@This Short the, short com, Operator op) {
		return switch (op) {
			case LT -> the.compareTo(com) < 0;
			case LE -> the.compareTo(com) <= 0;
			case GT -> the.compareTo(com) > 0;
			case GE -> the.compareTo(com) >= 0;
			case EQ -> the.compareTo(com) == 0;
			case NE -> the.compareTo(com) != 0;
		};
	}

	/**
	 * 逻辑运算符重载
	 *
	 * @param the Short 对象
	 * @param com Integer 对象
	 * @param op  逻辑运算符
	 * @return 逻辑运算结果
	 */
	public static boolean compareToUsing(@This Short the, int com, Operator op) {
		var value = Integer.valueOf(com).shortValue();
		return switch (op) {
			case LT -> the.compareTo(value) < 0;
			case LE -> the.compareTo(value) <= 0;
			case GT -> the.compareTo(value) > 0;
			case GE -> the.compareTo(value) >= 0;
			case EQ -> the.compareTo(value) == 0;
			case NE -> the.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算符重载
	 *
	 * @param the Short 对象
	 * @param com Byte 对象
	 * @param op  逻辑运算符
	 * @return 逻辑运算结果
	 */
	public static boolean compareToUsing(@This Short the, byte com, Operator op) {
		var value = Integer.valueOf(com).shortValue();
		return switch (op) {
			case LT -> the.compareTo(value) < 0;
			case LE -> the.compareTo(value) <= 0;
			case GT -> the.compareTo(value) > 0;
			case GE -> the.compareTo(value) >= 0;
			case EQ -> the.compareTo(value) == 0;
			case NE -> the.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算符重载
	 *
	 * @param the Short 对象
	 * @param com BigDecimal 对象
	 * @param op  逻辑运算符
	 * @return 逻辑运算结果
	 */
	public static boolean compareToUsing(@This Short the, BigDecimal com, Operator op) {
		var value = BigDecimal.valueOf(the);
		return switch (op) {
			case LT -> value.compareTo(com) < 0;
			case LE -> value.compareTo(com) <= 0;
			case GT -> value.compareTo(com) > 0;
			case GE -> value.compareTo(com) >= 0;
			case EQ -> value.compareTo(com) == 0;
			case NE -> value.compareTo(com) != 0;
		};
	}
}