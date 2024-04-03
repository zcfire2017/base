package com.base;

import com.base.listener.IKafkaMessageListener;
import com.base.tools.log.LogHelper;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;
import java.util.function.Consumer;

/**
 * kafka 操作抽象类
 *
 * @param <K> 键类型
 * @param <T> 消息对象类型
 */
public abstract class ABSKafkaKey<K, T> implements ApplicationRunner, IKafkaMessageListener {
	/**
	 * kafka配置
	 */
	@Resource
	private KafkaConfigUtil config;

	/**
	 * 操作对象信息
	 */
	private KafkaConstKeyKO<K, T> kafkaInfo;

	/**
	 * 注册
	 */
	protected abstract void register();

	/**
	 * 设置kafka信息
	 *
	 * @param kafkaInfo kafka信息
	 */
	protected void setKafka(KafkaConstKeyKO<K, T> kafkaInfo) {
		this.kafkaInfo = kafkaInfo;
	}

	/**
	 * 启动后执行
	 *
	 * @param args 参数
	 */
	@Override
	public void run(ApplicationArguments args) {
		//未开启kafka
		if (openKafka())
			return;

		register();
	}

	//region 生产者

	/**
	 * 生产者
	 */
	private KafkaTemplate<K, T> producer;

	/**
	 * 注册生产者
	 */
	protected void registerProducer() {
		this.registerProducer(null);
	}

	/**
	 * 注册生产者
	 *
	 * @param setting 自定义配置
	 */
	protected void registerProducer(Consumer<Map<String, Object>> setting) {
		//未开启kafka
		if (openKafka())
			return;

		if (validKafkaInfo())
			return;

		producer = new KafkaTemplate<>(config.producer(kafkaInfo.keySerializer, kafkaInfo.valueSerializer, setting));
	}

	/**
	 * 发送消息
	 *
	 * @param message 消息内容
	 */
	public void send(T message) {
		//未开启kafka
		if (openKafka())
			return;

		//生产者无效
		if (validProducer())
			return;

		producer.send(kafkaInfo.topic, message);
	}

	/**
	 * 发送消息
	 *
	 * @param key     键
	 * @param message 消息内容
	 */
	public void send(K key, T message) {
		//未开启kafka
		if (openKafka())
			return;

		//生产者无效
		if (validProducer())
			return;

		producer.send(kafkaInfo.topic, key, message);
	}

	//endregion

	//region 消费者

	/**
	 * 注册消费者
	 *
	 * @param containerSetting 容器属性配置
	 * @param consumerSetting  消费者自定义配置
	 */
	protected void registerConsumer(Consumer<ContainerProperties> containerSetting, Consumer<Map<String, Object>> consumerSetting) {
		//未开启kafka
		if (openKafka())
			return;

		if (validKafkaInfo())
			return;

		//消息监听对象
		var listener = this.getListener();
		//消费者工厂
		var factory = config.consumer(kafkaInfo.keyDeserializer, kafkaInfo.valueDeserializer, consumerSetting);
		//容器属性配置
		var containerProperties = config.getContainerConfig(kafkaInfo.topic, listener, containerSetting);
		//消费容器
		var container = config.getContainer(factory, containerProperties);
		//开启消费
		container.start();
	}


	/**
	 * 注册消费者
	 *
	 * @param containerSetting 容器属性配置
	 */
	protected void registerConsumer(Consumer<ContainerProperties> containerSetting) {
		this.registerConsumer(containerSetting, null);
	}

	/**
	 * 注册消费者
	 *
	 * @param consumerSetting 消费者自定义配置
	 */
	protected void registerConsumerConfig(Consumer<Map<String, Object>> consumerSetting) {
		this.registerConsumer(null, consumerSetting);
	}

	/**
	 * 注册消费者
	 */
	protected void registerConsumer() {
		this.registerConsumer(null, null);
	}

	//endregion

	//region 验证

	/**
	 * 是否开启kafka
	 *
	 * @return 是否开启
	 */
	private Boolean openKafka() {
		return config == null || (config.consumerConfigs.isEmpty() && config.producerConfigs.isEmpty());
	}

	/**
	 * 验证kafka信息
	 *
	 * @return 是否通过
	 */
	private Boolean validKafkaInfo() {
		if (kafkaInfo == null) {
			LogHelper.error("${this.getClass().name}：未初始化kafkaInfo");
			return true;
		}
		return false;
	}

	/**
	 * 验证生产者
	 *
	 * @return 是否通过
	 */
	private Boolean validProducer() {
		//生产者无效
		if (producer == null) {
			LogHelper.error("${kafkaInfo.topic}：生产者未注册...");
			return true;
		}
		return false;
	}

	//endregion
}