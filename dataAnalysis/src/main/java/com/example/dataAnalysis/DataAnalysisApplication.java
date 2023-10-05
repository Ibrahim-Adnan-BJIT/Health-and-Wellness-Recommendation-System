package com.example.dataAnalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DataAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAnalysisApplication.class, args);
	}

}
