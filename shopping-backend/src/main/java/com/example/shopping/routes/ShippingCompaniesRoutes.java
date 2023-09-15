package com.example.shopping.routes;

import com.example.shopping.model.SellingCompanies;
import com.example.shopping.model.ShippingCompanies;
import com.example.shopping.service.SellingCompaniesService;
import com.example.shopping.service.ShippingCompaniesService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/shipping/company")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShippingCompaniesRoutes {
    ShippingCompaniesService companiesService = new ShippingCompaniesService();

    @POST
    public Response addCompany(ShippingCompanies com){
        if(companiesService.addCompany(com)){
            return Response.ok("Company added successfully !").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public ArrayList<ShippingCompanies> getAllCompanies(){
        return companiesService.getAllCompanies();
    }
}
