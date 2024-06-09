package com.project.ottshare;

import com.project.ottshare.entity.OttRecQuestions;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.repository.OttRecQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;


@EnableJpaAuditing
@SpringBootApplication
public class OttshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(OttshareApplication.class, args);
	}

}

