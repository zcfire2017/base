package com.base.file;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;

/**
 * 云文件 抽象类
 */
public abstract class ABSCloudFile {
	//region 字段属性

	/**
	 * 文件存储桶
	 */
	private final FileStorageService storage;

	//endregion

	/**
	 * 获取存储对象
	 */
	public ABSCloudFile(FileStorageService storage) {
		this.storage = storage;
	}

	//region 上传文件

	/**
	 * 上传文件
	 *
	 * @param source   文件流
	 * @param fileName 文件名
	 */
	protected FileInfo upload(Object source, String fileName) {
		return storage.of(source, fileName).upload();
	}

	//endregion
}