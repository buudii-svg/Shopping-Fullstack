package com.example.shopping.service;

import com.example.shopping.model.SellingCompanies;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;


@Stateful
public class SellingCompaniesService {
    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    public SellingCompaniesService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public boolean addCompany(SellingCompanies com){
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

    public ArrayList<SellingCompanies> getAllCompanies(){
        try {
            entityManager.getTransaction().begin();
            ArrayList<SellingCompanies> companies = (ArrayList<SellingCompanies>) entityManager.createQuery("SELECT c FROM SellingCompanies c").getResultList();
            entityManager.getTransaction().commit();
            return companies;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
