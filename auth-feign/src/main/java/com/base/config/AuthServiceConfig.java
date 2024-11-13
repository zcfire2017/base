package com.base.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Feign 鉴权配置
 */
public class AuthServiceConfig {
	/**
	 * 配置鉴权服务器API 请求地址
	 *
	 * @return 服务器地址API
	 */
	@Bean
	public RequestInterceptor authRequestInterceptor() {
		//获取鉴权地址
		String authPath = "http://127.0.0.1:30120";
		return tmp -> {
			//默认请求连接
			var path = tmp.feignTarget().url()
					.replace("http://", "")
					.replace("https://", "");
			tmp.target(authPath + path);
		};
	}

}