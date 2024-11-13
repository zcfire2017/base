package com.base.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


/**
 * 鉴权 Feign 配置
 */
public abstract class OAuth2FeignConfig {
	/**
	 * 鉴权码
	 */
	private String accessToken;

	/**
	 * 获取鉴权token
	 */
	protected abstract String getAuthToken();

	/**
	 * json 解析对象
	 */
	protected abstract ObjectMapper getObjectMapper();

	/**
	 * 请求拦截器配置
	 *
	 * @return 拦截器
	 */
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/json");
			if (accessToken.isNotNullOrEmpty()) {
				requestTemplate.header("Authorization", "Bearer " + accessToken);
			}
		};
	}

	/**
	 * 解码器配置
	 *
	 * @return 解码器
	 */
	@Bean
	public Decoder decoder() {
		return (response, type) -> {
			var status = HttpStatus.valueOf(response.status());
			//成功，去掉APIResponse
			if (status == HttpStatus.OK) {
				var apiResponse = getObjectMapper().readTree(response.body().asInputStream());
				if (apiResponse.has("result")) {
					return apiResponse.get("result").toString().jsonDes(new TypeReference<>() {});
				}
			}
			return response;
		};
	}

	/**
	 * 错误解码器配置
	 *
	 * @return 解码器配置
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			var status = HttpStatus.valueOf(response.status());
			//鉴权401
			if (status == HttpStatus.UNAUTHORIZED) {
				//避免重复执行
				synchronized (this) {
					//获取鉴权码
					accessToken = getAuthToken();
					//鉴权码不为空
					if (accessToken.isNotNullOrEmpty()) {
						//返回重试异常，触发请求重试
						return new RetryableException(response.status(), "token失效，重新获取...",
								response.request().httpMethod(), 200L, response.request());
					}
				}
			}
			return new Exception(methodKey);
		};
	}

	/**
	 * 请求重试配置
	 *
	 * @return 重试信息
	 */
	@Bean
	public Retryer retryer() {
		return new Retryer.Default();
	}
}