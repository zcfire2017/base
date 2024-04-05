package com.base.extensions.java.time.LocalDate;

import com.base.tools.time.TimeSpan;
import com.base.tools.time.enums.DateFormat;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期 扩展方法
 */
@Extension
public class LocalDateExtends {
	//region 扩展方法

	/**
	 * 日期转时间戳
	 *
	 * @param date 日期
	 * @return 时间戳
	 */
	public static long getTimestamp(@This LocalDate date) {
		return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
	}

	//endregion

	//region 日期转字符串

	/**
	 * 日期转字符串
	 *
	 * @param date   日期
	 * @param format 格式化格式
	 * @return 字符串
	 */
	public static String format(@This LocalDate date, String format) {
		return date.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 日期转字符串
	 * 默认（yyyy-MM-dd）
	 *
	 * @param date 日期
	 * @return 字符串
	 */
	public static String format(@This LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern(DateFormat.DEFAULT.getValue()));
	}

	/**
	 * 日期转字符串
	 *
	 * @param date   日期
	 * @param format 格式化格式
	 * @return 字符串
	 */
	public static String format(@This LocalDate date, DateFormat format) {
		return date.format(DateTimeFormatter.ofPattern(format.getValue()));
	}

	//endregion

	//region 运算符重载

	/**
	 * 相减重载
	 *
	 * @param left  左边结束日期
	 * @param right 右边开始日期
	 * @return 时间间隔
	 */
	public static TimeSpan minus(@This LocalDate left, LocalDate right) {
		//时间差天数
		var diff = Period.between(right, left);
		var time = new TimeSpan();
		time.addDay(diff.getDays());
		return time;
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边结束日期
	 * @param right 右边开始日期
	 * @return 时间间隔
	 */
	public static TimeSpan minus(@This LocalDate left, LocalDateTime right) {
		//时间差毫秒
		var diff = Duration.between(right, LocalDateTime.of(left, LocalTime.MIN));
		var time = new TimeSpan();
		time.addMillis(diff.toMillis());
		return time;
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边日期
	 * @param right 右边时间
	 * @return 日期时间
	 */
	public static LocalDateTime minus(@This LocalDate left, LocalTime right) {
		var begin = LocalDateTime.of(left, LocalTime.MIN);
		return begin.minusHours(right.getHour())
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
	public static LocalDateTime minus(@This LocalDate left, TimeSpan right) {
		var begin = LocalDateTime.of(left, LocalTime.MIN);
		return begin.minusDays(right.day)
				.minusHours(right.hour)
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
	public static LocalDateTime plus(@This LocalDate left, LocalTime right) {
		var begin = LocalDateTime.of(left, LocalTime.MIN);
		return begin.plusHours(right.getHour())
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
	public static LocalDateTime plus(@This LocalDate left, TimeSpan right) {
		var begin = LocalDateTime.of(left, LocalTime.MIN);
		return begin.plusDays(right.day)
				.plusHours(right.hour)
				.plusMinutes(right.minute)
				.plusSeconds(right.second)
				.plus(right.milli, ChronoUnit.MILLIS);
	}

	//endregion
}