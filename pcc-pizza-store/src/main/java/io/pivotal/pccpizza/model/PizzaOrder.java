package io.pivotal.pccpizza.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.persistence.*;
import java.io.Serializable;

@Region("PizzaOrder")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PizzaOrder implements Serializable {

    @Id
    @javax.persistence.Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PIZZA_ID")
    private Pizza pizza;

    private boolean orderComplete = false;

    public PizzaOrder() {}

    public PizzaOrder(Customer customer, Pizza pizza) {
        this.customer = customer;
        this.pizza = pizza;
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void setOrderComplete(boolean orderComplete) {
        this.orderComplete = orderComplete;
    }

    public boolean isOrderComplete() {
        return orderComplete;
    }
}
