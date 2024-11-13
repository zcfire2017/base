package com.base.config;

import com.base.tools.json.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot mvc 配置类
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	/**
	 * 开启跨域
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置允许跨域的路由
		registry.addMapping("/**")
				/*是否允许请求带有验证信息*/
				.allowCredentials(true)
				/*允许访问的客户端域名*/
				.allowedOriginPatterns("*")
				/*允许服务端访问的客户端请求头*/
				.allowedHeaders("*")
				/*允许访问的方法名,GET POST等*/
				.allowedMethods("*");
	}

	/**
	 * 注册Json映射器
	 *
	 * @return 自定义映射器
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return JsonHelper.mapper;
	}
}