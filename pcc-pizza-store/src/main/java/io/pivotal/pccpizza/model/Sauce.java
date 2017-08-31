package io.pivotal.pccpizza.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Sauce implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String sauceName;

    public long getId() {
        return id;
    }

    public Sauce() {}

    public String getSauceName() {
        return sauceName;
    }

    public void setSauceName(String sauceName) {
        this.sauceName = sauceName;
    }
}
