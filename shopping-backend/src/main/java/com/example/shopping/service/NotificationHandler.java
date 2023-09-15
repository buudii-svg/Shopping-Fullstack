package com.example.shopping.service;

import com.example.shopping.model.Notification;
import com.example.shopping.model.User;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.persistence.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import java.util.ArrayList;
import java.util.List;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "java:/jms/queue/messageQ"
                ),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"
                )
        },
        name = "java:/jms/queue/messageQ"
)
public class NotificationHandler implements MessageListener {

    private final EntityManagerFactory emf;

    private EntityManager entityManager;

    public NotificationHandler() {
        emf = Persistence.createEntityManagerFactory("mysql");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void onMessage(Message message) {
            try {
                String[] messageParts = message.getBody(String.class).split(",");
                int userId = Integer.parseInt(messageParts[0]);
                String shippingMessage = messageParts[1];
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
                query.setParameter("id", userId);
                User user = query.getSingleResult();
                Notification notification = new Notification();
                notification.setUser(user);
                notification.setMessage(shippingMessage);
                entityManager.persist(notification);
                transaction.commit();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

}
