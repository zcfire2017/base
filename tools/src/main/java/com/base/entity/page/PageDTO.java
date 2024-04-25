package com.base.entity.page;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 分页列表 请求数据Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class PageDTO {

	/**
	 * 当前页
	 */
	@NotNull
	private Integer page = 1;

	/**
	 * 页码
	 */
	@NotNull
	private Integer pageSize = 10;

}