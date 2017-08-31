package io.pivotal.pccpizza.repository.gemfire;

import io.pivotal.pccpizza.model.PizzaOrder;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderRepository extends GemfireRepository<PizzaOrder, Long>{
}
