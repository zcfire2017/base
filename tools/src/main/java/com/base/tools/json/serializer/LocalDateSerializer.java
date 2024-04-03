package com.base.tools.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * LocalDate 序列化器
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
	@Override
	public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		//秒
		Long time = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		jsonGenerator.writeObject(time);
	}
}