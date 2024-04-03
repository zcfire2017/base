package com.base.extensions.java.math.BigDecimal;

import cn.hutool.core.convert.Convert;
import manifold.ext.rt.api.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 扩展
 */
@Extension
public abstract class BigDecimalExtend {
	//region 类方法扩展

	/**
	 * 转换类型
	 *
	 * @param obj    BigDecimal对象
	 * @param number 数字对象
	 * @return BigDecimal
	 */
	public static @Self BigDecimal from(@ThisClass Class<BigDecimal> obj, Object number) {
		return Convert.convert(BigDecimal.class, number);
	}

	//endregion

	//region 实例对象方法扩展

	/**
	 * 保留小数位（两位）
	 *
	 * @param decimal 小数
	 * @return 保留两位小数
	 */
	public static BigDecimal digit(@This BigDecimal decimal) {
		return decimal.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 保留小数位
	 *
	 * @param decimal 小数
	 * @param digit   小数位
	 * @return 保留两位小数
	 */
	public static BigDecimal digit(@This BigDecimal decimal, int digit) {
		return decimal.setScale(digit, RoundingMode.HALF_UP);
	}

	/**
	 * 向上取整
	 *
	 * @param decimal 小数
	 * @return 大于小数的最小整数
	 */
	public static Integer ceil(@This BigDecimal decimal) {
		return (int) Math.ceil(decimal.doubleValue());
	}

	//endregion

	//region 算术运算符重载

	//region 加法

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边8位整数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, byte right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边16位整数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, short right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边32位整数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, int right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边64位整数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, long right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, double right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, float right) {
		return left.add(new BigDecimal(right));
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal plus(@This BigDecimal left, BigDecimal right) {
		return left.add(right);
	}

	//endregion

	//region 减法

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边8位整数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, byte right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边16位整数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, short right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边32位整数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, int right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边64位整数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, long right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, double right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, float right) {
		return left.subtract(new BigDecimal(right));
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal minus(@This BigDecimal left, BigDecimal right) {
		return left.subtract(right);
	}

	//endregion

	//region 乘法

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边8位整数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, byte right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边16位整数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, short right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边32位整数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, int right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边64位整数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, long right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, double right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, float right) {
		return left.multiply(new BigDecimal(right));
	}

	/**
	 * 乘以重载
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal times(@This BigDecimal left, BigDecimal right) {
		return left.multiply(right);
	}

	//endregion

	//region 除法

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边8位整数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, byte right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边16位整数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, short right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边32位整数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, int right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边64位整数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, long right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, float right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, double right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(new BigDecimal(right), 4, RoundingMode.HALF_UP);
	}

	/**
	 * 除以重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal div(@This BigDecimal left, BigDecimal right) {
		if (right == 0) {
			return BigDecimal.ZERO;
		}
		return left.divide(right, 4, RoundingMode.HALF_UP);
	}

	//endregion

	//region 取余

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边8位整数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, byte right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边16位整数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, short right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边32位整数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, int right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边64位整数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, long right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边双精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, double right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, float right) {
		return left.remainder(new BigDecimal(right));
	}

	/**
	 * 取余重载
	 * 保留两位小数
	 *
	 * @param left  左边小数
	 * @param right 右边单精度浮点小数
	 * @return 小数
	 */
	public static BigDecimal rem(@This BigDecimal left, BigDecimal right) {
		return left.remainder(right);
	}

	//endregion

	//region 自身计算

	/**
	 * 加一
	 * a++或++a
	 *
	 * @param left 左边小数
	 * @return 小数
	 */
	public static BigDecimal inc(@This BigDecimal left) {
		return left.add(BigDecimal.ONE);
	}

	/**
	 * 减一
	 * a--或--a
	 *
	 * @param left 左边小数
	 * @return 小数
	 */
	public static BigDecimal dec(@This BigDecimal left) {
		return left.subtract(BigDecimal.ONE);
	}

	/**
	 * 减一
	 * -a
	 *
	 * @param left 左边小数
	 * @return 小数
	 */
	public static BigDecimal unaryMinus(@This BigDecimal left) {
		return left.subtract(BigDecimal.ONE);
	}

	//endregion

	//endregion

	//region 逻辑运算符重载

	/**
	 * 逻辑运算（Byte）
	 *
	 * @param left  BigDecimal
	 * @param right Byte
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, byte right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Short）
	 *
	 * @param left  BigDecimal
	 * @param right Short
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, short right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Integer）
	 *
	 * @param left  BigDecimal
	 * @param right Integer
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, int right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Long）
	 *
	 * @param left  BigDecimal
	 * @param right Long
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, long right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Double）
	 *
	 * @param left  BigDecimal
	 * @param right Double
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, double right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Float）
	 *
	 * @param left  BigDecimal
	 * @param right Float
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, float right, ComparableUsing.Operator op) {
		var value = BigDecimal.valueOf(right);
		return switch (op) {
			case LT -> left.compareTo(value) < 0;
			case LE -> left.compareTo(value) <= 0;
			case GT -> left.compareTo(value) > 0;
			case GE -> left.compareTo(value) >= 0;
			case EQ -> left.compareTo(value) == 0;
			case NE -> left.compareTo(value) != 0;
		};
	}

	/**
	 * 逻辑运算（Float）
	 *
	 * @param left  BigDecimal
	 * @param right Float
	 * @param op    逻辑运算符
	 * @return boolean
	 */
	public static boolean compareToUsing(@This BigDecimal left, BigDecimal right, ComparableUsing.Operator op) {
		return switch (op) {
			case LT -> left.compareTo(right) < 0;
			case LE -> left.compareTo(right) <= 0;
			case GT -> left.compareTo(right) > 0;
			case GE -> left.compareTo(right) >= 0;
			case EQ -> left.compareTo(right) == 0;
			case NE -> left.compareTo(right) != 0;
		};
	}

	//endregion
}