package com.base.tools.enums.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 自定义枚举
 */
public interface IEnum<T> {
	/**
	 * 枚举值
	 */
	@JsonValue
	T getValue();

	/**
	 * 获取枚举名称
	 *
	 * @return 枚举名称
	 */
	default String getName() {
		return this.toString();
	}
}