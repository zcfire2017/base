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
public class PageListVO<T> extends DataPage<T, Long> {
	/**
	 * 通用分页数据实体
	 */
	public PageListVO() {
		this(0L);
	}

	/**
	 * 通用分页数据实体
	 *
	 * @param dataCount 数量
	 */
	public PageListVO(Long dataCount) {
		this.setDataCount(dataCount);
	}

	/**
	 * 通用分页数据实体
	 */
	public static <T> PageListVO<T> newInstance() {
		return new PageListVO<T>(0L);
	}
}