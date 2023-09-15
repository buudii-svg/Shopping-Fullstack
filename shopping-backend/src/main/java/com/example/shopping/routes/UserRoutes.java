package com.example.shopping.routes;

import com.example.shopping.model.Notification;
import com.example.shopping.model.Products;
import com.example.shopping.model.User;
import com.example.shopping.service.OrdersService;
import com.example.shopping.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.List;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRoutes {
    private final UserService userService = new UserService();

    @POST
    public Response Register(User user) {
        if(userService.addUser(user)) {
            return Response.ok("User added successfully!").build();
        } else {
            return Response.ok("User already exists!").build();        }
    }

    @POST
    @Path("/login")
    public String login(User u) {
      return userService.login(u.getUsername(), u.getPassword());
    }

    @GET
    @Path("/clients")
    public List<User> getAllClients() {
        return userService.getAllClients();
    }

    @GET
    @Path("/cart/products/{id}")
    public List<Products> getAllOrders(@PathParam("id") int id) {
        return userService.getCart(id);
    }

    @POST
    @Path("/cart/checkout/{id}")
    public Response checkout(@PathParam("id") int id) {
        try {
            userService.buy(id);
            return Response.ok("Checkout successful!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/cart/{id}/{userId}")
    public Response addToCart(@PathParam("id") int id , @PathParam("userId") int userId) {
        try {
            userService.addToCart(id, userId);
            return Response.ok("Product added to cart successfully!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/cart/remove/{id}")
    public Response removeFromCart(@PathParam("id") int id) {
        try {
            userService.removeFromCart(id);
            return Response.ok("Product removed from cart successfully!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/notification/{id}")
    public Notification getNotification(@PathParam("id") int id) {
        return userService.getNotification(id);
    }

}
