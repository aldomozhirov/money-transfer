package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
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

    public Transaction performTransaction(Transaction transaction) {
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        Account source = accountRepository.get(transaction.getSourceAccountId());
        Account target = accountRepository.get(transaction.getTargetAccountId());
        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() - transaction.getAmount());
        return transactionRepository.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return repositoryFactory.getTransactionRepository().getAll();
    }

    public Transaction getTransactionById(long id) {
        return repositoryFactory.getTransactionRepository().get(id);
    }


}
