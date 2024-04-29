package com.base.file.entity;

import com.base.file.enums.EFileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文件 视图实体
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
	/**
	 * 是否成功
	 */
	private Boolean success;

	/**
	 * 原文件名称
	 */
	private String name;

	/**
	 * 文件类型
	 * <p>
	 * 0:其它 1:图片 2:视频
	 * </p>
	 */
	private EFileType fileType;

	/**
	 * 文件地址
	 */
	private String url;

	/**
	 * 压缩文件地址
	 */
	private String thumbUrl;

	/**
	 * 构造函数
	 *
	 * @param success 是否成功
	 */
	public FileVO(Boolean success) {
		this.success = success;
	}
}