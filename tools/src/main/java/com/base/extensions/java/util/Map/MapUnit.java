package com.base.extensions.java.util.Map;

import com.base.entity.key_value.KeyValue;
import manifold.ext.rt.api.Extension;

/**
 * 键值对 单位
 */
@Extension
public class MapUnit {
	/**
	 * 键值对 单位
	 */
	public static final MapUnit kv = new MapUnit();


	/**
	 * 后缀单位
	 *
	 * @return Duration
	 */
	public <K, V> KeyValue<K, V> postfixBind(K key) {
		return new KeyValue<>(key, null);
	}
}