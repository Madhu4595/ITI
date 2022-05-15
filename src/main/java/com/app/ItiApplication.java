package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ItiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ItiApplication.class, args);
	}

}
