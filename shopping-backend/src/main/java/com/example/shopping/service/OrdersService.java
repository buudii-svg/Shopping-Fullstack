package com.example.shopping.service;

import com.example.shopping.model.*;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateful
public class OrdersService {
    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    public OrdersService() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    public List<Orders> getAllOrders() {
        TypedQuery<Orders> query = entityManager.createQuery("SELECT o FROM Orders o", Orders.class);
        return query.getResultList();
    }

    public List<Orders> getAllOrdersWithStates(int userId) {
        TypedQuery<Orders> query = entityManager.createQuery("SELECT o FROM Orders o WHERE o.user.id = :userId", Orders.class);
        query.setParameter("userId", userId);
        List<Orders> result = new ArrayList<>();

        for (Orders order : query.getResultList()) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            String finalDateStr = strDate.substring(0, 10);
            String orderDateStr = order.getDate().substring(0, 10);

            try {
                // Parse the date strings into Date objects
                Date finalDate = formatter.parse(finalDateStr + " 00:00:00");
                Date orderDate = formatter.parse(orderDateStr + " 00:00:00");

                // Subtract two days from finalDate
                Calendar cal = Calendar.getInstance();
                cal.setTime(finalDate);
                cal.add(Calendar.DAY_OF_MONTH, -3);
                Date threeDaysBeforeFinalDate = cal.getTime();

                // Compare orderDate with threeDaysBeforeFinalDate
                if (orderDate.after(threeDaysBeforeFinalDate)) {
                    order.setStatusOfTheOrder("Current");
                }else{
                    order.setStatusOfTheOrder("Previous");
                }
                result.add(order);
            } catch (ParseException e) {
                // Handle parse exception
                e.printStackTrace();
            }
        }

        return result;
    }

}
