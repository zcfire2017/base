package com.base.tools.time.enums;

import com.base.tools.enums.entity.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时间(HH:mm:ss) 格式化字符串
 */
@Getter
@AllArgsConstructor
public enum TimeFormat implements IEnum<String> {

	/**
	 * 默认
	 */
	DEFAULT("HH:mm:ss"),

	/**
	 * 无分隔
	 */
	NO("HHmmss"),

	/**
	 * 无分隔（毫秒）
	 */
	NO_MILLIS("HHmmssSSS"),

	/**
	 * 时间（毫秒）
	 */
	MILLIS("HH:mm:ss.SSS"),

	/**
	 * 中文
	 */
	ZH("HH时mm分ss秒"),
	;

	/**
	 * 值
	 */
	public final String value;
}