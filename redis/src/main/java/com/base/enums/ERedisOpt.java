package com.base.enums;

import com.base.tools.enums.entity.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Redis 操作枚举
 */
@Getter
@AllArgsConstructor
public enum ERedisOpt implements IEnum<String> {
	新增("insert"),
	保存("save"),
	更新("update"),
	删除("delete"),
	;

	/**
	 * 值
	 */
	private final String value;
}