package org.hgc.cityRepository.service;

import org.hgc.cityRepository.model.City;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityService {

    /**
     * 通过省级id查询城市
     * @param provinceId 省级id
     * @return 省对应的城市列表
     */
    String findAllByProvinceId(int provinceId);

    /**
     * 保存城市信息
     * @param cities 城市列表
     */
    void saveAll(List<City> cities);
}
