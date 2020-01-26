package com.aldomozhirov.moneytransfer.service;

import com.aldomozhirov.moneytransfer.dto.Account;
import com.aldomozhirov.moneytransfer.dto.Transaction;
import com.aldomozhirov.moneytransfer.dto.User;
import com.aldomozhirov.moneytransfer.repository.AccountRepository;
import com.aldomozhirov.moneytransfer.repository.AccountTransactionsRepository;
import com.aldomozhirov.moneytransfer.repository.TransactionRepository;
import com.aldomozhirov.moneytransfer.repository.impl.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance;

    private RepositoryFactory repositoryFactory;

    private UserService() {
        repositoryFactory = RepositoryFactory.getInstance();
    };

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User createUser(User user) {
        return repositoryFactory.getUserRepository().add(user);
    }

    public void deleteUser(Long userId) {
        repositoryFactory.getUserRepository().remove(userId);
    }

    public User getUser(Long userId) {
        return repositoryFactory.getUserRepository().get(userId);
    }

    public List<User> getAllUsers() {
        return repositoryFactory.getUserRepository().getAll();
    }

    public List<Account> getUserAccounts(Long userId) {
        AccountRepository accountRepository = repositoryFactory.getAccountRepository();
        return repositoryFactory.getUserAccountsRepository()
                .getAll(userId).stream()
                .map(accountRepository::get)
                .collect(Collectors.toList());
    }

    public List<Transaction> getUserTransactions(Long userId) {
        AccountTransactionsRepository accountTransactionsRepository = repositoryFactory.getAccountTransactionsRepository();
        TransactionRepository transactionRepository = repositoryFactory.getTransactionRepository();
        return repositoryFactory.getUserAccountsRepository()
                .getAll(userId).stream()
                .flatMap((accountId) -> accountTransactionsRepository
                        .getAll(accountId).stream()
                        .map(transactionRepository::get))
                .collect(Collectors.toList());
    }

}
