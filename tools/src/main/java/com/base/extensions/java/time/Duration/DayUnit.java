package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Period;


/**
 * 日期 - 日 单位
 */
@Extension
public class DayUnit {
	/**
	 * 日 单位
	 */
	public static final DayUnit d = new DayUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Duration
	 */
	public Period postfixBind(Integer value) {
		return Period.ofDays(value);
	}
}