package com.base.extensions.java.lang.String;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.base.tools.json.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.List;

/**
 * 字符串扩展
 */
@Extension
public class StringExtends {
	//region 转换

	/**
	 * 字符串转数字
	 *
	 * @param str    字符串
	 * @param tClass 数字类型
	 * @param <T>    数字类型
	 *
	 * @return 数字
	 */
	public static <T extends Number> T parseNumber(@This String str, Class<T> tClass) {
		//判断是否为数字
		if (NumberUtil.isNumber(str)) {
			return Convert.convert(tClass, str);
		}
		return Convert.convert(tClass, 0);
	}

	/**
	 * 字符串转其它类型
	 * 包括（数字，日期等）
	 *
	 * @param str    字符串
	 * @param tClass 数字类型
	 * @param <T>    数字类型
	 *
	 * @return 数字
	 */
	public static <T> T parse(@This String str, Class<T> tClass) {
		return Convert.convert(tClass, str);
	}

	//endregion

	//region 字符串操作

	/**
	 * 判定字符串不为空或空格、空字符串等
	 * (NULL,"", " ") false
	 *
	 * @param str 字符串
	 *
	 * @return 是否不为空
	 */
	public static boolean isNotBlank(@This String str) {
		return StrUtil.isNotBlank(str);
	}

	/**
	 * 判定字符串不为空或空格、空字符串等
	 * (NULL,"", " ") false
	 *
	 * @param str 字符串
	 *
	 * @return 是否不为空
	 */
	public static boolean isNotNullOrEmpty(@This String str) {
		return StrUtil.isNotBlank(str);
	}

	/**
	 * 判定字符串是否为空、空字符、空格等
	 * (NULL,"", " ") true
	 *
	 * @param str 字符串
	 *
	 * @return 是否为空
	 */
	public static boolean isNullOrEmpty(@This String str) {
		return StrUtil.isBlank(str);
	}

	//endregion

	//region json操作

	/**
	 * 反序列化json （单个）
	 *
	 * @param str    json字符串
	 * @param tClass 实体类型对象
	 * @param <T>    实体类型
	 *
	 * @return 实体
	 */
	public static <T> T jsonDes(@This String str, Class<T> tClass) {
		return JsonHelper.des(str, tClass);
	}

	/**
	 * 反序列化json（集合）
	 *
	 * @param str    json字符串
	 * @param tClass 实体类型对象
	 * @param <T>    实体类型
	 *
	 * @return 实体集合
	 */
	public static <T> List<T> jsonDesList(@This String str, Class<T> tClass) {
		return JsonHelper.desList(str, tClass);
	}

	/**
	 * 反序列化json （Map）
	 * Map<String, Object>
	 *
	 * @param str 字符串
	 * @param <T> 泛型实体类型
	 *
	 * @return 带泛型的实体
	 */
	public static <T> T jsonDesMap(@This String str) {
		return JsonHelper.desMap(str);
	}

	/**
	 * 反序列化json（泛型）
	 *
	 * @param str  json字符串
	 * @param type 泛型类型对象
	 * @param <T>  实体类型
	 *
	 * @return 实体
	 */
	public static <T> T jsonDes(@This String str, TypeReference<T> type) {
		return JsonHelper.des(str, type);
	}

	//endregion
}