package com.base;

import com.base.tools.log.LogHelper;
import com.base.tools.string.StringBuilderUtils;
import manifold.ext.props.rt.api.get;
import manifold.ext.props.rt.api.var;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.GenericMessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * kafka 配置工具
 */
public class KafkaConfigUtil {
	/**
	 * 配置
	 */
	@var KafkaProperties properties;

	/**
	 * 生产者配置
	 */
	@get Map<String, Object> producerConfigs = new HashMap<>();

	/**
	 * 消费者配置
	 */
	@get Map<String, Object> consumerConfigs = new HashMap<>();

	/**
	 * 配置kafka 消费者和生产者一起配置
	 *
	 * @param properties 配置信息
	 */
	public void setConfig(KafkaProperties properties) {
		this.properties = properties;
		//配置生产者
		setProducerConfig();
		//配置消费者
		setConsumerConfig();
	}

	//region 生产者配置

	/**
	 * 配置生产者
	 */
	public void setProducerConfig() {
		// 集群的服务器地址
		producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		// 生产者空间不足时，send()被阻塞的时间，默认60s
		producerConfigs.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 6000);
		// 生产者重试次数
		producerConfigs.put(ProducerConfig.RETRIES_CONFIG, properties.producer.retries);
		/*
		# acks=0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
		# acks=1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
		# acks=all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
		 */
		producerConfigs.put(ProducerConfig.ACKS_CONFIG, properties.producer.acks);
		// 生产者会在ProducerBatch被填满或者等待超过LINGER_MS_CONFIG时发送
		producerConfigs.put(ProducerConfig.LINGER_MS_CONFIG, 100);
		// key 和 value 的序列化
		producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		//生产者特性配置
		if (properties.producer.properties != null && !properties.producer.properties.isEmpty()) {
			producerConfigs.putAll(properties.producer.properties);
		}
		//全局特性配置
		if (properties.properties != null && !properties.properties.isEmpty()) {
			producerConfigs.putAll(properties.properties);
		}
	}

	/**
	 * 创建生产者工厂
	 *
	 * @param keySerializer   键序列化对象
	 * @param valueSerializer 值序列化对象
	 * @param setting         自定义配置
	 * @param <K>             键类型
	 * @param <V>             值类型
	 * @return 生产者工厂
	 */
	public <K, V> DefaultKafkaProducerFactory<K, V> producer(Serializer<K> keySerializer, Serializer<V> valueSerializer, Consumer<Map<String, Object>> setting) {
		if (consumerConfigs.isEmpty())
			throw new IllegalArgumentException("未注册生产者配置...");
		//复制配置
		var config = new HashMap<>(this.producerConfigs);
		//自定义配置
		if (setting != null)
			setting.accept(config);
		//创建生产者工厂
		return new DefaultKafkaProducerFactory<>(config, keySerializer, valueSerializer);
	}

	/**
	 * 创建生产者工厂
	 *
	 * @param keySerializer   键序列化对象
	 * @param valueSerializer 值序列化对象
	 * @param <K>             键类型
	 * @param <V>             值类型
	 * @return 生产者工厂
	 */
	public <K, V> DefaultKafkaProducerFactory<K, V> producer(Serializer<K> keySerializer, Serializer<V> valueSerializer) {
		return producer(keySerializer, valueSerializer, null);
	}

	//endregion

	//region 消费者配置

	/**
	 * 配置消费者
	 */
	public void setConsumerConfig() {
		// 集群的服务器地址
		consumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		// 消费者组
		if (properties.getConsumer().getGroupId() != null)
			consumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, properties.consumer.groupId);
		// 自动位移提交
		if (properties.consumer.enableAutoCommit != null)
			consumerConfigs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, properties.consumer.enableAutoCommit);
		// 自动位移提交间隔时间
		if (properties.consumer.autoCommitInterval != null)
			consumerConfigs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, properties.consumer.autoCommitInterval.toMillis());
		/*
		# 该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
		# latest（默认值）在偏移量无效的情况下，消费者将从最新地记录开始读取数据（在消费者启动之后生成的记录）
		# earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
		 */
		if (properties.consumer.autoOffsetReset != null)
			consumerConfigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.consumer.autoOffsetReset);
		// key 和 value 的反序列化
		consumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());

		//生产者特性配置
		if (properties.consumer.properties != null && !properties.consumer.properties.isEmpty()) {
			consumerConfigs.putAll(properties.consumer.properties);
		}
		//全局特性配置
		if (properties.properties != null && !properties.properties.isEmpty()) {
			consumerConfigs.putAll(properties.properties);
		}
	}

	/**
	 * 创建消费者工厂
	 *
	 * @param keyDeserializer   键反序列化对象
	 * @param valueDeserializer 值反序列化对象
	 * @param setting           自定义配置
	 * @param <K>               键类型
	 * @param <V>               值类型
	 * @return 消费者工厂
	 */
	public <K, V> DefaultKafkaConsumerFactory<K, V> consumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer, Consumer<Map<String, Object>> setting) {
		if (consumerConfigs.isEmpty())
			throw new IllegalArgumentException("未注册消费者配置...");
		//复制配置
		var config = new HashMap<>(this.consumerConfigs);
		//自定义配置
		if (setting != null)
			setting.accept(config);

		//创建消费者工厂
		return new DefaultKafkaConsumerFactory<>(config, keyDeserializer, valueDeserializer);
	}

	/**
	 * 创建消费者工厂
	 *
	 * @param keyDeserializer   键反序列化对象
	 * @param valueDeserializer 值反序列化对象
	 * @param <K>               键类型
	 * @param <V>               值类型
	 * @return 消费者工厂
	 */
	public <K, V> DefaultKafkaConsumerFactory<K, V> consumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
		return consumer(keyDeserializer, valueDeserializer, null);
	}

	//endregion

	//region 消费者容器配置

	/**
	 * 获取监听器容器配置
	 *
	 * @param topic    主题
	 * @param listener 消息监听
	 * @param setting  自定义配置
	 * @return 监听器容器配置
	 */
	public ContainerProperties getContainerConfig(String topic, GenericMessageListener<?> listener, Consumer<ContainerProperties> setting) {
		//容器属性
		var containerProperties = new ContainerProperties(topic);
		//消息监听
		containerProperties.setMessageListener(listener);

		//消费者拉取消息的超时时间，单位是毫秒
		if (properties.listener.pollTimeout != null)
			containerProperties.setPollTimeout(properties.listener.pollTimeout.toMillis());
		//日志开关
		if (properties.listener.logContainerConfig != null)
			containerProperties.setLogContainerConfig(properties.listener.logContainerConfig);
		//客户端ID，用来区分日志
		if (properties.listener.clientId != null)
			containerProperties.setClientId(properties.listener.clientId);
		//手动提交模式
		if (properties.listener.ackMode != null)
			containerProperties.setAckMode(properties.listener.ackMode);
		//消费次数后提交
		if (properties.listener.ackCount != null)
			containerProperties.setAckCount(properties.listener.ackCount);
		//消费多少时间后提交
		if (properties.listener.ackTime != null)
			containerProperties.setAckTime(properties.listener.ackTime.toMillis());
		//容器日志开关
		if (properties.listener.logContainerConfig != null)
			containerProperties.setLogContainerConfig(properties.listener.logContainerConfig);

		//自定义设置
		if (setting != null)
			setting.accept(containerProperties);

		return containerProperties;
	}

	/**
	 * 获取监听器容器配置
	 *
	 * @param topic    主题
	 * @param listener 消息监听
	 * @return 监听器容器配置
	 */
	public ContainerProperties getContainerConfig(String topic, GenericMessageListener<?> listener) {
		return this.getContainerConfig(topic, listener, null);
	}

	/**
	 * 获取消费者容器
	 *
	 * @param kafkaConsumerFactory 消费工厂
	 * @param containerProperties  容器配置
	 * @param <K>                  键类型
	 * @param <V>                  值类型
	 * @return 消费者容器
	 */
	public <K, V> ConcurrentMessageListenerContainer<K, V> getContainer(DefaultKafkaConsumerFactory<K, V> kafkaConsumerFactory, ContainerProperties containerProperties) {
		//消费者容器
		var container = new ConcurrentMessageListenerContainer<>(kafkaConsumerFactory, containerProperties);
		//容器线程数，不能大于主题分区数量
		if (properties.listener.concurrency != null)
			container.setConcurrency(properties.listener.concurrency);
		//设置异常处理
		container.setCommonErrorHandler(getErrorHandler());
		return container;
	}

	//endregion

	//region 异常处理

	/**
	 * 获取异常处理对象
	 *
	 * @return 异常处理对象
	 */
	public DefaultErrorHandler getErrorHandler() {
		return new DefaultErrorHandler((data, ex) -> {
			//错误日志
			StringBuilderUtils builder = new StringBuilderUtils();
			builder.appendFormatLine("【Kafka】报错");
			LogHelper.error(ex.getCause(), builder.toString());
		}, new FixedBackOff(1000L, 2));
	}

	//endregion
}