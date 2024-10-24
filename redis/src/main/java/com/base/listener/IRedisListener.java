package com.base.listener;

import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.stream.StreamListener;

public interface IRedisListener<HK, HV extends Record<HK, HV>> extends StreamListener<HK, HV> {
}