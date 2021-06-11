package com.carbon.hospitalitysystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class HospitalitysystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalitysystemApplication.class, args);
	}

}
