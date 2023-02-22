package com.example.MontanaShop.Model;

import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

public class PositionShopingJoinProduct  implements Serializer {
    long id;
    long quantity;
    String name;
    String photo;
    double price;

    public PositionShopingJoinProduct(long id, String name, String photo, double price, long quantity) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.photo = photo;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {

    }
}
