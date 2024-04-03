package com.base.tools.json.serializer;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.base.tools.enums.entity.ByteEnum;
import com.base.tools.enums.entity.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * 枚举自定义序列化
 */
public class EnumJsonSerializer {
	/**
	 * 序列化对象
	 */
	public static JsonSerializer<IEnum<?>> serializer = new Serializer();

	/**
	 * 反序列化对象
	 */
	public static JsonDeserializer<ByteEnum> deserializer = new Deserializer<>();

	/**
	 * 序列化
	 */
	private static class Serializer extends JsonSerializer<IEnum<?>> {

		/**
		 * 获取类型对象
		 *
		 * @return 类型对象
		 */
		public Class<IEnum<?>> handledType() {
			return Convert.convert(new TypeReference<>() {}, IEnum.class);
		}

		/**
		 * 序列化
		 *
		 * @param anEnum             枚举对象
		 * @param jsonGenerator      json生成器
		 * @param serializerProvider 序列化器
		 * @throws IOException IO异常
		 */
		@Override
		public void serialize(IEnum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
			jsonGenerator.writeObject(anEnum.getValue());
		}
	}

	/**
	 * 反序列化
	 *
	 * @param <T> 泛型
	 */
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Deserializer<T extends IEnum<?>> extends JsonDeserializer<T> implements ContextualDeserializer {
		/**
		 * 枚举类型对象
		 */
		private Class<T> enumClass;

		/**
		 * 枚举类型 反序列化
		 *
		 * @param p       json解析器
		 * @param context 上下文
		 * @return 枚举类型
		 */
		@Override
		public T deserialize(JsonParser p, DeserializationContext context) throws IOException {
			//枚举值
			var value = p.getText();
			if (StrUtil.isBlank(value)) {
				return null;
			}
			for (T enumObj : enumClass.getEnumConstants()) {
				if (value.equals(enumObj.getValue().toString())) {
					return enumObj;
				}
			}
			return null;
		}

		/**
		 * 创建上下文
		 *
		 * @param deserializationContext 反序列化上下文
		 * @param beanProperty           实体属性
		 * @return 上下文
		 */
		@Override
		public JsonDeserializer<T> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
			//泛型类型
			Class<T> cls = Convert.convert(new TypeReference<>() {}, beanProperty.getType().getRawClass());
			return new Deserializer<>(cls);
		}
	}
}