package com.base.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * kafka 自动提交消息 监听
 *
 * @param <K> 键类型
 * @param <T> 值类型
 */
public interface IKafkaAutoListener<K, T> extends IKafkaMessageListener {
	/**
	 * 消息处理
	 *
	 * @param record 消息内容
	 */
	void message(ConsumerRecord<K, T> record);

	/**
	 * 获取消息监听对象
	 *
	 * @return 监听对象
	 */
	default MessageListener<K, T> getListener() {
		return this::message;
	}
}