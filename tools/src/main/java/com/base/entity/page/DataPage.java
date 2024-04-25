package com.base.entity.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息
 *
 * @param <T> 分页数据类型
 * @param <N> 总条数类型
 */
@Getter
@Setter
@NoArgsConstructor
public class DataPage<T, N extends Number> {

	/**
	 * 数据总条数
	 */
	private N dataCount;

	/**
	 * 分页数据
	 */
	private List<T> dataList = new ArrayList<>();

	/**
	 * 分页信息
	 *
	 * @param dataCount 数量
	 */
	public DataPage(N dataCount) {
		this.dataCount = dataCount;
	}

	/**
	 * 返回一个 新实体对象
	 */
	public static <T> DataPage<T, Long> newInstance() {
		return new DataPage<T, Long>(0L);
	}
}