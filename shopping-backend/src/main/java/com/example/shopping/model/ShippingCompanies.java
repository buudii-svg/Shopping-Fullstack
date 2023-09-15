package com.example.shopping.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ShippingCompanies")
public class ShippingCompanies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String geographicalCoverage;

//    @JsonManagedReference(value="shipping-company")
//    @OneToMany(mappedBy = "shippingCompany" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
//    private List<CompaniesAccounts> companyAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "shippingCompany" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "shippingCompanies" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Products> product = new ArrayList<>();

    public ShippingCompanies(String name , String geographicalCoverage) {
        this.name = name;
        this.geographicalCoverage = geographicalCoverage;
    }

    public ShippingCompanies() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }

    public String getGeographicalCoverage() {
        return geographicalCoverage;
    }

    public void setGeographicalCoverage(String geographicalCoverage) {
        this.geographicalCoverage = geographicalCoverage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }
}


