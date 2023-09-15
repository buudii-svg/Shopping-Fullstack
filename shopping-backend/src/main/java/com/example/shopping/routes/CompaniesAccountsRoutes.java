package com.example.shopping.routes;

import com.example.shopping.model.RepresentativeAccounts;
import com.example.shopping.service.CompaniesAccountsService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/comp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompaniesAccountsRoutes {

    private final CompaniesAccountsService companiesAccountsService = new CompaniesAccountsService();


    @POST
    public Response Register(RepresentativeAccounts companyAccount) {
        if(companiesAccountsService.addUser(companyAccount)) {
            return Response.ok("Representative added successfully!").build();
        } else {
            return Response.ok("Representative already exists!").build();
        }
    }

    @POST
    @Path("/login")
    public String login(RepresentativeAccounts u) {
        return companiesAccountsService.login(u.getUsername(), u.getPassword());
    }

    @GET
    public List<RepresentativeAccounts> getAllCompaniesAccounts() {
        return companiesAccountsService.getAllRepresentatives();
    }

    @GET
    @Path("/selling")
    public List<RepresentativeAccounts> getAllSellingCompaniesAccounts() {
        return companiesAccountsService.getAllSellingRepresentatives();
    }

}
