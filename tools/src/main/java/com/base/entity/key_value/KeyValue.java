package com.base.entity.key_value;

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
public class KeyValue<T, V> {

	/**
	 * 键
	 */
	private T key;

	/**
	 * 值
	 */
	private V value;

	public KeyValue(T key, V value) {
		this.key = key;
		this.value = value;
	}
}