package com.base.mybatisPlus.core;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 数据库连接
 */
@AllArgsConstructor
@NoArgsConstructor
public class DataSource {
	/**
	 * 连接IP
	 */
	public String ip;

	/**
	 * 端口
	 */
	public Integer port;

	/**
	 * 用户名
	 */
	public String userName;

	/**
	 * 密码
	 */
	public String password;

	/**
	 * 数据库名
	 */
	public String databaseName;

	/**
	 * 包名
	 */
	public String packageName;
}