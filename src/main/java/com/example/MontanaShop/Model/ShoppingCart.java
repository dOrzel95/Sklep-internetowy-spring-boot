package com.example.MontanaShop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonManagedReference(value="position-shopingcart")
    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PositionShopping> positionShoppings;

    public ShoppingCart() {
    }

    public ShoppingCart(Client client) {
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public List<PositionShopping> getPositionShoppings() {
        return positionShoppings;
    }

    public void setPositionShoppings(List<PositionShopping> positionShoppings) {
        this.positionShoppings = positionShoppings;
    }
}
