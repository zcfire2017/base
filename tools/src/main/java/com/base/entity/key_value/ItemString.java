package com.base.entity.key_value;

import lombok.NoArgsConstructor;

/**
 * 项和值(String)
 */
@NoArgsConstructor
public class ItemString extends ItemValue<String> {
	/**
	 * 构造函数
	 *
	 * @param value 值
	 * @param name  名称
	 */
	public ItemString(String value, String name) {
		super(name, value);
	}
}