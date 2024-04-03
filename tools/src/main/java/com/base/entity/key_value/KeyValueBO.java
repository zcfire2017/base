package com.base.entity.key_value;

import cn.hutool.core.lang.TypeReference;
import lombok.Getter;
import lombok.Setter;

/**
 * 键值对 原始领域值类型
 * 键名和值类型
 */
@Getter
@Setter
public class KeyValueBO<T> {
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 键名
	 */
	private String keyName;

	/**
	 * 值类型
	 */
	private Class<T> clazz;

	/**
	 * 键值对
	 *
	 * @param type    键类型
	 * @param keyName 键名
	 * @param clazz   值类型
	 */
	public KeyValueBO(String type, String keyName, Class<T> clazz) {
		this.type = type;
		this.keyName = keyName;
		this.clazz = clazz;
	}

	/**
	 * 键值对
	 *
	 * @param type          键类型
	 * @param keyName       键名
	 * @param typeReference 值类型
	 */
	public KeyValueBO(String type, String keyName, TypeReference<T> typeReference) {
		this.type = type;
		this.keyName = keyName;
		this.clazz = (Class<T>) typeReference.type;
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type    键类型
	 * @param keyName 键名
	 * @param clazz   值类型
	 * @return 建键值对信息
	 */
	public static <T> KeyValueBO<T> create(String type, String keyName, Class<T> clazz) {
		return new KeyValueBO<>(type, keyName, clazz);
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type    键类型
	 * @param keyName 键名
	 * @return 建键值对信息
	 */
	public static KeyValueBO<String> create(String type, String keyName) {
		return new KeyValueBO<>(type, keyName, String.class);
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type          键类型
	 * @param keyName       键名
	 * @param typeReference 值类型
	 * @return 建键值对信息
	 */
	public static <T> KeyValueBO<T> create(String type, String keyName, TypeReference<T> typeReference) {
		return new KeyValueBO<>(type, keyName, typeReference);
	}
}