package com.base.entity.key_value;

/**
 * 项和值(Integer)
 */
public class ItemInt extends ItemValue<Integer> {
	/**
	 * 构造函数
	 *
	 * @param value 值
	 * @param name  名称
	 */
	public ItemInt(Integer value, String name) {
		super(name, value);
	}
}