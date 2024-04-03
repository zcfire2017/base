package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Period;

/**
 * 日期 - 年 单位
 */
@Extension
public class YearUnit {
	/**
	 * 年 单位
	 */
	public static final YearUnit y = new YearUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Period
	 */
	public Period postfixBind(Integer value) {
		return Period.ofYears(value);
	}
}