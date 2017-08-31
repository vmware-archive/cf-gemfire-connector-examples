package io.pivotal.pccpizza.controller.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by pivotal on 8/28/17.
 */
public class PizzaOrderRequest {
    private long customerId;
    private long pizzaId;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(long pizzaId) {
        this.pizzaId = pizzaId;
    }
}
