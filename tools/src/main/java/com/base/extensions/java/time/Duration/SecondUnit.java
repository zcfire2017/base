package com.base.extensions.java.time.Duration;

import manifold.ext.rt.api.Extension;

import java.time.Duration;


/**
 * 时间 - 秒 单位
 */
@Extension
public class SecondUnit {
	/**
	 * 秒 单位
	 */
	public static final SecondUnit sec = new SecondUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return Duration
	 */
	public Duration postfixBind(Integer value) {
		return Duration.ofSeconds(value);
	}
}