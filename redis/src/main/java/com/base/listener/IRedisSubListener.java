package com.base.listener;


import com.base.enums.ERedisOpt;
import com.base.tools.enums.EnumCache;

/**
 * redis 订阅消息监听
 *
 * @param <T> 消息对象
 */
public interface IRedisSubListener<T> {

	/**
	 * 处理消息
	 *
	 * @param message 消息内容
	 * @param channel 主题
	 */
	default void handleMessage(T message, String channel) {
		if (channel.indexOf("-") > 0) {
			var split = channel.split("-");
			onMessage(message, EnumCache.value(ERedisOpt.class, split[1]), split[0]);
		}
		else {
			onMessage(message, channel);
		}
	}

	/**
	 * 接收消息
	 *
	 * @param t       消息内容
	 * @param opt     消息类型
	 * @param channel 主题
	 */
	default void onMessage(T t, ERedisOpt opt, String channel) {
		onMessage(t, channel);
	}

	/**
	 * 接收消息
	 *
	 * @param t       消息内容
	 * @param channel 主题
	 */
	default void onMessage(T t, String channel) {
		onMessage(t);
	}

	/**
	 * 接收消息
	 *
	 * @param t 消息内容
	 */
	default void onMessage(T t) {

	}
}