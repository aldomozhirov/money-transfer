package com.aldomozhirov.moneytransfer.controller;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.IncorrectInputDataException;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.NotEnoughMoneyException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.service.TransactionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    @POST
    @Path("/perform")
    public Transaction perform(Transaction transaction) throws RepositoryException, NoSuchIdException, NotEnoughMoneyException, IncorrectInputDataException {
        return TransactionService.getInstance().performTransaction(transaction);
    }

    @PUT
    @Path("/revert/{id}")
    public Transaction revert(@PathParam("id") Long id) throws RepositoryException, NoSuchIdException, NotEnoughMoneyException {
        return TransactionService.getInstance().revertTransaction(id);
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
    public List<Transaction> getAllTransactionsByAccountId(@PathParam("id") Long accountId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getTransactionsByAccount(accountId);
    }

    @GET
    @Path("/account/{id}/income")
    public List<Transaction> getIncomeTransactionsByAccountId(@PathParam("id") Long accountId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getIncomeTransactionsByAccount(accountId);
    }

    @GET
    @Path("/account/{id}/outcome")
    public List<Transaction> getOutcomeTransactionsByAccountId(@PathParam("id") Long accountId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getOutcomeTransactionsByAccount(accountId);
    }

    @GET
    @Path("/user/{id}")
    public List<Transaction> getAllTransactionsByUserId(@PathParam("id") Long userId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getTransactionsByUser(userId);
    }

    @GET
    @Path("/user/{id}/income")
    public List<Transaction> getIncomeTransactionsByUserId(@PathParam("id") Long userId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getIncomeTransactionsByUser(userId);
    }

    @GET
    @Path("/user/{id}/outcome")
    public List<Transaction> getOutcomeTransactionsByUserId(@PathParam("id") Long userId) throws NoSuchIdException, RepositoryException {
        return TransactionService.getInstance().getOutcomeTransactionsByUser(userId);
    }

}
