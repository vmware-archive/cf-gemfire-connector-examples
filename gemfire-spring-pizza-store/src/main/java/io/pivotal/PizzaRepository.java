package io.pivotal;

import org.springframework.data.gemfire.repository.GemfireRepository;

public interface PizzaRepository extends GemfireRepository<Pizza, String> {
}
