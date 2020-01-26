package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    @POST
    @Path("/create")
    public Account createAccount(Account account) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/all")
    public List<Account> getAllAccounts() {
        return null;
    }

    @GET
    @Path("/{id}")
    public Account getAccountById(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/user/{id}")
    public List<Account> getAccountsByUserId(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/{id}/balance")
    public Double getAccountBalance(@PathParam("id") long id) {
        return null;
    }

    @PUT
    @Path("/{id}/withdraw/{amount}")
    public Response withdraw(@PathParam("id") long id, @PathParam("amount") long amount) {
        return null;
    }

    @PUT
    @Path("/{id}/deposit/{deposit}")
    public Response deposit(@PathParam("id") long id, @PathParam("deposit") long deposit) {
        return null;
    }

}
