package com.base.tools.json.serializer;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.base.tools.time.DateTimeConst;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDate 反序列化器
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	/**
	 * 枚举类型 反序列化
	 *
	 * @param jsonParser             json解析器
	 * @param deserializationContext 上下文
	 *
	 * @return 枚举类型
	 */
	@Override
	public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		//字符串
		String value = jsonParser.getText();
		if (StrUtil.isBlank(value))
			return null;

		value = value.trim();
		LocalDate result = null;
		if (value.matches("-?\\d+(\\.\\d+)?")) {
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
			result = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		}
		else {
			if (value.length() == DateTimeConst.yyyy_MM_dd.length()) {
				result = LocalDateTimeUtil.parseDate(value, DateTimeConst.yyyy_MM_dd);
			}
			else if (value.length() == DateTimeConst.yyyy_MM_ddHHmmss.length()) {
				result = LocalDateTimeUtil.parseDate(value, DateTimeConst.yyyy_MM_ddHHmmss);
			}
			else if (value.length() == DateTimeConst.yyyy_MM_ddHHmmssSSS.length()) {
				result = LocalDateTimeUtil.parseDate(value, DateTimeConst.yyyy_MM_ddHHmmssSSS);
			}
		}

		return result;
	}
}