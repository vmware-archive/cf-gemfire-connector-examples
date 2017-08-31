package io.pivotal.pccpizza.controller;

import io.pivotal.pccpizza.model.Pizza;
import io.pivotal.pccpizza.model.Topping;
import io.pivotal.pccpizza.model.Sauce;
import io.pivotal.pccpizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class PizzaController {

    private PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService){
        this.pizzaService = pizzaService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pizza-toppings")
    public ResponseEntity<Set<Topping>> getAllPizzaTopings(){
        return ResponseEntity.ok(pizzaService.getPizzaAllToppings());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sauces")
    public ResponseEntity<Set<Sauce>> getAllSauces(){
        return ResponseEntity.ok(pizzaService.getAllSauces());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pizzas")
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }
}
