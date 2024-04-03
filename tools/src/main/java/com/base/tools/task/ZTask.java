package com.base.tools.task;


import com.base.tools.log.LogHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 任务
 * 线程池
 */
public class ZTask {
	/**
	 * 线程池最大线程数
	 */
	private final Integer _max = 10;

	/**
	 * 线程池对象
	 */
	private final ExecutorService _pool;

	/**
	 * 线程池任务
	 * 用于等待线程池任务执行完成
	 */
	private final List<Future<?>> _futures = new java.util.concurrent.CopyOnWriteArrayList<>();

	/**
	 * 初始化(最大线程)
	 */
	public ZTask() {
		_pool = Executors.newFixedThreadPool(_max);
	}

	/**
	 * 初始化
	 *
	 * @param threads 线程池大小
	 */
	public ZTask(Integer threads) {
		//限定最大线程数
		if (threads > _max)
			threads = _max;
		_pool = Executors.newFixedThreadPool(threads);
	}

	/**
	 * 创建线程池
	 *
	 * @return 线程池
	 */
	public static ZTask create() {
		return new ZTask();
	}

	/**
	 * 创建线程池
	 *
	 * @param threads 线程池大小
	 * @return 线程池
	 */
	public static ZTask create(Integer threads) {
		return new ZTask(threads);
	}

	/**
	 * 添加执行线程
	 *
	 * @param command 执行线程
	 */
	public void execute(Runnable command) {
		//防止线程池已关闭
		if (!this._pool.isShutdown())
			_futures.add(this._pool.submit(command));
	}

	/**
	 * 等待线程执行完成
	 * 不关闭线程池
	 */
	public void await() {
		//等待线程执行完成
		for (var future : _futures) {
			try {
				//监听线程池子线程执行状态及执行结果。
				future.get();
			} catch (Exception e) {
				LogHelper.error(e);
			}
		}
	}

	/**
	 * 等待线程执行完成
	 * 完成后关闭所有线程
	 */
	public void close() {
		try {
			//等待关闭所有线程
			this._pool.shutdown();
			//等待线程执行完成 (3分钟)
			if (!this._pool.awaitTermination(180, TimeUnit.SECONDS)) {
				//立即关闭所有线程
				this._pool.shutdownNow();
			}
		} catch (Exception e) {
			//立即关闭所有线程
			this._pool.shutdownNow();
			LogHelper.error(e);
		}
	}
}