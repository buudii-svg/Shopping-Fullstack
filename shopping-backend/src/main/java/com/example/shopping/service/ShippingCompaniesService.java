package com.example.shopping.service;

import com.example.shopping.model.SellingCompanies;
import com.example.shopping.model.ShippingCompanies;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;


@Stateless
public class ShippingCompaniesService {
    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    public ShippingCompaniesService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public boolean addCompany(ShippingCompanies com){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(com);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public ArrayList<ShippingCompanies> getAllCompanies(){
        try {
            entityManager.getTransaction().begin();
            ArrayList<ShippingCompanies> companies = (ArrayList<ShippingCompanies>) entityManager.createQuery("SELECT c FROM ShippingCompanies c").getResultList();
            entityManager.getTransaction().commit();
            return companies;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
