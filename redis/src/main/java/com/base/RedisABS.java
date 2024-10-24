package com.base;

import lombok.Setter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;
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
	 * 连接工厂
	 */
	protected final RedisConnectionFactory factory;

	/**
	 * 命名空间
	 */
	@Setter
	protected String nameSpace = "";

	/**
	 * 键前缀
	 */
	@Setter
	protected String keyPrefix = "";

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisABS(RedisConnectionFactory factory, Consumer<RedisTemplate<String, T>> consumer) {
		this.factory = factory;
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
		return getPrefix() + key;
	}

	/**
	 * 获取所有键
	 *
	 * @return 键集合
	 */
	protected Set<String> getAllKey() {
		return redisTemplate.keys(getPrefix() + "*");
	}

	/**
	 * 获取所有键
	 *
	 * @param key 模糊查询键
	 * @return 键集合
	 */
	protected Set<String> getAllKey(String key) {
		return redisTemplate.keys(key);
	}

	/**
	 * 获取键前缀部分
	 *
	 * @return 键前缀
	 */
	protected String getPrefix() {
		//命名空间
		var space = nameSpace.isNullOrEmpty() ? "" : nameSpace + ":";
		//前缀
		var prefix = keyPrefix.isNullOrEmpty() ? "" : keyPrefix + "_";

		return space + prefix;
	}

	/**
	 * 删除键
	 *
	 * @param key 键
	 */
	public void delete(K key) {
		redisTemplate.delete(getKey(key));
	}

	/**
	 * 删除所有键
	 */
	protected void deleteAll() {
		var keys = getAllKey();
		if (keys.isNotEmpty()) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除所有键
	 *
	 * @param key 模糊key
	 */
	protected void deleteAll(String key) {
		var keys = getAllKey(key);
		if (keys.isNotEmpty()) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * 删除键
	 *
	 * @param keys 键集合
	 */
	protected void delete(Collection<String> keys) {
		if (keys.isNotEmpty()) {
			redisTemplate.delete(keys);
		}
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