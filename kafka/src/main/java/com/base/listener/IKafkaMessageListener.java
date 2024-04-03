package com.base.listener;

import org.springframework.kafka.listener.GenericMessageListener;

/**
 * kafka消息监听
 */
public interface IKafkaMessageListener {
	/**
	 * 获取消息监听对象
	 *
	 * @return 监听对象
	 */
	default GenericMessageListener<?> getListener() {
		throw new UnsupportedOperationException();
	}
}