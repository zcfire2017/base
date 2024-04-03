package com.base.mybatisPlus.query;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.base.constant.SqlConst;

import java.util.Map;


/**
 * sql语句提供类
 */
public class SqlProvider {

	/**
	 * 方法名
	 */
	public final static String getSql = "getSql";

	/**
	 * 获取sql语句
	 *
	 * @param parameters 查询参数
	 * @return 查询语句
	 * @throws Exception 错误信息
	 */
	public String getSql(Map<String, Object> parameters) throws Exception {
		if (!parameters.containsKey(SqlConst.sqlStr))
			throw new Exception("直接写Sql查询时，Map里面不包含提供Sql语句的关键字：" + SqlConst.sqlStr);
		Object obj = parameters.get(SqlConst.sqlStr);
		if (obj == null)
			throw new Exception("直接写Sql查询时，Map里面提供Sql语句不存在");
		String sqlStr = obj.toString();
		if (StringUtils.isBlank(sqlStr))
			throw new Exception("直接写Sql查询时，Map里面提供Sql语句不存在");

		return sqlStr;
	}
}