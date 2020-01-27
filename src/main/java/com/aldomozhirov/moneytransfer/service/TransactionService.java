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
        if (source == null || target == null) {
            throw new NoSuchIdException();
        }
        if (source.getBalance() - transaction.getAmount() < 0) {
            throw new NotEnoughMoneyException();
        }
        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() - transaction.getAmount());
        return transactionRepository.add(transaction);
    }

    public List<Transaction> getAllTransactions() throws RepositoryException {
        return repositoryFactory.getTransactionRepository().getAll();
    }

    public Transaction getTransactionById(long id) throws NoSuchIdException, RepositoryException {
        Transaction transaction = repositoryFactory.getTransactionRepository().get(id);
        if (transaction == null) {
            throw new NoSuchIdException();
        }
        return transaction;
    }


}
