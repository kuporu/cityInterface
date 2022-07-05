package org.hgc.cityRepository.service;

import org.hgc.cityRepository.model.County;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountyService {

    /**
     * 查询城市下的县级数据
     * @param cityId 城市id
     * @return 县级数据列表
     */
    String findAllByCityId(int provinceId, int cityId);

    /**
     * 保存县信息
     * @param counties
     */
    void saveAll(List<County> counties);
}
