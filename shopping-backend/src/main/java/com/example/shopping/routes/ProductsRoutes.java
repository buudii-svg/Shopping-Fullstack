package com.example.shopping.routes;

import com.example.shopping.service.ProductsService;
import com.example.shopping.model.Products;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsRoutes {

    private final ProductsService productsService = new ProductsService();

    @POST
    @Path("/{id}")
    public Response representativeAddProduct(Products product , @PathParam("id") int id) {
        try {
            productsService.representativeAddProduct(product , id);
            return Response.ok("Product added successfully!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public List<Products> getAllOfferedProducts(@PathParam("id") int id) {
        return productsService.getOfferedProducts(id);
    }

    @GET
    @Path("/sold/{id}")
    public List<Products> getAllSoldProducts(@PathParam("id") int id) {
        return productsService.getSoldProducts(id);
    }

    @GET
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }

    @GET
    @Path("/single/{id}")
    public Products getProduct(@PathParam("id") int id) {
        return productsService.getProduct(id);
    }

}
