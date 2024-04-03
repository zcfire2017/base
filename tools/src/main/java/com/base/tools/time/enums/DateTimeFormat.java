package com.base.tools.time.enums;

import com.base.tools.enums.entity.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期时间(yyyy-MM-dd HH:mm:ss) 格式化字符串
 */
@Getter
@AllArgsConstructor
public enum DateTimeFormat implements IEnum<String> {

	/**
	 * 默认
	 */
	DEFAULT("yyyy-MM-dd HH:mm:ss"),

	/**
	 * 无分隔
	 */
	NO("yyyyMMddHHmmss"),

	/**
	 * 无分隔（毫秒）
	 */
	NO_MILLIS("yyyyMMddHHmmssSSS"),

	/**
	 * 日期
	 */
	DATE("yyyy-MM-dd"),

	/**
	 * 日期（无分隔）
	 */
	DATE_NO("yyyyMMdd"),

	/**
	 * 时间
	 */
	TIME("HH:mm:ss"),

	/**
	 * 时间（无分隔）
	 */
	TIME_NO("HHmmss"),

	/**
	 * 时间（毫秒）
	 */
	TIME_MILLIS("HH:mm:ss.SSS"),

	/**
	 * 时间（毫秒）（无分隔）
	 */
	TIME_MILLIS_NO("HHmmssSSS"),

	/**
	 * 中文
	 */
	ZH("yyyy年MM月dd日 HH时mm分ss秒"),

	/**
	 * 中文
	 */
	ZH_DATE("yyyy年MM月dd日"),

	/**
	 * 中文
	 */
	ZH_TIME("HH时mm分ss秒"),
	;

	/**
	 * 值
	 */
	public final String value;
}