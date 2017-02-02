package io.pivotal;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import java.io.Serializable;
import java.util.Set;

@Region("Pizza")
public class Pizza implements Serializable {
    private static final long serialVersionUID = 42L;

    public String getName() {
        return name;
    }

    public Set getToppings() {
        return toppings;
    }

    @Id
    String name;
    Set toppings;

    public Pizza(String name, Set toppings) {
        this.name = name;
        this.toppings = toppings;
    }

    /**
     * Default Constructor for Serialization
     */
    public Pizza() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;

        Pizza pizza = (Pizza) o;

        return name.equals(pizza.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
