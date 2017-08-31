package io.pivotal.pccpizza.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pizza implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    private String pizzaName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SAUCE_ID")
    private Sauce sauce;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PIZZA_TOPPINGS")
    private Set<Topping> pizzaToppings;

    public Pizza() {}

    @PersistenceConstructor
    public Pizza(String pizzaName, Sauce sauce, Set<Topping> pizzaToppings) {
        this.pizzaName = pizzaName;
        this.sauce = sauce;
        this.pizzaToppings = pizzaToppings;
    }

    public long getId() {
        return id;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public Set<Topping> getPizzaToppings() {
        return pizzaToppings;
    }

    public void setPizzaToppings(Set<Topping> pizzaToppings) {
        this.pizzaToppings = pizzaToppings;
    }
}
