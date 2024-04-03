package com.base.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.base.mybatisPlus.query.LambdaQueryWrapperExtend;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 通用mapper
 *
 * @param <TEntity> 实体
 */
public interface MyBaseMapper<TEntity> extends MPJBaseMapper<TEntity>, CommonMapper {

	/**
	 * Lambda查询对象
	 *
	 * @return 查询对象
	 */
	default LambdaQueryWrapper<TEntity> query() {
		return new LambdaQueryWrapperExtend<>();
	}

	/**
	 * 普通查询对象
	 *
	 * @return 查询对象
	 */
	default QueryWrapper<TEntity> query(Boolean isBase) {
		return new QueryWrapper<>();
	}

	/**
	 * Lambda更新对象
	 *
	 * @return 更新对象
	 */
	default LambdaUpdateWrapper<TEntity> update() {
		return new LambdaUpdateWrapper<>();
	}

	/**
	 * 普通更新对象
	 *
	 * @return 更新对象
	 */
	default UpdateWrapper<TEntity> update(Boolean isBase) {
		return new UpdateWrapper<>();
	}
}