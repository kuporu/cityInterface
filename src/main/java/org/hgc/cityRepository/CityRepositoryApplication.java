package org.hgc.cityRepository;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = "org.hgc.cityRepository.repository")
public class CityRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityRepositoryApplication.class, args);
	}

}
