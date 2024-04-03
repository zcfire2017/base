package com.base.tools.time;

import lombok.NoArgsConstructor;
import manifold.ext.props.rt.api.set;
import manifold.ext.props.rt.api.var;

import java.math.BigDecimal;

import static manifold.ext.props.rt.api.PropOption.Private;

/**
 * 时间间隔
 */
@NoArgsConstructor
public class TimeSpan {
	//region 属性

	/**
	 * 日
	 */
	@var
	@set(Private)
	int day = 0;

	/**
	 * 小时
	 */
	@var
	@set(Private)
	int hour = 0;

	/**
	 * 分钟
	 */
	@var
	@set(Private)
	int minute = 0;

	/**
	 * 秒
	 */
	@var
	@set(Private)
	long second = 0;

	/**
	 * 毫秒
	 */
	@var
	@set(Private)
	long milli = 0;

	//endregion

	//region 构造函数

	/**
	 * 构造函数
	 *
	 * @param day    日
	 * @param hour   小时
	 * @param minute 分钟
	 * @param second 秒
	 * @param milli  毫秒
	 */
	public TimeSpan(int day, int hour, int minute, long second, long milli) {
		addMillis(milli);
		addSecond(second);
		addMinute(minute);
		addHour(hour);
		addDay(day);
	}

	/**
	 * 构造函数
	 *
	 * @param day    日
	 * @param hour   小时
	 * @param minute 分钟
	 * @param second 秒
	 */
	public TimeSpan(int day, int hour, int minute, long second) {
		addSecond(second);
		addMinute(minute);
		addHour(hour);
		addDay(day);
	}

	/**
	 * 构造函数
	 *
	 * @param day    日
	 * @param hour   小时
	 * @param minute 分钟
	 */
	public TimeSpan(int day, int hour, int minute) {
		addMinute(minute);
		addHour(hour);
		addDay(day);
	}

	/**
	 * 构造函数
	 *
	 * @param minute 分钟
	 * @param second 秒
	 * @param milli  毫秒
	 */
	public TimeSpan(int minute, long second, long milli) {
		addMillis(milli);
		addSecond(second);
		addMinute(minute);
	}

	//endregion

	//region 计算值

	/**
	 * 计算小时
	 *
	 * @param hour 小时
	 */
	public void addDay(int hour) {
		this.day += hour;
	}

	/**
	 * 计算小时
	 *
	 * @param hours 小时
	 */
	public void addHour(int hours) {
		//日
		var day = (this.hour + hours) / 24;
		//小时
		this.hour = (this.hour + hours) % 24;
		//计算日
		if (day != 0) {
			addDay(day);
		}
	}

	/**
	 * 计算分钟
	 *
	 * @param minutes 分钟
	 */
	public void addMinute(int minutes) {
		//小时
		var hour = (this.minute + minutes) / 60;
		//分钟
		this.minute = (this.minute + minutes) % 60;
		//计算小时
		if (hour != 0) {
			addHour(hour);
		}
	}

	/**
	 * 计算秒
	 *
	 * @param seconds 秒
	 */
	public void addSecond(long seconds) {
		//分钟
		var min = (this.second + seconds) / 60;
		//秒
		this.second = (this.second + seconds) % 60;
		//计算分钟
		if (min != 0) {
			addMinute((int) min);
		}
	}

	/**
	 * 计算毫秒
	 *
	 * @param millis 毫秒
	 */
	public void addMillis(long millis) {
		//秒
		var sec = (this.milli + millis) / 1000;
		//毫秒
		this.milli = (this.milli + millis) % 1000;
		//计算秒
		if (sec != 0) {
			addSecond(sec);
		}
	}

	//endregion

	//region 汇总值

	/**
	 * 总天数
	 *
	 * @return 总天数
	 */
	public BigDecimal getTotalDays() {
		return BigDecimal.valueOf(day + hour / 24f + minute / 1440f + second / 86400f + milli / 86400000f);
	}

	/**
	 * 总小时
	 *
	 * @return 总小时
	 */
	public BigDecimal getTotalHours() {
		return BigDecimal.valueOf(day * 24 + hour + minute / 60f + second / 3600f + milli / 3600000f);
	}

	/**
	 * 总分钟
	 *
	 * @return 总分钟
	 */
	public BigDecimal getTotalMinutes() {
		return BigDecimal.valueOf(day * 1440 + hour * 60 + minute + second / 60f + milli / 60000f);
	}

	/**
	 * 总秒
	 *
	 * @return 总秒
	 */
	public BigDecimal getTotalSeconds() {
		return BigDecimal.valueOf(day * 86400 + hour * 3600 + minute * 60 + second + milli / 1000f);
	}

	/**
	 * 总毫秒
	 *
	 * @return 总毫秒
	 */
	public long getTotalMillis() {
		return day * 86400000L + hour * 3600000L + minute * 60000L + second * 1000L + milli;
	}

	//endregion

	//region 运算符重载

	/**
	 * 相减
	 *
	 * @param that 被减时间间隔
	 * @return 时间间隔
	 */
	public TimeSpan minus(TimeSpan that) {
		var diff = new TimeSpan();
		//相减
		diff.addMillis(this.totalMillis - that.totalMillis);
		return diff;
	}

	/**
	 * 相加
	 *
	 * @param that 被加时间间隔
	 * @return 时间间隔
	 */
	public TimeSpan plus(TimeSpan that) {
		var diff = new TimeSpan();
		//相减
		diff.addMillis(this.totalMillis + that.totalMillis);
		return diff;
	}

	//endregion
}