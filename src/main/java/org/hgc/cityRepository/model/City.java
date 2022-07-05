package org.hgc.cityRepository.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table
@Data
@NoArgsConstructor
public class City implements Serializable {
    /*
    省级id
     */
    @JSONField(serialize = false)
    private Integer provinceId;

    /*
    市级名称
     */
    @JSONField(name = "name")
    private String cityName;

    /*
    市级id
     */
    @Id
    @JSONField(name = "id")
    private Integer cityId;

    /*
     版本标识，如果为0或null，那么save执行insert，否者执行update
     */
    @Version
    @JSONField(serialize = false)
    private Integer version;

    public City(Integer provinceId, String cityName, Integer cityId) {
        this.provinceId = provinceId;
        this.cityName = cityName;
        this.cityId = cityId;
    }
}
