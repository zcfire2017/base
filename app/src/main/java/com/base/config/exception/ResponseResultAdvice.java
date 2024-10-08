package com.base.config.exception;

import com.base.entity.response.APIResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回结果统一封装
 */
@RestControllerAdvice
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

	/**
	 * 是否支持包装
	 *
	 * @param returnType    返回类型
	 * @param converterType 转换类型
	 * @return 是否支持
	 */
	@Override
	public boolean supports(@NotNull MethodParameter returnType, @Nullable Class converterType) {
		return !returnType.hasMethodAnnotation(IgnoreResponse.class);
	}

	/**
	 * 包装返回结果
	 *
	 * @param body                  返回体
	 * @param returnType            返回类型
	 * @param selectedContentType   选择的类型
	 * @param selectedConverterType 选择的转换类型
	 * @param request               请求体
	 * @param response              响应体
	 * @return 包装后的返回体
	 */
	@Override
	public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType,
	                              @Nullable Class selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
		if (response != null)
		//设置返回头
		{
			response.getHeaders().set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		}

		//如果已经被包装了，就不进行包装
		if (body instanceof APIResponse<?>) {
			return body;
		}
		//String因为内置转换问题，需要手动转为json
		if (body instanceof String) {
			return APIResponse.success(body).json();
		}
		return APIResponse.success(body);
	}
}