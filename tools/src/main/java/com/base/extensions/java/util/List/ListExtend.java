package com.base.extensions.java.util.List;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.ThisClass;

import java.util.ArrayList;
import java.util.List;

/**
 * List扩展
 */
@Extension
public class ListExtend {
	/**
	 * 初始化可变集合
	 *
	 * @param theClass List对象
	 * @param value    值
	 * @param <E>      值类型
	 * @return ArrayList对象
	 */
	@SafeVarargs
	public static @Self <E> List<E> on(@ThisClass Class<List<E>> theClass, E... value) {
		var list = new ArrayList<E>(value.length);
		for (var i = 0; i < value.length; i++) {
			list.add(i, value[i]);
		}
		return list;
	}
}