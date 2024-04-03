package com.base.entity.page;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据层返回分页数据实体
 *
 * @param <T> 分页数据类型
 */
@Getter
@Setter
public class PageLong<T> extends DataPage<T, Long> {
	/**
	 * 通用分页数据实体
	 */
	public PageLong() {
		this(0L);
	}

	/**
	 * 通用分页数据实体
	 *
	 * @param dataCount 数量
	 */
	public PageLong(Long dataCount) {
		this.setDataCount(dataCount);
	}

	/**
	 * 通用分页数据实体
	 */
	public static <T> PageBO<T> newInstance() {
		return new PageBO<T>(0L);
	}
}