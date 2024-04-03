package com.base.entity.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API响应实体
 *
 * @param <T> 返回对象类型
 */
@Getter
@Setter
@NoArgsConstructor
public class APIResponse<T> {
	/**
	 * 操作结果
	 */
	private ErrorCodeEnum errorCode;

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private T result;

	/**
	 * 构造函数
	 *
	 * @param errorCode 错误码
	 * @param message   消息
	 * @param result    数据
	 */
	public APIResponse(ErrorCodeEnum errorCode, String message, T result) {
		this.errorCode = errorCode;
		this.message = message;
		this.result = result;
	}

	/**
	 * 构造函数
	 *
	 * @param errorCode 结果码
	 * @param result    数据
	 */
	public APIResponse(ErrorCodeEnum errorCode, T result) {
		this(errorCode, errorCode.toString(), result);
	}

	/**
	 * 返回成功的结果
	 *
	 * @param data 数据
	 * @param <T>  泛型
	 * @return 返回结果
	 */
	public static <T> APIResponse<T> success(T data) {
		return new APIResponse<>(ErrorCodeEnum.Success, data);
	}

	/**
	 * 返回错误结果
	 *
	 * @param errorCode code
	 * @param <T>       泛型
	 * @return 返回结果
	 */
	public static <T> APIResponse<T> fail(ErrorCodeEnum errorCode) {
		return new APIResponse<>(errorCode, null);
	}

	/**
	 * 返回错误结果
	 *
	 * @param errorCode code
	 * @param message   消息
	 * @param <T>       泛型
	 * @return 返回结果
	 */
	public static <T> APIResponse<T> fail(ErrorCodeEnum errorCode, String message) {
		return new APIResponse<>(errorCode, message, null);
	}

	/**
	 * 返回结果
	 *
	 * @param errorCode 结果码
	 * @param data      数据
	 * @param <T>       泛型
	 * @return 返回结果
	 */
	public static <T> APIResponse<T> get(ErrorCodeEnum errorCode, T data) {
		return new APIResponse<>(errorCode, data);
	}
}