package com.project.ottshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class OttshareApplication {
	public static void main(String[] args) {
		SpringApplication.run(OttshareApplication.class, args);
	}


}

