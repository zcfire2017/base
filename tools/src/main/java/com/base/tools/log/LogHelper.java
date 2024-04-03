package com.base.tools.log;


import com.base.tools.string.StringBuilderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * 日志工具类
 */
public class LogHelper {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger("com.base");

	/**
	 * 启动日志
	 *
	 * @param applicationName 应用名称
	 */
	public static void starting(String applicationName) {
		println("Starting " + applicationName);
	}

	/**
	 * 启动后日志
	 *
	 * @param applicationName 应用名称
	 */
	public static void started(String applicationName) {
		println("Started  " + applicationName);
	}

	/**
	 * 打印
	 *
	 * @param msg 输出内容
	 */
	private static void println(String msg) {
		//等号数量
		var waitLine = "=".repeat(80);
		System.out.println("${waitLine} ${msg} ${waitLine}");
	}

	/**
	 * debug日志 用法：logger.debug("the message {} is from {}", msg, somebody);
	 *
	 * @param msg  输出内容
	 * @param args 可变参数
	 */
	public static void debug(String msg, Object... args) {
		logger.debug(msg, args);
	}

	/**
	 * info日志 用法：logger.debug("the message {} is from {}", msg, somebody);
	 *
	 * @param msg  输出内容
	 * @param args 可变参数
	 */
	public static void info(String msg, Object... args) {
		logger.info(msg, args);
	}

	/**
	 * warn日志 用法：logger.debug("the message {} is from {}", msg, somebody);
	 *
	 * @param msg  输出内容
	 * @param args 可变参数
	 */
	public static void warn(String msg, Object... args) {
		logger.warn(msg, args);
	}

	/**
	 * error日志 用法：logger.debug("the message {} is from {}", msg, somebody);
	 *
	 * @param msg  输出内容
	 * @param args 可变参数
	 */
	public static void error(String msg, Object... args) {
		logger.error(msg, args);
	}

	/**
	 * error日志 用法：logger.debug("the message {} is from {}", msg, somebody);
	 *
	 * @param msg  输出内容
	 * @param args 可变参数
	 */
	public static void trace(String msg, Object... args) {
		logger.trace(msg, args);
	}

	/**
	 * 异常日志（error日志）
	 *
	 * @param throwable   错误
	 * @param preMessages 需要打印的前置信息
	 */
	public static void error(Throwable throwable, String... preMessages) {
		//错误日志
		StringBuilderUtils builder = new StringBuilderUtils();
		builder.appendLine();
		builder.appendLine("------------------- exception message start -------------------");
		if (preMessages != null && preMessages.length > 0)
			Arrays.stream(preMessages).forEach(builder::appendLine);
		builder.appendFormatLine("错误信息：%s", throwable.getMessage());
		//循环错误
		for (var stack : throwable.getStackTrace()) {
			if ((stack.getClassName().contains("com.kc") || stack.getClassName().contains("com.base"))
					&& !"invoke".equals(stack.getMethodName())
					&& !Objects.equals(stack.getFileName(), "<generated>")) {
				builder.appendFormatLine("在：%s--{%s}--第【%d】行;", stack.getClassName(), stack.getMethodName(), stack.getLineNumber());
			}
		}
		builder.appendLine("------------------- exception message end   -------------------");
		error(builder.toString());
	}

}