package io.pivotal.pccpizza.service;

import io.pivotal.pccpizza.model.Customer;
import io.pivotal.pccpizza.model.Pizza;
import io.pivotal.pccpizza.model.PizzaOrder;
import io.pivotal.pccpizza.repository.jpa.CustomerRepository;
import io.pivotal.pccpizza.repository.jpa.PizzaOrderRepository;
import io.pivotal.pccpizza.repository.jpa.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaOrderService {

    private PizzaOrderRepository pizzaOrderRepository;
    private io.pivotal.pccpizza.repository.gemfire.PizzaOrderRepository pizzaOrderGemfireRepository;
    private PizzaRepository pizzaRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PizzaOrderService(PizzaOrderRepository pizzaOrderRepository, io.pivotal.pccpizza.repository.gemfire.PizzaOrderRepository pizzaOrderGemfireRepository, PizzaRepository pizzaRepository, CustomerRepository customerRepository) {
        this.pizzaOrderRepository = pizzaOrderRepository;
        this.pizzaOrderGemfireRepository = pizzaOrderGemfireRepository;
        this.pizzaRepository = pizzaRepository;
        this.customerRepository = customerRepository;
    }

    public List<PizzaOrder> getAllOrders(){
        return this.pizzaOrderRepository.findAll();
    }

    @Cacheable(value = "PizzaOrder")
    public PizzaOrder getPizzaOrder(long pizzaOrderId){
        this.simulateSlowService();
        return this.pizzaOrderRepository.findById(pizzaOrderId).orElse(null);
    }

    @CachePut(value = "PizzaOrder")
    public PizzaOrder createOrder (long customerId, long pizzaId){
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Pizza pizza = this.pizzaRepository.findById(pizzaId).get();
        PizzaOrder order = new PizzaOrder(customer, pizza);
        return this.pizzaOrderRepository.save(order);
    }

    @CacheEvict(value = "PizzaOrder")
    public PizzaOrder completePizzaOrder(long pizzaOrderId){
        PizzaOrder pizzaOrder = this.pizzaOrderGemfireRepository.findById(pizzaOrderId).orElseGet(()->this.getPizzaOrder(pizzaOrderId));
        pizzaOrder.setOrderComplete(true);
        return this.pizzaOrderRepository.save(pizzaOrder);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
