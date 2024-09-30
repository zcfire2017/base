package com.base.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.CommonMapper;
import com.base.mapper.MyBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * BaseService 提供直接写Sql语句查询
 */
public abstract class MyBaseServiceImpl<TMapper extends MyBaseMapper<TEntity>, TEntity> extends ServiceImpl<TMapper, TEntity> implements IMyBaseService<TEntity> {
	/**
	 * 获取实体mapper
	 *
	 * @return 实体mapper
	 */
	@Override
	public CommonMapper getCommonMapper() {
		return this.baseMapper;
	}

	/**
	 * 获取查询对象（LambdaQueryWrapper）
	 *
	 * @return 查询对象
	 */
	public LambdaQueryWrapper<TEntity> getQuery() {
		return new LambdaQueryWrapper<>();
	}

	/**
	 * 获取联查对象（MPJLambdaWrapper）
	 *
	 * @return 联查对象
	 */
	public MPJLambdaWrapper<TEntity> joinQuery() {
		return new MPJLambdaWrapper<>();
	}

	/**
	 * 获取查询对象（QueryWrapper）
	 *
	 * @return 查询对象
	 */
	public QueryWrapper<TEntity> getStringQuery() {
		return new QueryWrapper<>();
	}

	/**
	 * 获取聚合查询对象
	 *
	 * @return 聚合查询对象
	 */
	public QueryChainWrapper<TEntity> getQueryChain() {
		return new QueryChainWrapper<>(this.baseMapper);
	}

	/**
	 * 获取更新对象（LambdaUpdateWrapper）
	 *
	 * @return 更新对象
	 */
	public LambdaUpdateWrapper<TEntity> getUpdate() {
		return new LambdaUpdateWrapper<>();
	}

	/**
	 * 新增
	 *
	 * @param entity 实体
	 * @return 是否成功
	 */
	@Override
	public boolean add(TEntity entity) {
		return this.baseMapper.insert(entity) > 0;
	}

	/**
	 * 更新
	 *
	 * @param tEntity 实体
	 * @return 是否成功
	 */
	@Override
	public boolean update(TEntity tEntity) {
		return this.baseMapper.updateById(tEntity) > 0;
	}

	/**
	 * 删除
	 *
	 * @param id 主键ID
	 * @return 是否成功
	 */
	@Override
	public boolean delete(Integer id) {
		return this.baseMapper.deleteById(id) > 0;
	}

	/**
	 * 查询单个实体
	 *
	 * @param wrapper 查询参数
	 * @return 单个实体
	 */
	@Override
	public TEntity get(LambdaQueryWrapper<TEntity> wrapper) {
		wrapper.last(" limit 1 ");
		return this.baseMapper.selectOne(wrapper);
	}

	/**
	 * 查询单个实体
	 *
	 * @param wrapper 查询参数
	 * @return 单个实体
	 */
	public TEntity first(LambdaQueryWrapper<TEntity> wrapper) {
		wrapper.last(" limit 1 ");
		return this.baseMapper.selectOne(wrapper);
	}

	/**
	 * 查询单个实体
	 *
	 * @param wrapper 查询参数
	 * @return 单个实体
	 */
	public TEntity first(MPJLambdaWrapper<TEntity> wrapper) {
		wrapper.last(" limit 1 ");
		return this.baseMapper.selectOne(wrapper);
	}

	/**
	 * 信息是否存在
	 *
	 * @param wrapper 查询条件
	 * @return 是否存在
	 */
	@Override
	public boolean has(LambdaQueryWrapper<TEntity> wrapper) {
		return this.count(wrapper) > 0;
	}

	/**
	 * 新增
	 *
	 * @param entities 实体集合
	 * @return 是否成功
	 */
	@Transactional
	public boolean add(Collection<TEntity> entities) {
		return this.saveBatch(entities);
	}

	/**
	 * 查询信息
	 *
	 * @param id 主键ID
	 * @return 实体
	 */
	public TEntity get(Serializable id) {
		return this.baseMapper.selectById(id);
	}
}