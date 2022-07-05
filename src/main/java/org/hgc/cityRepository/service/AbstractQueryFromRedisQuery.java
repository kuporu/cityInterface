package org.hgc.cityRepository.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 原生RedisTemplate实现
 * @param <D>
 */
@Component
public abstract class AbstractQueryFromRedisQuery<D> extends AbstractQuery<D>{

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected String queryFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
