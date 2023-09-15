package com.example.shopping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String price;

    private boolean isSold = false;
    private boolean atCart = false;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "representative_id")
    private RepresentativeAccounts representative;

    @ManyToOne
    @JoinColumn(name = "selling_id")
    private SellingCompanies sellingCompany;

    @ManyToMany(mappedBy = "products")
    private List<Orders> orders = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private ShippingCompanies shippingCompanies;


    public Products() {
        super();
    }

    public Products(String name , String price) {
        super();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setShippingCompanies(ShippingCompanies shippingCompanies) {
        this.shippingCompanies = shippingCompanies;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isAtCart() {
        return atCart;
    }
    public void setAtCart(boolean atCart) {
        this.atCart = atCart;
    }
    public void setSellingCompany(SellingCompanies sellingCompany) {
        this.sellingCompany = sellingCompany;
    }
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public ShippingCompanies getShippingCompanies() {
        return shippingCompanies;
    }

    public User getUser() {
        return user;
    }

    public void setRepresentative(RepresentativeAccounts representative) {
        this.representative = representative;
    }
}
