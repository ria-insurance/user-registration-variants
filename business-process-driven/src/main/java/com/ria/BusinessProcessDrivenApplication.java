package com.ria;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
		//(exclude = {DataSourceAutoConfiguration.class })
@EnableProcessApplication
public class BusinessProcessDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessProcessDrivenApplication.class, args);
	}
}
