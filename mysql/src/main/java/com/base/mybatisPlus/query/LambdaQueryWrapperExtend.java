package com.base.mybatisPlus.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.base.tools.string.StringUtilsEx;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * LambdaQueryWrapper 扩展
 */
public class LambdaQueryWrapperExtend<T> extends LambdaQueryWrapper<T> {

	/**
	 * 取前几条数据
	 *
	 * @param number 数量
	 */
	public void limit(Integer number) {
		this.last(String.format(" limit %s ", number.toString()));
	}


	/**
	 * 查询全部字段。用于当字段名数据库为关键字时，给字段名加上` 主要实现在下一步的getSqlSelect获取select选择中
	 */
	public LambdaQueryWrapper<T> selectAll(Class<T> entityClass) {
		return super.select(entityClass, x -> true);
	}

	/**
	 * 用于给where条件中的字段、选中的更新字段 加上`
	 */
	@Override
	protected String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
		return StringUtilsEx.toBackTick(super.columnToString(column, onlyColumn));
	}

	/**
	 * 重写 获取select选择字段方法 。给指定的选择字段加上`
	 */
	@Override
	public String getSqlSelect() {
		String sqlSelect = super.getSqlSelect();
		if (StringUtils.isBlank(sqlSelect))
			return sqlSelect;

		return StringUtils.join(Arrays.stream(sqlSelect.split(StringPool.COMMA))
				.map(StringUtilsEx::toBackTick).collect(Collectors.toList()), StringPool.COMMA);
	}


}