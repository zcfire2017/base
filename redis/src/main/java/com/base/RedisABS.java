package com.base;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * redis操作抽象类
 *
 * @param <T> 值类型
 * @param <K> 键后缀类型
 */
public abstract class RedisABS<K, T> {
	/**
	 * redis模板
	 */
	protected final RedisTemplate<String, T> redisTemplate;

	/**
	 * 键前缀
	 */
	protected String _keyPrefix = "";

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisABS(RedisConnectionFactory factory, Consumer<RedisTemplate<String, T>> consumer) {
		//redis模板初始化
		this.redisTemplate = new RedisTemplate<>();
		//设置工厂
		this.redisTemplate.setConnectionFactory(factory);
		//键序列化方式
		this.redisTemplate.setKeySerializer(new StringRedisSerializer());
		//初始化模板设置
		consumer.accept(redisTemplate);
		//设置序列化初始化
		redisTemplate.afterPropertiesSet();
	}

	/**
	 * 设置key前缀
	 *
	 * @param keyPrefix key前缀
	 */
	protected void setKeyPrefix(String keyPrefix) {
		this._keyPrefix = keyPrefix;
	}

	/**
	 * 判断键是否存在
	 *
	 * @param key 键
	 * @return 是否存在
	 */
	protected boolean containsKey(K key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(getKey(key)));
	}

	/**
	 * 获取键
	 *
	 * @param key 键值
	 * @return 键
	 */
	protected String getKey(K key) {
		return _keyPrefix.isNullOrEmpty() ? key.toString() : _keyPrefix + "_" + key;
	}

	/**
	 * 移除键
	 *
	 * @param key 键
	 */
	public void delete(K key) {
		redisTemplate.delete(getKey(key));
	}

	/**
	 * 设置键过期时间
	 *
	 * @param key  键
	 * @param time 过期时间
	 * @param unit 时间单位
	 */
	public void setExpire(K key, Integer time, TimeUnit unit) {
		redisTemplate.expire(getKey(key), time, unit);
	}

	/**
	 * 设置键过期时间
	 *
	 * @param key  键
	 * @param time 过期时间（单位：秒）
	 */
	public void setExpire(K key, Integer time) {
		redisTemplate.expire(getKey(key), time, TimeUnit.SECONDS);
	}

	/**
	 * 设置键过期时间（60秒）
	 *
	 * @param key 键
	 */
	public void setExpire(K key) {
		redisTemplate.expire(getKey(key), 60, TimeUnit.SECONDS);
	}
}