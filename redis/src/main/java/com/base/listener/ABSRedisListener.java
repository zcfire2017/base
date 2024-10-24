package com.base.listener;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis 监听抽象类
 */
public abstract class ABSRedisListener extends KeyspaceEventMessageListener {
	/**
	 * 监听主题集合
	 */
	private final List<Topic> topics = new ArrayList<>();

	/**
	 * 构造函数
	 *
	 * @param listenerContainer 监听容器
	 */
	protected ABSRedisListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	/**
	 * 构造函数
	 *
	 * @param listenerContainer 监听容器
	 * @param topicName         主题名称
	 */
	protected ABSRedisListener(RedisMessageListenerContainer listenerContainer, String topicName) {
		this(listenerContainer);
		topics.add(new PatternTopic(topicName));
	}

	/**
	 * 构造函数
	 *
	 * @param listenerContainer 监听容器
	 * @param topicNames        主题名称集合
	 */
	protected ABSRedisListener(RedisMessageListenerContainer listenerContainer, @NotNull String... topicNames) {
		this(listenerContainer);

		for (String topicName : topicNames) {
			topics.add(new PatternTopic(topicName));
		}
	}

	/**
	 * 注册监听
	 *
	 * @param listenerContainer 监听容器
	 */
	protected void doRegister(RedisMessageListenerContainer listenerContainer) {
		listenerContainer.addMessageListener(this, topics);
	}

	/**
	 * 设置键空间监听参数
	 *
	 * @param keyspace 监听参数
	 */
	public void setKeyspaceNotificationsConfigParameter(@NotNull String keyspace) {
		this.keyspaceNotificationsConfigParameter = keyspace;
	}
}