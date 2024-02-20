package com.spring.SpeedAuction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SpeedAuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeedAuctionApplication.class, args);
	}

}
