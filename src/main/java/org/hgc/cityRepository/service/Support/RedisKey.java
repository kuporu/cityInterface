package org.hgc.cityRepository.service.Support;

public interface RedisKey {
    Object generateRedisKey(int...args);
}
