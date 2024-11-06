package com.base.tools.queue;


import com.base.tools.log.LogHelper;
import com.base.tools.timer.ZTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

/**
 * 线程安全环形队列（有界队列）
 * 默认容量：1_000
 * 默认定时：每200毫秒取1_000元素
 *
 * @param <T> 元素类型
 */
public class ZCircularQueue<T> {

	/**
	 * 线程安全环形队列（FIFO）
	 */
	private final ArrayBlockingQueue<T> _queue;

	/**
	 * 每次取出元素个数
	 */
	private int _take = 1_000;

	/**
	 * 队列容量
	 */
	private int _capacity = 1_000;

	/**
	 * 执行间隔（毫秒）
	 */
	private int _duration = 200;

	/**
	 * 回调方法 执行事件
	 */
	private final Consumer<Collection<T>> _action;

	/**
	 * 构造函数
	 *
	 * @param action 定时执行的任务
	 * @param take   每次取出元素个数
	 */
	public ZCircularQueue(Consumer<Collection<T>> action, int take) {
		this(0, action, take);
	}

	/**
	 * 构造函数
	 *
	 * @param action 定时执行的任务
	 */
	public ZCircularQueue(Consumer<Collection<T>> action) {
		this(action, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param action   定时执行的任务
	 * @param take     每次取出元素个数
	 * @param duration 执行间隔（毫秒）
	 */
	public ZCircularQueue(Consumer<Collection<T>> action, int take, int duration) {
		this(0, action, take, duration);
	}

	/**
	 * 构造函数
	 *
	 * @param capacity 队列容量
	 * @param action   定时执行的任务
	 */
	public ZCircularQueue(int capacity, Consumer<Collection<T>> action) {
		this(capacity, action, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param capacity 队列容量
	 * @param action   定时执行的任务
	 * @param take     每次取出元素个数
	 */
	public ZCircularQueue(int capacity, Consumer<Collection<T>> action, int take) {
		this(capacity, action, take, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param capacity 队列容量
	 * @param action   定时执行的任务
	 * @param take     每次取出元素个数
	 * @param duration 执行间隔（毫秒）
	 */
	public ZCircularQueue(int capacity, Consumer<Collection<T>> action, int take, int duration) {
		if (capacity > 0)
			_capacity = capacity;
		if (take > 0)
			_take = take;
		if (duration > 0)
			_duration = duration;

		_action = action;
		_queue = new ArrayBlockingQueue<>(_capacity, true);
		//定时器，定时取元素
		ZTimer.start(this::peek, _duration);
	}

	/**
	 * 创建队列
	 *
	 * @param action 定时执行的任务
	 * @param take   每次取出元素个数
	 * @param <T>    元素类型
	 * @return 队列
	 */
	public static <T> ZCircularQueue<T> create(Consumer<Collection<T>> action, int take) {
		return new ZCircularQueue<>(action, take);
	}

	/**
	 * 创建队列
	 *
	 * @param action 定时执行的任务
	 * @param <T>    元素类型
	 * @return 队列
	 */
	public static <T> ZCircularQueue<T> create(Consumer<Collection<T>> action) {
		return new ZCircularQueue<>(action);
	}

	/**
	 * 创建队列
	 *
	 * @param capacity 队列容量
	 * @param action   定时执行的任务
	 * @param take     每次取出元素个数
	 * @param <T>      元素类型
	 * @return 队列
	 */
	public static <T> ZCircularQueue<T> create(int capacity, Consumer<Collection<T>> action, int take) {
		return new ZCircularQueue<>(capacity, action, take);
	}

	/**
	 * 创建队列
	 *
	 * @param capacity 队列容量
	 * @param action   定时执行的任务
	 * @param <T>      元素类型
	 * @return 队列
	 */
	public static <T> ZCircularQueue<T> create(int capacity, Consumer<Collection<T>> action) {
		return new ZCircularQueue<>(capacity, action);
	}

	/**
	 * 添加元素
	 *
	 * @param entity 元素
	 */
	public void add(T entity) {
		try {
			_queue.put(entity);
		} catch (InterruptedException e) {
			LogHelper.error(e);
		}
	}

	/**
	 * 添加元素集合
	 *
	 * @param entityList 元素集合
	 */
	public void add(Collection<T> entityList) {
		for (T entity : entityList) {
			add(entity);
		}
	}

	/**
	 * 定时取出元素
	 */
	private void peek() {
		if (_queue.isEmpty())
			return;

		//元素集合
		var result = new ArrayList<T>();
		//取出元素
		for (int i = 0; i < _take; i++) {
			//取出元素
			var model = this._queue.poll();
			if (model == null)
				break;
			result.add(model);
		}
		_action.accept(result);
	}
}