package com.base.listener;

import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public abstract class RedisDeleteListener extends ABSRedisListener {
	/**
	 * 构造函数
	 *
	 * @param listenerContainer 监听容器
	 */
	protected RedisDeleteListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer, "__keyevent@*__:del");
	}
}