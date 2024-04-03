package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Period;

/**
 * 日期 - 月 单位
 */
@Extension
public class MonthUnit {
	/**
	 * 月 单位
	 */
	public static final MonthUnit mon = new MonthUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Period
	 */
	public Period postfixBind(Integer value) {
		return Period.ofMonths(value);
	}
}