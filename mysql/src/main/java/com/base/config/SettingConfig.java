package com.base.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置参数
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "settings")
public class SettingConfig {
	/**
	 * 数据库名称
	 */
	private String databaseName;

	/**
	 * 文件存储地址
	 */
	private String filePath;

	/**
	 * 是否使用调试模式
	 * 调试模式下使用本地服务地址
	 * 例如：门店获取工厂API
	 */
	private Boolean debug = false;
}