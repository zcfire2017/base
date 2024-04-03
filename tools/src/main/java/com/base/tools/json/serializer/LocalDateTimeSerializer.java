package com.base.tools.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime 序列化器
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	/**
	 * 序列化
	 *
	 * @param localDateTime      日期对象
	 * @param jsonGenerator      json生成器
	 * @param serializerProvider 序列化器
	 * @throws IOException IO异常
	 */
	@Override
	public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		//秒
		Long time = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
		jsonGenerator.writeObject(time);
	}
}