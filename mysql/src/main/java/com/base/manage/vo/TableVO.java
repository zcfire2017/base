package com.base.manage.vo;

import com.base.constant.SqlConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 分表信息 值类型
 * 表名和分表格式
 * 默认按天分表
 */
@Getter
@Setter
@AllArgsConstructor
public class TableVO {
	/**
	 * 数据库名称
	 */
	private String databaseName;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 分表格式
	 */
	private String format = SqlConst.formatDay;

	/**
	 * 分表信息
	 *
	 * @param tableName 表名
	 */
	public TableVO(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 创建分表信息
	 *
	 * @param tableName 表名
	 * @return 分表信息
	 */
	public static TableVO create(String tableName) {
		return new TableVO(tableName);
	}

	/**
	 * 创建分表信息
	 *
	 * @param databaseName 数据库名称
	 * @param tableName    表名
	 * @param format       分表格式
	 * @return 分表信息
	 */
	public static TableVO create(String databaseName, String tableName, String format) {
		return new TableVO(databaseName, tableName, format);
	}

	/**
	 * 创建分表信息
	 *
	 * @param databaseName 数据库名称
	 * @param tableName    表名
	 * @return 分表信息
	 */
	public static TableVO create(String databaseName, String tableName) {
		return new TableVO(databaseName, tableName, SqlConst.formatDay);
	}
}