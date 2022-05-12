package com.cg.covidalertservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CovidAlertServiceApplication {

		public static void main(String[] args) {
		SpringApplication.run(CovidAlertServiceApplication.class, args);
		
	}
		@Bean
		RestTemplate restTemplate(RestTemplateBuilder builder)
		{
			return builder.build();
		}
	
}
