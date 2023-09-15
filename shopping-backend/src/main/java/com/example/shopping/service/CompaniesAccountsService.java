package com.example.shopping.service;

import com.example.shopping.model.RepresentativeAccounts;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Stateless
public class CompaniesAccountsService implements Auth<RepresentativeAccounts>{

    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    private static final String SECRET_KEY = "mySecretKey123";

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
    private static final int PASSWORD_LENGTH = 10;

    public CompaniesAccountsService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public static String generatePassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public boolean addUser(RepresentativeAccounts companyAccount) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // Check if the username already exists
            Query query = entityManager.createQuery("SELECT u FROM RepresentativeAccounts u WHERE u.username = :username");
            query.setParameter("username", companyAccount.getUsername());
            List<RepresentativeAccounts> companyAccounts = query.getResultList();
            if (!companyAccounts.isEmpty()) {
                return false;
            }
            String password = generatePassword();
            companyAccount.setPassword(password);
            // If the username doesn't exist, add the user to the database
            entityManager.persist(companyAccount);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public String login(String username ,String password){
        TypedQuery<RepresentativeAccounts> query = entityManager.createQuery("SELECT u FROM RepresentativeAccounts u WHERE u.username = :username AND u.password = :password", RepresentativeAccounts.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        System.out.println("======================= "+query.getSingleResult().getId());
        if (query.getResultList().size() == 1) {
            String token = Jwts.builder()
                    .setSubject(query.getSingleResult().getUsername())
                    .claim("id", query.getSingleResult().getId())
                    .claim("role", query.getSingleResult().getRole())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
            return token;
        } else {
            return "Unauthorized";
        }
    }

    public List<RepresentativeAccounts> getAllRepresentatives() {
        TypedQuery<RepresentativeAccounts> query = entityManager.createQuery("SELECT u FROM RepresentativeAccounts u", RepresentativeAccounts.class);
        return query.getResultList();
    }


    public List<RepresentativeAccounts> getAllSellingRepresentatives() {
        TypedQuery<RepresentativeAccounts> query = entityManager.createQuery("SELECT u FROM RepresentativeAccounts u WHERE u.role = :role", RepresentativeAccounts.class);
        query.setParameter("role", "selling_representative");
        return query.getResultList();
    }


}
