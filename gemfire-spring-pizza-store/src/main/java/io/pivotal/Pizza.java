package io.pivotal;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import java.io.Serializable;
import java.util.Set;

@Region("Pizza")
public class Pizza implements Serializable {
    @Id
    String name;
    Set toppings;

    public Pizza(String name, Set toppings) {
        this.name = name;
        this.toppings = toppings;
    }

    @SuppressWarnings("unused") // required for PDX serialization
    public Pizza() {
    }

    public String getName() {
        return name;
    }

    public Set getToppings() {
        return toppings;
    }
}
