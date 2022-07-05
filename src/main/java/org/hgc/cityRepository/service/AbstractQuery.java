package org.hgc.cityRepository.service;

import org.hgc.cityRepository.service.Support.RedisKey;
import org.hgc.cityRepository.service.Support.SimpleRedisKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/*
定义模板模式
 */
@Component
public abstract class AbstractQuery<D> implements Query{

    /*
    引入RedisKey依赖，默认使用SimpleRedisKey
     */
    private RedisKey redisKey = new SimpleRedisKey();

    public RedisKey getRedisKey() {
        return redisKey;
    }
    public void setRedisKey(RedisKey redisKey) {
        this.redisKey = redisKey;
    }

    /**
     * 抽象方法执行流程
     * @param count 定义几级目录
     *              如localhost:8080/1 为一级目录
     *              localhost:8080/1/2 为二级目录
     *              localhost:8080/1/2/3 为三级目录
     * @param id URL 参数
     * @return 查询结果
     */
    @Override
    public String query(int count, int ...id) {

        /*
        查询Redis
         */
        String redisKey = (String) getRedisKey().generateRedisKey(id);
        String queryFromRedis = queryFromRedis(redisKey);
        if (queryFromRedis != null) {
            return queryFromRedis;
        }

        /*
        查询数据库，以URL中最后一个id作为查询条件（主键）
         */
        String queryFromDB = queryFromDB(id[id.length - 1]);
        if (queryFromDB != null) {
            /*
             放入redis
             */
            pushRedis(redisKey, queryFromDB);
            return queryFromDB;
        }

        /*
        查询网络
         */
        String queryFromWeb = queryFromWeb(id);
        /*
         放入数据库
         */
        pushDB(queryFromWeb, id);

        // 放入redis
        pushRedis(redisKey, queryFromWeb);
        return queryFromWeb;
    }

    /**
     * 从redis中查询
     * @param key key
     * @return redis中查询的数据
     */
    protected abstract String queryFromRedis(String key);

    /**
     * 从数据库中查询
     * @param primaryKey 主键
     * @return 主键对应的记录
     */
    protected abstract String queryFromDB(int primaryKey);

    /**
     * 从网络中查询
     * @param id URL参数
     * @return 网络中记录
     */
    protected abstract String queryFromWeb(int ...id);

    /**
     * 将数据存放入数据库
     * @param webDate
     * @param id
     */
    protected abstract void pushDB(String webDate, int ...id);

    /**
     * 将数据放入redis
     * @param key key
     * @param value value
     */
    protected abstract void pushRedis(String key, String value);
}
