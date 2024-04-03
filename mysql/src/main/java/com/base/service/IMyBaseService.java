package com.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * 自定义IBaseService
 *
 * @param <TEntity>
 */
public interface IMyBaseService<TEntity> extends ICommonService {
	//region 增删改

	/**
	 * 新增
	 *
	 * @param entity 实体
	 * @return 是否成功
	 */
	boolean add(TEntity entity);

	/**
	 * 更新
	 *
	 * @param entity 实体
	 * @return 是否成功
	 */
	boolean update(TEntity entity);

	/**
	 * 删除
	 *
	 * @param id 主键ID
	 * @return 是否成功
	 */
	boolean delete(Integer id);


	//endregion

	//region 查询

	/**
	 * 只查询一条记录 添加 limit 1
	 *
	 * @param wrapper 查询参数
	 * @return 单条实体
	 */
	TEntity get(LambdaQueryWrapper<TEntity> wrapper);

	/**
	 * 信息是否存在
	 *
	 * @param wrapper 查询条件
	 * @return 是否存在
	 */
	boolean has(LambdaQueryWrapper<TEntity> wrapper);

	//endregion
}