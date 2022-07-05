package org.hgc.cityRepository;

import org.hgc.cityRepository.repository.CityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = "org.hgc.cityRepository.repository")
public class CityRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityRepositoryApplication.class, args);
	}

}
