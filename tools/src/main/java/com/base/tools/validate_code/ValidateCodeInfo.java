package com.base.tools.validate_code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * 验证码图片信息
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidateCodeInfo {
	/**
	 * 图片对象
	 */
	private BufferedImage image;
	/**
	 * 验证码字符串
	 */
	private String code;
}