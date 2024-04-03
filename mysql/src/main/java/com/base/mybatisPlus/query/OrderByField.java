package com.base.mybatisPlus.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 排序字段实体
 *
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderByField<T> {

	/**
	 * 排序字段
	 */
	private SFunction<T, ?> column;

	/**
	 * 排序方式
	 */
	private boolean isAsc;

	/**
	 * 生成 排序字段实体
	 *
	 * @param column 排序字段
	 * @param isAsc  排序方式
	 * @param <T>
	 * @return
	 */
	public static <T> OrderByField of(SFunction<T, ?> column, boolean isAsc) {
		OrderByField<T> model = new OrderByField<>();
		model.setColumn(column);
		model.setAsc(isAsc);
		return model;
	}

	/**
	 * 生成 排序字段实体
	 *
	 * @param column 排序字段
	 * @param <T>
	 * @return
	 */
	public static <T> OrderByField asc(SFunction<T, ?> column) {
		return of(column, true);
	}

	/**
	 * 生成 排序字段实体
	 *
	 * @param column 排序字段
	 * @param <T>
	 * @return
	 */
	public static <T> OrderByField desc(SFunction<T, ?> column) {
		return of(column, false);
	}
}