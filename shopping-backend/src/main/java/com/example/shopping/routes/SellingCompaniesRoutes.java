package com.example.shopping.routes;

import com.example.shopping.model.SellingCompanies;
import com.example.shopping.service.SellingCompaniesService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/selling/company")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SellingCompaniesRoutes {
    SellingCompaniesService companiesService = new SellingCompaniesService();

    @POST
    public Response addCompany(SellingCompanies com){
        if(companiesService.addCompany(com)){
            return Response.ok("Company added successfully !").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public ArrayList<SellingCompanies> getAllCompanies(){
        return companiesService.getAllCompanies();
    }
}
