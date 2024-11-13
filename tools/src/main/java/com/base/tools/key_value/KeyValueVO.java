package com.base.tools.key_value;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;

/**
 * 键值对 原始领域值类型
 * 键名和值类型
 */
@Getter
@Setter
public class KeyValueVO<T> {
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 键名
	 */
	private String keyName;

	/**
	 * 值类型（集合）
	 */
	TypeReference<T> typeReference;

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
	public KeyValueVO(String type, String keyName, Class<T> clazz) {
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
	public KeyValueVO(String type, String keyName, TypeReference<T> typeReference) {
		this.type = type;
		this.keyName = keyName;
		this.typeReference = typeReference;
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type    键类型
	 * @param keyName 键名
	 * @param clazz   值类型
	 * @return 建键值对信息
	 */
	public static <T> KeyValueVO<T> create(String type, String keyName, Class<T> clazz) {
		return new KeyValueVO<>(type, keyName, clazz);
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type    键类型
	 * @param keyName 键名
	 * @return 建键值对信息
	 */
	public static KeyValueVO<String> create(String type, String keyName) {
		return new KeyValueVO<>(type, keyName, String.class);
	}

	/**
	 * 创建键值对信息
	 *
	 * @param type          键类型
	 * @param keyName       键名
	 * @param typeReference 值类型
	 * @return 建键值对信息
	 */
	public static <T> KeyValueVO<T> create(String type, String keyName, TypeReference<T> typeReference) {
		return new KeyValueVO<>(type, keyName, typeReference);
	}

	/**
	 * 获取值
	 *
	 * @param value 值字符串
	 * @return 值
	 */
	public T getValue(String value) {
		//是否是json对象
		if (JSONUtil.isTypeJSON(value)) {
			if (this.getClazz() != null) {
				return value.jsonDes(this.getClazz());
			}
			if (this.getTypeReference() != null) {
				return value.jsonDes(this.getTypeReference());
			}
		}
		else {
			return Convert.convert(this.getClazz(), value);
		}
		return null;
	}
}