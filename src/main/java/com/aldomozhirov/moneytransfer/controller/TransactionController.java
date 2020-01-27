package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.NotEnoughMoneyException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.service.AccountService;
import com.aldomozhirov.moneytransfer.service.TransactionService;
import com.aldomozhirov.moneytransfer.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    @POST
    public Transaction transferMoney(Transaction transaction) throws RepositoryException, NoSuchIdException, NotEnoughMoneyException {
        return TransactionService.getInstance().performTransaction(transaction);
    }

    @GET
    @Path("/all")
    public List<Transaction> getAllTransactions() throws RepositoryException {
        return TransactionService.getInstance().getAllTransactions();
    }

    @GET
    @Path("/{id}")
    public Transaction getTransactionById(@PathParam("id") Long id) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getTransactionById(id);
    }

    @GET
    @Path("/account/{id}")
    public List<Transaction> getTransactionsByAccountId(@PathParam("id") Long accountId) throws NoSuchIdException, RepositoryException {
        return AccountService.getInstance().getAccountTransactions(accountId);
    }

    @GET
    @Path("/user/{id}")
    public List<Transaction> getTransactionsByUserId(@PathParam("id") Long userId) throws NoSuchIdException, RepositoryException {
        return UserService.getInstance().getUserTransactions(userId);
    }

}
