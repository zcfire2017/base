package com.base.tools.json.serializer;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime 反序列化器
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	/**
	 * 枚举类型 反序列化
	 *
	 * @param jsonParser             json解析器
	 * @param deserializationContext 上下文
	 *
	 * @return 枚举类型
	 **/
	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		//时间戳字符串
		String value = jsonParser.getText();
		if (StrUtil.isBlank(value))
			return null;

		//去掉前后空格
		value = value.trim();
		LocalDateTime result;
		//是否是数字
		if (value.matches("-?\\d+(\\.\\d+)?")) {
			//时间戳
			var time = Long.parseLong(value);
			Instant instant;
			if (value.length() == 10) {
				//10位时间戳 秒级 1612627200
				instant = Instant.ofEpochSecond(time);
			}
			else {
				//13位时间戳 毫秒级 1612627200000
				instant = Instant.ofEpochMilli(time);
			}
			//返回时间格式，采用当地格式
			result = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		}
		else {
			result = LocalDateTimeUtil.parse(value);
		}

		return result;
	}
}