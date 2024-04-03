package com.base.tools.enums.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 自定义枚举
 */
public interface IEnumKey<K, V> {
	/**
	 * 键
	 *
	 * @return 键
	 */
	K getKey();

	/**
	 * 值
	 *
	 * @return 值
	 */
	@JsonValue
	V getValue();

	/**
	 * 获取枚举名称
	 *
	 * @return 枚举名称
	 */
	default String getName() {
		return this.toString();
	}
}