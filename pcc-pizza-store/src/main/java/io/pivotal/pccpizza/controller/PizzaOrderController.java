package io.pivotal.pccpizza.controller;

import io.pivotal.pccpizza.controller.payload.PizzaOrderRequest;
import io.pivotal.pccpizza.model.PizzaOrder;
import io.pivotal.pccpizza.service.PizzaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaOrderController {

    private PizzaOrderService pizzaOrderService;

    @Autowired
    public PizzaOrderController(PizzaOrderService pizzaOrderService){
        this.pizzaOrderService = pizzaOrderService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create-order", headers="Accept=application/json")
    public ResponseEntity<PizzaOrder> createOrder(@RequestBody PizzaOrderRequest pizzaOrderRequest){
        return ResponseEntity.ok(pizzaOrderService.createOrder(pizzaOrderRequest.getCustomerId(), pizzaOrderRequest.getPizzaId()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-order/{orderNumber}")
    public ResponseEntity<PizzaOrder> getOrder(@PathVariable long orderNumber){
        return ResponseEntity.ok(pizzaOrderService.getPizzaOrder(orderNumber));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/complete-order/{orderNumber}")
    public ResponseEntity<PizzaOrder> completeOrder(@PathVariable long orderNumber){
        return ResponseEntity.ok(pizzaOrderService.completePizzaOrder(orderNumber));
    }
}
