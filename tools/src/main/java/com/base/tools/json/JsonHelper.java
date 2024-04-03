package com.base.tools.json;

import com.base.tools.json.serializer.*;
import com.base.tools.log.LogHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Json Helper
 */
public class JsonHelper {
	/**
	 * 序列化配置
	 */
	public static final ObjectMapper mapper = new ObjectMapper();

	/*
	 * 静态加载
	 */
	static {
		//允许json属性名不使用双引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//日期序列化
		mapper.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false);
		//忽略不存在字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//忽略为空的对象
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		//序列化配置
		var simpleModule = new SimpleModule();
		//枚举（用JsonValue），这里不用单独写
		//simpleModule.addSerializer(EnumJsonSerializer.serializer);
		//simpleModule.addDeserializer(ByteEnum.class, EnumJsonSerializer.deserializer);
		//日期时间
		simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		//日期
		simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		//时间
		simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		//Long转为String，防止前端精度丢失
		simpleModule.addSerializer(Long.class, new LongSerializer());
		//BigInteger转为String，防止前端精度丢失
		simpleModule.addSerializer(BigInteger.class, new BigIntegerSerializer());

		//注册模型
		mapper.registerModule(simpleModule);
	}

	/**
	 * 序列化
	 *
	 * @param obj 对象
	 * @param <T> 对象类型
	 * @return json字符串
	 */
	public static <T> String ser(T obj) {
		if (obj == null)
			return "";
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LogHelper.error(e);
		}
		return "";
	}

	/**
	 * 反序列化（单个实体）
	 *
	 * @param jsonString json字符串
	 * @param clazz      实体类型对象
	 * @param <T>        实体类型
	 * @return 实体
	 */
	public static <T> T des(String jsonString, Class<T> clazz) {
		try {
			if (jsonString.isNullOrEmpty())
				return null;
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return null;
	}

	/**
	 * 反序列化（泛型对象）
	 *
	 * @param jsonString    json字符串
	 * @param typeReference 泛型类型
	 * @param <T>           泛型类型
	 * @return 泛型对象
	 */
	public static <T> T des(String jsonString, TypeReference<T> typeReference) {
		try {
			if (jsonString.isNullOrEmpty())
				return null;
			return mapper.readValue(jsonString, typeReference);
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return null;
	}

	/**
	 * 反序列化（集合）
	 *
	 * @param jsonString json字符串
	 * @param clazz      实体类型对象
	 * @param <T>        实体类型
	 * @return 实体集合
	 */
	public static <T> List<T> desList(String jsonString, Class<T> clazz) {
		try {
			CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
			return mapper.readValue(jsonString, listType);
		} catch (IOException e) {
			LogHelper.error(e);
		}
		return new ArrayList<>();
	}

	/**
	 * 反序列化（map对象）
	 *
	 * @param jsonString json字符串
	 * @param <T>        泛型类型
	 * @return map对象
	 */
	public static <T> T desMap(String jsonString) {
		try {
			if (jsonString.isNullOrEmpty())
				return null;
			return mapper.readValue(jsonString, new TypeReference<>() {});
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return null;
	}

	/**
	 * 序列化方式转换对象（单对象）
	 *
	 * @param original 要转换的对象
	 * @param target   转换的对象类型
	 * @param <T>      要转换的对象类型
	 * @param <R>      转换对象的类型
	 * @return 转换后的对象
	 */
	public static <T, R> R convert(T original, Class<R> target) {
		return mapper.convertValue(original, target);
	}

	/**
	 * 序列化方式转换对象（集合）
	 *
	 * @param original 要转换的对象集合
	 * @param target   转换的对象类型
	 * @param <T>      要转换的对象类型
	 * @param <R>      转换对象的类型
	 * @return 转换后的对象
	 */
	public static <T, R> List<R> convert(List<T> original, Class<R> target) {
		var listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, target);
		return mapper.convertValue(original, listType);
	}
}