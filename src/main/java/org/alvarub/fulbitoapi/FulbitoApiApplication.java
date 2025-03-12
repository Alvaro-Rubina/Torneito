package org.alvarub.fulbitoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class FulbitoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulbitoApiApplication.class, args);
	}

}
