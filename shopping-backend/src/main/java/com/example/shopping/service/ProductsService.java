package com.example.shopping.service;


import com.example.shopping.model.RepresentativeAccounts;
import com.example.shopping.model.Products;
import com.example.shopping.model.SellingCompanies;
import com.example.shopping.model.ShippingCompanies;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Stateless
public class ProductsService {
    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    public ProductsService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public boolean representativeAddProduct(Products product , int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<RepresentativeAccounts> query = entityManager.createQuery("SELECT r FROM RepresentativeAccounts  r where r.id=:id", RepresentativeAccounts.class);
            query.setParameter("id", id);
            RepresentativeAccounts ra = query.getSingleResult();
            TypedQuery<SellingCompanies> seller = entityManager.createQuery("SELECT r FROM SellingCompanies  r where r.id=:ID", SellingCompanies.class);
            seller.setParameter("ID", ra.getSellingCompanyId());
            SellingCompanies sc = seller.getSingleResult();
            product.setSellingCompany(sc);
            product.setRepresentative(ra);
            entityManager.persist(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

//    View previously sold products, including information about the customers who bought each product and the
//    shipping company.
//    representative id required
    public List<Products> getOfferedProducts(int id){
        TypedQuery<RepresentativeAccounts> representative = entityManager.createQuery("SELECT r FROM RepresentativeAccounts  r where r.id=:id", RepresentativeAccounts.class);
        representative.setParameter("id", id);
        RepresentativeAccounts ra = representative.getSingleResult();
        TypedQuery<SellingCompanies> seller = entityManager.createQuery("SELECT r FROM SellingCompanies  r where r.id=:ID", SellingCompanies.class);
        seller.setParameter("ID", ra.getSellingCompanyId());
        SellingCompanies sc = seller.getSingleResult();
        TypedQuery<Products> query = entityManager.createQuery("SELECT p FROM Products p where p.sellingCompany.id=:iid AND p.isSold = false", Products.class);
        query.setParameter("iid", sc.getId());
        return query.getResultList();
    }
    public int getRepresentativeCompany(int id) {
        TypedQuery<SellingCompanies> query1=entityManager.createQuery("SELECT s FROM SellingCompanies  s", SellingCompanies.class);
        List<SellingCompanies> sc=query1.getResultList();
        for (int i=0;i< sc.size(); i++){
            RepresentativeAccounts[] cr = sc.get(i).getCompanyAccounts().toArray(new RepresentativeAccounts[]{});
            for (int j=0;j< cr.length;j++){
                if(cr[j].getId()==id){
                    return sc.get(i).getId();
                }
                else
                    return 0;
            }
        }
        return 0;
    }

//    public List<Object[]> getSoldProducts(int id){
//
//        Query query = entityManager.createQuery("SELECT U.username , SC.name , P FROM User U INNER JOIN Products P on U.id = P.user.id INNER JOIN SellingCompanies SC on SC.id=:id  where P.isSold=true AND U.id = P.user.id");
//        query.setParameter("id", id);
//        return query.getResultList();
//    }


    public List<Products> getSoldProducts(int id){
        TypedQuery<RepresentativeAccounts> representative = entityManager.createQuery("SELECT r FROM RepresentativeAccounts  r where r.id=:id", RepresentativeAccounts.class);
        representative.setParameter("id", id);
        RepresentativeAccounts ra = representative.getSingleResult();
        TypedQuery<SellingCompanies> seller = entityManager.createQuery("SELECT r FROM SellingCompanies  r where r.id=:ID", SellingCompanies.class);
        seller.setParameter("ID", ra.getSellingCompanyId());
        SellingCompanies sc = seller.getSingleResult();
        TypedQuery<Products> query = entityManager.createQuery("SELECT p FROM Products p where p.sellingCompany.id=:iid AND p.isSold = true", Products.class);
        query.setParameter("iid", sc.getId());
        return query.getResultList();
    }
    public List<Products> getAllProducts() {
        TypedQuery<Products> query = entityManager.createQuery("SELECT p FROM Products p where p.atCart = false AND p.isSold = false", Products.class);
        return query.getResultList();
    }

    public Products getProduct(int id) {
        TypedQuery<Products> query = entityManager.createQuery("SELECT p FROM Products p WHERE p.id = :id", Products.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
