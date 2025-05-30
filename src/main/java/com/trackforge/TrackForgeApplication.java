package com.trackforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.trackforge.repository")
@EntityScan(basePackages = "com.trackforge.entity")
public class TrackForgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackForgeApplication.class, args);
	}

}
