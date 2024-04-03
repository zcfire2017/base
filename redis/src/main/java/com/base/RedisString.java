package com.base;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 操作String抽象类
 *
 * @param <K> 键后缀类型
 * @param <T> 值类型
 */
public abstract class RedisString<K, T> extends RedisABS<K, T> {
	/**
	 * 操作String对象
	 */
	protected final ValueOperations<String, T> operations;

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisString(RedisConnectionFactory factory, Consumer<RedisTemplate<String, T>> consumer) {
		super(factory, consumer);
		//String操作对象
		operations = redisTemplate.opsForValue();
	}

	/**
	 * 设置值
	 *
	 * @param key      键名
	 * @param value    值
	 * @param time     过期时间
	 * @param timeUnit 时间单位
	 */
	public void add(K key, T value, long time, TimeUnit timeUnit) {
		operations.set(getKey(key), value, time, timeUnit);
	}

	/**
	 * 设置值
	 *
	 * @param key   键名
	 * @param value 值
	 */
	public void add(K key, T value) {
		operations.set(getKey(key), value);
	}

	/**
	 * 设置值
	 *
	 * @param key   键名
	 * @param value 值
	 * @param time  过期时间（单位：秒）
	 */
	public void add(K key, T value, long time) {
		add(key, value, time, TimeUnit.SECONDS);
	}

	/**
	 * 获取值
	 *
	 * @param key 键名
	 * @return 值
	 */
	public T get(K key) {
		return operations.get(getKey(key));
	}

	/**
	 * 获取值并删除
	 *
	 * @param key 键名
	 * @return 值
	 */
	public T getAndDelete(K key) {
		return operations.getAndDelete(getKey(key));
	}

	/**
	 * 获取值并设置过期时间
	 *
	 * @param key      键名
	 * @param time     过期时间
	 * @param timeUnit 时间单位
	 * @return 值
	 */
	public T getAndExpire(K key, long time, TimeUnit timeUnit) {
		return operations.getAndExpire(getKey(key), time, timeUnit);
	}

	/**
	 * 获取值并设置过期时间
	 *
	 * @param key  键名
	 * @param time 过期时间（单位：秒）
	 * @return 值
	 */
	public T getAndExpire(K key, long time) {
		return this.getAndExpire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 设置自增长
	 *
	 * @param key      键名
	 * @param delta    增长值
	 * @param time     过期时间
	 * @param timeUnit 时间单位
	 * @return 增长后的值
	 */
	public Long incr(K key, long delta, Long time, TimeUnit timeUnit) {
		var rKey = getKey(key);
		var result = operations.increment(rKey, delta);
		if (time != null) {
			if (timeUnit == null)
				timeUnit = TimeUnit.SECONDS;
			redisTemplate.expire(rKey, time, timeUnit);
		}
		return result;
	}

	/**
	 * 设置自增长
	 *
	 * @param key   键名
	 * @param delta 增长值
	 * @return 增长后的值
	 */
	public Long incr(K key, long delta) {
		return incr(key, delta, null, null);
	}

	/**
	 * 设置自增长（+1）
	 *
	 * @param key 键名
	 * @return 增长后的值
	 */
	public Long incr(K key, Long time, TimeUnit timeUnit) {
		return this.incr(key, 1, time, timeUnit);
	}

	/**
	 * 设置自增长（+1）
	 *
	 * @param key 键名
	 * @return 增长后的值
	 */
	public Long incr(K key) {
		return this.incr(key, 1);
	}

	/**
	 * 设置自减少
	 *
	 * @param key   键名
	 * @param delta 减少值
	 * @return 减少后的值
	 */
	public Long decr(K key, long delta) {
		var rKey = getKey(key);
		var result = operations.decrement(rKey, delta);
		redisTemplate.expire(rKey, 60, TimeUnit.SECONDS);
		return result;
	}

	/**
	 * 设置自减少（-1）
	 *
	 * @param key 键名
	 * @return 减少后的值
	 */
	public Long decr(K key) {
		return this.decr(key, 1);
	}
}