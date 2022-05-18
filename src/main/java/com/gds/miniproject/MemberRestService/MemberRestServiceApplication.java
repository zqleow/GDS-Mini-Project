package com.gds.miniproject.MemberRestService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MemberRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberRestServiceApplication.class, args);
	}

}
