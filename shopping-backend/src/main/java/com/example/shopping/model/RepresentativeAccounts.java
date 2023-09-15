package com.example.shopping.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RepresentativeAccounts")
public class RepresentativeAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    private String role="selling" ;
    @ManyToOne
    @JoinColumn(name = "selling_company_id")
    private SellingCompanies company;

    @OneToMany(mappedBy = "representative" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<>();

    public RepresentativeAccounts() {
        super();
    }

    public RepresentativeAccounts(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompany(SellingCompanies company) {
        this.company = company;

    }

    public int getId() {
        return id;
    }

    public String getSellingCompany() {
        return company.getName();
    }

    public int getSellingCompanyId() {
        return company.getId();
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
//    public ShippingCompanies getShippingCompany() {
//        return shippingCompany;
//    }
//
//    public void setShippingCompany(ShippingCompanies shippingCompany) {
//        this.shippingCompany = shippingCompany;
//    }
}
