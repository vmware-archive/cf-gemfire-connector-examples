package io.pivotal;

import org.springframework.data.gemfire.repository.GemfireRepository;

@SuppressWarnings("unused")
public interface PizzaRepository extends GemfireRepository<Pizza, String>{
}
