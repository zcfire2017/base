package com.base.tools.bean;

import cn.hutool.core.lang.SimpleCache;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 复制对象缓存对象
 */
public class BeanCopyCache {
	/**
	 * 复制对象缓存集合
	 */
	private final static SimpleCache<String, BeanCopier> CACHE = new SimpleCache<>();

	/**
	 * 获取复制对象缓存
	 *
	 * @param source 源对象
	 * @param target 目标对象
	 * @return 复制对象
	 */
	public static BeanCopier get(Class<?> source, Class<?> target) {
		//对象标识
		var key = genKey(source.getName(), target.getName());
		return CACHE.get(key, () -> BeanCopier.create(source, target, false));
	}

	/**
	 * 获取复制对象缓存
	 *
	 * @param source 源对象
	 * @return 复制对象
	 */
	public static BeanCopier get(Class<?> source) {
		//对象标识
		var key = genKey(source.getName(), source.getName());
		return CACHE.get(key, () -> BeanCopier.create(source, source, false));
	}

	/**
	 * 生成对象唯一标识
	 *
	 * @param sourceName 源对象名称
	 * @param targetName 目标对象名称
	 * @return 唯一标识
	 */
	private static String genKey(String sourceName, String targetName) {
		return sourceName + "-" + targetName;
	}
}