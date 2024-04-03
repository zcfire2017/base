package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Duration;


/**
 * 时间 - 毫秒 单位
 */
@Extension
public class MillisUnit {
	/**
	 * 毫秒 单位
	 */
	public static final MillisUnit ms = new MillisUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Duration
	 */
	public Duration postfixBind(Integer value) {
		return Duration.ofMillis(value);
	}
}