package com.omnicuris.falcon.model;

import javax.persistence.*;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="quantity")
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity += quantity;
    }
}
