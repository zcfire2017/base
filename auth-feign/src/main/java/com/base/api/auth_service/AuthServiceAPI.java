package com.base.api.auth_service;

import com.base.config.AuthServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 鉴权服务 服务API
 */
@FeignClient(name = "auth-service-api", url = "/api/v1/oauth2/", configuration = AuthServiceConfig.class)
public interface AuthServiceAPI {

	/**
	 * 处理 "/oauth2/token" 端点的 POST 请求。
	 * <p>
	 * 此端点通常用于 OAuth2 流程中获取访问令牌。
	 *
	 * @param authorization Bearer token
	 * @return 获取到的令牌字信息
	 */
	@PostMapping(value = "token")
	String getToken(@RequestBody String authorization);
}