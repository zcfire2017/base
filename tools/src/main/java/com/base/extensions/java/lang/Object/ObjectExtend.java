package com.base.extensions.java.lang.Object;

import com.base.tools.bean.BeanCopyCache;
import com.base.tools.json.JsonHelper;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 对象扩展
 */
@Extension
public class ObjectExtend {
	//region 占比

	/**
	 * 两数占比（百分比）
	 *
	 * @param left  除数
	 * @param right 被除数
	 * @param <R>   被除数类型
	 * @return 占比（百分比）
	 */
	public static <R extends Number> BigDecimal ratio(@This Object left, R right) {
		if (left instanceof Number leftValue) {
			//两数都不能为0
			if (leftValue.equals(0) || right.doubleValue() == 0) {
				return BigDecimal.ZERO;
			}
			return BigDecimal.valueOf(leftValue.doubleValue() * 100 / right.doubleValue()).digit();
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 两数占比（百分比）
	 *
	 * @param left  除数
	 * @param right 被除数
	 * @param digit 保留小数位
	 * @param <R>   被除数类型
	 * @return 占比（百分比）
	 */
	public static <R extends Number> BigDecimal ratio(@This Object left, R right, byte digit) {
		if (left instanceof Number leftValue) {
			//两数都不能为0
			if (leftValue.equals(0) || right.equals(0)) {
				return BigDecimal.ZERO;
			}
			return BigDecimal.valueOf(leftValue.doubleValue() * 100 / right.doubleValue()).digit(digit);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 两数占比
	 *
	 * @param left  除数
	 * @param right 被除数
	 * @param <R>   被除数类型
	 * @return 占比
	 */
	public static <R extends Number> BigDecimal rate(@This Object left, R right) {
		if (right == null) {
			return BigDecimal.ZERO;
		}
		if (left instanceof Number leftValue) {
			//两数都不能为0
			if (leftValue.equals(0) || right.doubleValue() == 0) {
				return BigDecimal.ZERO;
			}
			return BigDecimal.valueOf(leftValue.doubleValue() / right.doubleValue()).digit();
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 两数占比
	 *
	 * @param left  除数
	 * @param right 被除数
	 * @param digit 保留小数位
	 * @param <R>   被除数类型
	 * @return 占比
	 */
	public static <R extends Number> BigDecimal rate(@This Object left, R right, byte digit) {
		if (left instanceof Number leftValue) {
			//两数都不能为0
			if (leftValue.equals(0) || right.equals(0)) {
				return BigDecimal.ZERO;
			}
			return BigDecimal.valueOf(leftValue.doubleValue() / right.doubleValue()).digit(digit);
		}
		return BigDecimal.ZERO;
	}

	//endregion

	/**
	 * 序列化为json字符串
	 *
	 * @param obj 序列化对象
	 * @return json字符串
	 */
	public static String json(@This Object obj) {
		return JsonHelper.ser(obj);
	}

	/**
	 * 转换为map
	 *
	 * @param obj 序列化对象
	 * @return 键值对集合
	 */
	public static Map<String, Object> toMap(@This Object obj) {
		return JsonHelper.ser(obj).jsonDesMap();
	}

	/**
	 * 适配转换对象
	 * 适用于具有相同属性的对象转换
	 *
	 * @param obj    当前对象
	 * @param target 目标对象
	 * @param <T>    目标对象类型
	 */
	public static <T> void adapter(@This Object obj, T target) {
		if (obj == null)
			return;
		//转换
		var copier = BeanCopyCache.get(obj.getClass(), target.getClass());
		copier.copy(obj, target, null);
	}

	/**
	 * 适配转换对象
	 * 适用于具有相同属性的对象转换
	 *
	 * @param obj         当前对象
	 * @param targetClass 目标对象
	 * @param <T>         目标对象类型
	 */
	public static <T> T adapter(@This Object obj, Class<T> targetClass) {
		if (obj == null)
			return null;
		//实例化对象
		var result = BeanUtils.instantiateClass(targetClass);
		//转换
		var copier = BeanCopyCache.get(obj.getClass(), targetClass);
		copier.copy(obj, result, null);

		return result;
	}

	/**
	 * 深度拷贝对象
	 * 只能用于相同类复制
	 *
	 * @param obj 当前对象
	 * @param <T> 拷贝后对象类型
	 * @return 拷贝后对象
	 */
	public static <T> T copy(@This Object obj) {
		if (obj == null)
			return null;
		//实例化对象
		var result = BeanUtils.instantiateClass(obj.getClass());
		//转换
		var copier = BeanCopyCache.get(obj.getClass());
		copier.copy(obj, result, null);
		return (T) result;
	}
}