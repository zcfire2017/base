package com.base;

import com.base.tools.json.JsonHelper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

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

	/**
	 * 构造函数
	 *
	 * @param topic      主题
	 * @param valueClass 值对象
	 * @return 对象
	 */
	public static <V> KafkaConstKO<V> create(String topic, Class<V> valueClass) {
		//这里设置序列化时不添加 类信息header，否则反序列化会报错
		var jsonSerializer = new JsonSerializer<V>(JsonHelper.mapper);
		jsonSerializer.setAddTypeInfo(false);
		return new KafkaConstKO<>(topic, jsonSerializer, new JsonDeserializer<>(valueClass, JsonHelper.mapper));
	}

	/**
	 * 构造函数
	 *
	 * @param topic 主题
	 * @return 对象
	 */
	public static KafkaConstKO<String> create(String topic) {
		return new KafkaConstKO<>(topic, new StringSerializer(), new StringDeserializer());
	}
}