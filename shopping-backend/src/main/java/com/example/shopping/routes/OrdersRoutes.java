package com.example.shopping.routes;

import com.example.shopping.model.Orders;
import com.example.shopping.service.OrdersService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersRoutes {
    private final OrdersService ordersService = new OrdersService();

    @GET
    public List<Orders> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GET
    @Path("/{id}")
    public List<Orders> getAllOrdersWithStates(@PathParam("id") int id) {
        return ordersService.getAllOrdersWithStates(id);
    }

}
