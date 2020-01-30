package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.*;
import com.aldomozhirov.moneytransfer.factory.RepositoryFactory;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TransactionServiceTest extends AbstractServiceTest {

    private TransactionService transactionService = TransactionService.getInstance();
    private TransactionRepository transactionRepository = RepositoryFactory.getInstance().getTransactionRepository();
    private AccountRepository accountRepository = RepositoryFactory.getInstance().getAccountRepository();

    @Test
    public void testPerformTransaction() throws RepositoryException, NotEnoughMoneyException, NoSuchIdException, IncorrectInputDataException, IncompatibleCurrenciesException {

        Long sourceAccountId = 4L;
        Long targetAccountId = 5L;
        Double amount = 10.0;
        Transaction transactionToPerform = new Transaction(sourceAccountId, targetAccountId, amount);
        Double expectedSourceAccountBalanceAfterTransaction = accountRepository.getById(sourceAccountId).getBalance() - amount;
        Double expectedTargetAccountBalanceAfterTransaction = accountRepository.getById(targetAccountId).getBalance() + amount;

        Transaction performedTransaction = transactionService.performTransaction(transactionToPerform);
        Assert.assertEquals(sourceAccountId, performedTransaction.getSourceAccountId());
        Assert.assertEquals(targetAccountId, performedTransaction.getTargetAccountId());
        Assert.assertEquals(amount, performedTransaction.getAmount());

        Transaction transactionInRepository = transactionRepository.getById(performedTransaction.getId());
        assertTransactionEquals(performedTransaction, transactionInRepository);

        Double actualSourceAccountBalanceAfterTransaction = accountRepository.getById(sourceAccountId).getBalance();
        Double actualTargetAccountBalanceAfterTransaction = accountRepository.getById(targetAccountId).getBalance();
        Assert.assertEquals(expectedSourceAccountBalanceAfterTransaction, actualSourceAccountBalanceAfterTransaction);
        Assert.assertEquals(expectedTargetAccountBalanceAfterTransaction, actualTargetAccountBalanceAfterTransaction);

    }

    @Test
    public void testRevertTransaction() throws RepositoryException, NoSuchIdException, NotEnoughMoneyException {

        Long transactionToRevertId = 6L;
        Transaction expectedTransaction = SAMPLE_TRANSACTIONS[5];
        Long expectedSourceAccountId = expectedTransaction.getSourceAccountId();
        Long expectedTargetAccountId = expectedTransaction.getTargetAccountId();
        Double expectedAmount = expectedTransaction.getAmount();
        Double expectedSourceAccountBalanceAfterRevert = accountRepository.getById(expectedSourceAccountId).getBalance() + expectedAmount;
        Double expectedTargetAccountBalanceAfterRevert = accountRepository.getById(expectedTargetAccountId).getBalance() - expectedAmount;

        Transaction revertedTransaction = transactionService.revertTransaction(transactionToRevertId);
        assertTransactionEquals(expectedTransaction, revertedTransaction);

        Double actualSourceAccountBalanceAfterRevert = accountRepository.getById(expectedSourceAccountId).getBalance();
        Double actualTargetAccountBalanceAfterRevert = accountRepository.getById(expectedTargetAccountId).getBalance();
        Assert.assertEquals(expectedSourceAccountBalanceAfterRevert, actualSourceAccountBalanceAfterRevert);
        Assert.assertEquals(expectedTargetAccountBalanceAfterRevert, actualTargetAccountBalanceAfterRevert);

    }

    @Test
    public void getAllTransactions() throws RepositoryException {
        List<Transaction> receivedTransactions = transactionService.getAllTransactions();
        List<Transaction> transactionsListInRepository = transactionRepository.getAll();
        assertTransactionsEquals(transactionsListInRepository, receivedTransactions);
    }

}
