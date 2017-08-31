package io.pivotal.pccpizza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.pivotal.pccpizza.repository.jpa")
public class JpaConfiguration {
}
