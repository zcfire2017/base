package com.base.extensions.java.math.BigDecimal;

import manifold.ext.rt.api.Extension;

import java.math.BigDecimal;

/**
 * BigDecimal 单位
 */
@Extension
public class BigDecimalUnit {
	/**
	 * BigDecimal 单位
	 */
	public static final BigDecimalUnit dm = new BigDecimalUnit();

	/**
	 * 后缀单位
	 *
	 * @param value Integer
	 * @return BigDecimal
	 */
	public BigDecimal postfixBind(Integer value) {
		return BigDecimal.valueOf(value);
	}

	/**
	 * 后缀单位
	 *
	 * @param value Double
	 * @return BigDecimal
	 */
	public BigDecimal postfixBind(Double value) {
		return BigDecimal.valueOf(value);
	}
}