package com.example.shopping.service;

import com.example.shopping.model.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateful;
import jakarta.jms.*;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Response;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Stateful
public class UserService implements Auth<User> {
    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    private static final String SECRET_KEY = "mySecretKey123";

    @Resource(mappedName = "java:/jms/queue/messageQ")
    private Queue queue;

    public UserService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public boolean addUser(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // Check if the username already exists
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username");
            query.setParameter("username", user.getUsername());
            List<User> users = query.getResultList();
            if (!users.isEmpty()) {
                return false;
            }
            // If the username doesn't exist, add the user to the database
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public String login(String username ,String password){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        if (query.getResultList().size() == 1) {
            String token = Jwts.builder()
                    .setSubject(query.getSingleResult().getUsername())
                    .claim("id", query.getSingleResult().getId())
                    .claim("role", query.getSingleResult().getRole())
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
            return token;
        } else {
            return "Unauthorized";
        }
    }

    public List<User> getAllClients() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class);
        query.setParameter("role", "Client");
        return query.getResultList();
    }

    public boolean  addToCart(int id , int userId){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Products product = entityManager.find(Products.class, id);
            User user = entityManager.find(User.class, userId);
            product.setAtCart(true);
            product.setUser(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public List<Products> getCart(int userId){
        TypedQuery<Products> query = entityManager.createQuery("SELECT p FROM Products p WHERE p.user.id = :userId AND p.atCart = true", Products.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
//    In this implementation, we first define the maximum number of retries (maxRetries) and initialize a counter for the number of retries (numRetries) and a flag to indicate whether the transaction was successful (isSuccessful).
//    We then enter a loop that retries the transaction until it is successful or the maximum number of retries is reached.
//
//    Inside the loop, we wrap the transaction code in a try-catch block and handle any exceptions by rolling back the transaction and incrementing the numRetries counter.
//    If the maximum number of retries is reached, we re-throw the exception. Otherwise, we wait for 1 second before retrying the transaction.
//
//    Once the transaction is successful, we set the isSuccessful flag to true and return it.
    public boolean buy(int userId) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
        int maxRetries = 3;
        int numRetries = 0;
        boolean isSuccessful = false;

        while (numRetries < maxRetries && !isSuccessful) {
            try {
                transaction.begin();
                User user = entityManager.find(User.class, userId);
                List<Products> products = getCart(userId);
                Orders order = new Orders();
                order.setUser(user);
                order.setRegion(user.getRegion());
                order.setProducts(products);
                String region = order.getRegion();
                TypedQuery<ShippingCompanies> query = entityManager.createQuery("SELECT s FROM ShippingCompanies s WHERE s.geographicalCoverage LIKE :region", ShippingCompanies.class);
                query.setParameter("region", "%" + region + "%");
                List<ShippingCompanies> shippingCompanies = query.getResultList();
                if (shippingCompanies.size() == 0) {
                    transaction.rollback();
                    throw new Exception("No shipping companies available in this region");
                }else{
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate= formatter.format(date);
                    String finalDate = strDate.substring(0, 10);
                    ShippingCompanies shippingCompany = shippingCompanies.get(0);
                    order.setShippingCompany(shippingCompany);
                    order.setStatus("Processing");
                    order.setDate(finalDate);
                    for (Products product : products) {
                        product.setAtCart(false);
                        product.setSold(true);
                        product.setShippingCompanies(shippingCompany);
                    }
                    entityManager.persist(order);
                    try {
                        Context context = new InitialContext();
                        QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("java:/ConnectionFactory");
                        queue = (Queue) context.lookup("java:/jms/queue/messageQ");
                        Connection connection = factory.createConnection();
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        MessageProducer producer = session.createProducer(queue);
                        ObjectMessage message = session.createObjectMessage();
                        message.setObject(user.getId() + "," + "The order is processing by the shipping company : " + shippingCompany.getName());
                        producer.send(message);
                        session.close();
                        connection.close();
                        System.out.println("Message sent to the JMS Provider");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    transaction.commit();
                }



                isSuccessful = true;
            } catch (Exception e) {
                transaction.rollback();
                numRetries++;
                if (numRetries >= maxRetries) {
                    throw e;
                }
                try {
                    Thread.sleep(1000); // wait for 1 second before retrying
                } catch (InterruptedException ie) {
                    // do nothing
                }
            }
        }
        return isSuccessful;
    }

    public boolean removeFromCart(int id){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Products product = entityManager.find(Products.class, id);
            product.setAtCart(false);
            product.setUser(null);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Notification getNotification(int userId) {
        TypedQuery<Notification> query = entityManager.createQuery("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.id DESC", Notification.class);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
