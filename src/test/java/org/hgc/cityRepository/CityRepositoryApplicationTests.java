package org.hgc.cityRepository;

import org.hgc.cityRepository.model.City;
import org.hgc.cityRepository.model.County;
import org.hgc.cityRepository.service.CityService;
import org.hgc.cityRepository.service.CountyService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CityRepositoryApplicationTests {

	public static final Logger LOG = LoggerFactory.getLogger(CityRepositoryApplicationTests.class);

	@Resource
	private CityService cityService;

	@Resource
	private CountyService countyService;

	@Resource
	private RedisTemplate redisTemplate;

	/*
	测试通过省份查询城市接口
	 */
	@Test
	void selectCities() {
		String cities = cityService.findAllByProvinceId(1);
	}

	/*
	测试通过城市查询县接口
	 */
	@Test
	void selectCounty() {
		String counties = countyService.findAllByCityId(0,2);
	}

	/*
	测试保存城市信息
	Spring Data JDBC- 使用自定义ID：https://spring.io/blog/2021/09/09/spring-data-jdbc-how-to-use-custom-id-generation
	 */
	@Test
	void saveCities() {
		List<City> cities = new ArrayList<>(2);
		City chengdu = new City(2, "chengdu", 3);
		City mianyang = new City(2, "mianyang", 4);
		cities.add(chengdu);
		cities.add(mianyang);
		cityService.saveAll(cities);
	}

	/*
	测试批量保存县信息
	 */
	@Test
	void saveCounties() {
		List<County> counties = new ArrayList<>(2);
		County dayi = new County(3, 1, "大邑县", "CN202021110");
		County shuangliu = new County(3, 2, "双流县", "CN202023411");
		counties.add(dayi);
		counties.add(shuangliu);
		countyService.saveAll(counties);
	}

	/*
	测试redis功能
	 */
	@Test
	void testRedis () {
		//操作redis中字符串 opsForValue实际操作就是redis中的String类型
		List<City> cities = new ArrayList<>(2);
		City chengdu = new City(1, "成都", 1);
		City mianyang = new City(1, "绵阳", 2);
		cities.add(chengdu);
		cities.add(mianyang);
		redisTemplate.opsForValue().set("cities", cities);
		redisTemplate.opsForValue().set("city",chengdu);
		System.out.println("测试value为list: " + redisTemplate.opsForValue().get("cities"));
		System.out.println("测试key, value为空: " + (String) redisTemplate.opsForValue().get("oo"));
		City city = (City) redisTemplate.opsForValue().get("city");

		//设置该key的过期时间
		redisTemplate.opsForValue().set("k3", chengdu);
		redisTemplate.expire("k3", 60, TimeUnit.SECONDS);
		System.out.println(redisTemplate.getExpire("k3"));

		//获取所有的key
		Set<String> keys = redisTemplate.keys("*");
		System.out.println(keys);
	}

	@Test
	public void testLog4j2 () {
		LOG.error("error test");
		LOG.warn("warn test");
		LOG.info("info test");
	}

	@Test
	public void JSONParse () throws JSONException {
		// json字符串
		String jsonString1 = "{\"city\":[{\"cityName\":\"成都\"},{\"cityName\":\"绵阳\"}]}";
		JSONObject jsonObject = new JSONObject(jsonString1);
		JSONArray city = new JSONArray(jsonObject.getString("city"));

		String jsonString2 = "{\"city\":\"[{\\\"cityName\\\":\\\"成都\\\"},{\\\"cityName\\\":\\\"绵阳\\\"}]\"}";
		JSONObject jsonObject2 = new JSONObject(jsonString2);
		JSONArray city2 = new JSONArray(jsonObject2.getString("city"));
	}
}
