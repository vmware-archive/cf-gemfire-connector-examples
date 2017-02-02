package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@EnableGemfireRepositories
public class CfGemfireTestAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfGemfireTestAppApplication.class, args);
    }
}
