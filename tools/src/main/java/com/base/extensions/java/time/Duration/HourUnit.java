package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Duration;


/**
 * 时间 - 小时 单位
 */
@Extension
public class HourUnit {
	/**
	 * 小时 单位
	 */
	public static final HourUnit h = new HourUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Duration
	 */
	public Duration postfixBind(Integer value) {
		return Duration.ofMinutes(value);
	}
}