package com.base.entity.enum_flags;

import cn.hutool.core.convert.Convert;
import com.base.tools.enums.EnumCache;
import com.base.tools.enums.entity.IntEnum;
import lombok.Getter;

import java.util.EnumSet;

/**
 * 枚举复合标识
 *
 * @param <T>
 */
public class Flags<T extends Enum<T> & IntEnum> {

	/**
	 * 标识值
	 */
	@Getter
	private long flag = 0;

	/**
	 * 枚举集合
	 */
	@Getter
	private final EnumSet<T> flags;

	/**
	 * 枚举类信息
	 */
	private final Class<T> enumType;

	/**
	 * 构造函数
	 *
	 * @param enumType 枚举类型
	 */
	public Flags(Class<T> enumType) {
		this.flags = EnumSet.noneOf(enumType);
		this.enumType = enumType;
	}

	/**
	 * 初始化
	 *
	 * @param flags 枚举集合
	 * @param <E>   枚举类型
	 *
	 * @return 枚举复合标识对象
	 */
	public static <E extends Enum<E> & IntEnum> Flags<E> of(E... flags) {
		var flg = new Flags<>((Class<E>) flags[0].getClass());
		flg.add(flags);
		return flg;
	}

	/**
	 * 初始化
	 *
	 * @param tClass 枚举类型
	 * @param value  标识值
	 * @param <E>    枚举类型
	 *
	 * @return 枚举复合标识对象
	 */
	public static <E extends Enum<E> & IntEnum> Flags<E> of(Class<E> tClass, long value) {
		var flg = new Flags<>(tClass);
		flg.add(value);
		return flg;
	}

	/**
	 * 添加枚举
	 *
	 * @param ems 枚举集合
	 */
	@SafeVarargs
	public final void add(T... ems) {
		//循环添加枚举
		for (var em : ems) {
			if (!has(em)) {
				flags.add(em);
				//计算标识值
				flag |= em.getValue();
			}
		}
	}

	/**
	 * 添加值
	 *
	 * @param value 值
	 */
	public void add(long value) {
		//枚举所有值
		var values = EnumCache.valueMap(enumType);
		//循环所有值
		for (var kv : values.entrySet()) {
			//当前值
			var cv = Convert.convert(Long.class, kv.getKey());
			if (cv > value) {
				break;
			}
			//包含key
			if ((value & cv) == cv) {
				flags.add((T) kv.getValue());
			}
		}
	}

	/**
	 * 移除枚举
	 *
	 * @param em 枚举信息
	 */
	public void remove(T em) {
		flags.remove(em);
		flag &= (~em.getValue());
	}

	/**
	 * 是否包含枚举
	 *
	 * @param em 枚举
	 *
	 * @return 是否包含
	 */
	public boolean has(T em) {
		return (flag & em.getValue()) != 0;
	}
}