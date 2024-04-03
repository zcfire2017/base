package com.base.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * kafka 批量手动提交消息 监听
 *
 * @param <K> 键类型
 * @param <T> 值类型
 */
public interface IKafkaBatchHandListener<K, T> extends IKafkaMessageListener {
	/**
	 * 消息处理
	 *
	 * @param data           消息内容
	 * @param acknowledgment 手动提交状态
	 */
	void message(List<ConsumerRecord<K, T>> data, Acknowledgment acknowledgment);

	/**
	 * 获取消息监听对象
	 *
	 * @return 监听对象
	 */
	default BatchAcknowledgingMessageListener<K, T> getListener() {
		return this::message;
	}
}