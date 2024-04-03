package com.base;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.function.Consumer;

/**
 * 操作List抽象类
 *
 * @param <K> 键后缀类型
 * @param <T> 值类型
 */
public abstract class RedisList<K, T> extends RedisABS<K, T> {
	/**
	 * 操作List对象
	 */
	protected final ListOperations<String, T> operations;

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisList(RedisConnectionFactory factory, Consumer<RedisTemplate<String, T>> consumer) {
		super(factory, consumer);
		//String操作对象
		operations = redisTemplate.opsForList();
	}
}