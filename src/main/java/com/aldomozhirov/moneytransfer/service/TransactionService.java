package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.entities.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {

    @POST
    public Transaction transferMoney(Transaction transaction) {
        return null;
    }

    @GET
    @Path("/all")
    public List<Transaction> getAllTransactions() {
        return null;
    }

    @GET
    @Path("/{id}")
    public Transaction getTransactionById(@PathParam("id") long id) {
        return null;
    }

    @GET
    @Path("/user/{id}")
    public List<Transaction> getTransactionsByUserId(@PathParam("id") long userId) {
        return null;
    }

    @GET
    @Path("/account/{id}")
    public List<Transaction> getTransactionsByAccountId(@PathParam("id") long accountId) {
        return null;
    }

}
