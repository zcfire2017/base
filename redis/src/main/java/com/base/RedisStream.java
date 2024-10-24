package com.base;

import com.base.tools.log.LogHelper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 操作Stream抽象类
 *
 * @param <K>  键后缀类型
 * @param <HK> Stream键类型
 * @param <HV> Stream值类型
 */
public abstract class RedisStream<K, HK, HV> extends RedisABS<K, HV> {
	/**
	 * 操作Stream对象
	 */
	protected final StreamOperations<String, HK, HV> operations;

	/**
	 * 消费组名称
	 */
	private String groupName;

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisStream(RedisConnectionFactory factory, Consumer<RedisTemplate<String, HV>> consumer) {
		super(factory, consumer);
		//操作对象
		operations = redisTemplate.opsForStream();
	}

	/**
	 * 注册消费者（不配置消费组，则消费所有消息）
	 *
	 * @param listener 消息监听对象
	 */
	protected void registerConsumer(StreamListener<String, MapRecord<String, String, String>> listener) {
		this.registerConsumer(null, listener, "");
	}

	/**
	 * 注册消费者（不配置消费组，则消费所有消息）
	 *
	 * @param key      键
	 * @param listener 消息监听对象
	 */
	protected void registerConsumer(K key, StreamListener<String, MapRecord<String, String, String>> listener) {
		this.registerConsumer(key, listener, "");
	}

	/**
	 * 注册消费者（配置消费组，则同一消费组下竞争消费）
	 *
	 * @param listener  消息监听对象
	 * @param groupName 消费组名称
	 */
	protected void registerConsumer(K key, StreamListener<String, MapRecord<String, String, String>> listener, String groupName) {
		this.groupName = groupName;
		//设置监听
		var opt = StreamMessageListenerContainer
				.StreamMessageListenerContainerOptions
				.builder()
				.batchSize(10)
				.pollTimeout(Duration.ofSeconds(1))
				.errorHandler(e -> {
					if (!(e instanceof IllegalStateException)) {
						LogHelper.error(e);
					}
				})
				.build();
		var listenerContainer = StreamMessageListenerContainer.create(factory, opt);

		//读取配置
		var readOptions = StreamMessageListenerContainer.StreamReadRequest
				// 设置订阅Stream的key和获取偏移量
				.builder(StreamOffset.create(getKey(key), ReadOffset.lastConsumed()));
		//设置消费组
		if (groupName.isNotNullOrEmpty()) {
			//新增消费组
			addGroup(groupName);
			//设置消费组的分组名称，并关闭自动提交
			readOptions = readOptions.consumer(org.springframework.data.redis.connection.stream.Consumer.from(groupName, groupName)).autoAcknowledge(false);
		}

		// 如果出现异常,自动关闭消费流的开关
		var optBuild = readOptions.cancelOnError((ex) -> false)
				.build();

		//注册监听对象
		listenerContainer.register(optBuild, listener);
		//开启监听
		listenerContainer.start();
	}

	/**
	 * 手动确认提交
	 *
	 * @param recordId 记录id
	 */
	protected void acknowledge(RecordId... recordId) {
		if (groupName.isNotNullOrEmpty()) {
			operations.acknowledge(getKey(), groupName, recordId);
		}
	}

	/**
	 * 获取键
	 *
	 * @return 键
	 */
	protected String getKey() {
		return getKey(null);
	}

	//region 生产

	/**
	 * 设置值
	 *
	 * @param key   键后缀
	 * @param hKey  hash键
	 * @param value 值
	 */
	public void add(K key, HK hKey, HV value) {
		operations.add(getKey(key), Map.of(hKey, value));
	}

	/**
	 * 设置值
	 *
	 * @param hKey  hash键
	 * @param value 值
	 */
	public void add(HK hKey, HV value) {
		var id = operations.add(getKey(), Map.of(hKey, value));
		LogHelper.info(id.json());
	}

	/**
	 * 设置值
	 *
	 * @param key   键后缀
	 * @param value 值
	 */
	public void add(K key, Map<HK, HV> value) {
		operations.add(getKey(key), value);
	}

	/**
	 * 设置值
	 *
	 * @param value 值
	 */
	public void add(Map<HK, HV> value) {
		operations.add(getKey(), value);
	}

	//endregion

	//region 消费组

	/**
	 * 设置消费组
	 *
	 * @param key       键后缀
	 * @param groupName 消费组名称
	 * @return hash值
	 */
	public String addGroup(K key, String groupName) {
		//验证消费组是否存在，避免报错
		if (getGroupList().contains(groupName)) {
			return "";
		}
		return operations.createGroup(getKey(key), groupName);
	}

	/**
	 * 设置消费组
	 *
	 * @param groupName 消费组名称
	 * @return hash值
	 */
	public String addGroup(String groupName) {
		//验证消费组是否存在，避免报错
		if (getGroupList().contains(groupName)) {
			return "";
		}
		return operations.createGroup(getKey(), groupName);
	}

	/**
	 * 获取所有消费组
	 *
	 * @return 消费组名称集合
	 */
	public Set<String> getGroupList() {
		var list = operations.groups(getKey());
		var groupList = new HashSet<String>();
		for (var group : list) {
			groupList.add(group.groupName());
		}
		return groupList;
	}

	//endregion
}