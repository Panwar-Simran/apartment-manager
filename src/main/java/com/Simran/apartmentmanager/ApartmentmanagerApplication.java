package com.Simran.apartmentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApartmentmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentmanagerApplication.class, args);
	}

}
