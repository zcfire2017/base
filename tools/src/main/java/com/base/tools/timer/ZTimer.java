package com.base.tools.timer;


import com.base.tools.log.LogHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程定时器
 */
public class ZTimer {
	/**
	 * 定时线程
	 */
	private final ScheduledExecutorService _time;

	/**
	 * 定时器
	 *
	 * @param action 定时执行的任务
	 * @param delay  执行间隔时间
	 */
	public ZTimer(Runnable action, long delay) {
		_time = Executors.newSingleThreadScheduledExecutor();
		_time.scheduleWithFixedDelay(() -> {
			try {
				action.run();
			} catch (Exception e) {
				LogHelper.error(e);
			}
		}, 200, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开始定时器
	 *
	 * @param action 定时执行的任务
	 * @param delay  执行间隔时间
	 * @return 定时器
	 */
	public static ZTimer start(Runnable action, long delay) {
		return new ZTimer(action, delay);
	}

	/**
	 * 停止定时器
	 */
	public void stop() {
		_time.shutdown();
	}
}