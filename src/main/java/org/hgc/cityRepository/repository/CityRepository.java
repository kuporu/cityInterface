package org.hgc.cityRepository.repository;

import org.hgc.cityRepository.model.City;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    /**
     * 通过省级id查询城市
     * @param provinceId 省级id
     * @return 省对应的城市列表
     */
    @Query("select province_id, city_id, city_name from city where province_id = :province_id")
    List<City> findAllByProvinceId(@Param("province_id") int provinceId);
}
