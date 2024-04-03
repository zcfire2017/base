package com.base.manage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.base.config.SettingConfig;
import com.base.constant.SqlConst;
import com.base.manage.vo.TableVO;
import com.base.service.ICommonService;
import com.base.tools.log.LogHelper;
import com.base.tools.string.StringBuilderUtils;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 表管理 抽象类
 */
public abstract class TableManager {
	//region 注入

	/**
	 * 通用Service
	 */
	@Resource(name = "ICommonService")
	private ICommonService commonService;

	/**
	 * 配置文件
	 */
	@Resource
	private SettingConfig settingConfig;

	//endregion

	//region 属性

	/**
	 * 表名集合
	 */
	private final ConcurrentSkipListSet<String> tableNames = new ConcurrentSkipListSet<>();

	/**
	 * 获取数据库表名sql
	 */
	private final String getTableSql = "select table_name from information_schema.tables where table_schema = '%s';";

	/**
	 * 创建表sql
	 */
	private final String createSql = "create table if not exists %s like %s.%s;";

	/**
	 * 复制表sql
	 */
	private final String copySql = "create table if not exists %s select * from %s;";

	/**
	 * 重置自增sql
	 */
	private final String autoIncrement = "alter table %s auto_increment = 1;";

	//endregion

	//region 操作

	/**
	 * 创建分表
	 *
	 * @param table     表信息
	 * @param splitName 分表名
	 */
	private void createSplitTable(TableVO table, String splitName) {
		//创建表格语句
		var sql = String.format(createSql, getSplitDBTableName(splitName), table.getDatabaseName(), table.getTableName());
		this.commonService.execute(sql);
		//添加到内存
		this.tableNames.add(splitName);
	}

	/**
	 * 创建分表
	 *
	 * @param tableName  母表名
	 * @param splitNames 分表名集合
	 */
	private void createSplitTable(String tableName, Collection<String> splitNames) {
		//创建表格语句
		var sql = new StringBuilderUtils();
		for (var splitName : splitNames) {
			sql.appendFormat(createSql, getSplitDBTableName(splitName), tableName);
		}
		this.commonService.execute(sql);
		//添加到内存
		this.tableNames.addAll(splitNames);
	}

	/**
	 * 重置自增
	 *
	 * @param tableName 表名
	 */
	public void autoIncrement(String tableName) {
		//重置语句
		var sql = String.format(autoIncrement, getSplitDBTableName(tableName));
		this.commonService.execute(sql);
	}

	/**
	 * 重置自增
	 *
	 * @param tableNames 表名集合
	 */
	public void autoIncrement(Collection<String> tableNames) {
		//重置语句
		var sql = new StringBuilderUtils();
		for (var tableName : tableNames) {
			sql.appendFormat(autoIncrement, getSplitDBTableName(tableName));
		}
		this.commonService.execute(sql);
	}

	//region 截断表

	/**
	 * 截断表
	 *
	 * @param tableName 表名
	 * @param dbName    数据库名 本库直接传null
	 */
	private void truncateTable(String tableName, String dbName) {
		//表名不能为空
		if (StringUtils.isBlank(tableName))
			return;

		try {
			//加上数据库名
			String fullName = tableName;
			if (StringUtils.isNotBlank(dbName))
				fullName = String.format("%s.%s", dbName, fullName);

			//执行清空 截断表
			String sql = String.format("truncate table %s", fullName);
			this.commonService.execute(sql);
		} catch (Exception e) {
			LogHelper.error(e);
		}
	}

	/**
	 * 截断表集合
	 *
	 * @param tableNames 表名集合
	 * @param dbName     数据库名 本库直接传null
	 */
	private void truncateTable(Collection<String> tableNames, String dbName) {
		//表名不能为空
		if (tableNames.isEmpty())
			return;

		try {
			//执行sql
			var sql = new StringBuilderUtils();
			for (var tbName : tableNames) {
				//加上数据库名
				String fullName = tbName;
				if (StringUtils.isNotBlank(dbName))
					fullName = String.format("%s.%s", dbName, fullName);

				//执行清空 截断表
				sql.appendFormat("truncate table %s;", fullName);
			}
			this.commonService.execute(sql);
		} catch (Exception e) {
			LogHelper.error(e);
		}
	}

	/**
	 * 截断表
	 *
	 * @param tableName 表名
	 */
	public void truncate(String tableName) {
		this.truncateTable(tableName, null);
	}

	/**
	 * 截断表
	 *
	 * @param tableName 表名
	 * @param dbName    数据库名
	 */
	public void truncate(String tableName, String dbName) {
		this.truncateTable(tableName, dbName);
	}

	/**
	 * 截断表集合
	 *
	 * @param tableNames 表名集合
	 */
	public void truncate(Collection<String> tableNames) {
		this.truncateTable(tableNames, null);
	}

	/**
	 * 截断表集合
	 *
	 * @param tableNames 表名集合
	 * @param dbName     数据库名
	 */
	public void truncate(Collection<String> tableNames, String dbName) {
		this.truncateTable(tableNames, dbName);
	}

	//endregion

	//region 复制表

	/**
	 * 复制表结构和数据
	 *
	 * @param databaseName     要复制的数据库名
	 * @param tableName        要复制的表名
	 * @param copyDatabaseName 复制到数据库名称
	 * @param copyTableName    复制到表名
	 * @return 是否成功
	 */
	public Boolean copy(String databaseName, String tableName, String copyDatabaseName, String copyTableName) {
		try {
			//数据库名不为空
			if (StringUtils.isNotBlank(databaseName))
				tableName = String.format("%s.%s", databaseName, tableName);
			//数据库名不为空
			if (StringUtils.isNotBlank(copyDatabaseName))
				copyTableName = String.format("%s.%s", copyDatabaseName, copyTableName);
			//复制语句
			var sql = String.format(copySql, copyTableName, tableName);
			//执行复制
			this.commonService.execute(sql);
			return true;
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return false;
	}

	/**
	 * 复制表结构和数据（主库 -> 从库）
	 *
	 * @param tableName        要复制的表名
	 * @param copyDatabaseName 复制到数据库名称
	 * @param copyTableName    复制到表名
	 * @return 是否成功
	 */
	public Boolean copy(String tableName, String copyDatabaseName, String copyTableName) {
		return this.copy("", tableName, copyDatabaseName, copyTableName);
	}

	/**
	 * 复制表结构和数据（主库 -> 主库）
	 *
	 * @param tableName     要复制的表名
	 * @param copyTableName 复制到表名
	 * @return 是否成功
	 */
	public Boolean copy(String tableName, String copyTableName) {
		return this.copy("", tableName, "", copyTableName);
	}

	/**
	 * 复制表结构和数据（从库 -> 从库）
	 *
	 * @param tableName     要复制的表名
	 * @param copyTableName 复制到表名
	 * @return 是否成功
	 */
	public Boolean copySplit(String tableName, String copyTableName) {
		return this.copy(getDbName(), tableName, getDbName(), copyTableName);
	}

	//endregion

	//endregion

	//region 查询

	//region 通用

	/**
	 * 获取扩展库名称
	 *
	 * @return 扩展库名称
	 */
	public String getDbName() {
		return this.settingConfig.getDatabaseName();
	}

	/**
	 * 获取分库所有表名
	 */
	protected void getAllSplitTableName() {
		//查询语句
		var sql = String.format(getTableSql, getDbName());
		var dataList = this.commonService.getStringList(sql);
		//添加到表名集合
		this.tableNames.addAll(dataList);
	}

	/**
	 * 获取分库的分表名（db.table）
	 *
	 * @param splitName 分表名
	 * @return 分库的分表名
	 */
	private String getSplitDBTableName(String splitName) {
		return String.format("%s.%s", getDbName(), splitName);
	}

	/**
	 * 获取分表名
	 *
	 * @param table 表信息
	 * @param date  日期（为空：默认今日）
	 * @return 分表名
	 */
	private String getSplitTableName(TableVO table, LocalDate date) {
		//表名分表格式，默认为每天
		var format = table.getFormat();
		if (StringUtils.isBlank(table.getFormat()))
			format = SqlConst.formatDay;
		//日期默认今日
		if (date == null)
			date = LocalDate.now();
		return String.format("%s%s", table.getTableName(), date.format(DateTimeFormatter.ofPattern(format)));
	}

	/**
	 * 是否存在表
	 *
	 * @param tableName 表名
	 * @return 是否存在
	 */
	private Boolean has(String tableName) {
		return this.tableNames.contains(tableName);
	}

	/**
	 * 根据格式添加日期
	 *
	 * @param date   当前日期
	 * @param format 日期格式
	 * @return 处理后日期
	 */
	private LocalDate addDate(LocalDate date, String format) {
		return switch (format) {
			case SqlConst.formatMonth -> {
				var formatMonthDate = date.plusMonths(1);
				yield LocalDate.of(formatMonthDate.getYear(), formatMonthDate.getMonth(), 1);
			}
			case SqlConst.formatYear -> {
				var formatYearDate = date.plusYears(1);
				yield LocalDate.of(formatYearDate.getYear(), 1, 1);
			}
			default -> date.plusDays(1);
		};
	}

	//endregion

	//region 获取单表

	/**
	 * 获取或新增分表名
	 *
	 * @param table 表信息
	 * @param date  日期（为空：默认今日）
	 * @param isAdd 是否新增
	 * @return 分库.分表名
	 */
	public String getAndAdd(@NotNull TableVO table, LocalDate date, @NotNull Boolean isAdd) {
		//分表名
		var splitName = this.getSplitTableName(table, date);
		if (isAdd && !this.has(splitName))
			this.createSplitTable(table, splitName);
		return getSplitDBTableName(splitName);
	}

	/**
	 * 获取并且添加分表名
	 *
	 * @param table 表信息
	 * @param date  日期（为空：默认今日）
	 * @return 分库.分表名
	 */
	public String getAndAdd(@NotNull TableVO table, @NotNull LocalDate date) {
		return getAndAdd(table, date, true);
	}

	/**
	 * 获取并且添加分表名
	 *
	 * @param table 表信息
	 * @return 分库.分表名
	 */
	public String getAndAdd(@NotNull TableVO table) {
		return getAndAdd(table, null, true);
	}

	/**
	 * 获取分表名
	 *
	 * @param table  表信息
	 * @param date   日期（为空：默认今日）
	 * @param format 分表格式
	 * @return 分库.分表名
	 */
	public String get(@NotNull TableVO table, LocalDate date, String format) {
		return getAndAdd(table, date, false);
	}

	/**
	 * 获取分表名
	 *
	 * @param table 表信息
	 * @param date  日期（为空：默认今日）
	 * @return 分库.分表名
	 */
	public String get(@NotNull TableVO table, LocalDate date) {
		return getAndAdd(table, date, false);
	}

	/**
	 * 获取今日分表名
	 *
	 * @param table 表信息
	 * @return 分库.分表名
	 */
	public String get(@NotNull TableVO table) {
		return getAndAdd(table, null, false);
	}

	//endregion

	//region 获取多表

	/**
	 * 获取分表集合
	 *
	 * @param table 表信息
	 * @param begin 开始日期
	 * @param end   结束日期
	 * @return 分库.分表集合
	 */
	public List<String> getList(@NotNull TableVO table, LocalDate begin, LocalDate end) {
		//返回对象
		var result = new ArrayList<String>();

		//返回所有分表
		if (begin == null && end == null)
			return getList(table);

		//开始日期
		if (begin == null)
			throw new RuntimeException("开始日期不能为空");
		//结束日期
		if (end == null)
			end = LocalDate.now();

		//循环日期
		for (var date = begin; !date.isAfter(end); date = addDate(date, table.getFormat())) {
			//分表名
			var splitName = this.getSplitTableName(table, date);
			//添加分表
			if (this.has(splitName))
				result.add(getSplitDBTableName(splitName));
		}
		return result;
	}

	/**
	 * 获取分表集合
	 *
	 * @param table 表信息
	 * @param begin 开始日期
	 * @param end   结束日期
	 * @return 分库.分表集合
	 */
	public List<String> getList(@NotNull TableVO table, LocalDateTime begin, LocalDateTime end) {
		return getList(table, begin.toLocalDate(), end.toLocalDate());
	}

	/**
	 * 获取分表名称集合
	 *
	 * @param table 表信息
	 * @return 表名集合
	 */
	public List<String> getList(@NotNull TableVO table) {
		var tbNames = tableNames.stream().filter(t -> t.contains(table.getTableName())).toList();
		return tbNames.stream().map(this::getSplitDBTableName).toList();
	}

	/**
	 * 获取分表名称集合
	 *
	 * @param table 表信息
	 * @param begin 开始日期
	 * @return 表名集合
	 */
	public List<String> getList(@NotNull TableVO table, @NotNull LocalDate begin) {
		return getList(table, begin, null);
	}

	//endregion

	//endregion
}