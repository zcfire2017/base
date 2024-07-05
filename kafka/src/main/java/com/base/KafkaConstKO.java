package com.base;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * kafka 常量对象
 *
 * @param <V> 值类型
 */
public class KafkaConstKO<V> extends KafkaConstKeyKO<String, V> {
	/**
	 * 构造函数
	 *
	 * @param topic             主题
	 * @param valueSerializer   值序列法方式
	 * @param valueDeserializer 值反序列化方式
	 */
	public KafkaConstKO(String topic, Serializer<V> valueSerializer, Deserializer<V> valueDeserializer) {
		super(topic, new StringSerializer(), new StringDeserializer(), valueSerializer, valueDeserializer, false);
	}
}