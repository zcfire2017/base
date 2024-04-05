package com.base.entity.key_value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通用键值对
 *
 * @param <T> 键
 * @param <V> 值
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<T, V> {

	/**
	 * 键
	 */
	private T key;

	/**
	 * 值
	 */
	private V value;

	/**
	 * 前缀单位操作
	 *
	 * @param value 值
	 * @return 键值对
	 */
	public KeyValue<T, V> prefixBind(V value) {
		return new KeyValue<>(this.key, value);
	}
}