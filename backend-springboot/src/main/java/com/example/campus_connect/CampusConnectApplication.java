package com.example.campus_connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Campus Connect API", version = "1.0", description = "API for Campus Connect application"))
public class CampusConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusConnectApplication.class, args);
	}

}
