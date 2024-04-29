package com.base.tools.cmd;

import com.base.tools.log.LogHelper;
import com.base.tools.task.ZTask;

import java.util.List;
import java.util.Scanner;

/**
 * 命令 帮助类
 */
public class CmdHelper {
	/**
	 * 执行命令
	 *
	 * @param cmd 命令集合
	 */
	public void run(List<String> cmd) {
		Process process = null;
		try {
			//执行器
			var builder = new ProcessBuilder(cmd);
			builder.redirectErrorStream(true);
			//执行
			process = builder.start();
			stream(process);

			//等待执行完成，超时30秒
			process.waitFor();
		} catch (Exception ex) {
			LogHelper.error(ex);
		} finally {
			//关闭线程
			if (process != null) {
				process.destroy();
			}
		}
	}

	/**
	 * 执行命令
	 *
	 * @param cmd 命令集合
	 */
	public void run(String... cmd) {
		run(cmd.toList());
	}

	/**
	 * 消费执行流，避免卡死主线程
	 *
	 * @param process 执行器
	 */
	private void stream(Process process) {
		//输入流
		ZTask.run(() -> {
			var input = new Scanner(process.getInputStream());
			//var is = new StringBuilderUtils();
			while (input.hasNext()) {
				var txt = input.nextLine();
				//LogHelper.info("${process.pid()}：${txt}");
				//is.appendLine(input.nextLine());
			}
			//LogHelper.info("${process.pid()}：结束");
		});
	}
}