package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.*;
import org.junit.Test;

import java.util.List;

public class TransactionServiceTest extends AbstractServiceTest {

    TransactionService transactionService = TransactionService.getInstance();

    @Test
    public void testPerformTransaction() throws RepositoryException, NotEnoughMoneyException, NoSuchIdException, IncorrectInputDataException, IncompatibleCurrenciesException {
        Transaction transaction = new Transaction();
        transactionService.performTransaction(transaction);
    }

    @Test
    public void testRevertTransaction() throws RepositoryException, NoSuchIdException, NotEnoughMoneyException {
        Transaction revertedTransaction = transactionService.revertTransaction(4L);
    }

    @Test
    public void testGetTransactionById() throws NoSuchIdException, RepositoryException {
        Transaction transaction = transactionService.getTransactionById(1L);
    }

    @Test
    public void testGetTransactionsByAccount() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getTransactionsByAccount(1L);
    }

    @Test
    public void testGetOutcomeTransactionsByAccount() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getOutcomeTransactionsByAccount(1L);
    }

    @Test
    public void testGetIncomeTransactionsByAccount() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getIncomeTransactionsByAccount(1L);
    }

    @Test
    public void testGetTransactionsByUser() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getTransactionsByUser(1L);
    }

    @Test
    public void testGetOutcomeTransactionsByUser() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getOutcomeTransactionsByUser(1L);
    }

    @Test
    public void testGetIncomeTransactionsByUser() throws NoSuchIdException, RepositoryException {
        List<Transaction> transactions = transactionService.getIncomeTransactionsByUser(1L);
    }

    @Test
    public void testGetAllTransactions() throws RepositoryException {
        List<Transaction> transactions = transactionService.getAllTransactions();
    }

}
