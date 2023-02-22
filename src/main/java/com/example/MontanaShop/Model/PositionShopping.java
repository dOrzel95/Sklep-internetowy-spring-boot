package com.example.MontanaShop.Model;


import com.example.MontanaShop.Model.Prodyct;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "item_in_the_shoppingcart")
public class PositionShopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonBackReference(value="position-shopingcart")
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "id_shopping_cart")
    ShoppingCart shoppingCart;

    @JsonBackReference(value="position-product")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    Prodyct prodyct;
    private int quantity;


    public void setId(long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getId() {
        return id;
    }

    public Prodyct getProdyct() {
        return prodyct;
    }

    public PositionShopping(ShoppingCart shoppingCart, Prodyct prodyct) {
        this.shoppingCart = shoppingCart;
        this.prodyct = prodyct;
    }

    public PositionShopping() {
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setProdyct(Prodyct prodyct) {
        this.prodyct = prodyct;
    }

}
