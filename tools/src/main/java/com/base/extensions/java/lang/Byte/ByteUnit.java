package com.base.extensions.java.lang.Byte;

import manifold.ext.rt.api.Extension;

/**
 * Byte 单位
 */
@Extension
public class ByteUnit {
	/**
	 * Byte单位
	 */
	public static final ByteUnit bt = new ByteUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return short
	 */
	public Byte postfixBind(Integer value) {
		return value.byteValue();
	}

	/**
	 * 后缀单位
	 *
	 * @param value Short
	 * @return short
	 */
	public Byte postfixBind(Short value) {
		return value.byteValue();
	}
}