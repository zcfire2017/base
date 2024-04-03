package com.base.tools.time.enums;

import com.base.tools.enums.entity.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期(yyyy-MM-dd) 格式化字符串
 */
@Getter
@AllArgsConstructor
public enum DateFormat implements IEnum<String> {

	/**
	 * 默认：中横线分隔
	 */
	DEFAULT("yyyy-MM-dd"),

	/**
	 * 无间隔
	 */
	NO("yyyyMMdd"),

	/**
	 * 下划线分隔
	 */
	UNDER("yyyy_MM_dd"),

	/**
	 * 斜线分隔
	 */
	OBLIQUE("yyyy/MM/dd"),

	/**
	 * 简单格式：中横线分隔
	 */
	SINGLE("yy-M-d"),

	/**
	 * 简单格式：无间隔
	 */
	SINGLE_NO("yyMd"),

	/**
	 * 中文格式
	 */
	ZH("yyyy年MM月dd日"),

	/**
	 * 中文格式
	 */
	ZH_SINGLE("yy年M月d日"),
	;

	/**
	 * 值
	 */
	public final String value;
}