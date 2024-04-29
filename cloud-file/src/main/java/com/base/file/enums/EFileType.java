package com.base.file.enums;

import com.base.tools.enums.entity.ByteEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型
 */
@AllArgsConstructor
@Getter
public enum EFileType implements ByteEnum {
	其它((byte) 0),
	图片((byte) 1),
	视频((byte) 2),
	压缩文件((byte) 10),
	;

	/**
	 * 值
	 */
	private final Byte value;
}