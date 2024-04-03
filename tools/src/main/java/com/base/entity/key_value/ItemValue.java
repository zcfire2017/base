package com.base.entity.key_value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 项和值
 * 一般用作统计报表的名称和值
 *
 * @param <T> 值类型
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemValue<T> {
	/**
	 * 项
	 */
	private String item;

	/**
	 * 值
	 */
	private T value;
}