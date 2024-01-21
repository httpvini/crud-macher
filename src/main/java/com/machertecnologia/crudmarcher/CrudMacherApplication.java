package com.machertecnologia.crudmarcher;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(servers = @Server(url = "/macher"))
@SpringBootApplication
public class CrudMacherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudMacherApplication.class, args);
	}

}
