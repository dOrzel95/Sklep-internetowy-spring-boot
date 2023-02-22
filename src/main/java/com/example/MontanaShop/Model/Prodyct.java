package com.example.MontanaShop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Prodyct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference(value="product-category")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "id_category")
    private Category category;
    @JsonBackReference(value="client-products")
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "id_client")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Client client;
    private String name;
    private double price;
    private int quantity;
    private String photo;
    private String productDescription;



    @JsonManagedReference(value="position-product")
    @OneToMany(mappedBy = "prodyct", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PositionShopping> positionShopping;




    public long getId() {
        return id;
    }

    public Prodyct(String name, double price, int quantity, String productDescription) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productDescription = productDescription;
    }

//    public Prodyct(long id, String name, double price, int quantity, String photo, String productDescription) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.productDescription = productDescription;
//        this.photo = photo;
//    }

    public Prodyct() {
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setId(long id) {
        this.id = id;
    }

}
