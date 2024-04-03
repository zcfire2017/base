package com.base;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 操作Hash抽象类
 *
 * @param <K>  键后缀类型
 * @param <HK> Hash键类型
 * @param <HV> Hash值类型
 */
public abstract class RedisHash<K, HK, HV> extends RedisABS<K, HV> {

	/**
	 * 操作List对象
	 */
	protected final HashOperations<String, HK, HV> operations;

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisHash(RedisConnectionFactory factory, Consumer<RedisTemplate<String, HV>> consumer) {
		super(factory, consumer);
		//Hash操作对象
		operations = redisTemplate.opsForHash();
	}

	//region 管理

	/**
	 * 设置值
	 *
	 * @param key   键后缀
	 * @param hKey  hash键
	 * @param value 值
	 */
	public void add(K key, HK hKey, HV value) {
		operations.put(getKey(key), hKey, value);
	}

	/**
	 * 设置值（不存在则新增，否则不动）
	 *
	 * @param key   键后缀
	 * @param hKey  hash键
	 * @param value 值
	 * @return 是否改动
	 */
	public Boolean addIfAbsent(K key, HK hKey, HV value) {
		return operations.putIfAbsent(getKey(key), hKey, value);
	}

	/**
	 * 设置值
	 *
	 * @param key 键后缀
	 * @param map hash集合
	 */
	public void addAll(K key, Map<HK, HV> map) {
		operations.putAll(getKey(key), map);
	}

	/**
	 * 是否存在hash键
	 *
	 * @param key  键后缀
	 * @param hKey hash键
	 * @return 是否存在
	 */
	public Boolean hasKey(K key, HK hKey) {
		return operations.hasKey(getKey(key), hKey);
	}

	/**
	 * 是否存在hash键
	 *
	 * @param key  键后缀
	 * @param hKey hash键
	 * @return 是否存在
	 */
	@SafeVarargs
	public final Boolean deleteHKey(K key, HK... hKey) {
		return operations.delete(getKey(key), hKey.toList()) > 0;
	}

	//endregion

	//region 查询

	/**
	 * 获取hash值
	 *
	 * @param key 键后缀
	 * @return hash值
	 */
	public HV get(K key, HK hKey) {
		return operations.get(getKey(key), hKey);
	}

	/**
	 * 获取hash值集合
	 *
	 * @param key 键后缀
	 * @return hash值集合
	 */
	public List<HV> getList(K key, Collection<HK> hKey) {
		return operations.multiGet(getKey(key), hKey);
	}

	/**
	 * 获取hash键集合
	 *
	 * @param key 键后缀
	 * @return hash键集合
	 */
	public Set<HK> keys(K key) {
		return operations.keys(getKey(key));
	}

	/**
	 * 获取hash值集合
	 *
	 * @param key 键后缀
	 * @return hash值集合
	 */
	public List<HV> values(K key) {
		return operations.values(getKey(key));
	}

	/**
	 * 获取键值对集合
	 *
	 * @param key 键后缀
	 * @return 键值对集合
	 */
	public Map<HK, HV> entry(K key) {
		return operations.entries(getKey(key));
	}

	//endregion
}