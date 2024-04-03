package com.base.extensions.java.lang.Short;

import manifold.ext.rt.api.Extension;

/**
 * Short 单位
 */
@Extension
public class ShortUnit {
	/**
	 * short单位
	 */
	public static final ShortUnit st = new ShortUnit();

	/**
	 * 后缀单位
	 *
	 * @param value int
	 * @return short
	 */
	public Short postfixBind(Integer value) {
		return value.shortValue();
	}
}