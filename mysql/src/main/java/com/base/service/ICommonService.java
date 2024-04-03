package com.base.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.constant.SqlConst;
import com.base.mapper.CommonMapper;
import com.base.tools.log.LogHelper;
import com.base.tools.string.StringBuilderUtils;
import com.base.tools.string.StringUtilsEx;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用Service 不包含泛型实体
 */
public interface ICommonService {
	/**
	 * 获取Mapper对象
	 *
	 * @return Mapper
	 */
	CommonMapper getCommonMapper();

	//region 执行sql语句

	/**
	 * 执行sql语句
	 *
	 * @param sql sql语句
	 * @return 受影响行数
	 */
	default Integer execute(String sql) {
		return this.execute(sql, null);
	}

	/**
	 * 执行sql语句
	 *
	 * @param sql sql语句
	 * @return 受影响行数
	 */
	default Integer execute(StringBuilderUtils sql) {
		return this.execute(sql.toString(), null);
	}

	/**
	 * 执行sql语句
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return 受影响行数
	 */
	default Integer execute(StringBuilderUtils sql, Map<String, Object> map) {
		return this.execute(sql.toString(), map);
	}

	/**
	 * 执行sql语句
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return 受影响行数
	 */
	default Integer execute(String sql, Map<String, Object> map) {
		//参数集合
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().execute(params);
	}

	//endregion

	//region 增删改

	/**
	 * 执行新增语句
	 *
	 * @param sql sql语句
	 * @return 自增长ID
	 */
	default Integer add(String sql) {
		return this.add(sql, null);
	}

	/**
	 * 执行新增语句
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return 自增长ID
	 */
	default Integer add(String sql, Map<String, Object> map) {
		//去掉前后空格
		var idSql = sql.strip();
		//添加分号结尾
		if (!idSql.endsWith(";"))
			idSql = idSql + ";";
		//拼接受影响行数结构
		idSql = idSql + " select last_insert_id(); ";
		//查询参数集合
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, idSql);
		if (map != null)
			params.putAll(map);
		//执行语句
		return this.getCommonMapper().selectInt(params);
	}

	/**
	 * 新增
	 *
	 * @param entity 实体对象
	 * @param <T>    对象类型
	 * @return 返回id
	 */
	default <T> Integer addWithId(T entity) {
		return addWithId(entity, "", "");
	}

	/**
	 * 新增
	 *
	 * @param entity 实体对象
	 * @param dbName 数据库名称
	 * @param tbName 表名称
	 * @param <T>    对象类型
	 * @return 返回id
	 */
	default <T> Integer addWithId(T entity, String dbName, String tbName) {
		Integer result = 0;

		try {
			Class<?> clazz = entity.getClass();
			//找到需要添加的字段。不包括自增主键
			Map<String, Field> fieldMap = new HashMap<>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				TableField annotation = field.getAnnotation(TableField.class);
				if (annotation != null && !StringUtils.isBlank(annotation.value()) && annotation.exist()) {
					field.setAccessible(true);
					fieldMap.put(annotation.value(), field);
				}
				else {
					TableId tableIdAnnotation = field.getAnnotation(TableId.class);
					if (tableIdAnnotation != null && !StringUtils.isBlank(tableIdAnnotation.value()) && tableIdAnnotation.type() == IdType.NONE) {
						field.setAccessible(true);
						fieldMap.put(tableIdAnnotation.value(), field);
					}
				}
			}
			if (fieldMap.size() <= 0)
				return result;

			String tableName = tbName;
			//region 表名

			if (StringUtils.isBlank(tableName)) {
				TableName annotation = clazz.getAnnotation(TableName.class);
				if (annotation != null || !StringUtils.isBlank(annotation.value()))
					tableName = annotation.value();
			}
			if (StringUtils.isBlank(tableName)) {
				tableName = clazz.getSimpleName();
			}
			tableName = StringUtilsEx.toBackTick(tableName);
			if (!StringUtils.isBlank(dbName)) {
				dbName = StringUtilsEx.toBackTick(dbName);
				tableName = MessageFormat.format("{0}.{1}", dbName, tableName);
			}

			//endregion

			String insertSqlColumn = MessageFormat.format("insert into {0} ({1})", tableName,
					StringUtils.join(fieldMap.keySet().stream().map(StringUtilsEx::toBackTick).collect(Collectors.toList()), ","));
			//参数列表
			Map<String, Object> parameters = new HashMap<>();
			List<String> entityTermList = new ArrayList<>();
			for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
				entityTermList.add(String.format(" #{%s%s} ", entry.getKey(), 0));
				parameters.put(String.format("%s%s", entry.getKey(), 0), entry.getValue().get(entity));
			}
			String term = MessageFormat.format("({0}) ", StringUtils.join(entityTermList, ","));

			StringBuilderUtils sbSql = new StringBuilderUtils();
			sbSql.appendLine(insertSqlColumn);
			sbSql.appendLine("values");
			sbSql.appendLine(term);
			result = this.execute(sbSql.toString(), parameters);
		} catch (IllegalAccessException e) {
			LogHelper.error(e);
		}
		return result;
	}

	/**
	 * 批量新增
	 *
	 * @param entityList 数据集合
	 * @param tbName     表名称
	 * @return 返回受影响行数
	 */
	default <T> Integer add(List<T> entityList, String tbName) {
		int result = 0;
		if (entityList.isEmpty())
			return result;

		Class<?> clazz = entityList.get(0).getClass();
		//找到需要添加的字段。不包括自增主键
		Map<String, Field> fieldMap = new HashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			TableField annotation = field.getAnnotation(TableField.class);
			if (annotation != null && !StringUtils.isBlank(annotation.value()) && annotation.exist()) {
				field.setAccessible(true);
				fieldMap.put(annotation.value(), field);
			}
			else {
				TableId tableIdAnnotation = field.getAnnotation(TableId.class);
				if (tableIdAnnotation != null && !StringUtils.isBlank(tableIdAnnotation.value()) && tableIdAnnotation.type() == IdType.NONE) {
					field.setAccessible(true);
					fieldMap.put(tableIdAnnotation.value(), field);
				}
			}
		}
		if (fieldMap.isEmpty())
			return result;

		String insertSqlColumn = MessageFormat.format("insert into {0} ({1})", tbName,
				StringUtils.join(fieldMap.keySet().stream().map(StringUtilsEx::toBackTick).collect(Collectors.toList()), ","));
		//分页
		int pageSize = 5000;
		int pageCount = (int) Math.ceil(entityList.size() * 1.0 / pageSize);
		for (int page = 1; page <= pageCount; page++) {
			try {
				//每页数据
				List<T> pageData = entityList.stream().skip((long) (page - 1) * pageSize).limit(pageSize).toList();
				//sql语句 片段
				List<String> termList = new ArrayList<>();
				//参数列表
				Map<String, Object> parameters = new HashMap<>();
				int index = 0;
				int count = pageData.size();
				for (T entity : pageData) {
					List<String> entityTermList = new ArrayList<>();
					for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
						entityTermList.add(String.format(" #{%s%s} ", entry.getKey(), index));
						parameters.put(String.format("%s%s", entry.getKey(), index), entry.getValue().get(entity));
					}
					termList.add(MessageFormat.format("({0})", StringUtils.join(entityTermList, ",")));
					index++;
				}
				StringBuilderUtils sbSql = new StringBuilderUtils();
				sbSql.appendMessageFormatLine(insertSqlColumn);
				sbSql.appendMessageFormatLine("values");
				sbSql.appendMessageFormatLine(StringUtils.join(termList, "," + System.getProperty("line.separator")));
				int pageResult = this.execute(sbSql.toString(), parameters);
				result += pageResult;
			} catch (Exception e) {
				LogHelper.error(e);
			}
		}
		return result;
	}

	/**
	 * 新增或更新实体
	 *
	 * @param tableName 指定表名
	 * @param entity    实体类信息
	 * @param <T>       实体类型
	 * @return 0或者自增长ID
	 */
	default <T> Integer addOrUpdate(String tableName, T entity) {
		//返回值
		Integer result = 0;
		//空值验证
		if (entity == null) return result;
		try {
			//保存对象
			Class<?> aClass = entity.getClass();

			//实体对应字段
			Map<String, Object> fieldMap = new LinkedHashMap<>();
			//字段集合
			Field[] fields = aClass.getDeclaredFields();
			//实体主键
			String main = "";
			//是否有自增键
			boolean isAuto = false;
			//循环字段，获取数据库对应字段
			for (Field field : fields) {
				//设置字段可以取值
				field.setAccessible(true);
				//字段值
				Object fieldValue = field.get(entity);
				//主键
				TableId tableId = field.getAnnotation(TableId.class);
				//有主键注解
				if (tableId != null && StringUtils.isNotBlank(tableId.value())) {
					main = tableId.value();
					fieldMap.put(tableId.value(), fieldValue);
					if (tableId.type() == IdType.AUTO)
						isAuto = true;
					continue;
				}
				//跳过空值字段
				if (fieldValue == null) continue;
				//字段注解
				TableField annotation = field.getAnnotation(TableField.class);
				//注解存在，并且需要映射数据库字段，则使用注解名称
				if (annotation != null) {
					if (annotation.exist() && StringUtils.isNotBlank(annotation.value())) {
						fieldMap.put(annotation.value(), fieldValue);
					}
				}
				else {
					//没有注解，使用字段名
					fieldMap.put(field.getName(), fieldValue);
				}
			}
			//无字段
			if (fieldMap.isEmpty()) return result;

			//参数集合
			Map<String, Object> parameterList = new LinkedHashMap<>();
			//执行语句
			StringBuilderUtils sql = new StringBuilderUtils();
			//这句sql是防止自增长ID过大和不连续，因为这种更新方式会自动增长ID，导致ID不连贯，并且ID会很大。
			//原理是在执行sql之前设置表格的自动增长值为1，当mysql执行下次插入时，会自动检测AUTO_INCREMENT是否合法，若不合法，就会自动将AUTO_INCREMENT设置为当前表max(id)+1
			//System.out.printf("[更新]表格【%s】,主键【%s】,自增长【%s】%n", tbName, main, isAuto);
			//有自增长列
			if (isAuto)
				sql.appendFormatLine("alter table %s AUTO_INCREMENT = 1;", tableName);
			sql.appendFormat("insert into %s (%s) values(", tableName,
					StringUtils.join(fieldMap.keySet().stream().map(StringUtilsEx::toBackTick).collect(Collectors.toList()), ","));
			//数据库参数字段集合
			List<String> columnList = new ArrayList<String>();
			//更新字段集合
			List<String> updateColumn = new ArrayList<String>();
			//循环字段
			for (Map.Entry<String, Object> file : fieldMap.entrySet()) {
				//加入字段集合
				columnList.add(String.format("#{%s}", file.getKey()));
				//写入参数集合
				parameterList.put(String.format("%s", file.getKey()), file.getValue());
				if (!file.getKey().equals(main))
					updateColumn.add(MessageFormat.format("{0}=values({0})", StringUtilsEx.toBackTick(file.getKey())));
			}
			//拼接更新字段
			sql.appendFormat("%s) on duplicate key update %s;", StringUtils.join(columnList, ","), StringUtils.join(updateColumn, ","));
			//获取自增长ID，只有新增成功才会返回，其它都为0
			result = this.execute(sql.toString(), parameterList);
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return result;
	}

	/**
	 * 新增或更新
	 *
	 * @param entityList 实体类集合
	 * @param <T>        实体类型
	 * @return 受影响行数
	 */
	default <T> Integer addOrUpdate(List<T> entityList) {
		return this.addOrUpdate("", entityList);
	}

	/**
	 * 新增或更新
	 *
	 * @param tableName  指定表名
	 * @param entityList 实体类集合信息
	 * @param <T>        实体类型
	 * @return 受影响行数
	 */
	default <T> Integer addOrUpdate(String tableName, List<T> entityList) {
		//返回值
		int result = 0;
		//空值验证
		if (entityList == null || entityList.isEmpty())
			return result;
		try {
			//保存对象
			Class<?> classt = entityList.get(0).getClass();
			//表名
			String tbName = tableName;
			//优先传入表名，主要用于分表
			if (StringUtils.isBlank(tbName)) {
				//表名注解
				TableName tableAnnotation = classt.getAnnotation(TableName.class);
				//有表名注解，并且不为空
				if (tableAnnotation != null && StringUtils.isNotBlank(tableAnnotation.value())) {
					tbName = tableAnnotation.value();
				}
				else {
					//如果没有注解也没传入表名，则默认用实体名
					tbName = classt.getSimpleName();
				}
			}
			tbName = StringUtilsEx.toBackTick(tbName);

			//实体对应字段
			var fieldMap = new LinkedHashMap<String, Field>();
			//字段集合
			Field[] fields = classt.getDeclaredFields();
			//实体主键
			String main = "";
			//是否有自增键
			boolean isAuto = false;
			//循环字段，获取数据库对应字段
			for (Field field : fields) {
				//设置字段可以取值
				field.setAccessible(true);
				//主键
				TableId tableId = field.getAnnotation(TableId.class);
				//有主键注解
				if (tableId != null && StringUtils.isNotBlank(tableId.value())) {
					main = tableId.value();

					if (tableId.type() == IdType.AUTO) {
						isAuto = true;
					}
					else {
						fieldMap.put(tableId.value(), field);
					}
					continue;
				}
				//字段注解
				TableField annotation = field.getAnnotation(TableField.class);
				//注解存在，并且需要映射数据库字段，则使用注解名称
				if (annotation != null) {
					if (annotation.exist() && StringUtils.isNotBlank(annotation.value())) {
						fieldMap.put(annotation.value(), field);
					}
				}
				else {
					//没有注解，使用字段名
					fieldMap.put(field.getName(), field);
				}
			}
			//无字段
			if (fieldMap.size() <= 0) return result;

			//参数集合
			var parameterList = new LinkedHashMap<String, Object>();
			//执行语句
			StringBuilderUtils sql = new StringBuilderUtils();
			//这句sql是防止自增长ID过大和不连续，因为这种更新方式会自动增长ID，导致ID不连贯，并且ID会很大。
			//原理是在执行sql之前设置表格的自动增长值为1，当mysql执行下次插入时，会自动检测AUTO_INCREMENT是否合法，若不合法，就会自动将AUTO_INCREMENT设置为当前表max(id)+1
			//System.out.printf("[更新]表格【%s】,主键【%s】,自增长【%s】%n", tbName, main, isAuto);
			//有自增长列
			if (isAuto)
				sql.appendFormatLine("alter table %s AUTO_INCREMENT = 1;", tbName);
			sql.appendFormat("insert into %s (%s) values(", tbName,
					StringUtils.join(fieldMap.keySet().stream().map(StringUtilsEx::toBackTick).collect(Collectors.toList()), ","));

			//循环实体集合
			for (T entity : entityList) {
				//数据库参数字段集合
				var columnList = new ArrayList<String>();
				//下标
				int index = entityList.indexOf(entity);
				if (index > 0) sql.append(",(");
				//循环字段
				for (Map.Entry<String, Field> file : fieldMap.entrySet()) {
					//加入字段集合
					columnList.add(String.format("#{%s%d}", file.getKey(), index));
					//写入参数集合
					parameterList.put(String.format("%s%d", file.getKey(), index), file.getValue().get(entity));
				}
				sql.appendFormat("%s)", StringUtils.join(columnList, ","));
			}
			//更新字段集合
			var updateColumn = new ArrayList<String>();
			//循环更新字段
			for (Map.Entry<String, Field> file : fieldMap.entrySet()) {
				if (!file.getKey().equals(main))
					updateColumn.add(MessageFormat.format("{0}=values({0})", StringUtilsEx.toBackTick(file.getKey())));
			}
			//拼接字段
			sql.appendFormat(" on duplicate key update %s;", StringUtils.join(updateColumn, ","));
			//执行语句
			result += this.execute(sql.toString(), parameterList);
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return result;
	}

	//endregion

	//region 执行查询语句

	//region 单个值

	/**
	 * 执行查询语句 返回单个值
	 *
	 * @param sql   sql语句
	 * @param clazz 返回类型
	 * @param <T>   返回类型
	 * @return 返回单个值
	 */
	default <T> T get(String sql, Class<T> clazz) {
		return get(sql, null, clazz);
	}

	/**
	 * 执行查询语句 返回单个值
	 *
	 * @param sql   sql语句
	 * @param map   参数集合
	 * @param clazz 返回类型
	 * @param <T>   返回类型
	 * @return 返回单个值
	 */
	default <T> T get(String sql, Map<String, Object> map, Class<T> clazz) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		var val = this.getCommonMapper().selectMap(params);
		return Convert.convert(clazz, val);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return int
	 */
	default Integer getInt(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectInt(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return int
	 */
	default Integer getInt(String sql) {
		return getInt(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return boolean
	 */
	default Boolean getBool(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectBool(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return boolean
	 */
	default Boolean getBool(String sql) {
		return getBool(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return long
	 */
	default Long getLong(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectLong(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return long
	 */
	default Long getLong(String sql) {
		return getLong(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return double
	 */
	default Double getDouble(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDouble(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return double
	 */
	default Double getDouble(String sql) {
		return getDouble(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return BigDecimal
	 */
	default BigDecimal getDecimal(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDecimal(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return BigDecimal
	 */
	default BigDecimal getDecimal(String sql) {
		return getDecimal(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return short
	 */
	default Short getShort(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectShort(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return short
	 */
	default Short getShort(String sql) {
		return getShort(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalDateTime
	 */
	default LocalDateTime getDateTime(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDateTime(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return LocalDateTime
	 */
	default LocalDateTime getDateTime(String sql) {
		return getDateTime(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalDate
	 */
	default LocalDate getDate(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDate(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return LocalDate
	 */
	default LocalDate getDate(String sql) {
		return getDate(sql, null);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalTime
	 */
	default LocalTime getTime(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectTime(params);
	}

	/**
	 * 获取单个值
	 *
	 * @param sql sql语句
	 * @return LocalTime
	 */
	default LocalTime getTime(String sql) {
		return getTime(sql, null);
	}

	//endregion

	//region 多个值

	/**
	 * 获取实体集合
	 *
	 * @param sql   sql语句
	 * @param map   参数集合
	 * @param clazz 实体类
	 * @param <T>   实体对象
	 * @return 实体集合
	 */
	default <T> List<T> getList(String sql, Map<String, Object> map, Class<T> clazz) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		var result = this.getCommonMapper().selectMapList(params);
		return Convert.toList(clazz, result);
	}

	/**
	 * 获取实体集合
	 *
	 * @param sql   sql语句
	 * @param clazz 实体类
	 * @param <T>   实体对象
	 * @return 实体集合
	 */
	default <T> List<T> getList(String sql, Class<T> clazz) {
		return getList(sql, null, clazz);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Integer 集合
	 */
	default List<Integer> getIntList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectIntList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Integer 集合
	 */
	default List<Integer> getIntList(String sql) {
		return getIntList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Integer 集合
	 */
	default List<String> getStringList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectStringList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Integer 集合
	 */
	default List<String> getStringList(String sql) {
		return getStringList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Long 集合
	 */
	default List<Long> getLongList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectLongList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Long 集合
	 */
	default List<Long> getLongList(String sql) {
		return getLongList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Boolean 集合
	 */
	default List<Boolean> getBoolList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectBoolList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Boolean 集合
	 */
	default List<Boolean> getBoolList(String sql) {
		return getBoolList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Short集合
	 */
	default List<Byte> getByteList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectByteList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Short集合
	 */
	default List<Byte> getByteList(String sql) {
		return getByteList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Short集合
	 */
	default List<Short> getShortList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectShortList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Short集合
	 */
	default List<Short> getShortList(String sql) {
		return getShortList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return Double集合
	 */
	default List<Double> getDoubleList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDoubleList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return Double集合
	 */
	default List<Double> getDoubleList(String sql) {
		return getDoubleList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return BigDecimal集合
	 */
	default List<BigDecimal> getDecimalList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDecimeList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return BigDecimal集合
	 */
	default List<BigDecimal> getDecimalList(String sql) {
		return getDecimalList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalDateTime集合
	 */
	default List<LocalDateTime> getDateTimeList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDateTimeList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return LocalDateTime集合
	 */
	default List<LocalDateTime> getDateTimeList(String sql) {
		return getDateTimeList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalDate集合
	 */
	default List<LocalDate> getDateList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectDateList(params);
	}


	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return LocalDate集合
	 */
	default List<LocalDate> getDateList(String sql) {
		return getDateList(sql, null);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @param map 参数集合
	 * @return LocalTime集合
	 */
	default List<LocalTime> getTimeList(String sql, Map<String, Object> map) {
		var params = new HashMap<String, Object>();
		params.put(SqlConst.sqlStr, sql);
		if (map != null)
			params.putAll(map);
		return this.getCommonMapper().selectTimeList(params);
	}

	/**
	 * 获取集合
	 *
	 * @param sql sql语句
	 * @return LocalTime集合
	 */
	default List<LocalTime> getTimeList(String sql) {
		return getTimeList(sql, null);
	}

	//endregion

	//endregion
}