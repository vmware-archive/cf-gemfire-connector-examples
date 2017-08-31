package io.pivotal.pccpizza.service;

import io.pivotal.pccpizza.model.Pizza;
import io.pivotal.pccpizza.model.Topping;
import io.pivotal.pccpizza.model.Sauce;
import io.pivotal.pccpizza.repository.jpa.PizzaRepository;
import io.pivotal.pccpizza.repository.jpa.ToppingRepository;
import io.pivotal.pccpizza.repository.jpa.SauceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PizzaService {

    private PizzaRepository pizzaRepository;
    private ToppingRepository toppingRepository;
    private SauceRepository sauceRepository;

    @Autowired
    public PizzaService(
            PizzaRepository pizzaRepository,
            ToppingRepository pizzaToppingRepository,
            SauceRepository sauceRepository
    ){
        super();
        this.pizzaRepository = pizzaRepository;
        this.toppingRepository = pizzaToppingRepository;
        this.sauceRepository = sauceRepository;
    }

    public Set<Topping> getPizzaAllToppings(){
        return new HashSet<Topping>(toppingRepository.findAll());
    }

    public Set<Sauce> getAllSauces(){
        return new HashSet<Sauce>(sauceRepository.findAll());
    }

    public List<Pizza> getAllPizzas(){
        return pizzaRepository.findAll();
    }
}
