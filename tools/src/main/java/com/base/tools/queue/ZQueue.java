package com.base.tools.queue;

import com.base.tools.timer.ZTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 线程安全队列（无界队列）
 * 最大容量默认100_000
 *
 * @param <T> 元素类型
 */
public class ZQueue<T> {

	/**
	 * 线程安全队列
	 * 链表实现，无界队列，不可读取size
	 * 设置安全计数器代替size
	 */
	private final ConcurrentLinkedQueue<T> _queue = new ConcurrentLinkedQueue<>();

	/**
	 * 每次取出元素个数
	 */
	private final int _take;

	/**
	 * 最大容量
	 */
	private final int _max = 100_000;

	/**
	 * 计数器
	 * 当前队列元素个数
	 */
	private final AtomicInteger _count = new AtomicInteger(0);

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
	public ZQueue(Consumer<Collection<T>> action, int take) {
		_take = take;
		_action = action;
		//定时器，定时取元素
		ZTimer.start(this::peek, 200);
	}

	/**
	 * 构造函数
	 *
	 * @param action 定时执行的任务
	 */
	public ZQueue(Consumer<Collection<T>> action) {
		this(action, 1_000);
	}

	/**
	 * 创建队列
	 *
	 * @param action 定时执行的任务
	 * @param take   每次取出元素个数
	 * @param <T>    元素类型
	 * @return 队列
	 */
	public static <T> ZQueue<T> create(Consumer<Collection<T>> action, int take) {
		return new ZQueue<>(action, take);
	}

	/**
	 * 创建队列
	 *
	 * @param action 定时执行的任务
	 * @param <T>    元素类型
	 * @return 队列
	 */
	public static <T> ZQueue<T> create(Consumer<Collection<T>> action) {
		return new ZQueue<>(action);
	}

	/**
	 * 添加元素
	 *
	 * @param entity 元素
	 */
	public void add(T entity) {
		if (_count.intValue() < _max) {
			_queue.add(entity);
			_count.incrementAndGet();
		}
	}

	/**
	 * 添加元素集合
	 *
	 * @param entityList 元素集合
	 */
	public void add(Collection<T> entityList) {
		if (_count.intValue() + entityList.size() <= _max) {
			_queue.addAll(entityList);
		}
		else {
			//可入队列元素个数
			var count = _max - _count.intValue();
			if (count <= 0)
				return;

			//截取元素
			var list = new ArrayList<>(entityList).subList(0, count);
			_queue.addAll(list);
			_count.addAndGet(count);
		}
	}

	/**
	 * 定时取出元素
	 */
	private void peek() {
		//元素集合
		var result = new ArrayList<T>();
		//取出元素
		for (int i = 0; i < _take; i++) {
			//取出元素
			var model = this._queue.poll();
			if (model == null)
				break;
			result.add(model);
			_count.decrementAndGet();
		}
		_action.accept(result);
	}
}