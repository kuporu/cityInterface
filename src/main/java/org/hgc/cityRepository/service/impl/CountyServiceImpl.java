package org.hgc.cityRepository.service.impl;


import com.alibaba.fastjson.JSON;
import org.hgc.cityRepository.model.County;
import org.hgc.cityRepository.repository.CountyRepository;
import org.hgc.cityRepository.service.AbstractQueryFromRedisQuery;
import org.hgc.cityRepository.service.CountyService;
import org.hgc.cityRepository.service.AbstractQuery;
import org.hgc.cityRepository.util.Callback;
import org.hgc.cityRepository.util.HttpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CountyServiceImpl extends AbstractQueryFromRedisQuery<County> implements CountyService {

    @Resource
    private CountyRepository countyRepository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /*
    先在数据库中查询，如果没有再联网查询
     */
    @Override
    public String findAllByCityId(int provinceId, int cityId) {
        return query(2, provinceId, cityId);
    }

    @Override
    public void saveAll(List<County> counties) {
        countyRepository.saveAll(counties);
    }

    @Override
    protected String queryFromDB(int id) {
        List<County> counties = countyRepository.findAllByCityId(id);
        if (counties.size() == 0) {
            return null;
        }
        return JSON.toJSONString(counties);
    }

    @Override
    protected String queryFromWeb(int... id) {
        String[] res = new String[1];
        HttpUtil.request("http://guolin.tech/api/china/" + id[0] + "/" + id[1], new Callback() {
            @Override
            public void onFailure(IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(String response) {
                res[0] = response;
            }
        });

        return res[0];
    }

    @Override
    protected void pushDB(String webDate, int... id) {
        List<County> countyList = JSON.parseArray(webDate, County.class);
        List<County> data = countyList.stream().peek((item) -> item.setCityId(id[id.length - 1])).collect(Collectors.toList());
        saveAll(data);
    }

    @Override
    protected void pushRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }
}
