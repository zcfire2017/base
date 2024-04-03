package com.base.tools.validate_code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 验证码操作
 */
public class ValidateCodeHelper {
	/**
	 * 随机字符集合
	 */
	private static final List<String> codeList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

	/**
	 * 生成随机验证码及图片
	 * Object[0]：验证码字符串；
	 * Object[1]：验证码图片。
	 */
	public static ValidateCodeInfo getValidateCode(Integer width, Integer height, Integer num) {
		StringBuffer sb = new StringBuffer();
		// 1.创建空白图片
		BufferedImage image = new BufferedImage(
				width, height, BufferedImage.TYPE_INT_RGB);
		// 2.获取图片画笔
		Graphics graphic = image.getGraphics();
		// 3.设置画笔颜色
		graphic.setColor(Color.LIGHT_GRAY);
		// 4.绘制矩形背景
		graphic.fillRect(0, 0, width, height);
		// 5.画随机字符
		Random ran = new Random();
		for (int i = 0; i < num; i++) {
			// 取随机字符索引
			int n = ran.nextInt(codeList.size());
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 设置字体大小
			graphic.setFont(new Font(
					null, Font.BOLD + Font.ITALIC, 30));
			// 画字符
			graphic.drawString(
					codeList.get(n), i * width / 4, height * 2 / 3);
			// 记录字符
			sb.append(codeList.get(n));
		}
		// 6.画干扰线
		for (int i = 0; i < 5; i++) {
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 随机画线
			graphic.drawLine(ran.nextInt(width), ran.nextInt(height),
					ran.nextInt(width), ran.nextInt(height));
		}
		// 7.返回验证码和图片
		return new ValidateCodeInfo(image, sb.toString().toLowerCase());
	}

	/**
	 * 随机取色
	 */
	public static Color getRandomColor() {
		Random ran = new Random();
		return new Color(ran.nextInt(256),
				ran.nextInt(256), ran.nextInt(256));
	}
}