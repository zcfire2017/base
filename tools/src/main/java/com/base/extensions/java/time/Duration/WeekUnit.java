package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Period;

/**
 * 日期 - 周 单位
 */
@Extension
public class WeekUnit {
	/**
	 * 周 单位
	 */
	public static final WeekUnit wk = new WeekUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Period
	 */
	public Period postfixBind(Integer value) {
		return Period.ofWeeks(value);
	}
}