package com.base.extensions.java.time.LocalDateTime;

import com.base.tools.time.TimeSpan;
import com.base.tools.time.enums.DateTimeFormat;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间 扩展方法
 */
@Extension
public class LocalDateTimeExtends {
	//region 扩展方法

	/**
	 * 日期转时间戳
	 *
	 * @param date 日期时间
	 * @return 时间戳
	 */
	public static long getTimestamp(@This LocalDateTime date) {
		return date.atZone(ZoneId.systemDefault()).toEpochSecond();
	}

	/**
	 * 获取日期部分
	 *
	 * @param date 日期时间
	 * @return 日期
	 */
	public static LocalDate getDate(@This LocalDateTime date) {
		return date.toLocalDate();
	}

	/**
	 * 获取时间部分
	 *
	 * @param date 日期时间
	 * @return 时间
	 */
	public static LocalTime getTime(@This LocalDateTime date) {
		return date.toLocalTime();
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
	public static String format(@This LocalDateTime date, String format) {
		return date.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 日期转字符串
	 * 默认（yyyy-MM-dd HH:mm:ss）
	 *
	 * @param date 日期
	 * @return 字符串
	 */
	public static String format(@This LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern(DateTimeFormat.DEFAULT.getValue()));
	}

	/**
	 * 日期转字符串
	 *
	 * @param date   日期
	 * @param format 格式枚举
	 * @return 字符串
	 */
	public static String format(@This LocalDateTime date, DateTimeFormat format) {
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
	public static TimeSpan minus(@This LocalDateTime left, LocalDate right) {
		//时间差毫秒
		var diff = Duration.between(LocalDateTime.of(right, LocalTime.MIN), left);
		var time = new TimeSpan();
		time.addMillis(diff.toMillis());
		return time;
	}

	/**
	 * 相减重载
	 *
	 * @param left  左边结束日期
	 * @param right 右边开始日期
	 * @return 时间间隔
	 */
	public static TimeSpan minus(@This LocalDateTime left, LocalDateTime right) {
		//时间差毫秒
		var diff = Duration.between(right, left);
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
	public static LocalDateTime minus(@This LocalDateTime left, LocalTime right) {
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
	public static LocalDateTime minus(@This LocalDateTime left, TimeSpan right) {
		return left.minusDays(right.day)
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
	public static LocalDateTime plus(@This LocalDateTime left, LocalTime right) {
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
	public static LocalDateTime plus(@This LocalDateTime left, TimeSpan right) {
		return left.plusDays(right.day)
				.plusHours(right.hour)
				.plusMinutes(right.minute)
				.plusSeconds(right.second)
				.plus(right.milli, ChronoUnit.MILLIS);
	}

	//endregion
}