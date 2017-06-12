package io.pivotal;


import org.apache.geode.LogWriter;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@DependsOn({"gemfireCache", "Pizza"})
public class AppController {

    @Autowired
    private ClientCache gemfireCache;

    @Autowired
    private PizzaRepository repository;

    @RequestMapping("/healthcheck")
    public ResponseEntity<Object> healthCheck() {
        LogWriter logger = gemfireCache.getLogger();

        Pizza plainPizza = makePlainPizza();
        Pizza fancyPizza = makeFancyPizza();
        repository.save(plainPizza);
        repository.save(fancyPizza);

        logger.info("Finished inserting the pizzas");

        Pizza found = repository.findOne("plain");
        if (found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!found.toppings.contains("cheese")) {
            logger.info("Where's my cheese? This is the pizza: " + found.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!found.sauce.equals("red")) {
            logger.info("I ordered red sauce!! This is the pizza: " + found.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Pizza makeFancyPizza() {
        Set<String> plainToppings = new HashSet<>();
        plainToppings.add("chicken");
        plainToppings.add("arugula");
        return new Pizza("fancy", plainToppings, "white");
    }

    private Pizza makePlainPizza() {
        Set<String> plainToppings = new HashSet<>();
        plainToppings.add("cheese");
        return new Pizza("plain", plainToppings, "red");
    }

    @RequestMapping("/pizza")
    public Pizza getPizza() {
        Pizza found = repository.findOne("plain");
        return found;
    }
}
