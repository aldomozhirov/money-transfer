package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.service.AccountService;
import com.aldomozhirov.moneytransfer.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    @POST
    @Path("/create")
    public Account createAccount(Account account) throws NoSuchIdException, RepositoryException {
        return AccountService.getInstance().createAccount(account);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        AccountService.getInstance().deleteAccount(id);
        return null;
    }

    @GET
    @Path("/all")
    public List<Account> getAllAccounts() throws RepositoryException {
        return AccountService.getInstance().getAllAccounts();
    }

    @GET
    @Path("/{id}")
    public Account getAccountById(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        return AccountService.getInstance().getAccountById(id);
    }

    @GET
    @Path("/user/{id}")
    public List<Account> getAccountsByUserId(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        return UserService.getInstance().getUserAccounts(id);
    }

    @GET
    @Path("/{id}/balance")
    public Double getAccountBalance(@PathParam("id") long id) throws NoSuchIdException, RepositoryException {
        return AccountService.getInstance().getAccountBalance(id);
    }

    @PUT
    @Path("/{id}/withdraw/{amount}")
    public Response withdraw(@PathParam("id") long id, @PathParam("amount") long amount) {
        //TODO
        return null;
    }

    @PUT
    @Path("/{id}/deposit/{deposit}")
    public Response deposit(@PathParam("id") long id, @PathParam("deposit") long deposit) {
        //TODO
        return null;
    }

}
