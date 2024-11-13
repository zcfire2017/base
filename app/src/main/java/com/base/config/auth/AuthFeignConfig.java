package com.base.config.auth;

import cn.hutool.core.codec.Base64;
import com.base.api.auth_service.AuthServiceAPI;
import com.base.config.OAuth2FeignConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.Getter;

/**
 * Feign 鉴权配置
 */
public class AuthFeignConfig extends OAuth2FeignConfig {
	/**
	 * 鉴权服务 服务API
	 */
	@Resource
	private AuthServiceAPI authServiceAPI;

	/**
	 * jackson 序列化器
	 */
	@Resource
	@Getter
	private ObjectMapper objectMapper;

	/**
	 * 获取鉴权码
	 *
	 * @return 鉴权码
	 */
	@Override
	protected String getAuthToken() {
		//账号密码加密
		var author = "Basic " + Base64.encode("test:123456");
		//获取token
		return authServiceAPI.getToken(author);
	}
}