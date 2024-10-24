package com.base;

import com.base.enums.ERedisOpt;
import com.base.listener.IRedisSubListener;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Redis 发布订阅
 */
public class RedisPubSub<T> extends RedisABS<String, T> {
	/**
	 * 主题
	 */
	private String _topic;

	/**
	 * 操作String对象
	 */
	protected final ValueOperations<String, T> operations;

	/**
	 * 构造方法
	 *
	 * @param factory  redis连接工厂
	 * @param consumer 初始化redis模板
	 */
	protected RedisPubSub(RedisConnectionFactory factory, Consumer<RedisTemplate<String, T>> consumer) {
		super(factory, consumer);
		operations = redisTemplate.opsForValue();
	}

	/**
	 * 构造方法
	 *
	 * @param factory redis连接工厂
	 */
	protected RedisPubSub(RedisConnectionFactory factory) {
		super(factory, null);
		operations = redisTemplate.opsForValue();
	}

	/**
	 * 注册订阅者
	 *
	 * @param listener 自定义消息监听对象
	 * @param consumer 消息监听适配器设置
	 * @param topic    订阅的主题
	 */
	protected void registerSub(IRedisSubListener<T> listener, Consumer<MessageListenerAdapter> consumer, String topic) {
		var adapter = new MessageListenerAdapter(listener);
		consumer.accept(adapter);
		adapter.afterPropertiesSet();
		registerSub(adapter, Set.of(topic));
	}

	/**
	 * 注册订阅者
	 *
	 * @param listener 消息监听对象
	 * @param consumer 消息监听适配器设置
	 * @param topics   订阅的主题集合
	 */
	protected void registerSub(IRedisSubListener<T> listener, Consumer<MessageListenerAdapter> consumer, Collection<String> topics) {
		var adapter = new MessageListenerAdapter(listener);
		consumer.accept(adapter);
		adapter.afterPropertiesSet();
		registerSub(adapter, topics);
	}

	/**
	 * 注册订阅者
	 *
	 * @param listener 消息监听对象
	 * @param topic    订阅的主题
	 */
	protected void registerSub(MessageListener listener, String topic) {
		registerSub(listener, Set.of(topic));
	}

	/**
	 * 注册订阅者
	 *
	 * @param listener 消息监听对象
	 * @param topics   订阅的主题集合
	 */
	protected void registerSub(MessageListener listener, Collection<String> topics) {
		var container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		//循环添加订阅主题
		for (String topic : topics) {
			container.addMessageListener(listener, new PatternTopic(topic));
		}
		container.afterPropertiesSet();
		//开始监听
		container.start();
	}

	/**
	 * 设置发布主题
	 *
	 * @param topic 主题名称
	 */
	public void setTopic(String topic) {
		_topic = topic;
	}

	/**
	 * 推送消息
	 *
	 * @param value 消息内容
	 */
	public void pub(T value) {
		redisTemplate.convertAndSend(_topic, value);
	}

	/**
	 * 推送消息
	 *
	 * @param opt   消息类型
	 * @param value 消息内容
	 */
	public void pub(ERedisOpt opt, T value) {
		redisTemplate.convertAndSend(subTopic(_topic, opt), value);
	}

	/**
	 * 拼接主题
	 *
	 * @param topic 主题名称
	 * @param opt   类型
	 * @return 主题
	 */
	protected String subTopic(String topic, ERedisOpt opt) {
		return topic + "-" + opt.getValue();
	}

	/**
	 * 拼接主题
	 *
	 * @param topic 主题名称
	 * @param opt   类型
	 * @return 主题集合
	 */
	protected Set<String> subTopic(String topic, ERedisOpt... opt) {
		var result = new HashSet<String>();
		for (var o : opt) {
			result.add(subTopic(topic, o));
		}
		return result;
	}
}