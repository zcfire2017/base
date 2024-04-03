package com.base.entity.key_value;

import lombok.NoArgsConstructor;

/**
 * 项和值(Long)
 */
@NoArgsConstructor
public class ItemLong extends ItemValue<Long> {
	/**
	 * 构造函数
	 *
	 * @param item  项
	 * @param value 值
	 */
	public ItemLong(String item, Long value) {
		super(item, value);
	}
}