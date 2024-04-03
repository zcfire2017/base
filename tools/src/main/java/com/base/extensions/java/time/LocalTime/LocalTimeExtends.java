package com.base.extensions.java.time.LocalTime;

import com.base.tools.time.TimeSpan;
import com.base.tools.time.enums.TimeFormat;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 时间 扩展方法
 */
@Extension
public class LocalTimeExtends {
	//region 转字符串

	/**
	 * 时间转字符串
	 *
	 * @param date   日期
	 * @param format 格式化格式
	 * @return 字符串
	 */
	public static String format(@This LocalTime date, String format) {
		return date.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 时间转字符串
	 * 默认（yyyy-MM-dd）
	 *
	 * @param date 日期
	 * @return 字符串
	 */
	public static String format(@This LocalTime date) {
		return date.format(DateTimeFormatter.ofPattern(TimeFormat.DEFAULT.getValue()));
	}

	/**
	 * 时间转字符串
	 *
	 * @param date   日期
	 * @param format 格式化格式
	 * @return 字符串
	 */
	public static String format(@This LocalTime date, TimeFormat format) {
		return date.format(DateTimeFormatter.ofPattern(format.getValue()));
	}

	//endregion

	//region 运算符重载

	/**
	 * 相减重载
	 *
	 * @param left  左边日期
	 * @param right 右边时间
	 * @return 日期时间
	 */
	public static LocalTime minus(@This LocalTime left, LocalTime right) {
		return left.minusHours(right.getHour())
				.minusMinutes(right.getMinute())
				.minusSeconds(right.getSecond())
				.minusNanos(right.getNano());
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边日期
	 * @param right 右边时间间隔
	 * @return 日期时间
	 */
	public static LocalTime minus(@This LocalTime left, TimeSpan right) {
		return left.minusHours(right.hour)
				.minusMinutes(right.minute)
				.minusSeconds(right.second)
				.minus(right.milli, ChronoUnit.MILLIS);
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边日期
	 * @param right 右边时间
	 * @return 日期时间
	 */
	public static LocalTime plus(@This LocalTime left, LocalTime right) {
		return left.plusHours(right.getHour())
				.plusMinutes(right.getMinute())
				.plusSeconds(right.getSecond())
				.plusNanos(right.getNano());
	}

	/**
	 * 相加重载
	 *
	 * @param left  左边日期
	 * @param right 右边时间间隔
	 * @return 日期时间
	 */
	public static LocalTime plus(@This LocalTime left, TimeSpan right) {
		return left.plusHours(right.hour)
				.plusMinutes(right.minute)
				.plusSeconds(right.second)
				.plus(right.milli, ChronoUnit.MILLIS);
	}

	//endregion
}