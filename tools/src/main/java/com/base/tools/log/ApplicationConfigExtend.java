package com.base.tools.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 应用程序配置扩展
 */
//@Component
@Getter
@Setter
@NoArgsConstructor
public class ApplicationConfigExtend {
	/**
	 * 活动配置文件模式
	 */
	@Value("${spring.profiles.active}")
	private String active;
	/**
	 * 需要读取包含的配置文件
	 */
	//@Value("${spring.profiles.include}")
	private String include;
	/**
	 * tomcat端口
	 */
	@Value("${server.port}")
	private String port;

	/**
	 * 日志输出
	 */
	public void println() {
		LogHelper.info("active：" + active);
		LogHelper.info("include：" + include);
		LogHelper.info("port：" + port);
	}

}