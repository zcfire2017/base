package com.base.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * kafka 手动提交消息 监听
 *
 * @param <K> 键类型
 * @param <T> 值类型
 */
public interface IKafkaHandListener<K, T> extends IKafkaMessageListener {

	/**
	 * 消息处理
	 *
	 * @param data           消息内容
	 * @param acknowledgment 手动提交状态
	 */
	void message(ConsumerRecord<K, T> data, Acknowledgment acknowledgment);

	/**
	 * 获取消息监听对象
	 *
	 * @return 监听对象
	 */
	AcknowledgingMessageListener<K, T> getListener();
}