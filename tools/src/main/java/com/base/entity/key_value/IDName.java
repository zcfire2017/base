package com.base.entity.key_value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ID和名称
 *
 * @param <T> ID类型
 */
@Getter
@Setter
@NoArgsConstructor
public class IDName<T> extends KeyValue<T, String> {
	/**
	 * 构造函数
	 *
	 * @param id       ID
	 * @param userName 名称
	 */
	public IDName(T id, String userName) {
		super(id, userName);
	}
}