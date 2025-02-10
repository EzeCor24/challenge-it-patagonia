package com.it.patagonia.challenge_it_patagonia;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeItPatagoniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeItPatagoniaApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("ChallengeItPatagonia API")
						.version("1.0.0"));
	}
}
