package com.base.tools.string;


import cn.hutool.core.util.StrUtil;

import java.text.MessageFormat;

/**
 * 字符串拼接工具
 */
public class StringBuilderUtils {

	/**
	 * 字符串拼接对象
	 */
	private final StringBuilder stringBuilder = new StringBuilder();

	/**
	 * 初始化
	 */
	public StringBuilderUtils() {

	}

	/**
	 * 初始化
	 *
	 * @param pattern 字符串
	 */
	public StringBuilderUtils(String pattern) {
		this.stringBuilder.append(pattern);
	}

	/**
	 * 初始化（默认String.format）
	 *
	 * @param pattern   字符串
	 * @param arguments 替换值集合
	 */
	public StringBuilderUtils(String pattern, Object... arguments) {
		if (arguments == null || arguments.length == 0) this.append(pattern);
		else this.stringBuilder.append(String.format(pattern, arguments));
	}

	/**
	 * 叠加字符串
	 *
	 * @param pattern 字符串
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils append(String pattern) {
		this.stringBuilder.append(pattern);
		return this;
	}

	/**
	 * 添加换行
	 *
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendLine() {
		this.stringBuilder.append("\r\n");
		return this;
	}

	/**
	 * 添加tab
	 *
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendTab() {
		this.stringBuilder.append("\t");
		return this;
	}

	/**
	 * 换行并叠加字符串
	 *
	 * @param pattern 字符串
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendLine(String pattern) {
		this.appendLine();
		if (StrUtil.isNotBlank(pattern))
			this.stringBuilder.append(pattern);
		return this;
	}

	/**
	 * 换行加Tab并叠加字符串
	 *
	 * @param pattern 字符串
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendLineTab(String pattern) {
		this.appendLine();
		this.appendTab();
		if (StrUtil.isNotBlank(pattern))
			this.stringBuilder.append(pattern);
		return this;
	}

	/**
	 * 换行加Tab并叠加字符串
	 *
	 * @param pattern 字符串
	 * @param num     换行次数
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendLineTab(String pattern, Integer num) {
		this.appendLine();
		for (int i = 0; i < num; i++)
			this.appendTab();
		if (StrUtil.isNotBlank(pattern))
			this.stringBuilder.append(pattern);
		return this;
	}

	/**
	 * 换行加Tab并叠加字符串
	 *
	 * @param pattern 字符串
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendTab(String pattern) {
		this.appendTab();
		if (StrUtil.isNotBlank(pattern))
			this.stringBuilder.append(pattern);
		return this;
	}

	/**
	 * 叠加字符串（MessageFormat）
	 *
	 * @param pattern   字符串（{0}{1}{2}...）
	 * @param arguments 参数
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendMessageFormat(String pattern, Object... arguments) {
		if (arguments == null || arguments.length == 0)
			this.append(pattern);
		else
			this.stringBuilder.append(MessageFormat.format(pattern, arguments));
		return this;
	}

	/**
	 * 叠加字符串并换行（MessageFormat）
	 *
	 * @param pattern   字符串（{0}{1}{2}...）
	 * @param arguments 参数
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendMessageFormatLine(String pattern, Object... arguments) {
		this.appendLine();
		if (arguments == null || arguments.length == 0)
			this.append(pattern);
		else
			this.stringBuilder.append(MessageFormat.format(pattern, arguments));
		return this;
	}

	/**
	 * 叠加字符串（StringFormat）
	 *
	 * @param pattern   字符串（%d,%s...）
	 * @param arguments 参数
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendFormat(String pattern, Object... arguments) {
		if (arguments == null || arguments.length == 0)
			this.append(pattern);
		else
			this.stringBuilder.append(String.format(pattern, arguments));
		return this;
	}

	/**
	 * 叠加字符串并换行（StringFormat）
	 *
	 * @param pattern   字符串（%d,%s...）
	 * @param arguments 参数
	 * @return 字符串拼接工具
	 */
	public StringBuilderUtils appendFormatLine(String pattern, Object... arguments) {
		this.appendLine();
		if (arguments == null || arguments.length == 0)
			this.append(pattern);
		else
			this.stringBuilder.append(String.format(pattern, arguments));
		return this;
	}

	/**
	 * 截取字符串
	 *
	 * @param begin 开始位置
	 * @param end   结束位置
	 */
	public void subString(int begin, int end) {
		//截取字符串
		String string = this.stringBuilder.substring(begin, end);
		//清空对象
		this.stringBuilder.setLength(0);
		//重新赋值
		this.stringBuilder.append(string);
	}

	/**
	 * 获取字符串长度
	 *
	 * @return 字符串长度
	 */
	public int getLength() {
		return this.stringBuilder.length();
	}

	/**
	 * 生成字符串
	 *
	 * @return 字符串
	 */
	public String toString() {
		return this.stringBuilder.toString();
	}
}