package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.exception.NoSuchIdException;
import com.aldomozhirov.moneytransfer.exception.NotEnoughMoneyException;
import com.aldomozhirov.moneytransfer.exception.RepositoryException;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;
import com.aldomozhirov.moneytransfer.repository.impl.RepositoryFactory;

import java.util.List;

public class TransactionService {

    private static TransactionService instance;

    private RepositoryFactory repositoryFactory;

    private TransactionService() {
        repositoryFactory = RepositoryFactory.getInstance();
    };

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public Transaction performTransaction(Transaction transaction) throws NoSuchIdException, NotEnoughMoneyException, RepositoryException {
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        Account source = accountRepository.get(transaction.getSourceAccountId());
        Account target = accountRepository.get(transaction.getTargetAccountId());
        if (source == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find source account with id=%d specified in transaction",
                    transaction.getSourceAccountId()));
        }
        if (target == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find target account with id=%d specified in transaction",
                    transaction.getTargetAccountId()));
        }
        if (source.getBalance() - transaction.getAmount() < 0) {
            throw new NotEnoughMoneyException(String.format(
                    "Not enough money to perform the transaction on the source account with id=%d",
                    transaction.getSourceAccountId())
            );
        }
        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() - transaction.getAmount());
        return transactionRepository.add(transaction);
    }

    public List<Transaction> getAllTransactions() throws RepositoryException {
        return repositoryFactory.getTransactionRepository().getAll();
    }

    public Transaction getTransactionById(long transactionId) throws NoSuchIdException, RepositoryException {
        Transaction transaction = repositoryFactory.getTransactionRepository().get(transactionId);
        if (transaction == null) {
            throw new NoSuchIdException(String.format(
                    "Cannot find transaction with id=%d",
                    transactionId));
        }
        return transaction;
    }


}
