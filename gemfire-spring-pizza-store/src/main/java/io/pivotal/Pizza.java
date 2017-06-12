package io.pivotal;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import java.util.Set;

@Region("Pizza")
public class Pizza {
    @Id
    String name;
    Set toppings;
    String sauce;

    public Pizza(String name, Set toppings, String sauce) {
        this.name = name;
        this.toppings = toppings;
        this.sauce = sauce;
    }

    public Pizza() {

    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", toppings=" + toppings +
                ", sauce='" + sauce + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public Set getToppings() {
        return toppings;
    }

    public String getSauce() {
        return sauce;
    }
}
