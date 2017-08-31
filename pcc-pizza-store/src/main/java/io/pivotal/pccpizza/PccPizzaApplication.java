package io.pivotal.pccpizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PccPizzaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PccPizzaApplication.class, args);
	}
}
