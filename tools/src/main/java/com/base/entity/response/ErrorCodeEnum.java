package com.base.entity.response;

import com.base.tools.enums.entity.IntEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 操作结果Enum
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements IntEnum {
	/**
	 * 成功
	 */
	Success(0, "成功"),

	/**
	 * 错误请求
	 * 请求参数有误
	 */
	BadRequest(400, "错误请求"),

	/**
	 * 无权限
	 * 用户无效，或者用户无权限
	 */
	Unauthorized(401, "无权限"),

	/**
	 * 拒绝执行，资源不可用
	 */
	TimeExpire(403, "拒绝执行"),

	/**
	 * 未找到
	 */
	NotFound(404, "未找到"),

	/**
	 * 请求超时
	 */
	RequestTimeout(408, "请求超时"),

	/**
	 * 服务器内部错误
	 */
	ServerError(500, "服务器内部错误"),
	;

	/**
	 * 值
	 */
	private final Integer value;

	/**
	 * 描述
	 */
	private final String desc;
}