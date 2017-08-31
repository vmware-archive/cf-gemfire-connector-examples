package io.pivotal.pccpizza.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Topping implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String toppingName;

    public long getId() {
        return id;
    }

    public Topping() {}

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }
}