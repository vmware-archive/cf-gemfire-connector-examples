package io.pivotal.pccpizza.repository.jpa;

import io.pivotal.pccpizza.model.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaPizzaOrderRepository")
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder,Long> {
}
