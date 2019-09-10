package com.example.displayMessageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@SpringBootApplication
public class DisplayMessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisplayMessageServiceApplication.class, args);
	}

}
