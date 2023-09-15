package com.example.shopping.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
@Table(name = "SellingCompanies")
public class SellingCompanies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "company" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<RepresentativeAccounts> companyAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "sellingCompany" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<>();
    public SellingCompanies(String name ) {
        this.name = name;
    }

    public SellingCompanies() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RepresentativeAccounts> getCompanyAccounts() {
        return companyAccounts;
    }

    public void setCompanyAccounts(List<RepresentativeAccounts> companyAccounts) {
        this.companyAccounts = companyAccounts;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

}
