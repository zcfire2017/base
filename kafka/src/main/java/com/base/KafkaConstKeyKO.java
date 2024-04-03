package com.base;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import manifold.ext.props.rt.api.var;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

/**
 * kafka 常量对象
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@AllArgsConstructor
@NoArgsConstructor
public class KafkaConstKeyKO<K, V> {
	/**
	 * 主题名称
	 */
	@var String topic;

	/**
	 * 键序列化对象
	 */
	@var Serializer<K> keySerializer;

	/**
	 * 键反序列化对象
	 */
	@var Deserializer<K> keyDeserializer;

	/**
	 * 值序列化对象
	 */
	@var Serializer<V> valueSerializer;

	/**
	 * 值反序列化对象
	 */
	@var Deserializer<V> valueDeserializer;
}