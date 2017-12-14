package com.barral.undustmypics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.barral.controllers"})
public class UndustmypicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UndustmypicsApplication.class, args);
	}
}
