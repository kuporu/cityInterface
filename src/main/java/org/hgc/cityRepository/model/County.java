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
public class County implements Serializable {

    /*
    城市id
     */
    @JSONField(serialize = false)
    private Integer cityId;

    /*
    县id
     */
    @Id
    @JSONField(name = "id")
    private Integer countyId;

    /*
    县名称
     */
    @JSONField(name = "name")
    private String countyName;

    /*
    天气接口id
     */
    @JSONField(name = "weather_id")
    private String weatherId;

    /*
    版本号
     */
    @Version
    @JSONField(serialize = false)
    private Integer version;

    public County(Integer cityId, Integer countyId, String countyName, String weatherId) {
        this.cityId = cityId;
        this.countyId = countyId;
        this.countyName = countyName;
        this.weatherId = weatherId;
    }
}
