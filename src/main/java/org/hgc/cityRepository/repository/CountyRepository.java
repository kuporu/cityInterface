package org.hgc.cityRepository.repository;

import org.hgc.cityRepository.model.County;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyRepository extends CrudRepository<County, Integer> {

    /**
     * 查询城市下的县级数据
     * @param cityId 城市id
     * @return 县级数据列表
     */
    @Query("select city_id, county_name, county_id, weather_id from county where city_id = :city_id")
    List<County> findAllByCityId(@Param("city_id") int cityId);
}
