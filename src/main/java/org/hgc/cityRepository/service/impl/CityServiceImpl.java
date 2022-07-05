package org.hgc.cityRepository.service.impl;

import com.alibaba.fastjson.JSON;
import org.hgc.cityRepository.model.City;
import org.hgc.cityRepository.repository.CityRepository;
import org.hgc.cityRepository.service.AbstractQueryFromRedisQuery;
import org.hgc.cityRepository.service.CityService;
import org.hgc.cityRepository.service.AbstractQuery;
import org.hgc.cityRepository.util.Callback;
import org.hgc.cityRepository.util.HttpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl extends AbstractQueryFromRedisQuery<City> implements CityService{

    @Resource
    private CityRepository cityRepository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 通过省级id查询城市，先在数据库中查询，如果没有在联网查询
     * @param provinceId 省级id
     * @return 省对应的城市列表
     */
    @Override
    public String findAllByProvinceId(int provinceId) {
        return query(1, provinceId);
    }

    /**
     * 保存城市信息
     * @param cities 城市列表
     */
    @Override
    public void saveAll(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    @Override
    protected String queryFromDB(int id) {
        List<City> cities = cityRepository.findAllByProvinceId(id);
        if (cities.size() == 0) {
            return null;
        }
        return JSON.toJSONString(cities);
    }

    @Override
    protected String queryFromWeb(int... id) {
        final String[] res = new String[1];
//        Instant beginTime = Instant.now();
        HttpUtil.request("http://guolin.tech/api/china/" + id[0], new Callback() {
            @Override
            public void onFailure(IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(String response) {
                res[0] = response;
            }
        });
//        Instant endTime = Instant.now();
//        logger.info("耗时(毫秒)：" + Duration.between(beginTime, endTime).toMillis());

        return res[0];
    }

    @Override
    protected void pushDB(String webDate, int ...id) {
        List<City> cityList = JSON.parseArray(webDate, City.class);
        List<City> data = cityList.stream().peek((item) -> item.setProvinceId(id[id.length - 1])).collect(Collectors.toList());
        saveAll(data);
    }

    @Override
    protected void pushRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
