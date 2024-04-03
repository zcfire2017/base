package com.base.entity.page;

import lombok.Getter;
import lombok.Setter;

/**
 * web服务层返回分页数据实体
 *
 * @param <T> 分页数据类型
 */
@Getter
@Setter
public class PageBO<T> extends DataPage<T, Long> {
	/**
	 * 通用分页数据实体
	 */
	public PageBO() {
		this(0L);
	}

	/**
	 * 通用分页数据实体
	 *
	 * @param dataCount 数量
	 */
	public PageBO(Long dataCount) {
		this.setDataCount(dataCount);
	}

	/**
	 * 通用分页数据实体
	 */
	public static <T> PageBO<T> newInstance() {
		return new PageBO<T>(0L);
	}
}